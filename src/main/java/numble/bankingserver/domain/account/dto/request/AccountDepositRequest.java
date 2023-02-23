package numble.bankingserver.domain.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDepositRequest {
    private Long accountNumber;
    private Long depositAmount;
}
