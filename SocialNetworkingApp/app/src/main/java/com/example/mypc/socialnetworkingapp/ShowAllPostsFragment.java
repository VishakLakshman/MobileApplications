package com.example.mypc.socialnetworkingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;


public class ShowAllPostsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ShowAllPostsInteractionListener mListener;

    public ShowAllPostsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ShowAllPostsFragment newInstance(String param1, String param2) {
        ShowAllPostsFragment fragment = new ShowAllPostsFragment();
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

    EditText postTxt;
    ImageView post,friends;

    String username,postname;

    DatabaseReference useroneRef;

    String timeStamp;

    ArrayList<Post> postArrayList;

    HashMap<String,Post> tempMap;

    public static User selectedus;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View show_all_posts_view = inflater.inflate(R.layout.fragment_show_all_posts, container, false);

        currentus = (TextView) show_all_posts_view.findViewById(R.id.textView_allposts_username);

        postTxt = (EditText) show_all_posts_view.findViewById(R.id.editText_allposts_newpost);
        post = (ImageView) show_all_posts_view.findViewById(R.id.imageView_allposts_addpost);
        friends = (ImageView) show_all_posts_view.findViewById(R.id.imageView_allposts_friends);

        mRecyclerView = (RecyclerView) show_all_posts_view.findViewById(R.id.recyclerView_allposts_allposts);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        username = MainActivity.currentUser.getEmail().replace("@", "").replace(".", "");


        MainActivity.mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Size "," "+dataSnapshot.getChildrenCount());

                postArrayList = new ArrayList<Post>();

                tempMap = new HashMap<String, Post>();

                ArrayList<User> temp = new ArrayList<User>();

                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                while(iterator.hasNext()){
                    DataSnapshot s = iterator.next();
                    User user = s.getValue(User.class);
                    temp.add(user);
                    Log.d("contact",user.toString());
                }

                HashMap<String,Friend> tempM = new HashMap<String, Friend>();

                for(int i = 0;i<temp.size();i++)
                {
                    if(username.equals(temp.get(i).getEmail().replace("@", "").replace(".", "")))
                    {
                        MainActivity.activeUser = temp.get(i);
                        currentus.setText(MainActivity.activeUser.getFname());
                        tempM = MainActivity.activeUser.friends;
                        if(temp.get(i).getPosts() != null)
                           postArrayList.addAll(temp.get(i).getPosts().values());
                    }
                    else
                    {

                    }
                }

                for(int i = 0;i<temp.size();i++)
                {
                    if(username.equals(temp.get(i).getEmail().replace("@", "").replace(".", "")))
                    {

                    }
                    else
                    {
                        String name = temp.get(i).getFname().concat(" ").concat(temp.get(i).getLastname());
                        if(temp.get(i).getPosts() != null &&
                                !(tempM == null || tempM.get(name) == null))
                            postArrayList.addAll(temp.get(i).getPosts().values());
                    }
                }

                Collections.sort(postArrayList);
                mAdapter = new PostAdapter(postArrayList,getActivity(),0);
                mRecyclerView.setAdapter(mAdapter);

                Log.d("posts",postArrayList.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        currentus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedus = MainActivity.activeUser;
            }
        });

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),FriendsActivity.class);
                startActivity(i);

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

                postname = username.concat("_").concat(timeStamp);

                useroneRef = MainActivity.mRootRef.child(username);

                useroneRef.child("posts/"+postname+"/posttxt").setValue(postTxt.getText().toString());
                useroneRef.child("posts/"+postname+"/postedBy").setValue(MainActivity.activeUser.getFname().concat(" ").concat(MainActivity.activeUser.getLastname()));
                useroneRef.child("posts/"+postname+"/posttime").setValue(timeStamp);

                postTxt.setText("");

            }
        });


        return show_all_posts_view;
    }

    ArrayList<Post> convert(HashMap<String,Post> input)
    {
        ArrayList<Post> reult = new ArrayList<>();


        return reult;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ShowAllPostsInteractionListener) {
            mListener = (ShowAllPostsInteractionListener) context;
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


    public interface ShowAllPostsInteractionListener {
        // TODO: Update argument type and name
        void allPostsmethod();
    }

}
