package com.bank.corebanking.controller;

import com.bank.corebanking.dto.request.BalanceInquiryRequest;
import com.bank.corebanking.dto.request.ExecuteTransferRequest;
import com.bank.corebanking.dto.resposne.BalanceInquiryResponse;
import com.bank.corebanking.dto.resposne.ExecuteTransferResponse;
import com.bank.corebanking.service.CoreBankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/esb/api")
@RequiredArgsConstructor
public class CoreBankingController {

    private final CoreBankingService coreBankingService;

    @PostMapping("/balance-inquiry")
    public ResponseEntity<BalanceInquiryResponse> balanceInquiry(@RequestBody BalanceInquiryRequest request) {

        log.info("📥 Received Balance Inquiry Request");

        BalanceInquiryResponse response = coreBankingService.getBalance(request);

        log.info("📤 Sending Balance Inquiry Response");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/execute-transfer")
    public ResponseEntity<ExecuteTransferResponse> executeTransfer(@RequestBody ExecuteTransferRequest request) {

        log.info("📥 Received Execute Transfer Request");

        ExecuteTransferResponse response = coreBankingService.executeTransfer(request);

        log.info("📤 Sending Execute Transfer Response");

        return ResponseEntity.ok(response);
    }
}
