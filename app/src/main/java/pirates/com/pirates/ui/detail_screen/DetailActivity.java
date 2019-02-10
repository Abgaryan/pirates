package pirates.com.pirates.ui.detail_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pirates.com.pirates.PirateApplication;
import pirates.com.pirates.R;
import pirates.com.pirates.comman.Constants;
import pirates.com.pirates.comman.DialogHelper;
import pirates.com.pirates.model.Comment;
import pirates.com.pirates.model.Photo;
import pirates.com.pirates.model.User;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    @Inject
    DetailPresenter mDetailPresenter;

    @Inject
    PhotoAdapter mPhotoAdapter;

    @Inject
    CommentAdapter mCommentAdapter;


    @BindView(R.id.photosRecyclerView)
    public RecyclerView mPhotoRecyclerView;

    @BindView(R.id.commentsRecyclerView)
    public RecyclerView mCommentsRecyclerView;

    @BindView(R.id.commentsTitleTextView)
    public TextView mCommentsTitleTextView;


    @BindView(R.id.userNameTextView)
    public TextView mUserNameTextView;

    @BindView(R.id.userEmailTextView)
    public TextView mUserEmailTextView;

    @BindView(R.id.progressBar)
    public ProgressBar mProgressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        PirateApplication pirateApplication = (PirateApplication) getApplication();
        pirateApplication.getAppComponent().inject(this);
        initAdapters();
        mDetailPresenter.attachView(this);
        Intent intent;
        intent = getIntent();
        if (intent != null) {
            mDetailPresenter.loadUserData(intent.getIntExtra(Constants.USER_ID, 0), intent.getIntExtra(Constants.POST_ID, 0));
        } else {
            showError();
        }

    }

    void initAdapters(){
        mPhotoRecyclerView.setAdapter(mPhotoAdapter);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mCommentsRecyclerView.setAdapter(mCommentAdapter);
        mCommentsRecyclerView.setLayoutManager( new LinearLayoutManager(this));
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
    public void loadUser(User user) {
      mUserNameTextView.setText(getResources().getString(R.string.name,user.getName()));
      mUserEmailTextView.setText(getResources().getString(R.string.email,user.getEmail()));
    }

    @Override
    public void loadPhotos(List<Photo> photoList) {
      mPhotoAdapter.setmPhotoList(photoList);
      mPhotoAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadComments(List<Comment> commentList) {
       mCommentsTitleTextView.setText(getResources().getString(R.string.comments_title,commentList.size()));
       mCommentAdapter.setmCommentList(commentList);
       mCommentAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDetailPresenter.detachView();
    }
}
