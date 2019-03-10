package com.example.rakesh.aryancoaching;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<uploads> mUploads;
    private OnItemClickListener mListener;

    public ImageAdapter(Context context, List<uploads> uploads) {
        mContext = context;
       mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.images_item, viewGroup, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder( ImageViewHolder imageViewHolder, int i) {
        uploads uploadcurrent = mUploads.get(i);
        imageViewHolder.textViewName.setText(uploadcurrent.getName());
        final String  urll = uploadcurrent.getImageUrl();
//        imageViewHolder.textViewName.setText(uploadcurrent.getName());
//        Picasso.get().load(uploadcurrent.getImageUrl()).fit().centerCrop().into(imageViewHolder.imageview);
//        Glide.with(mContext).load(uploadcurrent.getImageUrl().int(imageViewHolder.imageview));
        // for glide
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.coachingicon1).dontAnimate();
        Glide.with(mContext).load(uploadcurrent.getImageUrl()).apply(requestOptions).into(imageViewHolder.imageview);

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView textViewName;
        public ImageView imageview;


        public ImageViewHolder( View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            imageview = itemView.findViewById(R.id.image_view_upload);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem viewImage = menu.add(Menu.NONE, 1, 1, "View Full Image");
            MenuItem download = menu.add(Menu.NONE, 2, 2, "Download Image");
            MenuItem delete = menu.add(Menu.NONE, 3, 3, "Delete");

            viewImage.setOnMenuItemClickListener(this);
            download.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    switch (item.getItemId()) {
                        case 1:
                            mListener.onViewImage(position);
                            return true;

                        case 2:
                            mListener.onDownload(position);
                            return true;
                        case 3:
                            mListener.onDeleteClick(position);
                            return true;

                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDownload(int position);

        void onDeleteClick(int position);
        void onViewImage(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
