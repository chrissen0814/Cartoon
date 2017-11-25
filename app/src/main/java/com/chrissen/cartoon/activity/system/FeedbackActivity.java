package com.chrissen.cartoon.activity.system;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVPersistenceUtils;
import com.avos.avoscloud.AVUtils;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.LogUtil;
import com.avos.avoscloud.feedback.Comment;
import com.avos.avoscloud.feedback.FeedbackAgent;
import com.avos.avoscloud.feedback.FeedbackThread;
import com.avos.avoscloud.feedback.Resources;
import com.chrissen.cartoon.CartoonApplication;
import com.chrissen.cartoon.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity {

    private static final int IMAGE_REQUEST = 657843;
    public static final ImageCache cache = new ImageCache(AVOSCloud.applicationContext);
    FeedbackAgent agent;
    ListView feedbackListView;
    Button sendButton;
    EditText feedbackInput;
    FeedbackThread thread;
    FeedbackListAdapter adapter;
    FeedbackThread.SyncCallback syncCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        agent = CartoonApplication.getFeedbackAgent();
        adapter = new FeedbackListAdapter(this);
        thread = agent.getDefaultThread();
        feedbackListView = findViewById(R.id.avoscloud_feedback_thread_list);
        feedbackListView.setAdapter(adapter);
        sendButton = findViewById(R.id.avoscloud_feedback_send);
        feedbackInput = findViewById(R.id.avoscloud_feedback_input);
        feedbackInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    feedbackListView.setSelection(feedbackListView.getAdapter().getCount());
                    smoothScrollToBottom();
                }
            }
        });
        syncCallback = new FeedbackThread.SyncCallback() {

            @Override
            public void onCommentsSend(List<Comment> comments, AVException e) {
                LogUtil.avlog.d("send new comments");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCommentsFetch(List<Comment> comments, AVException e) {
                LogUtil.avlog.d("fetch new comments");
                adapter.notifyDataSetChanged();
            }
        };
        thread.sync(syncCallback);
    }



    public void onSendClick(View view) {
        String feedbackText = feedbackInput.getText().toString();
        feedbackInput.setText("");
        if (!AVUtils.isBlankString(feedbackText)) {
            thread.add(new Comment(feedbackText));
            adapter.notifyDataSetChanged();
            feedbackListView.setSelection(feedbackListView.getAdapter().getCount());
            smoothScrollToBottom();
            thread.sync(syncCallback);
        }
    }

    private void smoothScrollToBottom() {
        feedbackListView.post(new Runnable() {
            @Override
            public void run() {
                feedbackListView.smoothScrollToPosition(feedbackListView.getAdapter().getCount());
            }
        });
    }

    public void onChooseImageClick(View view) {
//        if (Build.VERSION.SDK_INT < 19) {
//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(
//                    Intent.createChooser(intent,getResources().getString(Resources.string.avoscloud_feedback_select_image(FeedbackActivity.this))),
//                    IMAGE_REQUEST);
//        } else {
//            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.setType("image/*");
//            startActivityForResult(intent, IMAGE_REQUEST);
//        }
    }


    public class FeedbackListAdapter extends BaseAdapter {

        Context mContext;
        LayoutInflater inflater;

        public FeedbackListAdapter(Context context) {
            this.mContext = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return thread.getCommentsList().size();
        }

        @Override
        public Object getItem(int position) {
            return thread.getCommentsList().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                if (this.getItemViewType(position) == 0) {
                    convertView = inflater.inflate(R.layout.item_feedback_user, null);
                } else {
                    convertView = inflater.inflate(R.layout.item_feedback_dev,null);
                }
                holder = new ViewHolder();
                holder.content = convertView.findViewById(R.id.avoscloud_feedback_content);
                holder.timestamp = convertView.findViewById(R.id.avoscloud_feedback_timestamp);
                holder.image = convertView.findViewById(R.id.avoscloud_feedback_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final Comment comment = (Comment) getItem(position);
            if (comment.getAttachment() != null && comment.getAttachment().getUrl() != null) {
                holder.content.setVisibility(View.GONE);
                holder.image.setVisibility(View.VISIBLE);
                final View.OnClickListener imageOnClickListener = new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(
                                Uri.fromFile(ImageCache.getCacheFile(comment.getAttachment().getUrl())), "image/*");
                        startActivity(intent);
                    }
                };
                Bitmap attachmentCache = cache.getImage(comment.getAttachment().getUrl());
                if (attachmentCache != null) {
                    holder.image.setImageBitmap(attachmentCache);
                    holder.image.setOnClickListener(imageOnClickListener);
                } else {
                    holder.image.setOnClickListener(null);
                    comment.getAttachment().getDataInBackground(new GetDataCallback() {

                        @Override
                        public void done(byte[] data, AVException e) {
                            if (e == null) {
                                Bitmap attachmentCache = cache.setImage(comment.getAttachment().getUrl(), data);
                                holder.image.setImageBitmap(attachmentCache);
                                holder.image.setOnClickListener(imageOnClickListener);
                            }
                        }
                    });
                }
            } else {
                holder.content.setVisibility(View.VISIBLE);
                holder.content.setText(comment.getContent());
                holder.image.setVisibility(View.GONE);
            }
            if (Math.abs(comment.getCreatedAt().getTime() - System.currentTimeMillis()) < 10000) {
                holder.timestamp.setText(getResources().getString(
                        Resources.string.avoscloud_feedback_just_now(FeedbackActivity.this)));
            } else {
                holder.timestamp.setText(DateUtils.getRelativeTimeSpanString(comment.getCreatedAt()
                        .getTime(), System.currentTimeMillis() - 1, 0l, DateUtils.FORMAT_ABBREV_ALL));
            }
            return convertView;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            Comment comment = (Comment) this.getItem(position);
            if (comment.getCommentType().equals(Comment.CommentType.USER)) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    public class ViewHolder {
        TextView content;
        TextView timestamp;
        ImageView image;
    }

    public static class ImageCache {
        LruCache<String, Bitmap> bitmapCache;
        static final int cacheSize = 20;
        Context context;

        public ImageCache(Context context) {
            this.context = context;
            bitmapCache = new LruCache<String, Bitmap>(cacheSize);
        }

        static String getFileName(String fileUrl) {
            Uri uri = Uri.parse(fileUrl);
            return uri.getLastPathSegment();
        }


        public static File getCacheFile(String fileName) {
            File imgCacheDir = new File(AVOSCloud.applicationContext.getExternalCacheDir(), "img");
            if (!imgCacheDir.exists()) {
                imgCacheDir.mkdirs();
            }
            return new File(imgCacheDir, getFileName(fileName));
        }

        static File getCacheThumbnailFile(String fileName) {
            File imgCacheDir = new File(AVOSCloud.applicationContext.getExternalCacheDir(), "img");
            if (!imgCacheDir.exists()) {
                imgCacheDir.mkdirs();
            }
            return new File(imgCacheDir, getFileName(fileName) + ".tn");
        }

        public Bitmap getImage(String key) {
            Bitmap cacheBitmap = bitmapCache.get(key);
            if (cacheBitmap == null) {
                cacheBitmap = BitmapFactory.decodeFile(getCacheThumbnailFile(key).getAbsolutePath());
            }
            return cacheBitmap;
        }

        public Bitmap setImage(String key, byte[] data) {
            OutputStream os = null;
            OutputStream thumbnailOS = null;
            byte[] imageData = null;
            Bitmap thumbnail = null;
            try {
                Bitmap tempBM = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(data, 0, data.length), 150, 150, false);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                tempBM.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                imageData = baos.toByteArray();

                os = new FileOutputStream(getCacheFile(key), true);
                os.write(data);
                thumbnailOS = new FileOutputStream(getCacheThumbnailFile(key), true);
                thumbnailOS.write(imageData);

                thumbnail = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                AVPersistenceUtils.closeQuietly(os);
                AVPersistenceUtils.closeQuietly(thumbnailOS);
            }
            return thumbnail;
        }
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri =
                        ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                                Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for MediaStore Uris, and other
     * file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return null;
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (IMAGE_REQUEST == requestCode && resultCode == RESULT_OK && data.getData() != null) {
            Uri uri = data.getData();
            String filePath = FeedbackActivity.getPath(this, uri);

            try {
                LogUtil.avlog.d("img picked:" + filePath);
                File attachmentFile = new File(filePath);
                thread.add(new Comment(attachmentFile));
                adapter.notifyDataSetChanged();
                feedbackListView.setSelection(feedbackListView.getAdapter().getCount());
                smoothScrollToBottom();
                thread.sync(syncCallback);
                feedbackInput.setText("");
            } catch (AVException e) {
                e.printStackTrace();
            }
        }
    }


}
