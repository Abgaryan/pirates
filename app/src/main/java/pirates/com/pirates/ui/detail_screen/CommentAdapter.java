package pirates.com.pirates.ui.detail_screen;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import pirates.com.pirates.model.Comment;


public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Provider<CommentAdapter> {

    private List<Comment> mCommentList;


    @Inject
    public CommentAdapter() {
        mCommentList = new ArrayList<>();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflateView(parent);
        return new CommentViewHolder(view);

    }

    private View inflateView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
        Comment comment = mCommentList.get(position);
        commentViewHolder.userNameTextView.setText(comment.getName());
        commentViewHolder.commentTextView.setText(comment.getBody());
        commentViewHolder.setmComment(comment);
        holder.itemView.setTag(commentViewHolder);
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    @Override
    public CommentAdapter get() {
        return this;
    }

    public void setmCommentList(List<Comment> mCommentList) {
        this.mCommentList = mCommentList;
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.userNameTextView)
        TextView  userNameTextView;


        @BindView(R.id.commentTextView)
        TextView  commentTextView;

        private Comment mComment;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void setmComment(Comment mComment) {
            this.mComment = mComment;
        }
    }

}
