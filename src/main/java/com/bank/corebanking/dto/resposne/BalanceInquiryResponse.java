package com.bank.corebanking.dto.resposne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceInquiryResponse {
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
        private String accountNumber;
        private String accountType;
        private Double availableBalance;
        private String currency;
        private String customerName;
    }
}
