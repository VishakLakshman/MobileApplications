package com.example.mypc.session3;


import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {


    ArrayList<Trip> mdata ;
    int type;
    FragmentActivity activity;

    public TripAdapter(ArrayList<Trip> mdata,FragmentActivity activity) {
        this.mdata = mdata;
        this.activity = activity;
        Log.d("mdata size",""+mdata.size());
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,cost;
        Trip trip;

        ImageView view, edit,delete;

        View holderview;

        public ViewHolder(View itemView) {
            super(itemView);
            name= (TextView)  itemView.findViewById(R.id.textView_mainrow_name);
            cost= (TextView)  itemView.findViewById(R.id.textView_mainrow_cost);

            view = (ImageView)itemView.findViewById(R.id.imageView_mainrow_view);
            edit = (ImageView)itemView.findViewById(R.id.imageView_mainrow_edit);
            delete = (ImageView)itemView.findViewById(R.id.imageView_mainrow_delete);

            holderview = itemView;

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    DatabaseReference deleteRef;

    Trip trip;
    int pos;

    TextView costText,nop;

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        trip = mdata.get(position);

        holder.name.setText(trip.getName());
        holder.cost.setText(""+trip.getCost());


        costText = activity.findViewById(R.id.textView_add_cost);
        nop = activity.findViewById(R.id.editText_add_number);


        holder.trip = trip;

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mdata.remove(holder.getAdapterPosition());
                notifyDataSetChanged();

            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity,MapsActivity.class);
                HashMap<String,Deal> asd= new HashMap<String, Deal>();

                ArrayList<Deal> deals = mdata.get(holder.getAdapterPosition()).getDeals();

                for(int i=0;i<deals.size();i++)
                {
                    asd.put(deals.get(i).getPlace(),deals.get(i));
                }

                intent.putExtra("route",asd);
                activity.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return mdata.size();
    }
}