package com.example.demo.service;


import com.example.demo.dto.PostRequest;
import com.example.demo.dto.PostResponse;
import com.example.demo.entity.Post;
import com.example.demo.vo.PostVO;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface PostService {

    public List<Post> getPostByUid(String uid);

    public Post getPostDetail(Integer pid);

    public Optional<String> addPost(MultipartFile[] file,PostRequest postRequest);

    public Optional<String> updatePostLikes(Integer pid,String uid);

    public List<PostResponse> getPostCoverByUid(String uid);

    public List<PostResponse> getPostCoverForPersonalPageByUid(String uid);

    public List<Post> getAllPostByIDPaging(Pageable pageable);

    public List<PostResponse> getPostCoverByCategory(Integer cid);

    public List<Post> searchPost(String text, List<String> fields, int limit);

}
