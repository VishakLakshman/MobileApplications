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


public class AddFriendFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AddFriendInteractionListener mListener;

    public AddFriendFragment() {
        // Required empty public constructor
    }

    public static AddFriendFragment newInstance(String param1, String param2) {
        AddFriendFragment fragment = new AddFriendFragment();
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
        View send_requests_view = inflater.inflate(R.layout.fragment_add_friend, container, false);

        mRecyclerView = (RecyclerView) send_requests_view.findViewById(R.id.recyclerView_allfriend);
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

                while(iterator.hasNext()){
                    DataSnapshot s = iterator.next();
                    User user = s.getValue(User.class);
                    userArrayList.add(user);
                    Log.d("contact",user.toString());
                }

                HashMap<String,Friend> temp = MainActivity.activeUser.friends;
                HashMap<String,Request> tempR = MainActivity.activeUser.requests;

                HashMap<String,String> map = new HashMap<String, String>();

                for(int i = 0;i<userArrayList.size();i++)
                {
                    if(username.equals(userArrayList.get(i).getEmail().replace("@", "").replace(".", "")))
                    {
                        continue;
                    }
                    else
                    {
                        String name = userArrayList.get(i).getFname().concat(" ").concat(userArrayList.get(i).getLastname());
                        String name1 = userArrayList.get(i).getFname().concat(" ").concat(userArrayList.get(i).getLastname());
                        if((temp == null || temp.get(name) == null) &&
                                (tempR == null || tempR.get(name1) == null))
                        {
                            users.add(userArrayList.get(i).getFname() + " " + userArrayList.get(i).getLastname());
                            map.put(userArrayList.get(i).getFname() + " " + userArrayList.get(i).getLastname(),userArrayList.get(i).getEmail());
                        }
                    }
                }
                Collections.sort(users);
                mAdapter = new FriendsAdapter(users,map,1);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return send_requests_view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddFriendInteractionListener) {
            mListener = (AddFriendInteractionListener) context;
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

    public interface AddFriendInteractionListener {
        // TODO: Update argument type and name
        void addFriend(Uri uri);
    }
}
