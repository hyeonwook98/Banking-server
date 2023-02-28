package numble.bankingserver.domain.alarm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import numble.bankingserver.domain.alarm.dto.request.AlarmRequest;
import numble.bankingserver.global.enums.TransferType;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AlarmService {

    public void requestAlarm(AlarmRequest request) throws InterruptedException {

        String type = null;
        String message = null;
        Long accountNumber = request.getHostAccountNumber() % 100000000;

        if (request.getTransferType() == TransferType.WITHDRAW) {
            type = "출금 ";
            message = "내 입출금통장" + "(" + String.format("%04d", accountNumber) + ")" + " -> " + request.getFriendName();
        }
        if (request.getTransferType() == TransferType.DEPOSIT){
            type = "입금 ";
            message = request.getFriendName() + " -> " + "내 입출금통장" + "(" + String.format("%04d", accountNumber) + ")";
        }

        log.info("" + type + request.getAmount());
        log.info(message);

    }
}
