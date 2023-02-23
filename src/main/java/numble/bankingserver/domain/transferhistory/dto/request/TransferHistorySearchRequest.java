package numble.bankingserver.domain.transferhistory.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.bankingserver.global.enums.TransferType;

@Getter
@NoArgsConstructor
public class TransferHistorySearchRequest {
    private Long accountNumber;
    private TransferType transferType;
}
