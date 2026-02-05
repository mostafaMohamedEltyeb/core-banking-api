package com.bank.corebanking.dto.resposne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteTransferResponse {
    private ResponseHeader responseHeader;
    private ResponseBody responseBody;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseHeader {
        private String messageId;
        private String correlationId;
        private String statusCode;
        private String statusMessage;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseBody {
        private String transactionId;
        private String fromAccountNumber;
        private String toAccountNumber;
        private Double transferredAmount;
        private String currency;
        private LocalDateTime transactionDate;
        private Double newBalance;
    }
}
