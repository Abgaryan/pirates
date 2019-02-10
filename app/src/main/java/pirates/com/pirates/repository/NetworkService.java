package pirates.com.pirates.repository;

import java.util.List;

import io.reactivex.Observable;
import pirates.com.pirates.model.Comment;
import pirates.com.pirates.model.Photo;
import pirates.com.pirates.model.Post;
import pirates.com.pirates.model.User;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkService {
    @GET("posts")
    Observable<List<Post>> getPosts(@Query("page") int pg);

    @GET("users/{userId}")
    Observable<User> getUser(@Path("userId") int userId);

    @GET("users/{userId}/comments")
    Observable<List<Comment>> getComments(@Path("userId") int userId,@Query("postId") int postId);

    @GET("users/{userId}/photos")
    Observable<List<Photo>> getPhotos(@Path("userId") int userId, @Query("postId") int postId);

}
