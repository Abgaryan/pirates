package pirates.com.pirates.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import pirates.com.pirates.repository.NetworkService;
import pirates.com.pirates.ui.detail_screen.DetailActivity;
import pirates.com.pirates.ui.detail_screen.DetailPresenter;
import pirates.com.pirates.ui.main_screen.MainActivity;
import pirates.com.pirates.ui.main_screen.MainPresenter;

@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                NetModule.class,
                PresenterModule.class
        }
)
public interface ApplicationComponent {
    Context context();
    NetworkService networkService();

    void inject(MainActivity mainActivity);

    void inject(MainPresenter mainPresenter);

    void inject(DetailActivity detailActivity);

    void inject(DetailPresenter detailPresenter);


}
