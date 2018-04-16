package mx.edu.utng.lajosefa.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import mx.edu.utng.lajosefa.R;
import mx.edu.utng.lajosefa.adapters.ScheduleImageAdapter;
import mx.edu.utng.lajosefa.Entity.uploads.ScheduleUpload;

public class ScheduleImagesActivity extends AppCompatActivity implements ScheduleImageAdapter.OnItemClickListener{

    private RecyclerView recyclerView;
    private ScheduleImageAdapter scheduleImageAdapter;
    private ProgressBar pgbCircle;

    private FirebaseStorage storage;

    private DatabaseReference databaseReference;
    private ValueEventListener dbListener;
    private List<ScheduleUpload> scheduleUploadList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        recyclerView = findViewById(R.id.rcv_schedule);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pgbCircle = findViewById(R.id.pgb_circle);

        scheduleUploadList = new ArrayList<>();

        scheduleImageAdapter = new ScheduleImageAdapter(ScheduleImagesActivity.this, scheduleUploadList);
        recyclerView.setAdapter(scheduleImageAdapter);

        scheduleImageAdapter.setOnItemClickListener(ScheduleImagesActivity.this);

        storage = FirebaseStorage.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("schedules");

        dbListener= databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                scheduleUploadList.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    ScheduleUpload scheduleUpload = postSnapshot.getValue(ScheduleUpload.class);
                    scheduleUpload.setKey(postSnapshot.getKey());
                    scheduleUploadList.add(scheduleUpload);
                }

                scheduleImageAdapter.notifyDataSetChanged();
                pgbCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ScheduleImagesActivity.this, databaseError.getMessage(),
                        Toast.LENGTH_SHORT).show();
                pgbCircle.setVisibility(View.INVISIBLE);
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
        //Toast.makeText(this, "Click a la posición: "+position,
               // Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWhatEverClick(int position) {
        //Toast.makeText(this, "Click a la posición: "+position,
                //Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {
        ScheduleUpload selectedItem = scheduleUploadList.get(position);
        final String selectedKey = selectedItem.getKey();

        StorageReference imageReference = storage.getReferenceFromUrl(selectedItem.getUrlImage());
        imageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseReference.child(selectedKey).removeValue();
                Toast.makeText(ScheduleImagesActivity.this, R.string.success_delete,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        databaseReference.removeEventListener(dbListener);
    }

    /**public void nextActivity(View view) {
            Intent intent = new Intent(this, PromotionActivity.class);
            startActivity(intent);
    }**/
}
