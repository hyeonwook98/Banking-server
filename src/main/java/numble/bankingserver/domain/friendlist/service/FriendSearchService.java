package numble.bankingserver.domain.friendlist.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.friendlist.dto.response.SearchFriendListResponse;
import numble.bankingserver.domain.friendlist.dto.SearchFriendDto;
import numble.bankingserver.domain.friendlist.repository.FriendListRepository;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.global.error.ErrorCode;
import numble.bankingserver.global.exception.BankingException;
import numble.bankingserver.global.jwt.JwtTokenProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FriendSearchService {

    private final UserRepository userRepository;
    private final FriendListRepository friendListRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public SearchFriendListResponse searchFriend(HttpServletRequest httpServletRequest) {

        String bearerToken = jwtTokenProvider.resolveToken(httpServletRequest);
        String token = jwtTokenProvider.parseToken(bearerToken);

        if (token == null) {
            throw new BankingException(ErrorCode.INVALID_JWT);
        }

        String findId = jwtTokenProvider.getTokenSubject(token);
        User hostUser = userRepository.findById(findId)
                .orElseThrow(() -> new BankingException(ErrorCode.USER_NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "friendUser.name"));
        Page<User> pageFriends = friendListRepository.findFriendUser(hostUser, pageRequest);
        List<User> friends = pageFriends.getContent();
        List<SearchFriendDto> friendList = friends.stream()
                .map(user -> new SearchFriendDto(user.getName(), user.getPhoneNumber()))
                .collect(Collectors.toList());

        return new SearchFriendListResponse(pageFriends.getTotalElements(), friendList);

    }
}
