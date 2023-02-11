package numble.bankingserver.global.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // 404
    USER_NOT_FOUND(404, "User Not Found"),


    // 409
    ALREADY_ID_EXIST(409, "Already Id Exist"),
    ALREADY_EMAIL_EXIST(409, "Already Email Exist"),
    ALREADY_PHONENUMBER_EXIST(409, "Already PhoneNumber Exist")
    ;

    private final int status;
    private final String message;
}