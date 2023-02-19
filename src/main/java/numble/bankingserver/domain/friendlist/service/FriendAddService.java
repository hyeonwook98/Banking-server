package numble.bankingserver.domain.friendlist.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.friendlist.dto.request.FriendAddRequest;
import numble.bankingserver.domain.friendlist.entity.FriendList;
import numble.bankingserver.domain.friendlist.repository.FriendListRepository;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.global.dto.response.SuccessResponse;
import numble.bankingserver.global.error.ErrorCode;
import numble.bankingserver.global.exception.BankingException;
import numble.bankingserver.global.jwt.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FriendAddService {

    private final FriendListRepository friendListRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public ResponseEntity<SuccessResponse> addFriend(HttpServletRequest httpServletRequest, FriendAddRequest request) {

        String bearerToken = jwtTokenProvider.resolveToken(httpServletRequest);
        String token = jwtTokenProvider.parseToken(bearerToken);

        if (token == null) {
            throw new BankingException(ErrorCode.INVALID_JWT);
        }

        String findId = jwtTokenProvider.getTokenSubject(token);
        User hostUser = userRepository.findById(findId)
                .orElseThrow(() -> new BankingException(ErrorCode.USER_NOT_FOUND));

        User friendUser = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new BankingException(ErrorCode.USER_NOT_FOUND));

        friendListRepository.findByHostUserAndFriendUser(hostUser, friendUser)
                .ifPresent(friendList -> {
                throw new BankingException(ErrorCode.ALREADY_FRIENDLIST_EXIST);
        });

        friendListRepository.save(FriendList.builder()
                .hostUser(hostUser)
                .friendUser(friendUser)
                .build());

        friendListRepository.save(FriendList.builder()
                .hostUser(friendUser)
                .friendUser(hostUser)
                .build());

        return new ResponseEntity<>(
                SuccessResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Friend Add Success")
                        .build(),
                HttpStatus.valueOf(HttpStatus.CREATED.value())
        );
    }
}
