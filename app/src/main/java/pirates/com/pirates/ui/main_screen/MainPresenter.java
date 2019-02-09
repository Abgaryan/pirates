package pirates.com.pirates.ui.main_screen;

import android.annotation.SuppressLint;

import javax.inject.Inject;
import javax.inject.Provider;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import pirates.com.pirates.di.ConfigPersistent;
import pirates.com.pirates.repository.NetworkService;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link MainActivity}), retrieves the data and updates the
 * UI as required.
 */
@ConfigPersistent
public class MainPresenter implements MainContract.Presenter, Provider<MainPresenter> {



    private Disposable mDisposable;

    @NonNull
    private MainContract.View mMainView;

    private NetworkService  mNetworkService;


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
    public MainPresenter get() {
        return null;
    }
}
