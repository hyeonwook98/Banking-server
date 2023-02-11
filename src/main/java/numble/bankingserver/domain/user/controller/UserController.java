package numble.bankingserver.domain.user.controller;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.user.dto.request.UserJoinRequest;
import numble.bankingserver.domain.user.dto.request.UserLoginRequest;
import numble.bankingserver.domain.user.dto.response.UserLoginResponse;
import numble.bankingserver.domain.user.service.UserJoinService;
import numble.bankingserver.domain.user.service.UserLoginService;
import numble.bankingserver.global.dto.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserJoinService userJoinService;
    private final UserLoginService userLoginService;

    @PostMapping("/join")
    public ResponseEntity<SuccessResponse> joinUser(@RequestBody @Valid UserJoinRequest request) {
        return userJoinService.joinUser(request);
    }

    @PostMapping("/login")
    public UserLoginResponse loginUser(@RequestBody @Valid UserLoginRequest request, HttpServletResponse response) {
        return userLoginService.loginUser(request, response);
    }
}
