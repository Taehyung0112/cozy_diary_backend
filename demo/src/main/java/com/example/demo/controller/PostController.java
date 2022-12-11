package com.example.demo.controller;


import com.example.demo.dto.PostRequest;
import com.example.demo.dto.PostResponse;
import com.example.demo.dto.SearchRequestDTO;
import com.example.demo.entity.Post;
import com.example.demo.service.FileStorageService;
import com.example.demo.service.PostService;
import com.example.demo.util.JsonResponse;
import com.example.demo.vo.PostUpdateVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    FileStorageService storageService;

    ObjectMapper objectMapper = new ObjectMapper();

    @CrossOrigin(origins = "http://localhost:10001")
    @RequestMapping(value = "/addPost", method = RequestMethod.POST)
    public ResponseEntity<?> addPost(@RequestParam(required=true, value="file") MultipartFile[] file,@RequestParam(required=true, value="jsondata") String jsonData){
        try {
            PostRequest postRequest = new PostRequest();
            postRequest = objectMapper.readValue(jsonData, PostRequest.class);
            Optional<String> optional = postService.addPost(file,postRequest);
//            try {
//                storageService.savePost(file,optional.get(),postRequest.getPost().getUid());
//            }catch (Exception e){
//                System.out.println("file upload error:"+e);
//            }
            return JsonResponse.generateResponse(optional.get(), HttpStatus.OK,"");
        }catch (Exception e){
            System.out.println(e);
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @CrossOrigin(origins = "http://172.20.10.10:8080")
    @RequestMapping(value = "/getPostCoverByUserCategory", method = RequestMethod.GET) //此功能是根據使用者追蹤的主題類別去抓貼文列表(只抓封面跟重點資訊
    public ResponseEntity<Object> getPostCoverByUid(@RequestParam String uid){
        try{
            List<PostResponse> post = postService.getPostCoverByUid(uid);
            return JsonResponse.generateResponse("獲取用戶貼文成功", HttpStatus.OK, post);
        }catch (Exception e){
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @CrossOrigin(origins = "http://172.20.10.10:8080")
    @RequestMapping(value = "/getPostCoverForLiked", method = RequestMethod.GET) //此功能是根據使用者追蹤的主題類別去抓貼文列表(只抓封面跟重點資訊
    public ResponseEntity<Object> getPostCoverForLiked(@RequestParam String uid){
        try{
            List<PostResponse> post = postService.getPostCoverForLiked(uid);
            return JsonResponse.generateResponse("獲取用戶貼文成功", HttpStatus.OK, post);
        }catch (Exception e){
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @CrossOrigin(origins = "http://172.20.10.10:8080")
    @RequestMapping(value = "/getPostCoverForCollected", method = RequestMethod.GET) //此功能是根據使用者追蹤的主題類別去抓貼文列表(只抓封面跟重點資訊
    public ResponseEntity<Object> getPostCoverForCollected(@RequestParam String uid){
        try{
            List<PostResponse> post = postService.getPostCoverForCollected(uid);
            return JsonResponse.generateResponse("獲取用戶貼文成功", HttpStatus.OK, post);
        }catch (Exception e){
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @CrossOrigin(origins = "http://172.20.10.10:8080")
    @RequestMapping(value = "/getPostByUid", method = RequestMethod.GET)//此功能是抓取該使用者發過的貼文
    public ResponseEntity<Object> getPostByUid(@RequestParam String uid){
        try{
            List<Post> post = postService.getPostByUid(uid);
            return JsonResponse.generateResponse("獲取用戶貼文成功", HttpStatus.OK, post);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @CrossOrigin(origins = "http://172.20.10.10:8080")
    @RequestMapping(value = "/getPostDetail", method = RequestMethod.GET)//點進選貼文後根據貼文id再抓詳細資料
    public ResponseEntity<Object> getPostDetailByPid(@RequestParam Integer pid){
        try{
            Post post = postService.getPostDetail(pid);
            return JsonResponse.generateResponse("獲取用戶貼文成功", HttpStatus.OK, post);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @CrossOrigin(origins = "http://172.20.10.10:8080")
    @RequestMapping(value = "/getPostCoverForPersonalPage", method = RequestMethod.GET)
    public ResponseEntity<Object> getPostCoverForPersonalPage(@RequestParam String uid){
        try{
            List<PostResponse> post = postService.getPostCoverForPersonalPageByUid(uid);
            return JsonResponse.generateResponse("獲取用戶貼文成功", HttpStatus.OK, post);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @CrossOrigin(origins = "http://localhost:10001")
    @RequestMapping(value = "/updatePost", method = RequestMethod.POST)
    public ResponseEntity<?> updatePost(@RequestBody PostUpdateVO postUpdateVO){
        try {
            Optional<String> optional = postService.updatePost(postUpdateVO);
            return JsonResponse.generateResponse(optional.get(), HttpStatus.OK,"");
        }catch (Exception e){
            System.out.println(e);
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @CrossOrigin(origins = "http://localhost:10001")
    @RequestMapping(value = "/updatePostLikes", method = RequestMethod.POST)
    public ResponseEntity<?> updateLikes(@RequestParam Integer pid, @RequestParam String uid){
        try {
            Optional<String> optional = postService.updatePostLikes(pid,uid);
            return JsonResponse.generateResponse(optional.get(), HttpStatus.OK,"");
        }catch (Exception e){
            System.out.println(e);
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @CrossOrigin(origins = "http://localhost:10001")
    @RequestMapping(value = "/updateCollects", method = RequestMethod.POST)
    public ResponseEntity<?> updateCollects(@RequestParam Integer pid, @RequestParam String uid){
        try {
            Optional<String> optional = postService.updatePostCollects(pid,uid);
            return JsonResponse.generateResponse(optional.get(), HttpStatus.OK,"");
        }catch (Exception e){
            System.out.println(e);
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @CrossOrigin(origins = "http://172.20.10.10:8080")
    @RequestMapping(value = "/getPostCoverByCategory", method = RequestMethod.GET) //此功能是根據使用者追蹤的主題類別去抓貼文列表(只抓封面跟重點資訊
    public ResponseEntity<Object> getPostCoverByCategory(@RequestParam Integer cid){
        try{
            List<PostResponse> post = postService.getPostCoverByCategory(cid);
            return JsonResponse.generateResponse("獲取用戶貼文成功", HttpStatus.OK, post);
        }catch (Exception e){
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @CrossOrigin(origins = "http://localhost:10001")
    @RequestMapping(value = "/getAllPost", method = RequestMethod.GET)
    public  ResponseEntity<Object> getAllPost(@RequestParam Integer page, @RequestParam Integer rows){
        try {
            Pageable pageable = PageRequest.of(page, rows, Sort.by("id"));
            return JsonResponse.generateResponse("獲取用戶貼文成功", HttpStatus.OK, postService.getAllPostByIDPaging(pageable));
        }catch (Exception e){
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }

    }
    @CrossOrigin(origins = "http://localhost:10001")
    @RequestMapping(value = "/searchPost", method = RequestMethod.GET)
    public  ResponseEntity<Object> getAllPost(SearchRequestDTO searchRequestDTO){
        try {
            List<Post> post = postService.searchPost(searchRequestDTO.getText(), searchRequestDTO.getFields(), searchRequestDTO.getLimit());
            return JsonResponse.generateResponse("獲取用戶貼文成功", HttpStatus.OK, post);
        }catch (Exception e){
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }

    }

    @CrossOrigin(origins = "http://localhost:10001")
    @RequestMapping(value = "/deletePost", method = RequestMethod.POST)
    public ResponseEntity<?> deletePost(@RequestParam Integer pid){
        try {
            Optional<String> optional = postService.deletePost(pid);
            return JsonResponse.generateResponse(optional.get(), HttpStatus.OK,"");
        }catch (Exception e){
            System.out.println(e);
            return JsonResponse.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
