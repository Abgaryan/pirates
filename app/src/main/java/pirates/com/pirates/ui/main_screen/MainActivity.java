package pirates.com.pirates.ui.main_screen;

import android.os.Bundle;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import pirates.com.pirates.PirateApplication;
import pirates.com.pirates.R;

public class MainActivity extends AppCompatActivity implements MainContract.View{


    @Inject
    MainPresenter mMainPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        PirateApplication pirateApplication = (PirateApplication) getApplication();
        pirateApplication.getAppComponent().inject(this);
        mMainPresenter.attachView(this);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }




}
