package com.chrissen.cartoon.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.chrissen.cartoon.R;
import com.chrissen.cartoon.activity.BaseAbstractActivity;
import com.chrissen.cartoon.activity.system.FeedbackActivity;
import com.chrissen.cartoon.util.ImageUtil;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

import es.dmoral.toasty.Toasty;

/**
 * Created by chris on 2017/11/20.
 */

public class AboutFragment extends Fragment {

    private CardView mAdviseCv , mProtocolCv , mProjectCv;
    private ImageView mDeveloperIv , mIconDesIv;
    private TextView mDevLinkTv , mIconDesLinkTv;
    private ImageView mBackIv;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mBackIv = view.findViewById(R.id.about_back_iv);
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        mAdviseCv = view.findViewById(R.id.about_advise_cv);
        mProtocolCv = view.findViewById(R.id.about_protocol_cv);
        mProjectCv = view.findViewById(R.id.about_project_cv);
        mDeveloperIv = view.findViewById(R.id.about_developer_iv);
        mIconDesIv = view.findViewById(R.id.about_icon_author_iv);
        mDevLinkTv = view.findViewById(R.id.about_dev_link_tv);
        mDevLinkTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseAbstractActivity)getActivity()).putBindClick(v);
                Uri uri = Uri.parse("mailto:1453191344@qq.com");
                String[] email = {"1453191344@qq.com"};
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
                intent.putExtra(Intent.EXTRA_SUBJECT, "猫漫"); // 主题
                startActivity(Intent.createChooser(intent, "请选择邮件应用"));
            }
        });
        mIconDesLinkTv = view.findViewById(R.id.about_icon_des_link_tv);
        mIconDesLinkTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseAbstractActivity)getActivity()).putBindClick(v);
                Uri uri = Uri.parse(getString(R.string.about_icon_designer_link));
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        ImageUtil.loadCircleImageByRes(R.drawable.image_developer,getContext(),mDeveloperIv);
        ImageUtil.loadCircleImageByRes(R.drawable.image_icon_designer,getContext(),mIconDesIv);
        mAdviseCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseAbstractActivity)getActivity()).putBindClick(v);
                if (AVUser.getCurrentUser() != null) {
                    getActivity().startActivity(new Intent(getContext(), FeedbackActivity.class));
                }else {
                    Toasty.warning(getContext(),getString(R.string.about_advise_no_user), Toast.LENGTH_SHORT,true).show();
                }
                ((BaseAbstractActivity)getActivity()).resetClick();
            }
        });
        mProtocolCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseAbstractActivity)getActivity()).putBindClick(v);
                new LibsBuilder()
                        //provide a style (optional) (LIGHT, DARK, LIGHT_DARK_TOOLBAR)
                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                        .withActivityTitle(getString(R.string.about_protocol_intro))
                        //start the activity
                        .start(getActivity());
            }
        });
        mProjectCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseAbstractActivity)getActivity()).putBindClick(v);
                Uri uri = Uri.parse(getString(R.string.project_location));
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
    }

}
