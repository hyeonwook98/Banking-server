package numble.bankingserver.domain.account.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.account.dto.request.AccountVerifyRequest;
import numble.bankingserver.domain.account.entity.Account;
import numble.bankingserver.domain.account.repository.AccountRepository;
import numble.bankingserver.domain.alarm.dto.request.AlarmRequest;
import numble.bankingserver.domain.alarm.service.AlarmService;
import numble.bankingserver.domain.transferhistory.entity.TransferHistory;
import numble.bankingserver.domain.transferhistory.repository.TransferHistoryRepository;
import numble.bankingserver.global.dto.response.SuccessResponse;
import numble.bankingserver.global.enums.TransferType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountTransferService {

    private final AccountRepository accountRepository;
    private final TransferHistoryRepository transferHistoryRepository;
    private final AlarmService alarmService;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<SuccessResponse> transferAccount(AccountVerifyRequest request) throws InterruptedException {

        Account hostAccount = accountRepository.findByAccountNumberWithPessimisticLock(
                request.getHostAccountNumber()).get();
        hostAccount.withdraw(request.getSentAmount());
        Account friendAccount = accountRepository.findByAccountNumberWithPessimisticLock(
                request.getFriendAccountNumber()).get();
        friendAccount.deposit(request.getSentAmount());

        transferHistoryRepository.save(TransferHistory.builder()
                .hostAccountNumber(hostAccount.getAccountNumber())
                .friendAccountNumber(request.getFriendAccountNumber())
                .friendName(friendAccount.getUser().getName())
                .transferType(TransferType.WITHDRAW)
                .amount(request.getSentAmount())
                .balance(hostAccount.getBalance())
                .build());

        transferHistoryRepository.save(TransferHistory.builder()
                .hostAccountNumber(friendAccount.getAccountNumber())
                .friendAccountNumber(request.getHostAccountNumber())
                .friendName(hostAccount.getUser().getName())
                .transferType(TransferType.DEPOSIT)
                .amount(request.getSentAmount())
                .balance(friendAccount.getBalance())
                .build());

        AlarmRequest hostAlarmRequest = new AlarmRequest(TransferType.WITHDRAW, request.getSentAmount(),
                request.getHostAccountNumber(), friendAccount.getUser().getName());
        alarmService.requestAlarm(hostAlarmRequest);

        AlarmRequest friendAlarmRequest = new AlarmRequest(TransferType.DEPOSIT, request.getSentAmount(),
                request.getFriendAccountNumber(), hostAccount.getUser().getName());
        alarmService.requestAlarm(friendAlarmRequest);

        return new ResponseEntity<>(
                SuccessResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Transfer Success")
                        .build(),
                HttpStatus.valueOf(HttpStatus.OK.value())
        );
    }
}
