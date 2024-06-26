package A1BnB.backend.domain.post.repository;

import static A1BnB.backend.domain.member.model.QMember.member;
import static A1BnB.backend.domain.post.model.QPost.post;

import A1BnB.backend.domain.post.dto.PostDto.PostSearchRequest;
import A1BnB.backend.domain.post.model.Post;
import A1BnB.backend.domain.postBook.model.QPostBookInfo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class PostSearchRepositoryImpl implements PostSearchRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Post> search(PostSearchRequest requestParam, List<LocalDateTime> searchDates, Pageable pageable) {
        // 결과 목록 조회
        List<Post> posts = queryFactory
                .select(post)
                .from(post)
                .leftJoin(post.host, member)
                .where(
                        memberNameEq(requestParam.hostName()),
                        locationEq(requestParam.location()),
                        priceGoe(requestParam.minPrice()),
                        priceLoe(requestParam.maxPrice()),
                        amenitiesContains(requestParam.amenities()),
                        occupancyGoe(requestParam.occupancy()),
                        datesNotContains(searchDates)
                )
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(posts, pageable, posts.size());
    }

    // 작성자 이름 일치 여부
    private BooleanExpression memberNameEq(String memberName){
        return memberName == null ? null : member.name.eq(memberName);
    }

    // 지역 일치 여부
    private BooleanExpression locationEq(String location){
        return location == null ? null : post.location.eq(location);
    }

    // 최소 가격 만족 여부
    private BooleanExpression priceGoe(Integer minPrice){
        return minPrice == null ? null : post.pricePerNight.goe(minPrice);
    }

    // 최대 가격 만족 여부
    private BooleanExpression priceLoe(Integer maxPrice){
        return maxPrice == null ? null : post.pricePerNight.loe(maxPrice);
    }

    // amenities 포함 여부
    private BooleanExpression amenitiesContains(List<String> amenities){
        if (amenities == null || amenities.isEmpty()) {
            return null;
        }
        BooleanExpression expression = null;
        for (String amenityName : amenities) {
            BooleanExpression amenityExpression = post.photos.any().amenities.any().type.eq(amenityName);
            expression = expression == null ? amenityExpression : expression.and(amenityExpression);
        }
        return expression;
    }

    // 수용 인원 만족 여부
    private BooleanExpression occupancyGoe(Integer occupancy) {
        return occupancy == null ? null : post.maximumOccupancy.goe(occupancy);
    }

    // 날짜 포함 여부를 확인하여, 해당 날짜에 예약이 없는 Post만 조회
    private BooleanExpression datesNotContains(List<LocalDateTime> searchDates) {
        if (searchDates == null || searchDates.isEmpty()) {
            return null;
        }

        QPostBookInfo subPostBookInfo = new QPostBookInfo("subPostBookInfo");

        return JPAExpressions.selectFrom(subPostBookInfo)
                .where(subPostBookInfo.post.eq(post),
                        subPostBookInfo.bookedDates.any().localDateTime.in(searchDates))
                .notExists();
    }

}