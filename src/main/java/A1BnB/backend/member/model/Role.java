package A1BnB.backend.member.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String roleName;

    public String getRole() {
        return roleName;
    }

}