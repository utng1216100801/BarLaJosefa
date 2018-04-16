package mx.edu.utng.lajosefa.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import mx.edu.utng.lajosefa.R;

public class LinkActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnYouTube;
    private Button  btnFacebook, btnWhatsapp;


    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        btnYouTube = (Button) findViewById(R.id.buttonYouTube);
        btnYouTube.setOnClickListener(this);

        btnFacebook = (Button) findViewById(R.id.buttonFacebook);
        btnFacebook.setOnClickListener(this);
        btnWhatsapp = (Button) findViewById(R.id.buttonwhatsapp);
        btnWhatsapp.setOnClickListener(this);


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
    public void onClick(View v) {


        btnYouTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://www.youtube.com/watch?v=FZqrf7ev6gg");
                Intent intent = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(intent);
            }
        });


        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://www.facebook.com/Josefa2015/?fref=ts");
                Intent intent = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(intent);
            }
        });

        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Bienvenidos a Bar La Josefa https://www.facebook.com/Josefa2015/?fref=ts");
                intent.setPackage("com.whatsapp");
                startActivity(intent);
            }
        });
    }
}
