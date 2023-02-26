package numble.bankingserver.domain.alarm.service;

import numble.bankingserver.domain.alarm.dto.request.AlarmRequest;
import numble.bankingserver.domain.alarm.dto.response.AlarmResponse;
import numble.bankingserver.global.enums.TransferType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class RestTemplateAlarmService {

    public ResponseEntity<AlarmResponse> requestAlarm(AlarmRequest request) {

        String message = null;
        Long accountNumber = request.getHostAccountNumber() % 100000000;

        if (request.getTransferType() == TransferType.WITHDRAW) {
            message = "내 입출금통장" + "(" + String.format("%04d", accountNumber) + ")" + " -> " + request.getFriendName();
        }
        if (request.getTransferType() == TransferType.DEPOSIT){
            message = request.getFriendName() + " -> " + "내 입출금통장" + "(" + String.format("%04d", accountNumber) + ")";
        }

        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:8080")
                .path("/alarm/transfer_server")
                .encode()
                .build()
                .toUri();

        AlarmResponse alarmResponse = AlarmResponse.builder()
                .transferType(request.getTransferType())
                .amount(request.getAmount())
                .message(message)
                .build();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AlarmResponse> responseEntity = restTemplate.postForEntity(uri, alarmResponse, AlarmResponse.class);
        System.out.println("2");
        return responseEntity;

    }
}
