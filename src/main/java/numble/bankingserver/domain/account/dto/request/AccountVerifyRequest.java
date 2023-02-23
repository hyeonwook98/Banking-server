package numble.bankingserver.domain.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountVerifyRequest {

    private Long hostAccountNumber;
    private Long friendAccountNumber;
    private Long sentAmount;

}
