package A1BnB.backend.post.service.impl;

import static A1BnB.backend.member.exception.constants.MemberExceptionMessages.MEMBER_ID_NOT_FOUND;

import A1BnB.backend.member.exception.MemberNotFoundException;
import A1BnB.backend.member.model.entity.Member;
import A1BnB.backend.post.dto.request.PostUploadRequest;
import A1BnB.backend.post.dto.response.PostResponse;
import A1BnB.backend.post.dto.response.mapper.PostResponseMapper;
import A1BnB.backend.post.model.Post;
import A1BnB.backend.post.repository.PostRepository;
import A1BnB.backend.post.service.PostService;
import A1BnB.backend.member.repository.MemberRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final PostResponseMapper postResponseMapper;

    // 게시물 등록
    @Override
    public void registerPost(Long memberId, PostUploadRequest uploadParam) {
        // 로그인 중인 회원 조회
        Member currentMember = findMember(memberId);

        // 사진 경로 반환
        String photoName = UUID.randomUUID() + "_" + uploadParam.photo().getOriginalFilename();
        String photoUrl = "1";

        // 게시물 저장
        Post post = Post.builder()
                .author(currentMember)
                .photoUrl(photoUrl)
                .caption(uploadParam.caption())
                .location(uploadParam.location())
                .build();
        postRepository.save(post);
    }

    // 게시물 전체 조회
    @Override
    public List<PostResponse> getAllPosts(Long memberId) {
        // 로그인 중인 회원 조회
        Member currentMember = findMember(memberId);

        // 게시물 전체 조회
        List<Post> posts = postRepository.findAll();

        // 게시물 응답 DTO 변환
        List<PostResponse> postResponses = postResponseMapper.toPostResponses(posts);
        return postResponses;
    }

    // 회원 반환
    private Member findMember(Long memberId){
        Member findMember = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_ID_NOT_FOUND.getMessage()));
        return findMember;
    }
}