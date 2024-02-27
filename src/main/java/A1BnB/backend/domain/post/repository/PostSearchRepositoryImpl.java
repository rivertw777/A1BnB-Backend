package A1BnB.backend.domain.post.repository;

import static A1BnB.backend.domain.member.model.entity.QMember.member;
import static A1BnB.backend.domain.post.model.entity.QPost.post;

import A1BnB.backend.domain.post.dto.PostSearchRequest;
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
    public List<PostSearchResult> search(PostSearchRequest searchCondition) {
        return queryFactory
                .select(new QPostSearchResult(
                        post.postId,
                        member.name,
                        post.location,
                        post.checkIn,
                        post.checkOut,
                        post.pricePerNight
                ))
                .from(post)
                .leftJoin(post.author, member)
                .where(memberNameEq(searchCondition.authorName()),
                        locationEq(searchCondition.location()),
                        betweenDate(searchCondition.checkIn(), searchCondition.checkOut()),
                        priceGoe(searchCondition.minPrice()),
                        priceLoe(searchCondition.maxPrice()),
                        amenitiesContains(searchCondition.amenities())
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

    // 날짜 해당 여부
    private BooleanExpression betweenDate(LocalDateTime checkIn, LocalDateTime checkOut){
        if (checkIn == null && checkOut == null) {
            return null;
        }
        return post.checkIn.goe(checkIn).and(post.checkOut.loe(checkOut));
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

}