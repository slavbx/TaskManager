package org.slavbx.taskmanager.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slavbx.taskmanager.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Сервис для работы с JWT (JSON Web Tokens).
 * Предоставляет методы для извлечения информации из JWT, генерации новых токенов,
 * проверки их валидности и извлечения всех необходимых данных, таких как имя пользователя
 */
@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSigningKey;

    /**
     * Извлечение имени пользователя из токена.
     * @param token JWT токен
     * @return имя пользователя, содержащееся в токене
     */
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Генерация JWT токена на основе предоставленных данных пользователя.
     * @param userDetails детали пользователя
     * @return сгенерированный JWT токен
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("email", customUserDetails.getEmail());
            claims.put("roles", customUserDetails.getRoles()); //Здесь была одна роль
        }
        return generateToken(claims, userDetails);
    }

    /**
     * Проверка валидности токена.
     * @param token JWT токен
     * @param userDetails детали пользователя для сравнения
     * @return true, если токен валиден, иначе false
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Извлечение указанного требования (claim) из токена.
     *
     * @param token            JWT токен
     * @param claimsResolvers  функция для извлечения значения требования
     * @param <T>             тип извлекаемого значения
     * @return значение требования на основе указанной функции
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Генерация JWT токена с дополнительными требованиями.
     * @param extraClaims     дополнительные требования для токена
     * @param userDetails     детали пользователя, которые будут включены в токен
     * @return сгенерированный JWT токен
     */
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Проверка, истек ли токен.
     * @param token JWT токен
     * @return true, если токен истек, иначе false
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Извлечение даты истечения токена.
     * @param token JWT токен
     * @return дата истечения токена
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Извлечение всех требований из токена.
     *
     * @param token JWT токен
     * @return объект Claims, содержащий все требования
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    /**
     * Получение ключа для подписи токена.
     * @return ключ для подписи
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
