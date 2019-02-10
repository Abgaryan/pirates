package pirates.com.pirates.repository;

import java.util.List;

import io.reactivex.Observable;
import pirates.com.pirates.model.Post;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkService {
    @GET("posts")
    Observable<List<Post>> getPosts(@Query("page") int pg);

}
