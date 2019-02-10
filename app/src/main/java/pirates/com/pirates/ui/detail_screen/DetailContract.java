package pirates.com.pirates.ui.detail_screen;

import java.util.List;

import pirates.com.pirates.model.Comment;
import pirates.com.pirates.model.Photo;
import pirates.com.pirates.model.User;
import pirates.com.pirates.ui.BasePresenter;
import pirates.com.pirates.ui.BaseView;

public class DetailContract {

    public interface View extends BaseView<Presenter> {
        void showProgress();

        void hideProgress();

        void showError();

        void loadUser(User user);

        void loadPhotos(List<Photo> photoList);

        void loadComments(List<Comment> commentList);

    }

    interface Presenter extends BasePresenter<View> {

        void loadUserData(int userId, int postId);

    }
}
