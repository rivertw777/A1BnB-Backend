package A1BnB.backend.domain.member.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import A1BnB.backend.global.redis.service.RefreshTokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Test
    @DisplayName("회원 가입 확인")
    void registerUser() {
    }

    @Test
    void findMember() {
    }

    @Test
    void findSettlementAmount() {
    }

    @Test
    void findHostPosts() {
    }

    @Test
    void findHostReservations() {
    }

    @Test
    void findNearestCheckInDate() {
    }

    @Test
    void findGuestReservations() {
    }

    @Test
    void findLikePosts() {
    }

    @Test
    void checkSameMember() {
    }

    @Test
    void findRoom() {
    }

    @Test
    void findMyChatRooms() {
    }

    @Test
    void deleteAllMember() {
    }
}