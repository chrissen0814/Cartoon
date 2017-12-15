package com.chrissen.cartoon.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVAnalytics;
import com.chrissen.cartoon.R;
import com.chrissen.cartoon.activity.CartoonContentActivity;
import com.chrissen.cartoon.bean.ContentBean;
import com.chrissen.cartoon.util.ImageUtil;
import com.chrissen.cartoon.util.view.PinchImageView;

/**
 * Created by chris on 2017/11/18.
 */

public class ContentFragment extends Fragment {

    private static final String CONTENT_IMAGE = "content_image";
    private ContentBean.Image mImage;

    private PinchImageView mImageView;

    public static ContentFragment newInstance(ContentBean.Image image){
        Bundle bundle = new Bundle();
        bundle.putSerializable(CONTENT_IMAGE,image);
        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImage = (ContentBean.Image) getArguments().getSerializable(CONTENT_IMAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content,container,false);
        mImageView = view.findViewById(R.id.content_image_iv);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CartoonContentActivity)getActivity()).setTopAndBottomBarVisibility();
            }
        });
        ImageUtil.loadImageByUrl(mImage.getImageUrl(),getContext(),mImageView);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        AVAnalytics.onFragmentStart("ContentFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        AVAnalytics.onFragmentEnd("ContentFragment");
    }
}
