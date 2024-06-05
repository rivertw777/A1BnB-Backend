package A1BnB.backend.domain.post.service;

import static A1BnB.backend.domain.post.exception.PostExceptionMessages.POST_NOT_FOUND;

import A1BnB.backend.domain.date.service.DateService;
import A1BnB.backend.domain.member.service.MemberService;
import A1BnB.backend.domain.photo.dto.PhotoInfo;
import A1BnB.backend.domain.photo.model.entity.Photo;
import A1BnB.backend.domain.photo.service.PhotoService;
import A1BnB.backend.domain.post.dto.request.PostBookRequest;
import A1BnB.backend.domain.post.dto.response.PostDetailResponse;
import A1BnB.backend.domain.post.dto.request.PostSearchRequest;
import A1BnB.backend.domain.post.dto.mapper.PostDetailResponseMapper;
import A1BnB.backend.domain.post.dto.response.PostLikeCheckResponse;
import A1BnB.backend.domain.post.dto.response.PostLikeCountResponse;
import A1BnB.backend.domain.post.model.entity.Post;
import A1BnB.backend.domain.post.repository.PostRepository;
import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.dto.request.PostUploadRequest;
import A1BnB.backend.domain.post.dto.response.PostResponse;
import A1BnB.backend.domain.post.dto.mapper.PostResponseMapper;
import A1BnB.backend.domain.postBook.service.PostBookService;
import A1BnB.backend.domain.postLike.service.PostLikeService;
import A1BnB.backend.domain.post.exception.PostException;
import A1BnB.backend.global.redis.service.PostLikeCountService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final MemberService memberService;
    private final PhotoService photoService;
    private final PostLikeService postLikeService;
    private final PostBookService postBookService;
    private final DateService dateService;
    private final PostLikeCountService postLikeCountService;

    private final PostResponseMapper postResponseMapper;
    private final PostDetailResponseMapper postDetailResponseMapper;

    // 게시물 등록
    @Override
    @Transactional
    @CacheEvict(cacheNames = "findPosts", allEntries = true)
    public void registerPost(String username, PostUploadRequest requestParam) {
        Member currentMember = memberService.findMember(username);
        List<Photo> photos = photoService.findPhotos(requestParam.photoIdList());
        Post post = savePost(requestParam, currentMember, photos);
        postLikeCountService.initCount(post.getId());
    }

    // 게시물 삭제
    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "findPosts", allEntries = true),
            @CacheEvict(cacheNames = "PostDetail", key= "#p0")
    })
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
        postLikeCountService.deleteCount(postId);
    }

    // Post 엔티티 저장
    private Post savePost(PostUploadRequest requestParam, Member currentMember, List<Photo> photos) {
        Post post = Post.builder()
                .host(currentMember)
                .location(requestParam.location())
                .photos(photos)
                .pricePerNight(requestParam.pricePerNight())
                .maximumOccupancy(requestParam.maximumOccupancy())
                .caption(requestParam.caption())
                .build();
        postRepository.save(post);
        return post;
    }

    // 게시물 DTO Page 반환
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "findPosts", keyGenerator = "cachingKeyGenerator")
    public Page<PostResponse> getAllPosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        // 게시물 응답 DTO 반환
        return postPage.map(postResponseMapper::toPostResponse);
    }

    // 게시물 검색 DTO Page 반환
    @Override
    @Transactional(readOnly = true)
    public Page<PostResponse> searchByCondition(PostSearchRequest requestParam, Pageable pageable) {
        List<LocalDateTime> searchDates = getSearchDates(requestParam.checkInDate(), requestParam.checkOutDate());
        Page<Post> postPage = postRepository.search(requestParam, searchDates, pageable);
        return postPage.map(postResponseMapper::toPostResponse);
    }

     private List<LocalDateTime> getSearchDates(LocalDateTime checkInDate, LocalDateTime checkOutDate){
        if (checkInDate == null || checkOutDate == null){
            return null;
        }
        return dateService.getLocalDateTimeDates(checkInDate, checkOutDate);
    }

    // 게시물 상세 DTO 반환
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "PostDetail", key= "#p0")
    public PostDetailResponse getPostDetail(Long postId) {
        Post post = findPostById(postId);
        List<PhotoInfo> photoInfos = photoService.getPhotoInfos(post.getPhotos());
        return postDetailResponseMapper.toPostDetailResponse(post, photoInfos);
    }

    // 게시물 좋아요 여부
    @Override
    @Transactional(readOnly = true)
    public PostLikeCheckResponse checkLike(String username, Long postId) {
        Post post = findPostById(postId);
        Member currentMember = memberService.findMember(username);
        boolean likeCheck = postLikeService.findByPostAndMember(post, currentMember);
        return new PostLikeCheckResponse(likeCheck);
    }

    // 게시물 좋아요
    @Override
    @Transactional
    public void likePost(String username, Long postId) {
        Post post = findPostById(postId);
        Member currentMember = memberService.findMember(username);
        postLikeService.likePost(post, currentMember);
        postLikeCountService.increaseCount(postId);
    }

    // 게시물 좋아요 취소
    @Override
    @Transactional
    public void unlikePost(String username, Long postId) {
        Post post = findPostById(postId);
        Member currentMember = memberService.findMember(username);
        postLikeService.unlikePost(post, currentMember);
        postLikeCountService.decreaseCount(postId);
    }

    // 게시물 예약
    @Override
    @Transactional
    @CacheEvict(cacheNames = "PostDetail", key= "#p1")
    public void bookPost(String username, Long postId, PostBookRequest requestParam) {
        Post post = findPostById(postId);
        Member currentMember = memberService.findMember(username);
        postBookService.bookPost(post, currentMember, requestParam);
    }

    // 게시물 예약 취소
    @Override
    @Transactional
    @CacheEvict(cacheNames = "PostDetail", key= "#p1")
    public void unbookPost(String username, Long postId, Long bookId) {
        Post post = findPostById(postId);
        Member currentMember = memberService.findMember(username);
        postBookService.unbookPost(post, currentMember, bookId);
    }

    // 게시물 인기순 DTO Page 반환
    @Override
    public Page<PostResponse> getLikeRanking(Pageable pageable) {
        List<Long> postIds = postLikeCountService.getRanking(pageable);
        List<Post> posts = postRepository.findAllByIdIn(postIds);
        // 순서 정리
        Page<Post> postPage = new PageImpl<>(postIds.stream()
                .map(postId -> posts.stream()
                        .filter(post -> post.getId().equals(postId))
                        .findFirst()
                        .orElse(null))
                .collect(Collectors.toList()));
        return postPage.map(postResponseMapper::toPostResponse);
    }

    // 좋아요 수 반환
    @Override
    public PostLikeCountResponse getLikeCount(Long postId) {
        Integer likeCount = postLikeCountService.getCount(postId);
        return new PostLikeCountResponse(likeCount);
    }

    // 게시물 단일 조회
    private Post findPostById(Long postId){
        return postRepository.findById(postId)
                .orElseThrow(()->new PostException(POST_NOT_FOUND.getMessage()));
    }

}