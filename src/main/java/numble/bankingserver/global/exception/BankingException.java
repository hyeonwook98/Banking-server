package numble.bankingserver.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import numble.bankingserver.global.error.ErrorCode;

@RequiredArgsConstructor
@Getter
public class BankingException extends RuntimeException{
    private final ErrorCode errorCode;
}
