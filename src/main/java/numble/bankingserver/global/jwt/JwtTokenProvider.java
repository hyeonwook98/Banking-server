package numble.bankingserver.global.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    public String createToken(String id) {

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .setSubject(id)
                .claim("id",id)
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(JwtProperties.SECRET_KEY.getBytes()))
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader(JwtProperties.HEADER);
        return parseToken(bearer);
    }

    public String parseToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(JwtProperties.TOKEN_PREFIX))
            return bearerToken.replace(JwtProperties.TOKEN_PREFIX, "");

        return null;
    }
}
