package numble.bankingserver.global.jwt;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.global.error.ErrorCode;
import numble.bankingserver.global.exception.BankingException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class JwtTokenCheckService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public User checkToken(HttpServletRequest httpServletRequest) {
        String bearerToken = jwtTokenProvider.resolveToken(httpServletRequest);
        String token = jwtTokenProvider.parseToken(bearerToken);

        if (token == null) {
            throw new BankingException(ErrorCode.INVALID_JWT);
        }

        String findId = jwtTokenProvider.getTokenSubject(token);
        return userRepository.findById(findId)
                .orElseThrow(() -> new BankingException(ErrorCode.USER_NOT_FOUND));
    }
}
