package numble.bankingserver.domain.friendlist.controller;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.friendlist.dto.request.FriendAddRequest;
import numble.bankingserver.domain.friendlist.dto.response.SearchFriendListResponse;
import numble.bankingserver.domain.friendlist.service.FriendAddService;
import numble.bankingserver.domain.friendlist.service.FriendSearchService;
import numble.bankingserver.global.dto.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/add")
    public ResponseEntity<SuccessResponse> addFriend(HttpServletRequest httpServletRequest,
                                                     @RequestBody @Valid FriendAddRequest request) {
        return friendAddService.addFriend(httpServletRequest, request);
    }
    @GetMapping("/search")
    public SearchFriendListResponse searchFriend(HttpServletRequest httpServletRequest) {
        return friendSearchService.searchFriend(httpServletRequest);
    }
}
