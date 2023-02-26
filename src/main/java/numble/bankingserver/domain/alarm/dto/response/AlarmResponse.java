package numble.bankingserver.domain.alarm.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.bankingserver.global.enums.TransferType;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmResponse {
    private TransferType transferType;
    private Long amount;
    private String message;
}
