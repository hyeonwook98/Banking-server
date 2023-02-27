package numble.bankingserver.domain.friendlist.controller;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.friendlist.dto.request.FriendAddRequest;
import numble.bankingserver.domain.friendlist.dto.request.FriendDeleteRequest;
import numble.bankingserver.domain.friendlist.dto.response.SearchFriendListResponse;
import numble.bankingserver.domain.friendlist.service.FriendAddService;
import numble.bankingserver.domain.friendlist.service.FriendDeleteService;
import numble.bankingserver.domain.friendlist.service.FriendSearchService;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.global.dto.response.SuccessResponse;
import numble.bankingserver.global.jwt.JwtTokenCheckService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/friend")
@RestController
public class FriendListController {

    private final FriendAddService friendAddService;
    private final FriendSearchService friendSearchService;
    private final JwtTokenCheckService jwtTokenCheckService;
    private final FriendDeleteService friendDeleteService;

    @PostMapping("/add")
    public ResponseEntity<SuccessResponse> addFriend(HttpServletRequest httpServletRequest,
                                                     @RequestBody @Valid FriendAddRequest request) {
        User hostUser = jwtTokenCheckService.checkToken(httpServletRequest);
        return friendAddService.addFriend(hostUser, request);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<SuccessResponse> deleteFriend(HttpServletRequest httpServletRequest,
                                                        @RequestBody @Valid FriendDeleteRequest request) {
        User hostUser = jwtTokenCheckService.checkToken(httpServletRequest);
        return friendDeleteService.deleteFriend(hostUser, request);
    }

    @GetMapping("/search")
    public SearchFriendListResponse searchFriend(HttpServletRequest httpServletRequest) {
        User hostUser = jwtTokenCheckService.checkToken(httpServletRequest);
        return friendSearchService.searchFriend(hostUser);
    }


}
