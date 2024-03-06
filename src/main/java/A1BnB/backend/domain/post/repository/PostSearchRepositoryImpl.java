package A1BnB.backend.domain.post.repository;

import static A1BnB.backend.domain.member.model.entity.QMember.member;
import static A1BnB.backend.domain.post.model.entity.QPost.post;

import A1BnB.backend.domain.post.dto.request.PostSearchRequest;
import A1BnB.backend.domain.post.dto.PostSearchResult;
import A1BnB.backend.domain.post.dto.QPostSearchResult;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostSearchRepositoryImpl implements PostSearchRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PostSearchResult> search(PostSearchRequest searchCondition, List<LocalDateTime> searchDates) {
        return queryFactory
                .select(new QPostSearchResult(
                        post.postId,
                        member.name,
                        post.location,
                        post.pricePerNight,
                        post.maximumOccupancy
                ))
                .from(post)
                .leftJoin(post.author, member)
                .where(memberNameEq(searchCondition.authorName()),
                        locationEq(searchCondition.location()),
                        priceGoe(searchCondition.minPrice()),
                        priceLoe(searchCondition.maxPrice()),
                        amenitiesContains(searchCondition.amenities()),
                        occupancyGoe(searchCondition.occupancy())
                )
                .fetch();
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
    private BooleanExpression priceGoe(Double minPrice){
        return minPrice == null ? null : post.pricePerNight.goe(minPrice);
    }

    // 최대 가격 만족 여부
    private BooleanExpression priceLoe(Double maxPrice){
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

}