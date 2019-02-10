package pirates.com.pirates.ui.main_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pirates.com.pirates.PirateApplication;
import pirates.com.pirates.R;
import pirates.com.pirates.comman.Constants;
import pirates.com.pirates.comman.DialogHelper;
import pirates.com.pirates.model.Post;
import pirates.com.pirates.ui.detail_screen.DetailActivity;

import static pirates.com.pirates.comman.Constants.LIST_PAGE_LIMIT;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    @BindView(R.id.posts_recycle_view)
    public RecyclerView mPostsRecyclerView;


    @BindView(R.id.progressBar)
    public ProgressBar mProgressBar;

    @Inject
    MainPresenter mMainPresenter;

    private boolean mIsRefreshing;

    @Inject
     PostAdapter mPostAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        PirateApplication pirateApplication = (PirateApplication) getApplication();
        pirateApplication.getAppComponent().inject(this);
        initAdapters();
        initRecyclerView();

        mMainPresenter.attachView(this);
        mMainPresenter.loadPosts();
    }


    /*
     * inits adapter for  mPostsRecyclerView,
     * */
    private void initAdapters() {
        mPostsRecyclerView.setAdapter(mPostAdapter);
        mPostsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    public void initRecyclerView() {

        RecyclerView.OnScrollListener osl = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItemPosition = 0;
                if (lm != null) {
                    lastVisibleItemPosition = lm.findLastVisibleItemPosition();
                }
                int totalItemsInRV = mPostsRecyclerView.getAdapter().getItemCount();
                if (lastVisibleItemPosition >= totalItemsInRV - LIST_PAGE_LIMIT / 2) {
                    if (!mIsRefreshing) {
                        mMainPresenter.loadMore();
                    }
                }
            }
        };

        mPostsRecyclerView.addOnScrollListener(osl);


    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }


    @Override
    public void showProgress() {
        if (!mProgressBar.isShown()) mProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        if (mProgressBar.isShown()) mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        DialogHelper.showErrorPopup(this,getResources().getString(R.string.dialog_message_error));
    }

    @Override
    public void showProgressAdapter() {
        mIsRefreshing = true;
     mPostAdapter.startLoading();
    }

    @Override
    public void hideProgressAdapter() {
        mPostAdapter.stopLoading();
        mIsRefreshing  =  false;
    }

    @Override
    public void showErrorAdapter() {
        DialogHelper.showErrorPopup(this,getResources().getString(R.string.dialog_message_error));
    }

    @Override
    public void loadPosts(List<Post> postList) {
        mPostAdapter.setmPostList(postList);
        mPostAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore(List<Post> postList) {
        mPostAdapter.addMore(postList);
        mPostAdapter.notifyItemInserted(mPostAdapter.getItemCount() - postList.size());
        mPostAdapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToPostDetail(int postId, int userId) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(Constants.POST_ID, postId);
        intent.putExtra(Constants.USER_ID, userId);
        startActivity(intent);
    }

}
