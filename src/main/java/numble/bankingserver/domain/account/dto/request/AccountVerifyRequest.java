package numble.bankingserver.domain.account.dto.request;

import lombok.Getter;

@Getter
public class AccountVerifyRequest {

    private Long hostAccountNumber;
    private Long friendAccountNumber;
    private Long sentAmount;

}
