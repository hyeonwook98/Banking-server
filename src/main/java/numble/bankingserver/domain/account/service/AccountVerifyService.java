package numble.bankingserver.domain.account.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.account.dto.request.AccountVerifyRequest;
import numble.bankingserver.domain.account.dto.response.AccountVerifyResponse;
import numble.bankingserver.domain.account.entity.Account;
import numble.bankingserver.domain.account.repository.AccountRepository;
import numble.bankingserver.domain.friendlist.repository.FriendListRepository;
import numble.bankingserver.domain.user.entity.User;
import numble.bankingserver.global.error.ErrorCode;
import numble.bankingserver.global.exception.BankingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountVerifyService {

    private final AccountRepository accountRepository;
    private final FriendListRepository friendListRepository;

    @Transactional
    public AccountVerifyResponse verifyAccount(User hostUser, AccountVerifyRequest request) {

        accountRepository.findByUserAndAccountNumber(hostUser, request.getHostAccountNumber())
                .orElseThrow(() -> new BankingException(ErrorCode.WRONG_ACCESS));

        Account friendAccount = accountRepository.findByAccountNumber(request.getFriendAccountNumber())
                .orElseThrow(() -> new BankingException(ErrorCode.ACCOUNT_NOT_FOUND));

        User friendUser = friendAccount.getUser();
        friendListRepository.findByHostUserAndFriendUser(hostUser, friendUser)
                .orElseThrow(() -> new BankingException(ErrorCode.NOT_FRIEND));

        return AccountVerifyResponse.builder()
                .accountHolder(friendUser.getName())
                .accountNumber(request.getFriendAccountNumber())
                .sentAmount(request.getSentAmount())
                .build();
    }
}
