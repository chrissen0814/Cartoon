package com.chrissen.cartoon.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.chrissen.cartoon.R;
import com.chrissen.cartoon.activity.system.FeedbackActivity;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

import es.dmoral.toasty.Toasty;

/**
 * Created by chris on 2017/11/20.
 */

public class AboutFragment extends Fragment {

    private CardView mAdviseCv , mProtocolCv;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mAdviseCv = view.findViewById(R.id.about_advise_cv);
        mProtocolCv = view.findViewById(R.id.about_protocol_cv);
        mAdviseCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AVUser.getCurrentUser() != null) {
                    getActivity().startActivity(new Intent(getContext(), FeedbackActivity.class));
                }else {
                    Toasty.warning(getContext(),getString(R.string.about_advise_no_user), Toast.LENGTH_SHORT,true).show();
                }
            }
        });
        mProtocolCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LibsBuilder()
                        //provide a style (optional) (LIGHT, DARK, LIGHT_DARK_TOOLBAR)
                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                        .withActivityTitle(getString(R.string.about_protocol_intro))
                        //start the activity
                        .start(getActivity());
            }
        });
    }

}
