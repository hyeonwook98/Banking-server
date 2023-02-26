package numble.bankingserver.domain.alarm.service;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.alarm.dto.request.AlarmRequest;
import numble.bankingserver.domain.alarm.dto.response.AlarmResponse;
import numble.bankingserver.global.enums.TransferType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AlarmService {

//    @Transactional
    public AlarmResponse requestAlarm(AlarmRequest request) {

        String message = null;
        Long accountNumber = request.getHostAccountNumber() % 100000000;

        if (request.getTransferType() == TransferType.WITHDRAW) {
            message = "내 입출금통장" + "(" + String.format("%04d", accountNumber) + ")" + " -> " + request.getFriendName();
        }
        if (request.getTransferType() == TransferType.DEPOSIT){
            message = request.getFriendName() + " -> " + "내 입출금통장" + "(" + String.format("%04d", accountNumber) + ")";
        }
        return AlarmResponse.builder()
                .transferType(request.getTransferType())
                .amount(request.getAmount())
                .message(message)
                .build();

    }
}
