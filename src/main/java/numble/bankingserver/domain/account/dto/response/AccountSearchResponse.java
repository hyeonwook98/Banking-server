package numble.bankingserver.domain.account.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountSearchResponse<T> {
    private Long count;
    private T data;
}
