package mx.edu.utng.lajosefa.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import mx.edu.utng.lajosefa.Common;
import mx.edu.utng.lajosefa.R;
import mx.edu.utng.lajosefa.Entity.MyResponse;
import mx.edu.utng.lajosefa.Entity.Notification;
import mx.edu.utng.lajosefa.Entity.Sender;
import mx.edu.utng.lajosefa.remote.APIService;
import mx.edu.utng.lajosefa.Entity.uploads.UploadEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainEventActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST =1;

    private Button btnChoose;
    private Button btnUpload;
    private EditText edtCommentary;
    private ImageView imvImage;
    private ProgressBar pgbLoading;

    private Uri uri;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    private StorageTask uploadTask;

    APIService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_event);
        btnChoose = findViewById(R.id.btn_choose_file);
        btnUpload = findViewById(R.id.btn_upload);
        edtCommentary = findViewById(R.id.edt_event_commentary);

        imvImage = findViewById(R.id.imv_event_image);
        pgbLoading = findViewById(R.id.pgb_loading);

        storageReference = FirebaseStorage.getInstance().getReference("events");
        databaseReference = FirebaseDatabase.getInstance().getReference("events");

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(MainEventActivity.this, R.string.loading, Toast.LENGTH_SHORT).show();
                }else {
                    uploadFile();
                }
            }
        });


        //message
        Common.currentToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("MY TOKEN", ""+ Common.currentToken);
        mService = Common.getFCMClient();

        FirebaseMessaging.getInstance().subscribeToTopic("MyTopic");



        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                && data.getData() != null){
            uri = data.getData();
            Picasso.with(this).load(uri).into(imvImage);
        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(){
        if(uri != null){
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()+"."+
                    getFileExtension(uri));
            uploadTask = fileReference.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    pgbLoading.setProgress(0);
                                }
                            },500);
                            Toast.makeText(MainEventActivity.this,R.string.success, Toast.LENGTH_SHORT).show();
                            UploadEvent upload = new UploadEvent(edtCommentary.getText().toString().trim(),taskSnapshot.getDownloadUrl().toString());
                            String uploadId = databaseReference.push().getKey();
                            databaseReference.child(uploadId).setValue(upload);
                            sendMessage();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainEventActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            pgbLoading.setProgress((int)progress);
                        }
                    });
        }else {
            Toast.makeText(this, R.string.file_not_selected, Toast.LENGTH_SHORT).show();
        }
    }
    private void sendMessage(){
        //send request
        Notification notification = new Notification("Bar La Josefa",getString(R.string.new_event));
        //Notification notification = new Notification(edtDrinkName.getText().toString(),edtDrinkDescription.getText().toString());
        Sender sender = new Sender(notification, "/topics/MyTopic");
        mService.sendNotification(sender)
                .enqueue(new Callback<MyResponse>() {
                    @Override
                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                        System.out.println(getString(R.string.success)+response.isSuccessful());
                        if(response.isSuccessful()){
                            Toast.makeText(MainEventActivity.this, R.string.success, Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainEventActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {
                        Log.e("SENDING ERROR:", t.getMessage());
                    }
                });

    }
}
