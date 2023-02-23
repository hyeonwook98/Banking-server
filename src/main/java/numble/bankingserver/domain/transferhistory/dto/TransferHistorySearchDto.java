package numble.bankingserver.domain.transferhistory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import numble.bankingserver.global.enums.TransferType;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class TransferHistorySearchDto {
    private Long friendAccountNumber;
    private String friendName;
    private TransferType transferType;
    private Long amount;
    private Long balance;
    private LocalDateTime transferAt;
}
