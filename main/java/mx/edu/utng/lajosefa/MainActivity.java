package mx.edu.utng.lajosefa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import mx.edu.utng.lajosefa.activities.LoginGoogleActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void nextActivity(View view) {
        startActivity(new Intent( this, LoginGoogleActivity.class));
        finish();
    }
}
