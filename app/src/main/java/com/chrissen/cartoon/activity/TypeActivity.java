package com.chrissen.cartoon.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.adapter.TypeAdapter;

public class TypeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TypeAdapter mTypeAdapter;
    private String[] mTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        mRecyclerView = findViewById(R.id.type_rv);
        mTypes = getResources().getStringArray(R.array.comic_type);
        mTypeAdapter = new TypeAdapter(this,mTypes);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mTypeAdapter);
    }
}
