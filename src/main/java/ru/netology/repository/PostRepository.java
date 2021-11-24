package ru.netology.repository;

;
import ru.netology.model.Post;

import java.util.*;


public class PostRepository {
  private final Map<Long, Post> posts = new HashMap<>();
  private Long id;

  public List<Post> all() {
    return new ArrayList<>(posts.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public synchronized Post save(Post post) {
    long newId = id;
    Post updatePost = post.withMewId(newId);
    posts.put(id, post.withMewId(id));
    return updatePost;
  }

  public void removeById(long id) {
  }
}
