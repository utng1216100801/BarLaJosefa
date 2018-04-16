package mx.edu.utng.lajosefa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;

import mx.edu.utng.lajosefa.activities.GalleryActivity;
import mx.edu.utng.lajosefa.activities.ImagesReserveActivity;
import mx.edu.utng.lajosefa.activities.MainDrinkActivity;
import mx.edu.utng.lajosefa.activities.MainEventActivity;
import mx.edu.utng.lajosefa.activities.PromotionActivity;
import mx.edu.utng.lajosefa.activities.ScheduleActivity;
import mx.edu.utng.lajosefa.fragments.ImagesEventFragment;
import mx.edu.utng.lajosefa.fragments.MainEventFragment;

public class AdminActivity extends AppCompatActivity {

    GridLayout mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mainGrid = findViewById(R.id.mainGrid);

        setSingleEvent(mainGrid);


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



    private void setSingleEvent(GridLayout mainGrid){

        for(int i = 0;i<6;i++){
            //Log.i("i--->", ""+i);
            CardView cardView = (CardView)mainGrid.getChildAt(i);

            final int finalI = i;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(AdminActivity.this,"Clicked at index"+finalI,Toast.LENGTH_SHORT).show();

                    if(finalI == 0){
                        Intent intent = new Intent(AdminActivity.this, ScheduleActivity.class);
                        startActivity(intent);
                    }else if(finalI == 1){
                        Intent intent = new Intent(AdminActivity.this, PromotionActivity.class);
                        startActivity(intent);
                    }else if(finalI == 2){
                        Intent intent = new Intent(AdminActivity.this, ImagesReserveActivity.class);
                        startActivity(intent);
                    }
                    else if(finalI == 3){
                        Intent intent = new Intent(AdminActivity.this, MainEventActivity.class);
                        startActivity(intent);
                    }else if(finalI == 4){
                        Intent intent = new Intent(AdminActivity.this, MainDrinkActivity.class);
                        startActivity(intent);

                    }else if(finalI == 5){
                        Intent intent = new Intent(AdminActivity.this, GalleryActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }

    }
}
