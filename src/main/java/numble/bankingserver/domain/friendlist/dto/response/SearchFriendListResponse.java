package numble.bankingserver.domain.friendlist.dto.response;

import lombok.Getter;

@Getter
public class SearchFriendListResponse<T> {

    private Long count;
    private T data;

    public SearchFriendListResponse(Long count, T data) {
        this.count = count;
        this.data = data;
    }

}
