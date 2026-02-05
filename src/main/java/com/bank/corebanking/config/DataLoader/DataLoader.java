package com.bank.corebanking.config.DataLoader;

import com.bank.corebanking.entity.Account;
import com.bank.corebanking.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final AccountRepository accountRepository;

    @Override
    public void run(String... args) {

        log.info("🔧 Creating test accounts...");

        // Account 1 - Mostafa Ahmed
        Account account1 = new Account();
        account1.setAccountNumber("1111111111");
        account1.setCustomerId("CUST-001");
        account1.setCustomerName("Mostafa Ahmed");
        account1.setAccountType("SAVINGS");
        account1.setBalance(10000.00);
        account1.setCurrency("SAR");
        account1.setStatus("ACTIVE");
        account1.setCreatedAt(LocalDateTime.now());

        // Account 2 - Ali Hassan
        Account account2 = new Account();
        account2.setAccountNumber("2222222222");
        account2.setCustomerId("CUST-002");
        account2.setCustomerName("Ali Hassan");
        account2.setAccountType("CURRENT");
        account2.setBalance(5000.00);
        account2.setCurrency("SAR");
        account2.setStatus("ACTIVE");
        account2.setCreatedAt(LocalDateTime.now());

        accountRepository.save(account1);
        accountRepository.save(account2);

        log.info("✅ Test accounts created successfully:");
        log.info("   📋 Account 1: {} - {} - Balance: {} SAR",
                account1.getAccountNumber(), account1.getCustomerName(), account1.getBalance());
        log.info("   📋 Account 2: {} - {} - Balance: {} SAR",
                account2.getAccountNumber(), account2.getCustomerName(), account2.getBalance());
    }
}
