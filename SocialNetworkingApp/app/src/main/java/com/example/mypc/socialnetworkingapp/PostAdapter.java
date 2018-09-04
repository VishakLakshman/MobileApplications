package com.example.mypc.socialnetworkingapp;


import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {


    ArrayList<Post> mdata ;
    int type;
    FragmentActivity activity;

    public static String selected_user;


    public PostAdapter(ArrayList<Post> mdata, FragmentActivity activity, int type) {
        this.mdata = mdata;
        this.type = type;
        this.activity =activity;
        Log.d("mdata size",""+mdata.size());
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView postBy,postTxt,posttime;
        Post post;
        ImageView delete;

        View holderview;
        public ViewHolder(View itemView) {
            super(itemView);
            postBy= (TextView)  itemView.findViewById(R.id.textView_row_postby);
            postTxt= (TextView)itemView.findViewById(R.id.textView_row_posttxt);
            posttime= (TextView) itemView.findViewById(R.id.textView_row_postime);
            delete = (ImageView) itemView.findViewById(R.id.imageView_row_delete);

            holderview = itemView;

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    DatabaseReference deleteRef;

    Post post;

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        post = mdata.get(position);

        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date1,date2,date3;

        if (type == 0){
            holder.delete.setVisibility(View.GONE);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selected_user = post.getPostedBy();

                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, new ShowParticularPostFragment());
                    fragmentTransaction.commit();

                }
            });

        }

        try {
            date1 = sdf.parse(post.getPosttime());
            date2 = Calendar.getInstance().getTime();

            long diff = Math.round((date2.getTime() - date1.getTime()));

            if(diff / (1000) < 60)
            {
                holder.posttime.setText("Just now");
            }
            else
            {
                if(diff / (1000*60) < 60)
                {
                    holder.posttime.setText(Long.toString(diff / (1000*60))+" minutes ago");
                }
                else
                {
                    if(diff/(1000*60*60) < 24 )
                        holder.posttime.setText(Long.toString(diff/(1000*60*60))+" hours ago");
                    else
                        holder.posttime.setText(Long.toString(diff/(1000*60*60*24))+" days ago");
                }
            }

        }
        catch (Exception e)
        {

        }
        holder.postBy.setText(post.getPostedBy());
        holder.postTxt.setText(post.getPosttxt());

        holder.post = post;
        Log.d("mdata size",""+mdata.get(position));

        /*holder.holderview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                deleteRef = MainActivity.mRootRef.child(MainActivity.currentUser.getEmail().replace("@","").replace(".",""));

                deleteRef.child(mdata.get(position).getName()).setValue(null);
                mdata.remove(position);
                notifyDataSetChanged();

                return false;
            }
        });*/

    }

    @Override
    public int getItemCount()
    {
        return mdata.size();
    }



}