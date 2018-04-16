package mx.edu.utng.lajosefa.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import mx.edu.utng.lajosefa.R;
import mx.edu.utng.lajosefa.Entity.uploads.UploadReserve;

public class MainReserveActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private Button btnReserve;
   // private Button btnShow;
    private ImageButton btnDate;
    private EditText edtReservationName;
    private EditText edtReservationPhone;
    private EditText edtReservationPeople;
    private EditText edtReservationDate;
    private EditText edtReservationOrder;
    private Calendar calendar;

    private DatabaseReference databaseReference;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reserve);

        //btnShow = findViewById(R.id.btn_show);
        btnReserve = findViewById(R.id.btn_Reserve);

        edtReservationName = findViewById(R.id.edt_name);
        edtReservationPhone = findViewById(R.id.edt_phone);
        edtReservationPeople = findViewById(R.id.edt_num_people);
        edtReservationDate = findViewById(R.id.edt_date_reservation);
        edtReservationOrder = findViewById(R.id.edt_order);

        databaseReference = FirebaseDatabase.getInstance().getReference("reservations");

        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });
        /**btnShow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openImagesActivity();
            }
        });**/

        btnDate = findViewById(R.id.btn_date);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment =
                        new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("date", edtReservationDate.getText().toString());
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(), "date");
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



    public  void uploadFile(){
        UploadReserve upload = new UploadReserve(edtReservationName.getText().toString().trim(),
                edtReservationPhone.getText().toString().trim(), edtReservationPeople.getText().toString().trim(),
                edtReservationDate.getText().toString().trim(), edtReservationOrder.getText().toString().trim());
        String uploadId = databaseReference.push().getKey();
        databaseReference.child(uploadId).setValue(upload);
        Toast.makeText(this, R.string.reservation_ready,Toast.LENGTH_SHORT).show();

        //
    }

    private  void openImagesActivity(){
        Intent intent = new Intent(this, ImagesReserveActivity.class);
        startActivity(intent);
    }

    private void setDate(final Calendar calendar){
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        edtReservationDate.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public void onDateSet(DatePicker datePicker,
                          int year, int month, int day) {
        Calendar calendar = new GregorianCalendar(year, month, day);
        setDate(calendar);
    }



    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle bundle) {
            // Use the current date as the default date in the picker
            final Calendar calendar = Calendar.getInstance();
            int year, month, day;
            bundle = getArguments();
            if(!bundle.getString("date").equals("")){
                DateFormat format = DateFormat.getDateInstance();
                try {
                    Date dateEdit = format.parse(
                            bundle.getString("date"));
                    calendar.setTime(dateEdit);
                }catch (ParseException e){
                    e.printStackTrace();
                }
            }
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);


            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener) getActivity(),
                    year, month, day);
        }

    }

}
