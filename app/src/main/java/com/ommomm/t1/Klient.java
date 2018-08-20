package com.ommomm.t1;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.print.PrintAttributes;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.LinearLayout.LayoutParams;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Klient extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    boolean doubleClickBack;


    EditText editTextKontaktKontakt;
    EditText editTextKontaktTresc;

    DatabaseReference database;
    EditText editTextEdycjaImie;
    EditText editTextEdycjaNazwisko;
    EditText editTextEdycjaTelefon;
    BazaKlient bazaKlient;
    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    TextView header;

    LinearLayout linearLayoutContent;
    ArrayList<String> listWiadomoscID =new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        doubleClickBack=false;

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance().getReference();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klient);
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

        edditHeader();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void onBackPressed() {

        if(doubleClickBack==true){
            moveTaskToBack(true);
        }
        doubleClickBack=true;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                doubleClickBack=false;
            }
        }, 500);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.klient, menu);
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
        linearLayoutContent = (LinearLayout) findViewById(R.id.linear_klient_content);
        linearLayoutContent.removeAllViews();

        if (id == R.id.nav_logout) {
            mAuth.signOut();
            updateUI(null);
        } else if (id == R.id.nav_kontakt) {
            fragmentKontakt();
        } else if (id == R.id.nav_info) {
            fragmentInfo();
        } else if (id == R.id.nav_edytuj) {
            fragmentProfilEdycja();
        } else if (id == R.id.nav_wiadomosci){
            fragmentWiadomosc();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void fragmentKontakt(){
        Kontakt kontakt = new Kontakt();
        fragmentManager.beginTransaction().replace(R.id.fragment, kontakt).commit();
    }

    public void fragmentInfo(){
        Info info = new Info();
        fragmentManager.beginTransaction().replace(R.id.fragment, info).commit();
    }

    public void fragmentProfilEdycja(){
        KlientProfilEdycja klientProfilEdycja =  new KlientProfilEdycja();
        fragmentManager.beginTransaction().replace(R.id.fragment, klientProfilEdycja).commit();
    }

    public void fragmentWiadomosc(){



        database.child("Klient").child(mAuth.getUid()).child("Wiadomosc").addListenerForSingleValueEvent(new ValueEventListener()  {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    final BazaWiadomosc bazaWiadomosc = childSnapshot.getValue(BazaWiadomosc.class);
                    linearLayoutContent = (LinearLayout) findViewById(R.id.linear_klient_content);
                    TextView textView1 = new TextView(Klient.this);
                    textView1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                            LayoutParams.WRAP_CONTENT));
                    listWiadomoscID.add(childSnapshot.getKey());
                    textView1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            linearLayoutContent.removeAllViews();
                            boolean flag=true;
                            for(int i=0; i<=listWiadomoscID.size(); i++){
                                if(v.getId()==i){


                                    database.child("Klient").child(mAuth.getUid()).child("Wiadomosc").child(listWiadomoscID.get(i-1)).addListenerForSingleValueEvent(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            BazaWiadomosc bazaWiadomosc1 = dataSnapshot.getValue(BazaWiadomosc.class);

                                            TextView textView1 = new TextView(Klient.this);
                                            textView1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                                                    LayoutParams.WRAP_CONTENT));
                                            textView1.setText(bazaWiadomosc1.getHeader());
                                            textView1.setTextSize(20);
                                            textView1.setBackgroundColor(0xff66ff66);
                                            textView1.setPadding(20,10,20,10);
                                            LinearLayout.LayoutParams layoutParams = new
                                                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                                            layoutParams.setMargins(20, 20, 30, 10);
                                            linearLayoutContent.addView(textView1, layoutParams);
                                            textView1 = new TextView(Klient.this);
                                            textView1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                                                    LayoutParams.WRAP_CONTENT));
                                            textView1.setText(bazaWiadomosc1.getBody());
                                            textView1.setTextSize(15);
                                            textView1.setBackgroundColor(0xffffffff);
                                            textView1.setPadding(20,10,20,10);
                                            linearLayoutContent.addView(textView1, layoutParams);

                                            if(bazaWiadomosc1.getStatus().equals("1")){
                                                HashMap<String, Object> result = new HashMap<>();
                                                result.put("status", "0");
                                                database.child("Klient").child(mAuth.getUid()).child("Wiadomosc").child(dataSnapshot.getKey()).updateChildren(result);
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
                                Toast.makeText(Klient.this, "Błąd podczas pobierania wiadomości spróbuj ponownie", Toast.LENGTH_LONG).show();
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
                Toast.makeText(Klient.this, "Błąd podczas pobierania wiadomości, spróbuj ponownie", Toast.LENGTH_LONG).show();
            }
        });
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
            }
        }
        else
        {
            Toast.makeText(this, "Wprowadź treść wiadomości", Toast.LENGTH_LONG).show();
        }

    }

    public void profilEdycja(View view){
        editTextEdycjaImie = (EditText) findViewById(R.id.input_imie);
        editTextEdycjaNazwisko = (EditText) findViewById(R.id.input_nazwisko);
        editTextEdycjaTelefon = (EditText) findViewById(R.id.input_telefon);
        String telefon = editTextEdycjaTelefon.getText().toString().trim();
        Boolean isNumber= Patterns.PHONE.matcher(editTextEdycjaTelefon.getText().toString()).matches();
        if(!isNumber && !TextUtils.isEmpty(telefon) && editTextEdycjaTelefon.getText().toString().length()!=9){
            Toast.makeText(Klient.this, "Błędny numer telefonu", Toast.LENGTH_LONG).show();
        }
        else{
            database.child("Klient").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String imie = editTextEdycjaImie.getText().toString().trim();
                    HashMap<String, Object> result = new HashMap<>();
                    if(!TextUtils.isEmpty(imie)){
                        result.put("imie", imie);
                    }
                    String nazwisko = editTextEdycjaNazwisko.getText().toString().trim();
                    if(!TextUtils.isEmpty(nazwisko)){
                        result.put("nazwisko", nazwisko);
                    }
                    String telefon = editTextEdycjaTelefon.getText().toString().trim();
                    if(!TextUtils.isEmpty(telefon)){
                        result.put("telefon", telefon);
                    }
                    if(result.isEmpty()){
                        Toast.makeText(Klient.this, "Wprowadź dane", Toast.LENGTH_LONG).show();
                    }
                    else {
                        database.child("Klient").child(mAuth.getUid()).updateChildren(result);
                        Toast.makeText(Klient.this, "Dane zostały zmienione", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(Klient.this,  "błąd podczas edycji danych, spróbuj ponownie", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void edditHeader(){

        database.child("Klient").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bazaKlient = dataSnapshot.getValue(BazaKlient.class);
                header = (TextView) findViewById(R.id.Textheader);
                header.setText("Witaj, " + bazaKlient.getImie());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateUI(FirebaseUser user){
        if(user == null)
        {
            this.finish();
        }
    }
}
