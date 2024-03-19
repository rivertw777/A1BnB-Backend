package A1BnB.backend.domain.member.model.entity;

import A1BnB.backend.domain.member.model.Role;
import A1BnB.backend.domain.post.model.entity.Post;
import A1BnB.backend.domain.postBook.model.PostBookInfo;
import A1BnB.backend.domain.postLike.model.entity.PostLikeInfo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "username", length = 10)
    private String name;

    @Column(name = "password", length = 60)
    private String password;

    @Column(name = "roles")
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<PostLikeInfo> postLikeInfos = new ArrayList<>();

    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    private List<PostBookInfo> postBookInfos = new ArrayList<>();

    @Column(name = "settlement_amount")
    private Integer settlementAmount;

    @Builder
    public Member(String name, String password, List<Role> roles) {
        this.name = name;
        this.password = password;
        this.roles = roles;
        this.settlementAmount = 0;
    }

    public void setPost(Post post){
        this.posts.add(post);
    }

    public void setPostLikeInfos(PostLikeInfo postLikeInfo){
        this.postLikeInfos.add(postLikeInfo);
    }

    public void setPostBookInfos(PostBookInfo postBookInfo){
        this.postBookInfos.add(postBookInfo);
    }

    public void addAmount(Integer paymentAmount) {
        this.settlementAmount += paymentAmount;
    }

    public void subAmount(Integer paymentAmount) {
        this.settlementAmount -= paymentAmount;
    }
}
