package com.example.mypc.session3;


import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
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


public class DealAdapter extends RecyclerView.Adapter<DealAdapter.ViewHolder> {


    ArrayList<Deal> mdata ;
    int type;
    FragmentActivity activity;

    public static HashMap<String,Deal> selected;
    Boolean[] isselected;

    public static Double cost;

    public DealAdapter(ArrayList<Deal> mdata,FragmentActivity activity) {
        this.mdata = mdata;
        this.activity = activity;
        selected = new HashMap<>();
        isselected = new Boolean[mdata.size()];
        for(int i =0;i<mdata.size();i++)
            isselected[i]=false;
        cost = 0.0;
        Log.d("mdata size",""+mdata.size());
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,cost,duration;
        Deal trip;

        CheckBox checkBox;

        View holderview;

        public ViewHolder(View itemView) {
            super(itemView);
            name= (TextView)  itemView.findViewById(R.id.textView_addrow_place);
            cost= (TextView)  itemView.findViewById(R.id.textView_addrow_cost);
            duration= (TextView)  itemView.findViewById(R.id.textView_addrow_duration);

            checkBox= (CheckBox)  itemView.findViewById(R.id.checkBox_addrow);

            holderview = itemView;

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    DatabaseReference deleteRef;

    Deal deal;
    int pos;

    TextView costText,nop;

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        deal = mdata.get(position);

        holder.name.setText(deal.getPlace());
        holder.cost.setText(""+deal.getCost());
        holder.duration.setText(deal.getDuration());

        costText = activity.findViewById(R.id.textView_add_cost);
        nop = activity.findViewById(R.id.editText_add_number);

        if(isselected[holder.getAdapterPosition()])
        {
            holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setChecked(false);
        }

        holder.trip = deal;

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View parentRow = (View) view.getParent();
                RecyclerView lv = (RecyclerView) parentRow.getParent();

                pos = holder.getAdapterPosition();

                RecyclerView.ViewHolder hold = lv.findViewHolderForAdapterPosition(pos);
                Log.d("Position:"," "+pos);

                CheckBox cb = (CheckBox) view;

                Deal tempDeal = mdata.get(pos);

                if(cb.isChecked())
                {
                    isselected[pos] = true;
                    if(nop.getText().toString().equals(""))
                        cost+=tempDeal.getCost();
                    else
                        cost=(tempDeal.getCost()+cost)*Double.parseDouble(nop.getText().toString());
                    costText.setText("$"+cost);
                    selected.put(tempDeal.getPlace(),tempDeal);
                    Log.d("selected",selected.toString());
                }
                else
                {
                    isselected[pos] = false;
                    selected.remove(tempDeal.getPlace());
                    if(nop.getText().toString().equals(""))
                        cost-=tempDeal.getCost();
                    else
                        cost=(cost/Double.parseDouble(nop.getText().toString())) - tempDeal.getCost();
                    costText.setText("$"+cost);
                    Log.d("selected",selected.toString());
                }

            }
        });

    }

    @Override
    public int getItemCount()
    {
        return mdata.size();
    }
}