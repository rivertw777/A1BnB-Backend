package A1BnB.backend.global.security.model;

import A1BnB.backend.domain.member.model.Role;
import A1BnB.backend.domain.member.model.Member;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record CustomUserDetails(Member member) implements UserDetails {

    // 권환 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> roles = member.getRoles();
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }

    // 비밀번호 반환
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    // 이름 반환
    @Override
    public String getUsername() {
        return member.getName();
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 자격 증명 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 여부
    @Override
    public boolean isEnabled() {
        return true;
    }

}
