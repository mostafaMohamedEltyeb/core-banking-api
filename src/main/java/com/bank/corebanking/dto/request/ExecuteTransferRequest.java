package com.bank.corebanking.dto.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ExecuteTransferRequest {
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    @Data
    public static class RequestHeader {
        private String messageId;
        private LocalDateTime timestamp;
        private String sourceSystem;
        private String targetSystem;
        private String correlationId;
        private String idempotencyKey;
    }

    @Data
    public static class RequestBody {
        private String transactionType;
        private TransferDetails transferDetails;
    }

    @Data
    public static class TransferDetails {
        private AccountInfo fromAccount;
        private AccountInfo toAccount;
        private Amount amount;
        private String transferType;
        private String remarks;
    }

    @Data
    public static class AccountInfo {
        private String accountNumber;
        private String customerId;
    }

    @Data
    public static class Amount {
        private Double value;
        private String currency;
    }
}