package A1BnB.backend.domain.post.service;

import static A1BnB.backend.domain.member.exception.constants.MemberExceptionMessages.MEMBER_NAME_NOT_FOUND;

import A1BnB.backend.domain.file.service.FileSystemService;
import A1BnB.backend.domain.member.exception.MemberNotFoundException;
import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.model.entity.Post;
import A1BnB.backend.domain.post.repository.PostRepository;
import A1BnB.backend.domain.post.dto.request.PostUploadRequest;
import A1BnB.backend.domain.post.dto.response.PostResponse;
import A1BnB.backend.domain.post.dto.response.mapper.PostResponseMapper;
import A1BnB.backend.domain.member.repository.MemberRepository;
import java.io.IOException;
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
    @Autowired
    private final FileSystemService fileService;

    // 게시물 등록
    @Override
    public void registerPost(String userName, PostUploadRequest uploadParam) throws IOException {
        // 로그인 중인 회원 조회
        Member currentMember = findMember(userName);

        // 사진 경로 반환
        String photoName = UUID.randomUUID() + "_" + uploadParam.photos()[0].getOriginalFilename();
        String photoUrl = fileService.uploadPhoto(uploadParam.photos(), photoName);

        // Post 엔티티 저장
        Post post = Post.builder()
                .author(currentMember)
                .photoUrl(photoUrl)
                .location(uploadParam.location())
                .build();
        postRepository.save(post);
    }

    // 게시물 전체 조회
    @Override
    public List<PostResponse> getAllPosts() {
        // 게시물 전체 조회
        List<Post> posts = postRepository.findAll();

        // 게시물 응답 DTO 변환
        List<PostResponse> postResponses = postResponseMapper.toPostResponses(posts);
        return postResponses;
    }

    // 이름으로 찾아서 반환
    private Member findMember(String userName){
        Member member = memberRepository.findByName(userName)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NAME_NOT_FOUND.getMessage()));
        return member;
    }
}