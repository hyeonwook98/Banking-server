package numble.bankingserver.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginRequest {

    private String id;
    private String password;

    @Builder
    public UserLoginRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
