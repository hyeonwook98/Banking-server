package numble.bankingserver.domain.friendlist.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.friendlist.dto.request.FriendDeleteRequest;
import numble.bankingserver.domain.friendlist.repository.FriendListRepository;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.global.dto.response.SuccessResponse;
import numble.bankingserver.global.error.ErrorCode;
import numble.bankingserver.global.exception.BankingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FriendDeleteService {

    private final FriendListRepository friendListRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<SuccessResponse> deleteFriend(User hostUser, FriendDeleteRequest request) {

        User friendUser = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new BankingException(ErrorCode.USER_NOT_FOUND));

        friendListRepository.deleteFriendList(hostUser, friendUser);

        return new ResponseEntity<>(
                SuccessResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Friend Delete Success")
                        .build(),
                HttpStatus.valueOf(HttpStatus.OK.value())
        );
    }
}
