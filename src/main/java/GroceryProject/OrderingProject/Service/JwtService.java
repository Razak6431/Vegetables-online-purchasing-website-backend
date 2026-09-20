package GroceryProject.OrderingProject.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private  String secret;

     @Value("${jwt.expiration}")
     private Long jwtExpiration;

     public String generateToken(String username){
         return generateToken(new HashMap<String,Object>(),username);
     }

     private String generateToken(HashMap<String,Object>extraClaims,String username){
         return Jwts.builder()
                 .claims(extraClaims)
                 .subject(username)
                 .issuedAt(new Date(System.currentTimeMillis()))
                 .signWith(getSignKey())
                 .expiration(new Date(System.currentTimeMillis()+jwtExpiration))
                 .compact();
     }

     public String extractUsername(String token){
         return extractClaim(token, Claims::getSubject);
     }

     private <T>T extractClaim(String token, Function<Claims,T>claimsTFunction){
      final Claims claims=extractAllClaims(token);

      return claimsTFunction.apply(claims);
     }

     public boolean isTokenValid(String token, UserDetails userDetails){
         final String username=extractUsername(token);
         return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
     }

     public boolean isTokenExpired(String token){
         return extractExpiration(token).before(new Date());
     }

    public Date extractExpiration(String token) {
         return extractClaim(token,Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
         return Jwts.parser()
                 .verifyWith(getSignKey())
                 .build()
                 .parseSignedClaims(token)
                 .getPayload();

    }


    private SecretKey getSignKey(){
         byte[] bytes=secret.getBytes(StandardCharsets.UTF_8);

         return Keys.hmacShaKeyFor(bytes);


     }

     public long getJwtExpirationTime(){
         return jwtExpiration;
     }


}
