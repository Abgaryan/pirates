package pirates.com.pirates.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pirates.com.pirates.repository.NetworkService;
import pirates.com.pirates.ui.main_screen.MainPresenter;


@Module
 class PresenterModule {

    @Provides
    @Singleton
    MainPresenter MainPresenter(NetworkService networkService) {
        return new MainPresenter(networkService);
    }

}