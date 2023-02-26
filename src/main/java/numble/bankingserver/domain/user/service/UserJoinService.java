package numble.bankingserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.user.dto.request.UserJoinRequest;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.domain.user.repository.UserRepository;
import numble.bankingserver.global.dto.response.SuccessResponse;
import numble.bankingserver.global.error.ErrorCode;
import numble.bankingserver.global.exception.BankingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserJoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<SuccessResponse> joinUser(UserJoinRequest request) {

        userRepository.findById(request.getId()).ifPresent(user -> {
            throw new BankingException(ErrorCode.ALREADY_ID_EXIST);
        });

        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new BankingException(ErrorCode.ALREADY_EMAIL_EXIST);
        });

        userRepository.findByPhoneNumber(request.getPhoneNumber()).ifPresent(user -> {
            throw new BankingException(ErrorCode.ALREADY_PHONENUMBER_EXIST);
        });

        userRepository.save(User.builder()
                .id(request.getId())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .address(request.getAddress())
                .gender(request.getGender())
                .birthYear(request.getBirthYear())
                .build());

        return new ResponseEntity<>(
                SuccessResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Join Success")
                        .build(),
                HttpStatus.valueOf(HttpStatus.CREATED.value())
        );

    }
}
