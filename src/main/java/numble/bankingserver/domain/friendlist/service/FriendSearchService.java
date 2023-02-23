package numble.bankingserver.domain.friendlist.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.friendlist.dto.response.SearchFriendListResponse;
import numble.bankingserver.domain.friendlist.dto.SearchFriendDto;
import numble.bankingserver.domain.friendlist.repository.FriendListRepository;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.global.jwt.JwtTokenCheckService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FriendSearchService {

    private final FriendListRepository friendListRepository;
    private final JwtTokenCheckService jwtTokenCheckService;

    @Transactional(readOnly = true)
    public SearchFriendListResponse searchFriend(User hostUser) {

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "friendUser.name"));
        Page<User> pageFriends = friendListRepository.findFriendUser(hostUser, pageRequest);
        List<User> friends = pageFriends.getContent();
        List<SearchFriendDto> friendList = friends.stream()
                .map(user -> new SearchFriendDto(user.getName(), user.getPhoneNumber()))
                .collect(Collectors.toList());

        return new SearchFriendListResponse(pageFriends.getTotalElements(), friendList);

    }
}
