package com.ommomm.t1;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class Firma extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText editTextKontaktFirma;

    private static final String permissionFineLocation = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String permissionCoarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int permissionCode = 13;
    private Boolean permissionLocation = false;

    private FirebaseAuth mAuth;
    DatabaseReference database;
    DatabaseReference database2;
    boolean doubleClickBack;
    LinearLayout linearLayoutContent;
    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    ArrayList<String> listWiadomoscID =new ArrayList<>();

    BazaLokal bazaLokal;
    BazaOferta bazaOferta;
    ArrayList<BazaOferta> listBazaOfert = new ArrayList<>();
    ArrayList<BazaLokal> listBazaLokal = new ArrayList<>();

    EditText editTextLokalNazwa;
    EditText editTextLokalOpis;
    EditText editTextLokalTel1;
    EditText editTextLokalTel2;
    EditText editTextLokalAdres;
    EditText editTextLokalPhoto1;
    EditText editTextLokalPhoto2;
    EditText editTextLokalPhoto3;
    int flag;

    ArrayList<String> listaLokalID =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        doubleClickBack=false;

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance().getReference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firma);
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.firma, menu);
        return true;
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

        android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        if(fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        linearLayoutContent = (LinearLayout) findViewById(R.id.linear_firma_content);
        linearLayoutContent.removeAllViews();


        if (id == R.id.nav_logout) {
            mAuth.signOut();
            updateUI(null);
        } else if (id == R.id.nav_edytuj) {
            fragmentProfilEdycja();
        } else if (id == R.id.nav_wiadomosci){
            fragmentWiadomosc();
        } else if (id == R.id.nav_lokale){
            fragmentLokale();
        } else if (id == R.id.nav_actual_oferts){
            fragmentDanie();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void fragmentProfilEdycja(){
        FirmaProfilEdycja firmaProfilEdycja = new FirmaProfilEdycja();
        fragmentManager.beginTransaction().replace(R.id.fragment, firmaProfilEdycja).commit();
    }

    private void updateUI(FirebaseUser user){
        if(user == null)
        {
            this.finish();
        }
    }

    public void kontaktFirma(View view){

        editTextKontaktFirma = (EditText) findViewById(R.id.input_tresc);
        String tresc= editTextKontaktFirma.getText().toString().trim();

        if(!TextUtils.isEmpty(tresc))
        {
            if(tresc.length()<20){
                Toast.makeText(this, "Proszę wprowadzić minimum 20 znaków", Toast.LENGTH_LONG).show();
            }
            else if(tresc.length()>250){
                Toast.makeText(this, "Wiadomość jest zbyt długa", Toast.LENGTH_LONG).show();
            }
            else {
                String id = database.push().getKey();
                BazaKontakt bazaKontakt = new BazaKontakt(mAuth.getUid(), "nazwa firmy|", tresc);
                database.child("Kontakt_Firma").child(id).setValue(bazaKontakt);
                Toast.makeText(this, "Wysłano wiadomosc", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this, "Wprowadź treść wiadomości", Toast.LENGTH_LONG).show();
        }

    }

    public void fragmentLokale(){
        database.child("Firma").child(mAuth.getUid()).child("Lokal").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()){

                    listaLokalID.add(childSnapshot.getKey());
                    final BazaLokal bazaLokal = childSnapshot.getValue(BazaLokal.class);
                    linearLayoutContent = (LinearLayout) findViewById(R.id.linear_firma_content);
                    TextView textView1 = new TextView(Firma.this);
                    textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    textView1.setText(bazaLokal.getNazwa());
                    textView1.setPadding(20,10,20,10);
                    textView1.setTextSize(20);
                    textView1.setBackgroundColor(0xffffffff);
                    LinearLayout.LayoutParams layoutParams = new
                            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(20, 20, 30, 10);
                    textView1.setId(listaLokalID.size());

                    textView1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            linearLayoutContent.removeAllViews();
                            boolean flag=true;

                            for(int i=0; i<=listaLokalID.size(); i++){
                                if(v.getId()==i){

                                    database.child("Firma").child(mAuth.getUid()).child("Lokal").child(listaLokalID.get(i-1)).addListenerForSingleValueEvent(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            BazaLokal bazaLokal = dataSnapshot.getValue(BazaLokal.class);
                                            Intent intent = new Intent(Firma.this, FirmaLokal.class);
                                            intent.putExtra("Lokal",bazaLokal);
                                            intent.putExtra("LokalID",dataSnapshot.getKey());
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                    flag=false;
                                    break;
                                }
                            }
                            if(flag){
                                Toast.makeText(Firma.this, "Błąd podczas pobierania wiadomości spróbuj ponownie", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                    linearLayoutContent.addView(textView1, layoutParams);
                }
                linearLayoutContent = (LinearLayout) findViewById(R.id.linear_firma_content);
                final Button buttonLokalAdd = new Button(Firma.this);
                buttonLokalAdd.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                buttonLokalAdd.setText("Utwórz profil nowego lokalu");
                buttonLokalAdd.setPadding(20,15,20,15);
                buttonLokalAdd.setTextSize(20);

                buttonLokalAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        locationPermission();
                        linearLayoutContent.removeAllViews();
                        FirmaLokalNowy firmaLokalNowy = new FirmaLokalNowy();
                        fragmentManager.beginTransaction().replace(R.id.fragment, firmaLokalNowy).commit();
                    }
                });


                LinearLayout.LayoutParams layoutParams = new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(20, 20, 30, 10);
                linearLayoutContent.addView(buttonLokalAdd, layoutParams);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void fragmentWiadomosc(){

        database.child("Firma").child(mAuth.getUid()).child("Wiadomosc").addListenerForSingleValueEvent(new ValueEventListener()  {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    final BazaWiadomosc bazaWiadomosc = childSnapshot.getValue(BazaWiadomosc.class);
                    linearLayoutContent = (LinearLayout) findViewById(R.id.linear_firma_content);
                    TextView textView1 = new TextView(Firma.this);
                    textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    listWiadomoscID.add(childSnapshot.getKey());
                    textView1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            linearLayoutContent.removeAllViews();
                            boolean flag=true;
                            for(int i=0; i<=listWiadomoscID.size(); i++){
                                if(v.getId()==i){

                                    database.child("Firma").child(mAuth.getUid()).child("Wiadomosc").child(listWiadomoscID.get(i-1)).addListenerForSingleValueEvent(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            BazaWiadomosc bazaWiadomosc1 = dataSnapshot.getValue(BazaWiadomosc.class);

                                            TextView textView1 = new TextView(Firma.this);
                                            textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                                            textView1.setText(bazaWiadomosc1.getHeader());
                                            textView1.setTextSize(20);
                                            textView1.setBackgroundColor(0xff66ff66);
                                            textView1.setPadding(20,10,20,10);
                                            LinearLayout.LayoutParams layoutParams = new
                                                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                                            layoutParams.setMargins(20, 20, 30, 10);
                                            linearLayoutContent.addView(textView1, layoutParams);
                                            textView1 = new TextView(Firma.this);
                                            textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                                            textView1.setText(bazaWiadomosc1.getBody());
                                            textView1.setTextSize(15);
                                            textView1.setBackgroundColor(0xffffffff);
                                            textView1.setPadding(20,10,20,10);
                                            linearLayoutContent.addView(textView1, layoutParams);

                                            if(bazaWiadomosc1.getStatus().equals("1")){
                                                HashMap<String, Object> result = new HashMap<>();
                                                result.put("status", "0");
                                                database.child("Firma").child(mAuth.getUid()).child("Wiadomosc").child(dataSnapshot.getKey()).updateChildren(result);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    flag=false;
                                    break;
                                }
                            }
                            if(flag){
                                Toast.makeText(Firma.this, "Błąd podczas pobierania wiadomości spróbuj ponownie", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    textView1.setText(bazaWiadomosc.getHeader());
                    textView1.setId(listWiadomoscID.size());
                    textView1.setPadding(20,10,20,10);
                    textView1.setTextSize(20);
                    textView1.setBackgroundColor(0xffffffff);
                    if(bazaWiadomosc.status.equals("1")){
                        textView1.setTypeface(null, Typeface.BOLD);
                        textView1.setBackgroundColor(0xff66ff66);
                    }

                    LinearLayout.LayoutParams layoutParams = new
                            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(20, 20, 30, 10);
                    linearLayoutContent.addView(textView1, layoutParams);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Firma.this, "Błąd podczas pobierania wiadomości, spróbuj ponownie", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void dodajLokal(View view){

        editTextLokalNazwa = findViewById(R.id.input_lokalNazwa);
        editTextLokalOpis = findViewById(R.id.input_lokalOpis);
        editTextLokalAdres = findViewById(R.id.input_lokalAdres);
        editTextLokalTel1 = findViewById(R.id.input_lokalTel1);
        editTextLokalTel2 = findViewById(R.id.input_lokalTel2);

        Boolean isNumber1= Patterns.PHONE.matcher(editTextLokalTel1.getText().toString()).matches();
        Boolean isNumber2= Patterns.PHONE.matcher(editTextLokalTel2.getText().toString()).matches();
        if(!(isNumber1 && editTextLokalTel1.getText().toString().length()==9
                && (editTextLokalTel2.getText().toString().length()==9 && isNumber2 || editTextLokalTel2.getText().toString().isEmpty()))){
            Toast.makeText(Firma.this, "Błędny numer telefonu",Toast.LENGTH_SHORT).show();
        } else if (editTextLokalNazwa.getText().toString().length()<3){
            Toast.makeText(Firma.this, "Błędna nazwa lokalu",Toast.LENGTH_SHORT).show();
        } else if (editTextLokalOpis.getText().toString().length()<20){
            Toast.makeText(Firma.this, "Błędny opis firmy",Toast.LENGTH_SHORT).show();
        } else if (editTextLokalAdres.getText().toString().length()<10){
            Toast.makeText(Firma.this, "Błędna lokalizacja",Toast.LENGTH_SHORT).show();
        } else {
            String tel2;
            if(editTextLokalTel2.getText().toString().isEmpty()){
                tel2="null";
            }
            else {
                tel2=editTextLokalTel2.getText().toString().trim();
            }

            BazaLokal bazaLokal = new BazaLokal(editTextLokalNazwa.getText().toString().trim(),
                    editTextLokalOpis.getText().toString().trim(), editTextLokalTel1.getText().toString().trim(),
                    tel2, editTextLokalAdres.getText().toString().trim(), "null", "null", "null");
            database.child("Firma").child(mAuth.getUid()).child("Lokal").child(database.push().getKey()).setValue(bazaLokal);
            Toast.makeText(Firma.this, "Utworzono nowy Lokal",Toast.LENGTH_SHORT).show();
            android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
            if(fragment != null)
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            fragmentLokale();
        }
    }

    public void fragmentDanie(){

        database.child("Firma").child(mAuth.getUid()).child("Lokal").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                flag=0;
                for (final DataSnapshot childSnapshot: snapshot.getChildren()) {
                    listBazaLokal.add(childSnapshot.getValue(BazaLokal.class));
                    listaLokalID.add(childSnapshot.getKey());
                    bazaLokal = childSnapshot.getValue(BazaLokal.class);
                    database.child("Firma").child(mAuth.getUid()).child("Lokal").child(childSnapshot.getKey()).child("Oferta").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            linearLayoutContent = (LinearLayout) findViewById(R.id.linear_firma_content);
                            TextView textView1 = new TextView(Firma.this);
                            textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                            textView1.setText(listBazaLokal.get(flag).getNazwa());
                            flag++;
                            textView1.setPadding(20, 10, 20, 10);
                            textView1.setTextSize(20);
                            textView1.setBackgroundColor(0xffffffff);
                            LinearLayout.LayoutParams layoutParams = new
                                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(20, 20, 30, 10);
                            textView1.setId(listaLokalID.size());
                            linearLayoutContent.addView(textView1, layoutParams);

                            for (final DataSnapshot childSnapshot: dataSnapshot.getChildren()){
                                bazaOferta = childSnapshot.getValue(BazaOferta.class);
                                linearLayoutContent = (LinearLayout) findViewById(R.id.linear_firma_content);
                                TextView textView2 = new TextView(Firma.this);
                                textView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT));
                                textView2.setText(bazaOferta.getNazwa());
                                textView2.setPadding(20, 10, 20, 10);
                                textView2.setTextSize(20);
                                textView2.setBackgroundColor(0xffffffff);
                                LinearLayout.LayoutParams layoutParams2 = new
                                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT);
                                layoutParams2.setMargins(120, 20, 30, 10);
                                linearLayoutContent.addView(textView2, layoutParams2);
                            }
                            if(flag==listBazaLokal.size())
                            {
                                linearLayoutContent = (LinearLayout) findViewById(R.id.linear_firma_content);
                                final Button buttonLokalAdd = new Button(Firma.this);
                                buttonLokalAdd.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT));
                                buttonLokalAdd.setText("Dodaj oferte");
                                buttonLokalAdd.setPadding(20,15,20,15);
                                buttonLokalAdd.setTextSize(20);
                                buttonLokalAdd.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        linearLayoutContent.removeAllViews();
                                        FirmaDanieNowe firmaDanieNowe = new FirmaDanieNowe();
                                        fragmentManager.beginTransaction().replace(R.id.fragment, firmaDanieNowe).commit();
                                    }
                                });
                                LinearLayout.LayoutParams layoutParams3 = new
                                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT);
                                layoutParams3.setMargins(20, 20, 30, 10);
                                linearLayoutContent.addView(buttonLokalAdd, layoutParams3);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionLocation = false;

        switch (requestCode){
            case permissionCode:{
                if(grantResults.length >0)
                {
                    for(int i=0; i<grantResults.length; i++)
                    {
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED)
                        {
                            permissionLocation =false;
                            return;
                        }
                    }
                    permissionLocation = true;
                }
            }
        }
    }

    private void  locationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this, permissionCoarseLocation) == PackageManager.PERMISSION_GRANTED)
        {
            //Toast.makeText(this, "Nieprawidłowy mail",Toast.LENGTH_SHORT).show();
            permissionLocation = true;
        }
        else if (ContextCompat.checkSelfPermission(this, permissionFineLocation) == PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "Nieprawidłowy mail2",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, permissions, permissionCode);
        }
        else
        {
            Toast.makeText(this, "Nieprawidłowy mail3",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, permissions, permissionCode);
        }
    }

}
