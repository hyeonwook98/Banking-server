package numble.bankingserver.global.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SuccessResponse {

    private final int status;
    private final String message;

}
