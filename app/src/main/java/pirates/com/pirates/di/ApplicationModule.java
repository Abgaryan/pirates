package pirates.com.pirates.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pirates.com.pirates.PirateApplication;

/**
 * This is where you will inject application-wide dependencies.
 */
@Module
public class ApplicationModule {

    private Context mApplication;

    public ApplicationModule(PirateApplication application) {
        mApplication = application;
    }


    @Provides
    @Singleton
    public Context getContext() {
        return mApplication;
    }

}