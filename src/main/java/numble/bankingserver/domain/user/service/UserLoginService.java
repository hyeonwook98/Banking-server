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

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Service
public class UserLoginService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserLoginResponse loginUser(UserLoginRequest request, HttpServletResponse response) {

        String id = request.getId();
        String password = request.getPassword();

        userRepository.findByIdAndPassword(id, password)
                .orElseThrow(() -> new BankingException(ErrorCode.USER_NOT_FOUND));

        String accessToken = jwtTokenProvider.createToken(id);

        response.addHeader(JwtProperties.HEADER, JwtProperties.TOKEN_PREFIX+accessToken);

        return UserLoginResponse.builder()
                .accessToken(JwtProperties.TOKEN_PREFIX +accessToken)
                .build();
    }
}
