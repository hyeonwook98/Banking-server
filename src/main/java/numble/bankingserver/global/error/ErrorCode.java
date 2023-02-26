package numble.bankingserver.global.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // 401
    INVALID_JWT(401, "Invalid Jwt"),
    EXPIRED_JWT(401, "Expired Jwt"),
    WRONG_PASSWORD(401, "Wrong password"),
    WRONG_ACCOUNT_TYPE(401, "Wrong Account Type"),
    WRONG_ACCESS(401, "Wrong Access"),
    ACCOUNT_STILL_EXISTS(401, "Account Still Exists"),
    MONEY_EXIST_IN_ACCOUNT(401, "Money Exist In Account"),

    // 404
    USER_NOT_FOUND(404, "User Not Found"),
    ACCOUNT_NOT_FOUND(404, "Account Not Found"),
    NOT_FRIEND(404, "Not Friend"),


    // 409
    ALREADY_ID_EXIST(409, "Already Id Exist"),
    ALREADY_EMAIL_EXIST(409, "Already Email Exist"),
    ALREADY_PHONENUMBER_EXIST(409, "Already PhoneNumber Exist"),
    ALREADY_FRIENDLIST_EXIST(409,"Already FriendList Exist"),

    // 500
    ACCOUNT_NUMBER_OVER(500,"Acount Number Over"),
    BALANCE_LACK(500,"Balance Lack")
    ;

    private final int status;
    private final String message;
}
