package numble.bankingserver.domain.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountVerifyRequest {

    private Long hostAccountNumber;
    private Long friendAccountNumber;
    private Long sentAmount;

}
