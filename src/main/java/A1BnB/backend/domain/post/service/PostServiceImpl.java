package A1BnB.backend.domain.post.service;

import static A1BnB.backend.domain.member.exception.constants.MemberExceptionMessages.MEMBER_NAME_NOT_FOUND;

import A1BnB.backend.domain.member.exception.MemberNotFoundException;
import A1BnB.backend.domain.member.service.MemberService;
import A1BnB.backend.domain.photo.dto.PhotoInfo;
import A1BnB.backend.domain.photo.model.entity.Photo;
import A1BnB.backend.domain.photo.service.PhotoService;
import A1BnB.backend.domain.post.dto.response.PostDetailResponse;
import A1BnB.backend.domain.post.dto.request.PostSearchRequest;
import A1BnB.backend.domain.post.dto.PostSearchResult;
import A1BnB.backend.domain.post.dto.mapper.PostDetailResponseMapper;
import A1BnB.backend.domain.post.model.entity.Post;
import A1BnB.backend.domain.post.model.entity.PostLikeInfo;
import A1BnB.backend.domain.post.repository.PostLikeRepository;
import A1BnB.backend.domain.post.repository.PostRepository;
import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.dto.request.PostUploadRequest;
import A1BnB.backend.domain.post.dto.response.PostResponse;
import A1BnB.backend.domain.post.dto.mapper.PostResponseMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final MemberService memberService;
    private final PhotoService photoService;
    private final PostResponseMapper postResponseMapper;
    private final PostDetailResponseMapper postDetailResponseMapper;

    // 게시물 등록
    @Override
    @Transactional
    public void registerPost(String username, PostUploadRequest requestParam) {
        Member currentMember = memberService.findMember(username);
        List<Photo> photos = photoService.getPhotos(requestParam.photoIdList());
        List<LocalDateTime> availableDates = initAvailableDates(requestParam.startDate(), requestParam.endDate());
        savePost(requestParam, currentMember, photos, availableDates);
    }

    private List<LocalDateTime> initAvailableDates(LocalDateTime startDate, LocalDateTime endDate) {
        return Stream.iterate(startDate, date -> date.isBefore(endDate.plusDays(1)), date -> date.plusDays(1))
                .collect(Collectors.toList());
    }

    // Post 엔티티 저장
    private void savePost(PostUploadRequest requestParam, Member currentMember, List<Photo> photos,
                          List<LocalDateTime> availableDates) {
        Post post = Post.builder()
                .author(currentMember)
                .location(requestParam.location())
                .photos(photos)
                .availableDates(availableDates)
                .pricePerNight(requestParam.pricePerNight())
                .maximumOccupancy(requestParam.maximumOccupancy())
                .caption(requestParam.caption())
                .build();
        postRepository.save(post);
    }

    // 게시물 응답 DTO Page 반환
    @Override
    @Transactional(readOnly = true)
    public Page<PostResponse> getAllPosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        // 게시물 응답 DTO 반환
        return postPage.map(postResponseMapper::toPostResponse);
    }

    // 게시물 검색 응답 DTO Page 반환
    @Override
    @Transactional(readOnly = true)
    public Page<PostResponse> searchByCondition(PostSearchRequest searchCondition, Pageable pageable) {
        // 게시물 검색
        List<PostSearchResult> searchResults = postRepository.search(searchCondition);
        List<Long> postIdList = makePostIdList(searchResults);
        // 게시물 조회
        Page<Post> postPage = postRepository.findAllByIdList(postIdList, pageable);
        return postPage.map(postResponseMapper::toPostResponse);
    }

    // postId 리스트 반환
    private List<Long> makePostIdList(List<PostSearchResult> searchResults){
        return searchResults.stream()
                .map(PostSearchResult::postId)
                .collect(Collectors.toList());
    }

    // 게시물 상세 DTO 반환
    @Override
    @Transactional(readOnly = true)
    public PostDetailResponse getPostDetail(String username, Long postId) {
        Post post = findPostByPostId(postId);
        List<PhotoInfo> photoInfoList = photoService.getPhotoInfoList(post.getPhotos());
        Member currentMember = findMember(username);
        return postDetailResponseMapper.toPostDetailResponse(currentMember, post, photoInfoList);
    }

    private Member findMember(String username){
        if (username!=null){
            return memberService.findMember(username);
        }
        return null;
    }

    @Override
    @Transactional
    public void likePost(String username, Long postId) {
        Post post = findPostByPostId(postId);
        Member currentMember = memberService.findMember(username);
        savePostLike(post, currentMember);
    }

    private void savePostLike(Post post, Member currentMember) {
        PostLikeInfo postLikeInfo = PostLikeInfo.builder()
                .post(post)
                .member(currentMember)
                .build();
        postLikeRepository.save(postLikeInfo);
    }

    @Override
    @Transactional
    public void unlikePost(String username, Long postId) {
        Post post = findPostByPostId(postId);
        Member currentMember = memberService.findMember(username);
        PostLikeInfo postLikeInfo = findByPostAndMember(post, currentMember);
        postLikeRepository.delete(postLikeInfo);
    }

    private PostLikeInfo findByPostAndMember(Post post, Member currentMember){
        return postLikeRepository.findByPostAndMember(post, currentMember)
                .orElseThrow(()->new MemberNotFoundException(MEMBER_NAME_NOT_FOUND.getMessage()));
    }

    // 게시물 상세 조회
    @Transactional(readOnly = true)
    public Post findPostByPostId(Long postId){
        return postRepository.findByPostId(postId)
                .orElseThrow(()->new MemberNotFoundException(MEMBER_NAME_NOT_FOUND.getMessage()));
    }

}