package mx.edu.utng.lajosefa.activities;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

import mx.edu.utng.lajosefa.R;
import mx.edu.utng.lajosefa.Entity.uploads.UploadGallery;

public class GalleryActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Button mButtonShow;
    private ImageButton botonCompartir;

    private String mPath;

    private Uri mImageUri;


    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    private final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 200;
    private final int  MY_PERMISSIONS_REQUEST_CAMERA = 200;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private final int PHOTO_CODE = 200;

    private final String TEMPORAL_PICTURE_NAME = "temporal.jpg";
    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "media";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_upload);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);
        mButtonShow = findViewById(R.id.button_show);
        mStorageRef = FirebaseStorage.getInstance().getReference(getString(R.string.uploads));
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(getString(R.string.uploads));

        botonCompartir = (ImageButton) findViewById(R.id.button_share);

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mUploadTask != null && mUploadTask.isInProgress() ){
                    Toast.makeText(GalleryActivity.this, R.string.upload_progress,Toast.LENGTH_SHORT).show();


                }else
                    uploadFile();
            }
        });
        mButtonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagesActivity();
            }
        });

        botonCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aqui asginamos la accion a realizar
                //importamos las librerias con alt+intro
                mImageView.buildDrawingCache();
                Bitmap bitmap = mImageView.getDrawingCache();
                /***** COMPARTIR IMAGEN *****/
                try {
                    File file = new File(getCacheDir(), bitmap + ".png");
                    FileOutputStream fOut = null;
                    fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    file.setReadable(true, false);
                    final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                    intent.setType("image/png");
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            }
        }


        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.CAMERA)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

            }
        }
    }


    private void openFileChooser() {
        final CharSequence[] option = {getString(R.string.take_photo), getString(R.string.choose_gallery), getString(R.string.cancel)};
        final AlertDialog.Builder builder = new AlertDialog.Builder(GalleryActivity.this);
        builder.setTitle(R.string.choose_option);
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(option[which] == getString(R.string.take_photo)) {
                    openCamera();
                }else if (option[which] == getString(R.string.choose_gallery)) {
                    Intent intent = new Intent();
                    intent.setType(getString(R.string.format_image));
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, PICK_IMAGE_REQUEST);
                }else {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        file.mkdirs();

        String path =Environment.getExternalStorageDirectory()+file.separator + MEDIA_DIRECTORY+file.separator + TEMPORAL_PICTURE_NAME;
        File newfile = new File(path);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(newfile));
        startActivityForResult(intent,PHOTO_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_CODE:
                if(resultCode == RESULT_OK){
                    String mDir =Environment.getExternalStorageDirectory() + File.separator+MEDIA_DIRECTORY+File.separator+TEMPORAL_PICTURE_NAME;
                    decodeBitmap(mDir);


                }
                break;
            case PICK_IMAGE_REQUEST:
                if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                        && data != null && data.getData() != null) {
                    mImageUri = data.getData();
                    Picasso.with(this).load(mImageUri).into(mImageView);
                    break;
                }


        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {

        if (mImageUri != null ) {

            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));


            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(GalleryActivity.this, R.string.upload_sucess, Toast.LENGTH_SHORT).show();
                            UploadGallery upload = new UploadGallery(taskSnapshot.getDownloadUrl().toString());
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(GalleryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, R.string.no_file_selected, Toast.LENGTH_SHORT).show();

        }


    }


    private void openImagesActivity (){
        Intent intent = new Intent( this, ImageGalleryActivity.class);
        startActivity(intent);
    }


    private void decodeBitmap(String dir) {
        Bitmap bitmap;
        bitmap= BitmapFactory.decodeFile(dir);
        mImageView.setImageBitmap(bitmap);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(REQUEST_CODE_ASK_PERMISSIONS == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // OK Do something with..
            } else {
                // The user does not grant permissions
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mPath = savedInstanceState.getString("file_path");
    }
}
