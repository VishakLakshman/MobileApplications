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
        import java.util.HashMap;


public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {


    ArrayList<String> mdata ;
    HashMap<String,String> map;

    int type;

    public FriendsAdapter(ArrayList<String> mdata, HashMap<String,String> map,int type) {
        this.mdata = mdata;
        this.type = type;
        this.map = map;
        Log.d("mdata size",""+mdata.size());
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        ImageView accept,add,decline,remove;

        View holderview;
        public ViewHolder(View itemView) {
            super(itemView);
            name= (TextView)  itemView.findViewById(R.id.textView_friend_name);

            accept = (ImageView) itemView.findViewById(R.id.imageView_friend_accept);
            add = (ImageView) itemView.findViewById(R.id.imageView_friend_add);
            decline = (ImageView) itemView.findViewById(R.id.imageView_friend_decline);
            remove = (ImageView) itemView.findViewById(R.id.imageView_friend_unfriend);

            holderview = itemView;

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    String username;

    DatabaseReference deleteRef;

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        username = mdata.get(position);

        holder.name.setText(username);

        if(type == 1)
        {
            holder.accept.setVisibility(View.GONE);
            holder.decline.setVisibility(View.GONE);
            holder.remove.setVisibility(View.GONE);
            holder.add.setVisibility(View.VISIBLE);
        }
        else
            if(type == 2)
            {
                holder.accept.setVisibility(View.VISIBLE);
                holder.decline.setVisibility(View.VISIBLE);
                holder.remove.setVisibility(View.GONE);
                holder.add.setVisibility(View.GONE);

            }
            else
            {
                holder.accept.setVisibility(View.GONE);
                holder.decline.setVisibility(View.GONE);
                holder.remove.setVisibility(View.VISIBLE);
                holder.add.setVisibility(View.GONE);
            }


        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteRef = MainActivity.mRootRef.child(MainActivity.activeUser.getEmail().replace("@","").replace(".",""));

                deleteRef.child("requests").child(mdata.get(position)).setValue(null);

                deleteRef.child("friends/"+mdata.get(position)+"/name").setValue(mdata.get(position));

                String email = map.get(mdata.get(position));

                deleteRef = MainActivity.mRootRef.child(email.replace("@","").replace(".",""));

                deleteRef.child("requests").child(MainActivity.activeUser.getFname()+" "+MainActivity.activeUser.getLastname()).setValue(null);

                deleteRef.child("friends/"+MainActivity.activeUser.getFname()+" "+MainActivity.activeUser.getLastname()+"/name")
                        .setValue(MainActivity.activeUser.getFname()+" "+MainActivity.activeUser.getLastname());

                mdata.remove(position);
                notifyDataSetChanged();

            }
        });


        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteRef = MainActivity.mRootRef.child(MainActivity.activeUser.getEmail().replace("@","").replace(".",""));

                deleteRef.child("requests").child(mdata.get(position)).setValue(null);

                String email = map.get(mdata.get(position));

                deleteRef = MainActivity.mRootRef.child(email.replace("@","").replace(".",""));

                deleteRef.child("requests").child(MainActivity.activeUser.getFname()+" "+MainActivity.activeUser.getLastname())
                        .setValue(null);

                mdata.remove(position);

                notifyDataSetChanged();

            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteRef = MainActivity.mRootRef.child(MainActivity.activeUser.getEmail().replace("@","").replace(".",""));

                deleteRef.child("friends").child(mdata.get(position)).setValue(null);

                String email = map.get(mdata.get(position));

                deleteRef = MainActivity.mRootRef.child(email.replace("@","").replace(".",""));

                deleteRef.child("friends").child(MainActivity.activeUser.getFname()+" "+MainActivity.activeUser.getLastname()+"/name")
                        .setValue(null);


                mdata.remove(position);
                notifyDataSetChanged();

            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = map.get(mdata.get(position));

                deleteRef = MainActivity.mRootRef.child(email.replace("@","").replace(".",""));

                deleteRef.child("requests/"+MainActivity.activeUser.getFname()+" "+MainActivity.activeUser.getLastname()+"/name")
                        .setValue(MainActivity.activeUser.getFname()+" "+MainActivity.activeUser.getLastname());

                deleteRef = MainActivity.mRootRef.child(MainActivity.activeUser.getEmail().replace("@","").replace(".",""));

                deleteRef.child("requests/"+mdata.get(position)+"/name")
                        .setValue(mdata.get(position));

                View parentRow = (View) view.getParent();
                RecyclerView lv = (RecyclerView) parentRow.getParent();
                int location = lv.getChildPosition(parentRow);

                View temp = (View) lv.getChildAt(location);
                ImageView rbt = (ImageView) temp.findViewById(R.id.imageView_friend_accept);
                rbt.setVisibility(View.GONE);


                mdata.remove(position);

                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return mdata.size();
    }



}