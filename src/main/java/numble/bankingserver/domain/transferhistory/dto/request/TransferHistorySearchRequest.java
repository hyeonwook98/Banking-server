package numble.bankingserver.domain.transferhistory.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.bankingserver.global.enums.TransferType;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransferHistorySearchRequest {
    private Long accountNumber;
    private TransferType transferType;
}
