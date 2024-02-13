package A1BnB.backend.domain.security.dto;

public record TokenData(String accessToken, String refreshToken) {
    public TokenData{
    }
}
