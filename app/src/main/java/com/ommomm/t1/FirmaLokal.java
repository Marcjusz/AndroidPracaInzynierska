package com.ommomm.t1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class FirmaLokal extends AppCompatActivity {

    BazaLokal bazaLokal;
    StorageReference storageReference;
    StorageReference storageReference1;
    StorageReference storageReference2;
    StorageReference storageReference3;

    ImageView imageViewZdjecie1;
    TextView textViewOpis;
    EditText editTextTel;
    File fileLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        bazaLokal = (BazaLokal)intent.getSerializableExtra("Lokal");
        storageReference = FirebaseStorage.getInstance().getReference();

        setContentView(R.layout.activity_firma_lokal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(bazaLokal.getNazwa());
        setSupportActionBar(toolbar);


        loadPhoto();
        loadOpis();

        super.onCreate(savedInstanceState);
    }

    private void loadPhoto() {
        if(bazaLokal.getPhoto1().equals("null"))
            storageReference1 = storageReference.child("null.png");
        else
            storageReference1 = storageReference.child(bazaLokal.getPhoto1());

        if(bazaLokal.getPhoto2().equals("null"))
            storageReference2 = storageReference.child("null.png");
        else
            storageReference2 = storageReference.child(bazaLokal.getPhoto2());

        if(bazaLokal.getPhoto3().equals("null"))
            storageReference3 = storageReference.child("null.png");
        else
            storageReference3 = storageReference.child(bazaLokal.getPhoto3());

        try {
            fileLocal = File.createTempFile("image", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        storageReference1.getFile(fileLocal)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        Bitmap bitmap = BitmapFactory.decodeFile(fileLocal.getAbsolutePath());
                        imageViewZdjecie1 = (ImageView) findViewById(R.id.image_Lokal1);
                        imageViewZdjecie1.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(FirmaLokal.this, "Błąd ", Toast.LENGTH_LONG).show();

            }
        });
        storageReference2.getFile(fileLocal)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        Bitmap bitmap = BitmapFactory.decodeFile(fileLocal.getAbsolutePath());
                        imageViewZdjecie1 = (ImageView) findViewById(R.id.image_Lokal2);
                        imageViewZdjecie1.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(FirmaLokal.this, "Błąd ", Toast.LENGTH_LONG).show();

            }
        });
        storageReference3.getFile(fileLocal)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        Bitmap bitmap = BitmapFactory.decodeFile(fileLocal.getAbsolutePath());
                        imageViewZdjecie1 = (ImageView) findViewById(R.id.image_Lokal3);
                        imageViewZdjecie1.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(FirmaLokal.this, "Błąd ", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void loadOpis(){
        textViewOpis = (TextView) findViewById(R.id.text_LokalOpis);
        textViewOpis.setText(bazaLokal.getOpis());

        /*
        editTextTel = (EditText) findViewById(R.id.input_lokalTel1);
        editTextTel.setHint(bazaLokal.getTelefon1());
        editTextTel = (EditText) findViewById(R.id.input_lokalTel2);
        if(bazaLokal.getTelefon2().equals("null"))
            editTextTel.setHint("Opcjonalny");
        else
            editTextTel.setHint(bazaLokal.getTelefon2());
            */
    }

    public void editOpis(View view){

    }

}
