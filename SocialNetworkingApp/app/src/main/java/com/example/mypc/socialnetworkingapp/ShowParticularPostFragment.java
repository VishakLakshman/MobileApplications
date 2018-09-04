package com.example.mypc.socialnetworkingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class ShowParticularPostFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ShowParticularInteractionListener mListener;

    public ShowParticularPostFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ShowParticularPostFragment newInstance(String param1, String param2) {
        ShowParticularPostFragment fragment = new ShowParticularPostFragment();
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

    TextView currentus;

    String username;

    ArrayList<Post> postArrayList;

    HashMap<String,Post> tempMap;

    ImageView home;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View show_particular_view = inflater.inflate(R.layout.fragment_show_particular_post, container, false);

        currentus = (TextView) show_particular_view.findViewById(R.id.textView_parposts_username);

        home = (ImageView) show_particular_view.findViewById(R.id.imageView_parposts_friends);

        mRecyclerView = (RecyclerView) show_particular_view.findViewById(R.id.recyclerView_parposts_allposts);
        mRecyclerView.setHasFixedSize(true);

        currentus.setText(PostAdapter.selected_user);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        username = MainActivity.currentUser.getEmail().replace("@", "").replace(".", "");

        MainActivity.mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Size "," "+dataSnapshot.getChildrenCount());

                postArrayList = new ArrayList<Post>();

                tempMap = new HashMap<String, Post>();

                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                while(iterator.hasNext()){
                    DataSnapshot s = iterator.next();
                    User user = s.getValue(User.class);

                    if(PostAdapter.selected_user.equals(user.getFname().concat(" ").concat(user.getLastname())))
                    {
                        postArrayList.addAll(user.getPosts().values());
                        break;
                    }
                }

                if(PostAdapter.selected_user.equals(MainActivity.activeUser.getFname().concat(" ").concat(MainActivity.activeUser.getLastname()))){
                    Collections.sort(postArrayList);
                    mAdapter = new PostAdapter(postArrayList,getActivity(),1);
                    mRecyclerView.setAdapter(mAdapter);
                }
                else {
                    Collections.sort(postArrayList);
                    mAdapter = new PostAdapter(postArrayList,getActivity(),0);
                    mRecyclerView.setAdapter(mAdapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new ShowAllPostsFragment());
                fragmentTransaction.commit();

            }
        });




        return show_particular_view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ShowParticularInteractionListener) {
            mListener = (ShowParticularInteractionListener) context;
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


    public interface ShowParticularInteractionListener {
        // TODO: Update argument type and name
        void anotherMethod();
    }
}
