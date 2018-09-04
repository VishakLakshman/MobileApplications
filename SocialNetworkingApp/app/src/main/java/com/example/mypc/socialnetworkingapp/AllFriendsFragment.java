package com.example.mypc.socialnetworkingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;


public class AllFriendsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AllFriendsInteractionListener mListener;

    public AllFriendsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AllFriendsFragment newInstance(String param1, String param2) {
        AllFriendsFragment fragment = new AllFriendsFragment();
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

    String username;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<User> userArrayList;

    ArrayList<String> users;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View all_friends_view = inflater.inflate(R.layout.fragment_all_friends, container, false);

        mRecyclerView = (RecyclerView) all_friends_view.findViewById(R.id.recyclerView_addfriend);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        username = MainActivity.currentUser.getEmail().replace("@", "").replace(".", "");

        MainActivity.mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Size "," "+dataSnapshot.getChildrenCount());

                userArrayList = new ArrayList<User>();

                users = new ArrayList<String>();

                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                HashMap<String,String> map = new HashMap<String, String>();

                while(iterator.hasNext()){
                    DataSnapshot s = iterator.next();
                    User user = s.getValue(User.class);
                    userArrayList.add(user);
                    Log.d("contact",user.toString());
                }

                for(int i = 0;i<userArrayList.size();i++)
                {
                    if(username.equals(userArrayList.get(i).getEmail().replace("@", "").replace(".", "")))
                    {
                        MainActivity.activeUser = userArrayList.get(i);
                    }
                    else
                    {
                        map.put(userArrayList.get(i).getFname()+" "+userArrayList.get(i).getLastname(),userArrayList.get(i).getEmail());

                    }
                }

                HashMap<String,Friend> temp = MainActivity.activeUser.friends;

                ArrayList<Friend> friends = new ArrayList<Friend>();

                if(temp !=null)
                {
                    friends.addAll(temp.values());
                    for(int i=0;i<friends.size();i++)
                        users.add(friends.get(i).getName());

                    mAdapter = new FriendsAdapter(users,map,3);
                    mRecyclerView.setAdapter(mAdapter);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return all_friends_view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AllFriendsInteractionListener) {
            mListener = (AllFriendsInteractionListener) context;
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


    public interface AllFriendsInteractionListener {
        // TODO: Update argument type and name
        void allfriendsMethod();
    }
}
