package com.bank.corebanking.service;

import com.bank.corebanking.dto.request.BalanceInquiryRequest;
import com.bank.corebanking.dto.request.ExecuteTransferRequest;
import com.bank.corebanking.dto.resposne.BalanceInquiryResponse;
import com.bank.corebanking.dto.resposne.ExecuteTransferResponse;
import com.bank.corebanking.entity.Account;
import com.bank.corebanking.entity.Transaction;
import com.bank.corebanking.repository.AccountRepository;
import com.bank.corebanking.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Log4j2
public class CoreBankingService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public BalanceInquiryResponse getBalance(BalanceInquiryRequest request) {

        String accountNumber = request.getRequestBody().getAccountDetails().getAccountNumber();

        log.info("🔍 ESB API #1 - Balance Inquiry for Account: {}", accountNumber);

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));

        log.info("✅ Balance Found: {} SAR", account.getBalance());

        return BalanceInquiryResponse.builder()
                .responseHeader(BalanceInquiryResponse.ResponseHeader.builder()
                        .messageId(request.getRequestHeader().getMessageId())
                        .correlationId(request.getRequestHeader().getCorrelationId())
                        .statusCode("SUCCESS")
                        .statusMessage("Balance inquiry successful")
                        .build())
                .responseBody(BalanceInquiryResponse.ResponseBody.builder()
                        .accountNumber(account.getAccountNumber())
                        .accountType(account.getAccountType())
                        .availableBalance(account.getBalance())
                        .currency(account.getCurrency())
                        .customerName(account.getCustomerName())
                        .build())
                .build();
    }

    @Transactional
    public ExecuteTransferResponse executeTransfer(ExecuteTransferRequest request) {

        String fromAccountNumber = request.getRequestBody().getTransferDetails().getFromAccount().getAccountNumber();
        String toAccountNumber = request.getRequestBody().getTransferDetails().getToAccount().getAccountNumber();
        Double amount = request.getRequestBody().getTransferDetails().getAmount().getValue();

        log.info("💸 ESB API #2 - Transfer Request: {} SAR from {} to {}", amount, fromAccountNumber, toAccountNumber);

        // Get sender account
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new RuntimeException("Sender account not found: " + fromAccountNumber));

        // Get beneficiary account
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new RuntimeException("Beneficiary account not found: " + toAccountNumber));

        // Check balance
        if (fromAccount.getBalance() < amount) {
            throw new RuntimeException(String.format(
                    "Insufficient balance. Available: %.2f SAR, Required: %.2f SAR",
                    fromAccount.getBalance(), amount
            ));
        }

        // Deduct from sender
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        fromAccount.setUpdatedAt(LocalDateTime.now());

        // Add to beneficiary
        toAccount.setBalance(toAccount.getBalance() + amount);
        toAccount.setUpdatedAt(LocalDateTime.now());

        // Save accounts
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        log.info("💰 Sender New Balance: {} SAR", fromAccount.getBalance());
        log.info("💰 Beneficiary New Balance: {} SAR", toAccount.getBalance());

        // Create transaction record
        String transactionId = "TXN-" + UUID.randomUUID().toString();
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.setFromAccountNumber(fromAccountNumber);
        transaction.setToAccountNumber(toAccountNumber);
        transaction.setAmount(amount);
        transaction.setCurrency("SAR");
        transaction.setStatus("SUCCESS");
        transaction.setRemarks(request.getRequestBody().getTransferDetails().getRemarks());
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);

        log.info("✅ Transfer Successful - Transaction ID: {}", transactionId);

        return ExecuteTransferResponse.builder()
                .responseHeader(ExecuteTransferResponse.ResponseHeader.builder()
                        .messageId(request.getRequestHeader().getMessageId())
                        .correlationId(request.getRequestHeader().getCorrelationId())
                        .statusCode("SUCCESS")
                        .statusMessage("Transfer completed successfully")
                        .build())
                .responseBody(ExecuteTransferResponse.ResponseBody.builder()
                        .transactionId(transactionId)
                        .fromAccountNumber(fromAccountNumber)
                        .toAccountNumber(toAccountNumber)
                        .transferredAmount(amount)
                        .currency("SAR")
                        .transactionDate(LocalDateTime.now())
                        .newBalance(fromAccount.getBalance())
                        .build())
                .build();
    }
}
