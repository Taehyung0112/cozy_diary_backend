package com.example.demo.service.impl;


import com.example.demo.dao.*;
import com.example.demo.dto.PostRequest;
import com.example.demo.dto.PostResponse;
import com.example.demo.entity.*;
import com.example.demo.exception.DuplicateCreateException;
import com.example.demo.exception.ProjectException;
import com.example.demo.index.Indexer;
import com.example.demo.service.FileStorageService;
import com.example.demo.service.PostService;
import com.example.demo.vo.PostUpdateVO;
import com.example.demo.vo.PostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.multipart.MultipartFile;


import org.springframework.data.domain.Pageable;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PostServiceImpl  implements PostService {
    @Autowired
    private PostDao postDao;

    @Autowired
    private PostFileDao postFileDao;

    @Autowired
    private LikesDao likesDao;

    @Autowired
    private CollectsDao collectsDao;

    @Autowired
    private CommentsDao commentsDao;

    @Autowired
    private RepliesCommentDao repliesCommentDao;

    @Autowired
    FileStorageService storageService;


    private static final List<String> SEARCHABLE_FIELDS = Arrays.asList("title","content");


    @Override
    public List<Post> getPostByUid(String uid) {
        return postDao.findPostByUid(uid);
    }

    @Override
    public Post getPostDetail(Integer pid) {
        return postDao.findPostByPid(pid);
    }


    @Override
    public Optional<String> addPost(MultipartFile[] files,PostRequest postRequest) {
//        String basicUrl = "http://172.20.10.10:8080/staticFile/";
        String basicUrl = "http://140.131.114.166:80/staticFile/";

        try{
            Post post = new Post();
            Integer nextPid = 1;
            if (postDao.findNewPid() == null){
                postDao.resetPostId();
            } else {
                nextPid = postDao.findNewPid();
            }
            List<PostFile> pFile = new ArrayList<>();
            for(int i=0;i<files.length;i++){
                PostFile postFile = new PostFile();
                postFile.setPostUrl(basicUrl+postRequest.getPost().getUid()+"/postFile/"+nextPid+"/"+files[i].getOriginalFilename());
                pFile.add(postFile);
            }
            post.setUid(postRequest.getPost().getUid());
            post.setTitle(postRequest.getPost().getTitle());
            post.setCid(postRequest.getPost().getCid());
            post.setContent(postRequest.getPost().getContent());
            post.setLikes(0);
            post.setCollects(postRequest.getPost().getCollects());
            post.setPost_time(LocalDateTime.now().toString());
            post.setCover(basicUrl+postRequest.getPost().getUid()+"/postFile/"+nextPid+"/resize.jpeg");
            post.setPostFiles(pFile);
            post.setComments(postRequest.getPost().getComments());
            post.setPostLng(postRequest.getPost().getPostLng());
            post.setPostLat(postRequest.getPost().getPostLat());

            postDao.save(post);
            try{
                storageService.savePost(files,post.getPid().toString(),post.getUid(),postRequest.getPost().getCover());
            }catch (Exception e){
                System.out.println("file upload error:"+e);
                throw new ProjectException(e);
            }
            return Optional.of(post.getPid().toString());
        }catch (Exception e){
            System.out.println(e.toString());
            throw new ProjectException(e);
        }
    }

    @Override
    public Optional<String> updatePost(PostUpdateVO postUpdateVO) {
        Post post = postDao.findPostByPid(postUpdateVO.getPid());
        try {
            if(post != null){
                post.setTitle(postUpdateVO.getTitle());
                post.setContent(postUpdateVO.getContent());
                post.setModify_time(LocalDateTime.now().toString());
                postDao.save(post);
                return Optional.of("更新貼文成功");
            }else {
                return Optional.of("無效貼文ID");
            }
        }catch (Exception e){
            throw new ProjectException(e);
        }
    }

    @Override
    public Optional<String> updatePostLikes(Integer pid,String uid) {
        try {
            Likes likes = likesDao.getLikesByPidAndUidAndType(pid,uid ,0);
            Optional<Post> post = postDao.findById(pid);
            if(!post.isEmpty()){
                Integer newLikes = post.get().getLikes();
                if(Objects.isNull(likes)){
                    Likes addLikes = new Likes();
                    addLikes.setPid(pid);
                    addLikes.setUid(uid);
                    addLikes.setLike_time(LocalDateTime.now().toString());
                    addLikes.setType(0);
                    likesDao.save(addLikes);
                    newLikes +=1;
                    post.get().setLikes(newLikes);
                    postDao.save(post.get());
                    return Optional.of("新增讚數成功");
                } else {
                    newLikes -=1;
                    post.get().setLikes(newLikes);
                    postDao.save(post.get());
                    likesDao.delete(likes);
                    return Optional.of("取消讚數成功");
                }
            } else {
                return Optional.of("貼文ID有誤，查無此活動");
            }
        }catch (Exception e){
            throw new ProjectException(e);
        }
    }

    @Override
    public Optional<String> updatePostCollects(Integer pid, String uid) {
        try {
            Collects collects = collectsDao.getCollectsByPidAndUid(pid,uid);
            Optional<Post> post = postDao.findById(pid);
            if(!post.isEmpty()){
                Integer newCollects = post.get().getCollects();
                if(Objects.isNull(collects)){
                    Collects addCollects = new Collects();
                    addCollects.setPid(pid);
                    addCollects.setUid(uid);
                    addCollects.setCollectTime(LocalDateTime.now().toString());
                    collectsDao.save(addCollects);
                    newCollects +=1;
                    post.get().setCollects(newCollects);
                    postDao.save(post.get());
                    return Optional.of("新增典藏成功");
                } else {
                    newCollects -=1;
                    post.get().setCollects(newCollects);
                    postDao.save(post.get());
                    collectsDao.delete(collects);
                    return Optional.of("取消典藏成功");
                }
            } else {
                return Optional.of("貼文ID有誤，查無此活動");
            }
        }catch (Exception e){
            throw new ProjectException(e);
        }
    }

    @Override
    public List<PostResponse> getPostCoverByUid(String uid) {
        return postDao.findPostCoverByUid(uid);
    }

    @Override
    public List<PostResponse> getPostCoverForPersonalPageByUid(String uid) {
        return postDao.findPostCoverForPersonalPageByUid(uid);
    }

    @Override
    public List<PostResponse> getPostCoverForLiked(String uid) {
        return postDao.findPostCoverForLiked(uid);
    }

    @Override
    public List<PostResponse> getPostCoverForCollected(String uid) {
        return postDao.findPostCoverForCollected(uid);
    }

    @Override
    public List<Post> getAllPostByIDPaging(Pageable pageable) {
        return postDao.findPostByIdPaging(pageable);
    }

    @Override
    public List<PostResponse> getPostCoverByCategory(Integer cid) {
        return postDao.findPostCoverByCategory(cid);
    }

    @Override
    public List<Post> searchPost(String text, List<String> fields, int limit) {
        List<String> fieldsToSearchBy = fields.isEmpty() ? SEARCHABLE_FIELDS : fields;
        List<Post> returnPost;


        boolean containsInvalidField = fieldsToSearchBy.stream(). anyMatch(f -> !SEARCHABLE_FIELDS.contains(f));


        if(containsInvalidField) {
            throw new IllegalArgumentException();
        }
        try {
            returnPost = postDao.searchBy(text, limit, fieldsToSearchBy.toArray(new String[0]));
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException(e);
        }
        return returnPost;

    }

    @Override
    public Optional<String> deletePost(Integer pid) {
        try {
            Post post = postDao.findPostByPid(pid);
            List<Comments> comments = commentsDao.getCommentsByPid(pid);
            String uid ;
            if (post != null){
                for (int i = 0 ; i < post.getPostFiles().size() ; i++){
                    postFileDao.delete(post.getPostFiles().get(i));
                }
                for (int i = 0; i < comments.size(); i++){
                    repliesCommentDao.deleteRepliesComment(comments.get(i).getCommentId());
                }
                commentsDao.deleteComment(pid);
                collectsDao.deleteCollects(pid);
                likesDao.deleteLikes(pid);

                postDao.delete(post);
                uid = post.getUid();
                storageService.deleteAll(uid,pid);
                return Optional.of("貼文刪除成功");
            }else {
                return Optional.of("找不到該貼文");
            }
        }catch (Exception e){
            throw new DuplicateCreateException("貼文刪除失敗："+e);
        }
    }
}
