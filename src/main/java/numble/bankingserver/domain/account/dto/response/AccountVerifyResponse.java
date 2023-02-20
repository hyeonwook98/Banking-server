package numble.bankingserver.domain.account.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountVerifyResponse {

    private String accountHolder;
    private Long accountNumber;
    private Long sentAmount;

    @Builder
    public AccountVerifyResponse(String accountHolder, Long accountNumber, Long sentAmount) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.sentAmount = sentAmount;
    }
}
