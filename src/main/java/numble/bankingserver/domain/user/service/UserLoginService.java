package numble.bankingserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.user.dto.request.UserLoginRequest;
import numble.bankingserver.domain.user.dto.response.UserLoginResponse;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.global.error.ErrorCode;
import numble.bankingserver.global.exception.BankingException;
import numble.bankingserver.global.jwt.JwtProperties;
import numble.bankingserver.global.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Service
public class UserLoginService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserLoginResponse loginUser(UserLoginRequest request) {

        userRepository.findByIdAndPassword(request.getId(), request.getPassword())
                .orElseThrow(() -> new BankingException(ErrorCode.USER_NOT_FOUND));

        String accessToken = jwtTokenProvider.createToken(request.getId());

        return UserLoginResponse.builder()
                .accessToken(JwtProperties.TOKEN_PREFIX +accessToken)
                .build();
    }
}
