package mx.edu.utng.lajosefa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import mx.edu.utng.lajosefa.R;

public class VideoActivity extends AppCompatActivity {
TextView txvTitle;
Button btnLista;
Button btnLady;
Button btnIntro;

public static final long DURATION_TRANSITION= 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        txvTitle = findViewById(R.id.txv_main);
        btnLista=findViewById(R.id.btn_show);
        btnLady=findViewById(R.id.btn_lady);

        btnIntro=findViewById(R.id.btn_intro);


        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aparicion();
            }
        });


        txvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animacion();
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



    public void animacion (){
        Animation anima = AnimationUtils.loadAnimation(this,R.anim.animacion);
        txvTitle.startAnimation(anima);
    }
    public void aparicion (){
        Animation anima = AnimationUtils.loadAnimation(this,R.anim.aparicion);
        btnLady.startAnimation(anima);
        btnIntro.startAnimation(anima);
    }
    public void video(View view) {
        startActivity(new Intent(this, IntroActivity.class));

    }

    public void video1(View view) {
        startActivity(new Intent(this, LadyActivity.class));

    }






}
