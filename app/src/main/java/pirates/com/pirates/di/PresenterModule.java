package pirates.com.pirates.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pirates.com.pirates.repository.NetworkService;
import pirates.com.pirates.ui.detail_screen.DetailPresenter;
import pirates.com.pirates.ui.main_screen.MainPresenter;


@Module
 class PresenterModule {

    @Provides
    @Singleton
    MainPresenter MainPresenter(NetworkService networkService) {
        return new MainPresenter(networkService);
    }

    @Provides
    @Singleton
    DetailPresenter  DetailPresenter(NetworkService networkService) {
        return new  DetailPresenter(networkService);
    }

}