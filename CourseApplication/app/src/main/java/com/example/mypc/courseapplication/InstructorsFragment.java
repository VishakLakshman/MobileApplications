package com.example.mypc.courseapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class InstructorsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private InstructorsInteractionListener mListener;

    public InstructorsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InstructorsFragment newInstance(String param1, String param2) {
        InstructorsFragment fragment = new InstructorsFragment();
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

    ImageView instructor_add;

    TextView warning;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View instructors_view = inflater.inflate(R.layout.fragment_instructors, container, false);

        instructor_add = (ImageView) instructors_view.findViewById(R.id.imageView_instructor_add);
        warning = (TextView) instructors_view.findViewById(R.id.textView_instructor_warning);

        instructor_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new AddInstructorFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        mRecyclerView = (RecyclerView) instructors_view.findViewById(R.id.recyclerView_instructor_instructors);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if(MainActivity.instructorslist.size() == 0)
        {
            warning.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);

        }
        else
        {
            warning.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);

            mAdapter = new InstructorDetailAdapter(MainActivity.instructorslist,getActivity());
            mRecyclerView.setAdapter(mAdapter);

        }

        return instructors_view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InstructorsInteractionListener) {
            mListener = (InstructorsInteractionListener) context;
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

    public interface InstructorsInteractionListener {
        // TODO: Update argument type and name
        void intructorsMethod();
    }
}
