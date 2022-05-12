
package com.llax.blog.repo;

import com.llax.blog.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
    
    
}
