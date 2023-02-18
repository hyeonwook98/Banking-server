package numble.bankingserver.domain.friendlist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SearchFriendDto<T> {
    private String name;
    private String phoneNumber;
}
