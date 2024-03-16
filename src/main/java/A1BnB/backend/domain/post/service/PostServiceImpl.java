package A1BnB.backend.domain.post.service;

import static A1BnB.backend.global.exception.constants.PostExceptionMessages.POST_NOT_FOUND;

import A1BnB.backend.domain.date.model.entity.Date;
import A1BnB.backend.domain.date.service.DateService;
import A1BnB.backend.domain.member.service.MemberService;
import A1BnB.backend.domain.photo.dto.PhotoInfo;
import A1BnB.backend.domain.photo.model.entity.Photo;
import A1BnB.backend.domain.photo.service.PhotoService;
import A1BnB.backend.domain.post.dto.request.PostBookRequest;
import A1BnB.backend.domain.post.dto.response.PostDetailResponse;
import A1BnB.backend.domain.post.dto.request.PostSearchRequest;
import A1BnB.backend.domain.post.dto.mapper.PostDetailResponseMapper;
import A1BnB.backend.domain.post.model.entity.Post;
import A1BnB.backend.domain.post.repository.PostRepository;
import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.dto.request.PostUploadRequest;
import A1BnB.backend.domain.post.dto.response.PostResponse;
import A1BnB.backend.domain.post.dto.mapper.PostResponseMapper;
import A1BnB.backend.domain.postBook.service.PostBookService;
import A1BnB.backend.domain.postLike.service.PostLikeService;
import A1BnB.backend.global.exception.PostException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
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

    private final PostResponseMapper postResponseMapper;
    private final PostDetailResponseMapper postDetailResponseMapper;

    // 게시물 등록
    @Override
    @Transactional
    @CacheEvict(cacheNames = "findPosts", allEntries = true)
    public void registerPost(String username, PostUploadRequest requestParam) {
        Member currentMember = memberService.findMember(username);
        List<Photo> photos = photoService.findPhotos(requestParam.photoIdList());
        List<Date> availableDates = dateService.getDates(requestParam.startDate(), requestParam.endDate());
        savePost(requestParam, currentMember, photos, availableDates);
    }

    // Post 엔티티 저장
    private void savePost(PostUploadRequest requestParam, Member currentMember, List<Photo> photos,
                          List<Date> availableDates) {
        Post post = Post.builder()
                .author(currentMember)
                .location(requestParam.location())
                .photos(photos)
                .pricePerNight(requestParam.pricePerNight())
                .maximumOccupancy(requestParam.maximumOccupancy())
                .caption(requestParam.caption())
                .availableDates(availableDates)
                .build();
        postRepository.save(post);
    }

    // 게시물 응답 DTO Page 반환
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "findPosts", keyGenerator = "cachingKeyGenerator")
    public Page<PostResponse> getAllPosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        // 게시물 응답 DTO 반환
        return postPage.map(postResponseMapper::toPostResponse);
    }

    // 게시물 검색 응답 DTO Page 반환
    @Override
    @Transactional(readOnly = true)
    public Page<PostResponse> searchByCondition(PostSearchRequest requestParam, Pageable pageable) {
        // 게시물 검색
        List<LocalDateTime> searchDates = getSearchDates(requestParam.checkInDate(), requestParam.checkOutDate());
        Page<Post> postPage = postRepository.search(requestParam, searchDates, pageable);
        return postPage.map(postResponseMapper::toPostResponse);
    }

     List<LocalDateTime> getSearchDates(LocalDateTime checkInDate, LocalDateTime checkOutDate){
        if (checkInDate == null || checkOutDate == null){
            return null;
        }
        return dateService.getLocalDateTimeDates(checkInDate, checkOutDate);
    }

    // 게시물 상세 DTO 반환
    @Override
    @Transactional(readOnly = true)
    public PostDetailResponse getPostDetail(String username, Long postId) {
        Post post = findPostByPostId(postId);
        List<PhotoInfo> photoInfos = photoService.getPhotoInfos(post.getPhotos());
        // 인증 O
        if (username!=null) {
            Member currentMember = memberService.findMember(username);
            return postDetailResponseMapper.toPostDetailResponse(currentMember, post, photoInfos);
        }
        // 인증 X
        return postDetailResponseMapper.toPostDetailResponse(null, post, photoInfos);
    }


    @Override
    @Transactional
    public void likePost(String username, Long postId) {
        Post post = findPostByPostId(postId);
        Member currentMember = memberService.findMember(username);
        postLikeService.likePost(post, currentMember);
    }

    @Override
    @Transactional
    public void unlikePost(String username, Long postId) {
        Post post = findPostByPostId(postId);
        Member currentMember = memberService.findMember(username);
        postLikeService.unlikePost(post, currentMember);
    }

    @Override
    @Transactional
    public void bookPost(String username, Long postId, PostBookRequest requestParam) {
        Post post = findPostByPostId(postId);
        Member currentMember = memberService.findMember(username);
        postBookService.bookPost(post, currentMember, requestParam.checkInDate(), requestParam.checkOutDate());
    }

    @Override
    @Transactional
    public void unbookPost(String username, Long postId) {
        Post post = findPostByPostId(postId);
        Member currentMember = memberService.findMember(username);
        postBookService.unbookPost(post, currentMember);
    }

    // 게시물 단일 조회
    public Post findPostByPostId(Long postId){
        return postRepository.findByPostId(postId)
                .orElseThrow(()->new PostException(POST_NOT_FOUND.getMessage()));
    }

}