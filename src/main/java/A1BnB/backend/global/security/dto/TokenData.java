package A1BnB.backend.global.security.dto;

public record TokenData(String accessToken, String refreshToken) {
    public TokenData{
    }
}
