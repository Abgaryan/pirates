package pirates.com.pirates.ui.main_screen;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pirates.com.pirates.R;
import pirates.com.pirates.model.Post;

public class PostAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Provider<PostAdapter> {
    private static final int USUAL_ADAPTER = 1;
    private static final int FOOTER_ADAPTER = 2;



    private RecyclerView.ViewHolder holder;
    private int position;
    private  List<Post> mPostList;
    private  boolean  mIsLoading = false;


    @Inject
    Context context;

    @Inject
    MainPresenter mMainPresenter;


    @Inject
    public PostAdapter() {
        mPostList = new ArrayList<>();
    }

    public void setmPostList(List<Post> mPostList) {
        this.mPostList = mPostList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case USUAL_ADAPTER :
                view = inflateUsualView(parent);
                break;
            case FOOTER_ADAPTER:
                view = inflateFooterView(parent);
                return new LoadingViewHolder(view);
            default:
                view = null;
        }

        return new PostViewHolder(view);

    }

    private View inflateFooterView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_footer_loading_item, parent, false);

    }

    private View inflateUsualView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
    }


    public void startLoading() {
        if (!mIsLoading && mPostList.size() > 2) {
            mPostList.add(null);
            notifyItemInserted(mPostList.size() - 1);
            mIsLoading = true;
        }

    }

    public void stopLoading() {
        if (mIsLoading) {
            mPostList.remove(mPostList.size() - 1);
            notifyItemRemoved(mPostList.size());
            mIsLoading = false;
        }

    }



    @Override
    public int getItemViewType(int position) {
        if (mPostList.get(position) == null) {
            return FOOTER_ADAPTER;
        }
        return USUAL_ADAPTER;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PostViewHolder) {
            PostViewHolder PostViewHolder = (PostViewHolder) holder;

            Post post = mPostList.get(position);
            PostViewHolder.titleMovieTextView.setText(post.getTitle());
            PostViewHolder.releaseDateTextView.setText(post.getBody());
            PostViewHolder.setmPost(post);
            holder.itemView.setTag(PostViewHolder);

        } else {
            LoadingViewHolder LoadingHolder = (LoadingViewHolder) holder;
            LoadingHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    @Override
    public PostAdapter get() {
        return this;
    }

    public void addMore(List<Post> postList) {
        mPostList.addAll(postList);
    }



    class PostViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.title_textView)
        TextView titleMovieTextView;

        @BindView(R.id.body_textView)
        TextView releaseDateTextView;

        private   Post mPost;

        public PostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setInItemViewClick(itemView);

        }

        public void setmPost(Post mPost) {
            this.mPost = mPost;
        }

        private void setInItemViewClick(View itemView) {
            itemView.setOnClickListener((v) -> {
                mMainPresenter.onItemClicked(mPost.getId(),mPost.getUserId());
            });
        }

    }


    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.footer_progressBar)
        ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }



}
