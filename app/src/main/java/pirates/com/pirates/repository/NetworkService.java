package pirates.com.pirates.repository;

import io.reactivex.Observable;
import pirates.com.pirates.model.Post;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkService {
    @GET("posts")
    Observable<Post> getPosts(@Query("page") int pg);

}
