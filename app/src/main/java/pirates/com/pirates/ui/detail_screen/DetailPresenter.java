package pirates.com.pirates.ui.detail_screen;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pirates.com.pirates.di.ConfigPersistent;
import pirates.com.pirates.model.Comment;
import pirates.com.pirates.model.Photo;
import pirates.com.pirates.model.User;
import pirates.com.pirates.repository.NetworkService;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link DetailActivity}), retrieves the data and updates the UI
 * as required.
 */
@ConfigPersistent
public class DetailPresenter implements DetailContract.Presenter, Provider<DetailPresenter> {


    private Disposable mUserDisposable;
    private Disposable mPhotosDisposable;
    private Disposable mCommentsDisposable;

    @NonNull
    private DetailContract.View mDetailView;

    private NetworkService mNetworkService;


    @Inject
    public DetailPresenter(NetworkService networkService) {
        mNetworkService = networkService;
    }


    @Override
    public void attachView(DetailContract.View view) {
        mDetailView = checkNotNull(view, "detailView cannot be null!");
    }


    @Override
    public DetailPresenter get() {
        return this;
    }

    @Override
    public void loadUserData(int userId, int postId) {
        loadUser(userId);
        loadPhotos(userId, postId);
        loadComments(userId, postId);
    }

    private void loadUser(int userId) {
        mDetailView.showProgress();
        mNetworkService.getUser(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<User>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        mUserDisposable = d;
                    }

                    @Override
                    public void onNext(User user) {
                        mDetailView.loadUser(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mDetailView.hideProgress();
                        mDetailView.showError();
                    }

                    @Override
                    public void onComplete() {
                        mDetailView.hideProgress();
                    }
                });
    }

    private void loadPhotos(int userId, int postId) {
        mDetailView.showProgress();
        mNetworkService.getPhotos(userId, postId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Photo>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        mPhotosDisposable = d;
                    }

                    @Override
                    public void onNext(List<Photo> photoList) {
                        //load only 16 photos
                        List<Photo> shortenList;
                        if (photoList != null && photoList.size() > 16) {
                            shortenList = photoList.subList(0, 12);
                        } else {
                            shortenList = photoList;
                        }
                        if (shortenList != null && !shortenList.isEmpty()) {
                            mDetailView.loadPhotos(shortenList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mDetailView.hideProgress();
                        mDetailView.showError();
                    }

                    @Override
                    public void onComplete() {
                        mDetailView.hideProgress();
                    }
                });
    }

    private void loadComments(int userId, int postId) {
        mDetailView.showProgress();
        mNetworkService.getComments(userId, postId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Comment>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        mCommentsDisposable = d;
                    }

                    @Override
                    public void onNext(List<Comment> commentList) {
                        mDetailView.loadComments(commentList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mDetailView.hideProgress();
                        mDetailView.showError();
                    }

                    @Override
                    public void onComplete() {
                        mDetailView.hideProgress();
                    }
                });
    }


    @Override
    public void detachView() {
        if (mUserDisposable != null) mUserDisposable.dispose();
        if (mPhotosDisposable != null) mPhotosDisposable.dispose();
        if (mCommentsDisposable != null) mCommentsDisposable.dispose();
    }
}
