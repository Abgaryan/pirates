package pirates.com.pirates.ui.main_screen;

import android.annotation.SuppressLint;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pirates.com.pirates.di.ConfigPersistent;
import pirates.com.pirates.model.Post;
import pirates.com.pirates.repository.NetworkService;
import pirates.com.pirates.rx.RxUtils;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link MainActivity}), retrieves the data and updates the UI
 * as required.
 */
@ConfigPersistent
public class MainPresenter implements MainContract.Presenter, Provider<MainPresenter> {


    private Disposable mDisposable;

    @NonNull
    private MainContract.View mMainView;

    private NetworkService mNetworkService;

    private int mCurrentPage;


    @Inject
    public MainPresenter(NetworkService networkService) {
        mNetworkService = networkService;
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void attachView(@NonNull MainContract.View mainView) {
        mMainView = checkNotNull(mainView, "mainView cannot be null!");
    }


    @Override
    public void detachView() {
        if (mDisposable != null) mDisposable.dispose();

    }


    @Override
    public void loadPosts() {
        mMainView.showProgress();
        RxUtils.dispose(mDisposable);
        mCurrentPage = 1;
        loadCurrentPagePosts(new Observer<List<Post>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(List<Post> postList) {
                mMainView.hideProgress();
                if (!postList.isEmpty()) {
                    mMainView.loadPosts(postList);
                }
            }

            @Override
            public void onError(Throwable e) {
                mMainView.hideProgress();
                mMainView.showError();
            }

            @Override
            public void onComplete() {
                mMainView.hideProgress();
            }
        });
    }

    @Override
    public void loadMore() {
        mCurrentPage++;
        mMainView.showProgressAdapter();
         loadCurrentPagePosts(new Observer<List<Post>>() {
             @Override
             public void onSubscribe(Disposable d) {
                 mDisposable = d;
             }

             @Override
             public void onNext(List<Post> postList) {
                 mMainView.hideProgressAdapter();
                 if (!postList.isEmpty()) {
                     mMainView.loadMore(postList);
                 }
             }

             @Override
             public void onError(Throwable e) {
                 mMainView.hideProgressAdapter();
                 mMainView.showError();
             }

             @Override
             public void onComplete() {
                 mMainView.hideProgressAdapter();
             }
         });

    }

    @Override
    public void onItemClicked(int postId, int userId) {

    }

    private void loadCurrentPagePosts(Observer<List<Post>> observer) {
        mNetworkService.getPosts(mCurrentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);

    }

    @Override
    public MainPresenter get() {
        return this;
    }
}
