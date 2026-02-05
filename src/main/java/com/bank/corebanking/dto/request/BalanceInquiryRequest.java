package com.bank.corebanking.dto.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BalanceInquiryRequest {
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    @Data
    public static class RequestHeader {
        private String messageId;
        private LocalDateTime timestamp;
        private String sourceSystem;
        private String targetSystem;
        private String correlationId;
    }

    @Data
    public static class RequestBody {
        private String transactionType;
        private AccountDetails accountDetails;
    }

    @Data
    public static class AccountDetails {
        private String accountNumber;
        private String customerId;
    }
}
