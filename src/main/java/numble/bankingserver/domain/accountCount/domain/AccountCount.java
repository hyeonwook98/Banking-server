package numble.bankingserver.domain.accountCount.domain;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AccountCount {

    public static Long count = 0L;

    public void addCount() {
        this.count += 1;
    }

}
