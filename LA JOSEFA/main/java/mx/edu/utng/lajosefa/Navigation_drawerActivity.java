package mx.edu.utng.lajosefa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import mx.edu.utng.lajosefa.activities.AcercaDeActivity;
import mx.edu.utng.lajosefa.activities.BotellaActivity;
import mx.edu.utng.lajosefa.activities.ChatActivity;
import mx.edu.utng.lajosefa.activities.GalleryActivity;
import mx.edu.utng.lajosefa.activities.ImagesDrinkActivity;
import mx.edu.utng.lajosefa.activities.LinkActivity;
import mx.edu.utng.lajosefa.activities.LoginGoogleActivity;
import mx.edu.utng.lajosefa.activities.MainReserveActivity;
import mx.edu.utng.lajosefa.activities.PromotionImagesActivity;
import mx.edu.utng.lajosefa.activities.ScheduleImagesActivity;
import mx.edu.utng.lajosefa.activities.VideoActivity;
import mx.edu.utng.lajosefa.fragments.ContactUsFragment;
import mx.edu.utng.lajosefa.fragments.ImagesEventFragment;

public class Navigation_drawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth mAuth;
    private String username;
    private String email;
    private FirebaseDatabase database;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    //Para condicionar usuario
    private GoogleApiClient googleApiClient;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();



        //Log in with google
        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        //for (int i = 0; i <2 ; i++) {
            mSectionsPagerAdapter.getItem(0);
        //}



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    //extraer cuenta

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();

            email = account.getEmail();

            if(email.equals("lajosefadolores@gmail.com")){
                mostrarItems();
            }else{
                esconderAlgunosItems();
            }
        }else{
            goLogInScreen();
        }
    }


    private void esconderAlgunosItems()
    {
        NavigationView navigationVie = (NavigationView) findViewById(R.id.nav_view);// Colocas el id de tu NavigationView
        Menu miMenu= navigationVie.getMenu();
        miMenu.findItem(R.id.admin).setVisible(false);
    }
    private void mostrarItems()
    {
        NavigationView navigationVie = (NavigationView) findViewById(R.id.nav_view);// Colocas el id de tu NavigationView
        Menu miMenu= navigationVie.getMenu();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.reservar_nav_item) {
            Intent intent = new Intent(this, MainReserveActivity.class);
            startActivity(intent);
        } else if (id == R.id.promo_nav_item) {
           Intent intent = new Intent(getApplicationContext(), PromotionImagesActivity.class);
           startActivity(intent);
        } else if (id == R.id.msj_nav_item) {
           Intent sig= new Intent(getApplicationContext(), ChatActivity.class);
           startActivity(sig);
        } else if (id == R.id.bebida_nav_item) {
            Intent intent = new Intent(this, ImagesDrinkActivity.class);
            startActivity(intent);
        } else if (id == R.id.horario_nav_item) {
            Intent sig= new Intent(getApplicationContext(), ScheduleImagesActivity.class);
            startActivity(sig);
        } else if(id == R.id.facebook_nav_item){
            Intent intent = new Intent(getApplicationContext(), LinkActivity.class);
            startActivity(intent);
        } else if(id == R.id.galeria_nav_item){
            Intent sig= new Intent(getApplicationContext(), GalleryActivity.class);
            startActivity(sig);
        } else if(id == R.id.video){
            Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
            startActivity(intent);
        }else if(id == R.id.bottle) {
            Intent intent = new Intent(getApplicationContext(), BotellaActivity.class);
            startActivity(intent);
        }else if(id == R.id.ubic) {
            ContactUsFragment contactUsFragment = new ContactUsFragment();
            FragmentManager manager= getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainLayout,contactUsFragment).commit();
        }else if(id == R.id.admin){
            Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
            startActivity(intent);
        }else if(id == R.id.info) {
            Intent intent = new Intent(getApplicationContext(), AcercaDeActivity.class);
            startActivity(intent);
        }else if(id == R.id.sesion) {
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if(status.isSuccess()){
                        goLogInScreen();
                    }else{
                        Toast.makeText(getApplicationContext(), R.string.not_log_out,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private void goLogInScreen() {
        Intent intent = new Intent(this,LoginGoogleActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter{


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position){
                case 0:
                    ImagesEventFragment imagesEventFragment = new ImagesEventFragment();
                    FragmentManager manager= getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.mainLayout,imagesEventFragment).commit();

            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
        public CharSequence getPageTitle(int position){
            switch (position){
                case 0:
                    return getString(R.string.welcome);
            }
            return null;
        }
    }
}
