package numble.bankingserver.domain.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountDepositRequest {
    private Long accountNumber;
    private Long depositAmount;
}
