package numble.bankingserver.domain.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import numble.bankingserver.global.enums.AccountType;

@Getter
@AllArgsConstructor
public class AccountSearchDto {
    private Long accountNumber;
    private Long balance;
    private AccountType accountType;
}
