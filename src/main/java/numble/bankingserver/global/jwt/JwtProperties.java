package numble.bankingserver.global.jwt;

public interface JwtProperties {

    String SECRET_KEY = "김현욱";
    Long EXPIRATION_TIME = 30 * 60 * 1000L;  //30분
    String TOKEN_PREFIX = "Bearer ";
    String HEADER = "Authorization";

}
