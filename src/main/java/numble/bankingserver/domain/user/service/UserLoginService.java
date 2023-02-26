package numble.bankingserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.user.dto.request.UserLoginRequest;
import numble.bankingserver.domain.user.dto.response.UserLoginResponse;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.global.error.ErrorCode;
import numble.bankingserver.global.exception.BankingException;
import numble.bankingserver.global.jwt.JwtProperties;
import numble.bankingserver.global.jwt.JwtTokenProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class UserLoginService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserLoginResponse loginUser(UserLoginRequest request) {

        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new BankingException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BankingException(ErrorCode.WRONG_PASSWORD);
        }

        String accessToken = jwtTokenProvider.createToken(request.getId());

        return UserLoginResponse.builder()
                .accessToken(JwtProperties.TOKEN_PREFIX +accessToken)
                .build();
    }
}
