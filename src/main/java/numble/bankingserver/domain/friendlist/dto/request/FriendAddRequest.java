package numble.bankingserver.domain.friendlist.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FriendAddRequest {
    private String phoneNumber;
}
