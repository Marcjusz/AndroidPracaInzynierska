package com.ommomm.t1;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    int flag_exit;
    boolean doubleClickBack;
    ArrayList<Integer> list = new ArrayList<>();




    DatabaseReference database;
    EditText editTextKontaktKontakt;
    EditText editTextKontaktTresc;

    EditText editTextLoginEmail;
    EditText editTextLoginHaslo;
    EditText editTextImie;
    EditText editTextNazwisko;
    EditText editTextTelefon;

    ProgressBar progressBarLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        doubleClickBack=false;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance().getReference();

        fragmentGosc();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if(list.isEmpty() || flag_exit==1 || doubleClickBack==true){
            super.onBackPressed();
        }
        else if (list.size()==1) {
            fragmentGosc();
            flag_exit=1;
        }
        else {
            if (list.get(list.size() - 1) == 1) {
                fragmentLogin();
            }
            else if (list.get(list.size() - 1) == 2) {
                fragmentSignup();
            }
            else if (list.get(list.size() - 1) == 3) {
                fragmentInfo();
            }
            else if (list.get(list.size() - 1) == 4) {
                fragmentKontakt();
            }
            list.remove(list.size() - 1);
            list.remove(list.size() - 1);
        }
        doubleClickBack=true;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                doubleClickBack=false;
            }
        }, 500);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            fragmentLogin();
        } else if (id == R.id.nav_sign_up) {
            fragmentSignup();
        } else if (id == R.id.nav_info) {
            fragmentInfo();
        } else if (id == R.id.nav_kontakt) {
            fragmentKontakt();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void activitySignup(View view) {
        fragmentSignup();
    }

    public void activityLogin(View view) {
        fragmentLogin();
    }

    public void activityLoginReset(View view){
        fragmentLoginReset();
    }

    public void fragmentGosc(){
        Gosc gosc = new Gosc();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, gosc).commit();
        list.clear();
        list.add(0);
    }

    public void fragmentLogin(){
        flag_exit=0;
        list.add(1);
        Login login = new Login();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, login).commit();
    }

    public void fragmentSignup(){
        flag_exit=0;
        list.add(2);
        Signup signup = new Signup();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, signup).commit();
    }

    public void fragmentInfo(){
        flag_exit=0;
        list.add(3);
        Info info = new Info();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, info).commit();
    }

    public void fragmentKontakt(){
        flag_exit=0;
        list.add(4);
        Kontakt kontakt = new Kontakt();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, kontakt).commit();
    }

    public void fragmentLoginReset(){
        LoginReset loginReset = new LoginReset();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, loginReset).commit();
    }

    public void kontaktGosc(View view){
        editTextKontaktKontakt = (EditText) findViewById(R.id.input_kontakt);
        editTextKontaktTresc = (EditText) findViewById(R.id.input_tresc);
        String kontakt= editTextKontaktKontakt.getText().toString().trim();
        String tresc= editTextKontaktTresc.getText().toString().trim();
        if(!TextUtils.isEmpty(tresc))
        {
            if(tresc.length()<20){
                Toast.makeText(this, "Proszę wprowadzić minimum 20 znaków", Toast.LENGTH_LONG).show();
            }
            else if(tresc.length()>250){
                Toast.makeText(this, "Wiadomość jest zbyt długa", Toast.LENGTH_LONG).show();
            }
            else {
                if (TextUtils.isEmpty(kontakt)) {
                    kontakt = "anonim";
                }
                String id = database.push().getKey();
                BazaKontakt bazaKontakt = new BazaKontakt(id, kontakt, tresc);
                database.child("Kontakt").child(id).setValue(bazaKontakt);
                Toast.makeText(this, "Wysłano wiadomosc", Toast.LENGTH_LONG).show();
                fragmentGosc();
            }
        }
        else
        {
            Toast.makeText(this, "Wprowadź treść wiadomości", Toast.LENGTH_LONG).show();
        }

    }

    public void singup(View view) {
        EditText editTextEmail = findViewById(R.id.input_email);
        EditText editTextPassword = findViewById(R.id.input_password);
        editTextImie = findViewById(R.id.input_imie);
        editTextNazwisko = findViewById(R.id.input_nazwisko);
        editTextTelefon = findViewById(R.id.input_telefon);
        Boolean isNumber= Patterns.PHONE.matcher(editTextTelefon.getText().toString()).matches();
        Boolean isEmail= Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getText().toString()).matches();
        if(editTextEmail.getText().toString().length()<6 || !isEmail){
            Toast.makeText(MainActivity.this, "Nieprawidłowy mail",Toast.LENGTH_SHORT).show();
        } else if(editTextPassword.getText().toString().length()<8){
            Toast.makeText(MainActivity.this, "Nieprawidłowe hasło",Toast.LENGTH_SHORT).show();
        } else if(editTextImie.getText().toString().length()<2){
            Toast.makeText(MainActivity.this, "Nieprawidłowe imie",Toast.LENGTH_SHORT).show();
        } else if(editTextNazwisko.getText().toString().length()<3){
            Toast.makeText(MainActivity.this, "Nieprawidłowe nazwisko",Toast.LENGTH_SHORT).show();
        } else if(editTextTelefon.getText().toString().length()!=9 || !isNumber){
            Toast.makeText(MainActivity.this, "Błędny numer telefonu",Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString().trim(), editTextPassword.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success");
                                Toast.makeText(MainActivity.this, "Konto zostało założone",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                BazaKlient bazaKlient = new BazaKlient(editTextImie.getText().toString().trim(), editTextNazwisko.getText().toString().trim()
                                        , editTextTelefon.getText().toString().trim());
                                database.child("Klient").child(user.getUid()).setValue(bazaKlient);
                                BazaWiadomosc bazaWiadomosc= new BazaWiadomosc("Witaj nowu użytkowniku","Witamu cię w gornie użytkowników... trochę informacji","1");
                                String id= database.child("Klient").child("Wiadomosc").push().getKey();
                                database.child("Klient").child(user.getUid()).child("Wiadomosc").child(id).setValue(bazaWiadomosc);
                                fragmentGosc();
                                updateUI(user);
                            } else {
                                Log.w("TAG", "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Mail jest zajęty, bądź wystąpił problem podczas komunikacji, spróbuj ponownie",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }
    }

    public void login(View view) {
        editTextLoginEmail = findViewById(R.id.input_Email);
        editTextLoginHaslo = findViewById(R.id.input_Haslo);
        if (TextUtils.isEmpty(editTextLoginEmail.getText().toString().trim()) || TextUtils.isEmpty(editTextLoginHaslo.getText().toString().trim())) {
            Toast.makeText(MainActivity.this, "Wprowadź dane do logowania",Toast.LENGTH_SHORT).show();
        } else if (editTextLoginEmail.getText().toString().length() < 6) {
            Toast.makeText(MainActivity.this, "Nieprawidłowy mail",Toast.LENGTH_SHORT).show();
        } else if( editTextLoginHaslo.getText().toString().length() < 8){
            Toast.makeText(MainActivity.this, "Nieprawidłowe hasło",Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(editTextLoginEmail.getText().toString().trim(), editTextLoginHaslo.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                fragmentGosc();
                                updateUI(mAuth.getCurrentUser());
                            } else {
                                Toast.makeText(MainActivity.this, "Błędne hasło bądź mail",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void resetHasla(View view){
        EditText editTextLoginEmail = findViewById(R.id.input_Email);
        if(!TextUtils.isEmpty(editTextLoginEmail.getText().toString().trim())){
            mAuth.sendPasswordResetEmail(editTextLoginEmail.getText().toString().trim())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("EmailPassword", "Mail z dalszymi instrukcjami został wysłany");
                                Toast.makeText(MainActivity.this, "Mail z dalszymi instrukcjami został wysłąny",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Błędny mail, bądź niezarejestrowany",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else{
            Toast.makeText(MainActivity.this, "Wprowadź adres mail",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(FirebaseUser user){
        if(user != null)
        {
            database.child("Firma").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        Intent intent = new Intent(MainActivity.this, Firma.class);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, Klient.class);
                        startActivity(intent);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, "Błąd podczas komunikacji z bazą",
                            Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

}
