package pirates.com.pirates.ui.main_screen;

import java.util.List;

import pirates.com.pirates.model.Post;
import pirates.com.pirates.ui.BasePresenter;
import pirates.com.pirates.ui.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
public class MainContract {

    public interface View extends BaseView<Presenter> {
        void showProgress();

        void hideProgress();

        void showError();

        void showProgressAdapter();

        void hideProgressAdapter();

        void  showErrorAdapter();

        void loadPosts(List<Post> postList);

        void loadMore(List<Post> postList);

        void navigateToPostDetail(int postId,int userId);

    }

    interface Presenter extends BasePresenter<View> {

        void loadPosts();
        void loadMore();
        void onItemClicked(int postId,int userId);


    }

}
