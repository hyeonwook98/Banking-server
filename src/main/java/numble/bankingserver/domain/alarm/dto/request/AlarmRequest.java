package numble.bankingserver.domain.alarm.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.bankingserver.global.enums.TransferType;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmRequest {
    private TransferType transferType;
    private Long amount;
    private Long hostAccountNumber;
    private String friendName;
}
