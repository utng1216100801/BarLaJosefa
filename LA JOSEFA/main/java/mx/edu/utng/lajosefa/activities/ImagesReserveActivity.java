package mx.edu.utng.lajosefa.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import mx.edu.utng.lajosefa.R;
import mx.edu.utng.lajosefa.adapters.ImageReserveAdapter;
import mx.edu.utng.lajosefa.Entity.uploads.UploadReserve;

public class ImagesReserveActivity extends AppCompatActivity implements ImageReserveAdapter.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private ImageReserveAdapter mAdapter;

    private ProgressBar mPgbCircle;

    private DatabaseReference mDatabaseRef;

    private ValueEventListener mDBListener;

    private List<UploadReserve> mUpload;

    private ImageButton btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_reserve);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPgbCircle = findViewById(R.id.pgb_circle);

        mUpload = new ArrayList<>();

        mAdapter = new ImageReserveAdapter(ImagesReserveActivity.this, mUpload);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(ImagesReserveActivity.this);


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("reservations");

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUpload.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    UploadReserve upload = postSnapshot.getValue(UploadReserve.class);
                    upload.setKey(postSnapshot.getKey());
                    mUpload.add(upload);
                }

                mAdapter.notifyDataSetChanged();

                mPgbCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ImagesReserveActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mPgbCircle.setVisibility(View.INVISIBLE);
            }
        });


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


    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onWhatEverClick(int position) {

    }

    @Override
    public void onDeleteClick(int position) {
        UploadReserve selectedItem = mUpload.get(position);
        final String selectedKey = selectedItem.getmKey();

        mDatabaseRef.child(selectedKey).removeValue();
        Toast.makeText(ImagesReserveActivity.this, R.string.success_process, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
}
