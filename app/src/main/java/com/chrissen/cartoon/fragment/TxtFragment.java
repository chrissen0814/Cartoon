package com.chrissen.cartoon.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVAnalytics;
import com.chrissen.cartoon.R;
import com.chrissen.cartoon.adapter.list.TxtAdapter;
import com.chrissen.cartoon.module.model.TxtModel;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

public class TxtFragment extends Fragment {
    private static final int ADD_FILE = 5;
    private static final String FRAGMENT_NAME = "TxtFragment";

    private RecyclerView mTxtRv;
    private FloatingActionButton mAddFileFab;
    private TxtAdapter mTxtAdapter;
    private List<File> mFileList;

    public TxtFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFileList = new ArrayList<>();
        mFileList.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_txt, container, false);
        findViews(view);
        initParams();
        return view;
    }

    private void initParams() {
        mTxtAdapter = new TxtAdapter(mFileList,getContext());
        mTxtRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mTxtRv.setAdapter(mTxtAdapter);
        mAddFileFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getChildFragmentManager().beginTransaction()
//                        .add(new FilePickerFragment(),null)
//                        .addToBackStack(null)
//                        .commit();
                new MaterialFilePicker().withSupportFragment(TxtFragment.this)
                        .withFilter(Pattern.compile(".*\\.txt$"))
                        .withRequestCode(ADD_FILE)
                        .start();
            }
        });
    }

    private void findViews(View view) {
        mTxtRv = view.findViewById(R.id.txt_rv);
        mAddFileFab = view.findViewById(R.id.txt_add_file_fab);
    }

    @Override
    public void onStart() {
        super.onStart();
        mFileList.clear();
        List<File> fileList = new TxtModel().readFiles();
        if (fileList != null && fileList.size() > 0) {
            mFileList.addAll(fileList);
            mTxtAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AVAnalytics.onFragmentStart(FRAGMENT_NAME);
    }

    @Override
    public void onPause() {
        super.onPause();
        AVAnalytics.onFragmentEnd(FRAGMENT_NAME);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_FILE && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            new TxtModel().copyFile(filePath);
        }
    }

}
