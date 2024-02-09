package A1BnB.backend.security.dto;

public record TokenData(String accessToken, String refreshToken) {
    public TokenData{
    }
}
