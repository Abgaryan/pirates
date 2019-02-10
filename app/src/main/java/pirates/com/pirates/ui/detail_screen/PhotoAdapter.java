package pirates.com.pirates.ui.detail_screen;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pirates.com.pirates.R;
import pirates.com.pirates.comman.Constants;
import pirates.com.pirates.model.Photo;


public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Provider<PhotoAdapter> {

    private  List<Photo> mPhotoList;



    @Inject
    public PhotoAdapter() {
        mPhotoList = new ArrayList<>();
    }
    

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  inflateView(parent);
        return new PhotoViewHolder(view);

    }

    private View inflateView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);

    }
    

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PhotoViewHolder photoViewHolder = (PhotoViewHolder) holder;
            Photo photo = mPhotoList.get(position);
            photoViewHolder.setmPhoto(photo);
        if (photo.getThumbnailUrl() != null) {
            Picasso.get().load(photo.getThumbnailUrl()).fit().centerCrop().into(((PhotoViewHolder) holder).photoImageView);
        } else {
            Picasso.get().load(Constants.NO_IMAGE_URL).fit().centerCrop().into(((PhotoViewHolder) holder).photoImageView);
        }
            holder.itemView.setTag(photoViewHolder);
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    @Override
    public PhotoAdapter get() {
        return this;
    }

    public void setmPhotoList(List<Photo> mPhotoList) {
        this.mPhotoList = mPhotoList;
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.photo_image_view)
        ImageView photoImageView;

       
        private Photo mPhoto;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            
        }

        public void setmPhoto(Photo mPhoto) {
            this.mPhoto = mPhoto;
        }
    }
    
}
