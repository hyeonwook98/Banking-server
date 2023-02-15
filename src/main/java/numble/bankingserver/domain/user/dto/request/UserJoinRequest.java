package numble.bankingserver.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import lombok.NoArgsConstructor;
import numble.bankingserver.domain.enums.Gender;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJoinRequest {

    private String id;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private Gender gender;
    private String birthYear;

    @Builder
    public UserJoinRequest(String id, String password, String name, String phoneNumber, String email,
                           String address, Gender gender, String birthYear) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.birthYear = birthYear;
    }
}
