package pirates.com.pirates;

import android.app.Application;
import android.content.Context;

import pirates.com.pirates.di.ApplicationComponent;
import pirates.com.pirates.di.ApplicationModule;
import pirates.com.pirates.di.DaggerApplicationComponent;


public class PirateApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();

    }




    private void initAppComponent(){
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getAppComponent(){
        if (applicationComponent == null) {
            initAppComponent();
        }

        return applicationComponent;
    }



    public static PirateApplication get(Context context) {
        return (PirateApplication) context.getApplicationContext();
    }



    public void setComponent(ApplicationComponent applicationComponent) {
        applicationComponent = applicationComponent;
    }
}
