package com.example.delaney.photobucket1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.koushikdutta.ion.Ion;

public class PhotoBucketDetailActivity extends AppCompatActivity {

    private DocumentReference mDocRef;
    private DocumentSnapshot mDocSnapshot;
    private TextView mCaptionTextView;
    private TextView mImageurlTextView;
    private ImageView mImageView;      //******


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_bucket_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mCaptionTextView = findViewById(R.id.detail_caption);
        mImageurlTextView = findViewById(R.id.detail_imageurl);
        mImageView = findViewById(R.id.detail_image_view);   //*****



        // Intent receivedIntent = getIntent();
        String docId = getIntent().getStringExtra(Constants.EXTRA_DOC_ID);

        // Temp test
      //  mImageurlTextView.setText(docId);    // This proves that we clicked it


        mDocRef = FirebaseFirestore.getInstance().collection(Constants.COLLECTION_PATH).document(docId);   // Navigates into our firebase firestore
        mDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(Constants.TAG, "Listen failed");   //This prints a warning if we get an exception
                    return;

                }
                if (documentSnapshot.exists()) {      //Check to make sure the document exists
                    mDocSnapshot = documentSnapshot;            // Save documentSnapshot as mDocSnapshot
                    mCaptionTextView.setText((String) documentSnapshot.get(Constants.KEY_CAPTION));  //Sets mCaptionTextView to a string value of Key_CAPTION

                  //  mImageurlTextView.setText((String) documentSnapshot.get(Constants.KEY_IMAGEURL)); //Sets mImageurlTextView to a string value of KEY_IMAGEURL

                         Ion.with(mImageView).load((String)documentSnapshot.get("imageurl"));    //*******
                }
            }

                });




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_delete:

                //TODO: Delete this caption and close this activity
                return true;


        }

        return super.onOptionsItemSelected(item);
    }


}
