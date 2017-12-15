package com.chrissen.cartoon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.dao.greendao.Book;
import com.chrissen.cartoon.dao.manager.BookDaoManager;
import com.chrissen.cartoon.util.IntentConstants;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class BookNoteActivity extends BaseAbstractActivity {

    private Toolbar mToolbar;
    private EditText mNoteEt;
    private Book mBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_note);
        initViews();
        initParams();
    }

    protected void initParams() {
        mBook = (Book) getIntent().getSerializableExtra(IntentConstants.BOOK);
        mToolbar.setTitle(mBook.getBookName());
        if (mBook.getComment() != null && !mBook.getComment().equals("")) {
            mNoteEt.setText(mBook.getComment());
            mNoteEt.setSelection(mBook.getComment().length());
        }
    }

    protected void initViews() {
        mToolbar = findViewById(R.id.book_note_toolbar);
        mNoteEt = findViewById(R.id.book_note_et);
        mNoteEt.setFocusable(true);
        mNoteEt.requestFocusFromTouch();
        mNoteEt.requestFocus();
    }


    private void saveNote(){
        String note = mNoteEt.getText().toString();
        if (note == null || note.equals("") || note.equals(" ")) {
            Toasty.error(this,getString(R.string.book_note_none), Toast.LENGTH_SHORT,false).show();
        }else {
            mBook.setComment(note);
            mBook.setUpdatedTime(Calendar.getInstance().getTimeInMillis());
            new BookDaoManager().updateBook(mBook);
            Toasty.success(this,getString(R.string.book_note_save_success),Toast.LENGTH_SHORT,true).show();
        }
    }

    public void onSaveClick(View view) {
        putBindClick(view);
        saveNote();
        Intent intent = new Intent();
        intent.putExtra(IntentConstants.BOOK_NOTE,mNoteEt.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }
}
