package com.frontbackend.springboot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.frontbackend.springboot.model.Post;
import com.frontbackend.springboot.model.PostRequest;
import com.frontbackend.springboot.service.PostService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/posts")
public class PostsController {

    private final PostService postService;

    @Autowired
    public PostsController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Post> post(@PathVariable String id) {
        System.out.println("id224455=" + id);
        Optional<Post> post = postService.findById(id);
        return post.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound()
                        .build());
    }

    @GetMapping
    public List<Post> list(@RequestParam(required = false) String title) {
        System.out.println("title=" + title);
        if (StringUtils.isEmpty(title)) {
            System.out.println("Eampty Title");
            return postService.getAll();
        }
        return postService.findByTitle(title);
    }

    @PostMapping
    public String save(@RequestBody PostRequest request) {
        return postService.save(request);
    }

    @PutMapping("{id}/publish")
    public void publishUnpublish(@PathVariable String id, @RequestBody PostRequest request) {
        postService.changePublishedFlag(id, request);
    }

    @PutMapping("{id}")
    public void update(@PathVariable String id, @RequestBody PostRequest request) {
        Optional<Post> post = postService.findById(id);
        if (post.isPresent()) {
            postService.update(id, request);
        } else {
            postService.save(request);
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        postService.delete(id);
    }
}