package com.example.mypc.session3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DecorContentParent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;


public class AddTripFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AddTripInteractionListener mListener;

    public AddTripFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddTripFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTripFragment newInstance(String param1, String param2) {
        AddTripFragment fragment = new AddTripFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    EditText name,number;
    TextView cost;

    Button add,view,cancel;

    DatabaseReference useroneRef;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Deal> deals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View add_trip = inflater.inflate(R.layout.fragment_add_trip, container, false);

        name = (EditText) add_trip.findViewById(R.id.editText_add_name);
        number = (EditText) add_trip.findViewById(R.id.editText_add_number);
        cost = (TextView) add_trip.findViewById(R.id.textView_add_cost);

        add = (Button) add_trip.findViewById(R.id.button_add_add);
        view = (Button) add_trip.findViewById(R.id.button_add_view);
        cancel = (Button) add_trip.findViewById(R.id.button_add_cancel);

        mRecyclerView = (RecyclerView) add_trip.findViewById(R.id.recylcer_add);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        useroneRef = MainActivity.mRootRef.child("Deals");

        useroneRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                deals = new ArrayList<Deal>();

                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while(iterator.hasNext()){
                    DataSnapshot s = iterator.next();

                    Deal contact = s.getValue(Deal.class);
                    deals.add(contact);
                    Log.d("contact",contact.toString());
                }

                if(deals.size()>0) {
                    mAdapter = new DealAdapter(deals,getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new MainScreenFragment()).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(DealAdapter.selected.size()>0)
                {
                    Trip trip = new Trip();

                    trip.setName(name.getText().toString());
                    trip.setCost(cost.getText().toString());

                    ArrayList<Deal> deals = new ArrayList<Deal>();
                    deals.addAll(DealAdapter.selected.values());

                    trip.setDeals(deals);

                    MainActivity.trips.add(trip);
                }
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new MainScreenFragment()).addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getActivity(),MapsActivity.class);
                intent.putExtra("route",DealAdapter.selected);
                startActivity(intent);
            }
        });


        return add_trip;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddTripInteractionListener) {
            mListener = (AddTripInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface AddTripInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteractionAdd();
    }
}
