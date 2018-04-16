package mx.edu.utng.lajosefa.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MainEventFragment extends Fragment {
    Context mBase;
    private static final int PICK_IMAGE_REQUEST =1;
    public static final int RESULT_OK = -1;

    private Button btnChoose;
    private Button btnUpload;
    private Button btnShow;
    private EditText edtCommentary;
    private ImageView imvImage;
    private ProgressBar pgbLoading;

    private Uri uri;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    private StorageTask uploadTask;

    APIService mService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_event, container, false);
        btnChoose = v.findViewById(R.id.btn_choose_file);
        btnShow = v.findViewById(R.id.btn_show);
        btnUpload = v.findViewById(R.id.btn_upload);
        edtCommentary = v.findViewById(R.id.edt_event_commentary);

        imvImage = v.findViewById(R.id.imv_event_image);
        pgbLoading = v.findViewById(R.id.pgb_loading);

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
                    Toast.makeText(getActivity(),R.string.loading, Toast.LENGTH_SHORT).show();
                }else {
                    uploadFile();
                }
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagesActivity();
            }
        });

        //message
        Common.currentToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("MY TOKEN", ""+Common.currentToken);
        mService = Common.getFCMClient();

        FirebaseMessaging.getInstance().subscribeToTopic("MyTopic");

        return v;
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                && data.getData() != null){
            uri = data.getData();
            Picasso.with(getActivity()).load(uri).into(imvImage);
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
                            Toast.makeText(getActivity(),R.string.success, Toast.LENGTH_SHORT).show();
                            UploadEvent upload = new UploadEvent(edtCommentary.getText().toString().trim(),taskSnapshot.getDownloadUrl().toString());
                            String uploadId = databaseReference.push().getKey();
                            databaseReference.child(uploadId).setValue(upload);
                            sendMessage();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();

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
            Toast.makeText(getActivity(), R.string.do_not_selected, Toast.LENGTH_SHORT).show();
        }
    }
    private  void openImagesActivity(){
        Intent intent = new Intent(getActivity(), ImagesEventFragment.class);
        startActivity(intent);
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
                            Toast.makeText(getActivity(), R.string.success, Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getActivity(), R.string.failed, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {
                        Log.e("SENDING ERROR:", t.getMessage());
                    }
                });

    }
    public ContentResolver getContentResolver() {
        return mBase.getContentResolver();
    }

}
