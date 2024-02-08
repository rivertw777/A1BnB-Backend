package A1BnB.backend.member.model.entity;

import A1BnB.backend.member.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원")
@Builder
@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "회원 id")
    private Long memberId;

    @Column(unique = true, name = "username", length = 10)
    @Schema(description = "이름")
    private String name;

    @Column(name = "password", length = 60)
    @Schema(description = "비밀번호")
    private String password;

    @Column(name = "roles")
    private List<Role> roles = new ArrayList<>();

    @Builder
    public Member(String name, String password, List<Role> roles) {
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

}
