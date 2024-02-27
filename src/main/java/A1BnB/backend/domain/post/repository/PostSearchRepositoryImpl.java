package A1BnB.backend.domain.post.repository;

import static A1BnB.backend.domain.member.model.entity.QMember.member;
import static A1BnB.backend.domain.post.model.entity.QPost.post;

import A1BnB.backend.domain.post.dto.PostSearchRequest;
import A1BnB.backend.domain.post.dto.PostSearchResponse;
import A1BnB.backend.domain.post.dto.QPostSearchResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostSearchRepositoryImpl implements PostSearchRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PostSearchResponse> search(PostSearchRequest requestParam) {
        return queryFactory
                .select(new QPostSearchResponse(
                        member.name,
                        post.location,
                        post.checkIn,
                        post.checkOut,
                        post.pricePerNight
                ))
                .from(post)
                .leftJoin(post.author, member)
                .where(memberNameEq(requestParam.authorName()),
                        locationEq(requestParam.location()),
                        betweenDate(requestParam.checkIn(), requestParam.checkOut()),
                        priceGoe(requestParam.minPrice()),
                        priceLoe(requestParam.maxPrice()),
                        amenitiesContains(requestParam.amenities())
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
        return post.createdAt.between(checkIn, checkOut);
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