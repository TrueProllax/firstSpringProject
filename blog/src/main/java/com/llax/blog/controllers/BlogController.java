package com.llax.blog.controllers;

import com.llax.blog.models.Post;
import com.llax.blog.repo.PostRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BlogController {
    
    @Autowired
    private PostRepository postRep;
    
    @GetMapping("/blog")
    public String blogMain (Model model) {
        model.addAttribute("title", "Страница блога");
        Iterable<Post> posts = postRep.findAll();
        model.addAttribute("posts", posts);
        return "blog-main"; 
    }
    
    @GetMapping("/blog/add")
    public String blogAdd (Model model) {
        model.addAttribute("title", "Добавление поста");
        return "blog-add"; 
    }
    
    @PostMapping("/blog/add")
    public String blogPostAdd (@RequestParam String title, @RequestParam String anons, @RequestParam String full_text , Model model) {
       Post post = new Post(title, anons, full_text);
       postRep.save(post);
       return "redirect:/blog"; 
    }
    
    @GetMapping("/blog/{id}")
    public String blogDetails (@PathVariable(value = "id") long id, Model model) {
        
        if (!postRep.existsById(id)) {
            return "redirect:/blog"; 
        } else {
            Optional<Post> post = postRep.findById(id);
            ArrayList<Post> res = new ArrayList<>();
            post.ifPresent(res::add);
            model.addAttribute("post",res);
            model.addAttribute("title", post.get().getTitle());
            return "blog-details"; 
        }
    }
    
    @GetMapping("/blog/{id}/edit")
    public String blogEdit (@PathVariable(value = "id") long id, Model model) {
        
        if (!postRep.existsById(id)) {
            return "redirect:/blog"; 
        } else {
            Optional<Post> post = postRep.findById(id);
            ArrayList<Post> res = new ArrayList<>();
            post.ifPresent(res::add);
            model.addAttribute("post",res);
            model.addAttribute("title", "Редактирование статьи: " + post.get().getTitle());
            return "blog-edit"; 
        }
    }
    
    
    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate (@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text , Model model) {
       Post post = postRep.findById(id).orElseThrow();
       post.setTitle(title);
       post.setAnons(anons);
       post.setFull_text(full_text);
       postRep.save(post);
       return "redirect:/blog"; 
    }
    
    @PostMapping("/blog/{id}/remove")
    public String blogPostRemove (@PathVariable(value = "id") long id, Model model) {
       Post post = postRep.findById(id).orElseThrow();
       postRep.delete(post);
       return "redirect:/blog"; 
    }
}
