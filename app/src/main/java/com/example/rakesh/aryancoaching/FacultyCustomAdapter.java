package com.example.rakesh.aryancoaching;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public  class FacultyCustomAdapter extends RecyclerView.Adapter<FacultyCustomAdapter.MyViewHolder> {
  private   Context facultycontext;
    private List<Faculty> mfaculties;
    private OnItemClickListener mFacultyListener;
    public FacultyCustomAdapter(Context context,ArrayList<Faculty> f)
    {
        facultycontext = context;
        mfaculties = f;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View v = LayoutInflater.from(facultycontext).inflate(R.layout.facultycardview,viewGroup,false);
       return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {

        Faculty currentfaculty = mfaculties.get(i);

        viewHolder.fName.setText(currentfaculty.getName());
        viewHolder.fSubject.setText(currentfaculty.getSubject());
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.coachingicon1).dontAnimate();
        Glide.with(facultycontext).load(currentfaculty.getFacultyPic()).apply(requestOptions).into(viewHolder.fImage);


    }

    @Override
    public int getItemCount() {
        return mfaculties.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements com.example.rakesh.aryancoaching.MyViewHolder,View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        TextView fName,fSubject;
        ImageView fImage;

        public MyViewHolder(View itemView){
            super(itemView);
            fName = itemView.findViewById(R.id.facultyName);
            fSubject  = itemView.findViewById(R.id.facultySubject);
            fImage = itemView.findViewById(R.id.facultyProfilePic);
            itemView.setOnClickListener(this);
//
        }
        @Override
        public void onClick(View v) {
            if (mFacultyListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mFacultyListener.onItemClick(position);
                }
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mFacultyListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    switch (item.getItemId()) {
                        case 1:
                            mFacultyListener.onViewDetail(position);
                            return true;

                        case 2:
                            mFacultyListener.onDeleteClick(position);
                            return true;

                    }
                }
            }
            return false;
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem viewDetail = menu.add(Menu.NONE, 1, 1, "View Details");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");

            viewDetail.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
        void onViewDetail(int position);
    }

    public void setOnItemClickListener(FacultyCustomAdapter.OnItemClickListener listener) {
        mFacultyListener = listener;
    }
}
