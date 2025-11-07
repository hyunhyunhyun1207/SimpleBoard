package com.board.simpleboard.controller;

import com.board.simpleboard.dto.member.response.LoginRes;
import com.board.simpleboard.dto.post.response.PostDetailRes;
import com.board.simpleboard.dto.post.response.PostListRes;
import com.board.simpleboard.service.PostService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post") // localhost:8088/post
public class PostController {

    private final PostService postService;
    private final String uploadDir = "C:/upload/";

    @GetMapping({"", "/"})
    public String postList(@RequestParam(defaultValue = "1") int page, Model model) {
        PostListRes res = postService.getPostList(page);
        model.addAttribute("res", res);
        return "post/posts";
    }

    @GetMapping("/create")
    public String postCreate(HttpSession session) {
        if (session.getAttribute("member") == null) {
            return "redirect:/member/login";
        }
        return "post/post-create";
    }

    @PostMapping("/create")
    public String postCreateP(
            @RequestParam String title,
            @RequestParam String content,
            @SessionAttribute("member") LoginRes member,
            @RequestParam(required = false) String imageUuidsJson
    ) throws Exception {
        List<String> imageUuids = new ArrayList<>();
        if (imageUuidsJson != null && !imageUuidsJson.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            imageUuids = mapper.readValue(imageUuidsJson, new TypeReference<List<String>>() {});
        }

        postService.createPost(member.getId(), title, content, imageUuids);
        return "redirect:/post";
    }

    @GetMapping("/{postId}")
    public String postDetail(@PathVariable Long postId, Model model) {
        PostDetailRes post = postService.getPostDetail(postId);
        model.addAttribute("post", post);
        return "post/post-detail";
    }

    @PostMapping("/uploadImage")
    @ResponseBody
    public Map<String, Object> uploadImage(@RequestParam("image") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();

        try {
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);

            Files.copy(file.getInputStream(), filePath);

            postService.saveImageInfo(
                    uuid,
                    fileName,
                    "/upload/" + fileName,
                    file.getSize(),
                    file.getContentType()
            );

            result.put("url", "/upload/" + fileName);
            result.put("uuid", uuid);
        } catch (Exception e) {
            result.put("error", e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

}
