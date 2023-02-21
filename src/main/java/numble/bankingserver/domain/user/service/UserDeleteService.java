package numble.bankingserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.account.repository.AccountRepository;
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

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class UserDeleteService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;


    public ResponseEntity<SuccessResponse> deleteUser(HttpServletRequest httpServletRequest) {

        String bearerToken = jwtTokenProvider.resolveToken(httpServletRequest);
        String token = jwtTokenProvider.parseToken(bearerToken);

        if (token == null) {
            throw new BankingException(ErrorCode.INVALID_JWT);
        }

        String findId = jwtTokenProvider.getTokenSubject(token);
        User hostUser = userRepository.findById(findId)
                .orElseThrow(() -> new BankingException(ErrorCode.USER_NOT_FOUND));

        if (accountRepository.findByUser(hostUser).size() != 0) {
            throw new BankingException(ErrorCode.ACCOUNT_STILL_EXISTS);
        }
        userRepository.delete(hostUser);

        return new ResponseEntity<>(
                SuccessResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Delete User Success")
                        .build(),
                HttpStatus.valueOf(HttpStatus.OK.value())
        );
    }
}
