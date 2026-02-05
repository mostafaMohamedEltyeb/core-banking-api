package com.bank.corebanking.service;

import com.bank.corebanking.dto.request.BalanceInquiryRequest;
import com.bank.corebanking.dto.resposne.BalanceInquiryResponse;
import com.bank.corebanking.entity.Account;
import com.bank.corebanking.repository.AccountRepository;
import com.bank.corebanking.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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
}
