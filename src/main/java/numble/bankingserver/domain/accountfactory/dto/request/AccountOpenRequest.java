package numble.bankingserver.domain.accountfactory.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.bankingserver.global.enums.AccountType;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AccountOpenRequest {
    private AccountType accountType;
}
