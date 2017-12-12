package com.chrissen.cartoon.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.module.model.TxtModel;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilePickerFragment extends Fragment {
    private static final int ADD_FILE = 5;


    public FilePickerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_file_picker, container, false);
        findViews(view);
        initParams();
        return view;
    }

    private void initParams() {

        new MaterialFilePicker().withSupportFragment(this)
                .withFilter(Pattern.compile(".*\\.txt$"))
                .withRequestCode(ADD_FILE)
                .start();

    }

    private void findViews(View view) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_FILE && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            EventBus.getDefault().postSticky(new File(filePath));
            new TxtModel().copyFile(filePath);
        }
    }
}
