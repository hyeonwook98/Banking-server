package numble.bankingserver.domain.accountnumber.dto.request;

import lombok.Getter;
import numble.bankingserver.domain.enums.AccountType;

@Getter
public class AccountOpenRequest {
    private AccountType accountType;
}
