package numble.bankingserver.domain.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountCloseRequest {

    private Long accountNumber;

}
