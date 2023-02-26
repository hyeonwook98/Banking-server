package numble.bankingserver.domain.alarm.controller;

import lombok.RequiredArgsConstructor;
import numble.bankingserver.domain.alarm.dto.request.AlarmRequest;
import numble.bankingserver.domain.alarm.dto.response.AlarmResponse;
import numble.bankingserver.domain.alarm.service.AlarmService;
import numble.bankingserver.domain.alarm.service.RestTemplateAlarmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/alarm")
@RestController
public class AlarmController {

    private final AlarmService alarmService;
    private final RestTemplateAlarmService restTemplateAlarmService;

    @PostMapping("/transfer")
    public AlarmResponse transferAlarm(@RequestBody @Valid AlarmRequest request) {
        return alarmService.requestAlarm(request);
    }

    @PostMapping("/transfer_temp")
    public ResponseEntity<AlarmResponse> transferAlarmTemp(@RequestBody @Valid AlarmRequest request) {
        System.out.println("뱅크서버에서 -> 알람서버로 간다");
        System.out.println("1");
        return restTemplateAlarmService.requestAlarm(request);
    }

    @PostMapping("/transfer_server")
    public ResponseEntity<AlarmResponse> transferAlarmServer(@RequestBody AlarmResponse response) {
        System.out.println("3");
        System.out.println("알람서버에서 도착함.");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
