package com.example.demo.service.impl;


import com.example.demo.service.FileStorageService;
import com.idrsolutions.image.JDeli;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public class FileStorageServiceImpl implements FileStorageService {
//    String rootPath = "/Users/yangzhelun/Desktop/development/uploadFile/";
//    private final Path root = Paths.get("/Users/yangzhelun/Desktop/development/uploadFile");

    String rootPath = "/root/uploadFile/";
    private final Path root = Paths.get(rootPath);

    @Override
    public void init() {
        try {
            File uploadFolder = root.toFile();
            if(!uploadFolder.exists()){
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void savePost(MultipartFile[] files,String pid , String uid , String coverName) {
        Arrays.asList(files).forEach(file ->{
            try {
                Path userPath = Paths.get(rootPath + uid + "/postFile/" + pid);
                File userFile = userPath.toFile();
                System.out.println(rootPath);
                if(! userFile.exists()){
                    userFile.mkdir();
                }
                Files.copy(file.getInputStream(),userPath.resolve(file.getOriginalFilename()));

                if(Objects.equals(file.getOriginalFilename(), coverName)){
                    try {
                        if(file.getOriginalFilename().contains("HEIC")){
                            BufferedImage heic = JDeli.read(file.getInputStream());
                            File resize = new File(userPath.toString()+"/resize.jpeg");
                            JDeli.write(heic, "jpeg", resize);
                        }
                        Thumbnails.of(file.getInputStream()).scale(0.5f).outputFormat("jpeg").outputQuality(0.5).toFile(userPath.toString()+"/resize");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
            }
        });
    }

    @Override
    public void saveActivity(MultipartFile[] files, String aid, String uid ,String coverName) {
        Arrays.asList(files).forEach(file ->{
            try {
                Path userPath = Paths.get(rootPath + uid + "/activityFile/" + aid);
                File userFile = userPath.toFile();
                System.out.println(rootPath);
                if(! userFile.exists()){
                    userFile.mkdir();
                }
                Files.copy(file.getInputStream(),userPath.resolve(file.getOriginalFilename()));
                if(Objects.equals(file.getOriginalFilename(), coverName)){
                    try {
                        if(file.getOriginalFilename().contains("HEIC")){
                            BufferedImage heic = JDeli.read(file.getInputStream());
                            File resize = new File(userPath.toString()+"/resize.jpeg");
                            JDeli.write(heic, "jpeg", resize);
                        }
                        Thumbnails.of(file.getInputStream()).scale(0.5f).outputFormat("jpeg").outputQuality(0.5).toFile(userPath.toString()+"/resize");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
            }
        });
    }

    @Override
    public void deleteActivityAll(String uid , Integer aid) {
        String deletePath = rootPath + uid + "/activityFile/" + aid;
        Path deleteP = Paths.get(deletePath);
        try {
            System.out.println(deletePath);
            FileUtils.deleteDirectory(deleteP.toFile());
        }catch (Exception e){
            throw new RuntimeException("Error: " + e.getMessage());
        }
//        FileSystemUtils.deleteRecursively(deleteP.toFile());
    }

    @Override
    public void saveProfile(MultipartFile file, String uid) {
        try {
            Path userPath = Paths.get(rootPath + uid + "/userProfile/");
            File userFile = userPath.toFile();
            if(! userFile.exists()){
                userFile.mkdir();
            }
            Files.copy(file.getInputStream(),userPath.resolve(file.getOriginalFilename()));
            try {
                Thumbnails.of(file.getInputStream()).scale(0.5f).outputFormat("jpeg").outputQuality(0.5).toFile(userPath.toString()+"/resize");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }



    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll(String uid , Integer pid) {
        String deletePath = rootPath + uid + "/postFile/" + pid;
        Path deleteP = Paths.get(deletePath);
        try {
            System.out.println(deletePath);
            FileUtils.deleteDirectory(deleteP.toFile());
        }catch (Exception e){
            throw new RuntimeException("Error: " + e.getMessage());
        }
//        FileSystemUtils.deleteRecursively(deleteP.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
