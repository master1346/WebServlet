package ru.netology.repository;

import ru.netology.model.Post;

import java.util.*;

public class PostRepository {
  private final Map<Long, Post> posts = new HashMap<>();
  private static long id = 0L;

  public List<Post> all() {
    return new ArrayList<>(posts.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public synchronized Post save(Post post) {
   if(post.getId() == 0){
     long newIdPost = id++;
     posts.put(newIdPost, post.withMewId(newIdPost));
     return post.withMewId(newIdPost);
   }
   if(post.getId() > id){
       long newIdPost = id++;
       posts.put(newIdPost, post.withMewId(newIdPost));
       return post.withMewId(newIdPost);
   }
   if(post.getId() <= id){
         long newIdPost = post.getId();
         posts.put(newIdPost, post);
         return post.withMewId(newIdPost);
   }
   return null;
 }

  public Post removeById(long id) {
     return posts.remove(id);
  }
}
