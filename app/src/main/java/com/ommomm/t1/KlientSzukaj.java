package com.ommomm.t1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class KlientSzukaj extends Fragment {

    private TextView textView;
    private ImageView imageView;
    private StorageReference storageReference;
    private File fileLocal;

    public KlientSzukaj() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_klient_szukaj, null);
        return setContentFragment(view);
    }

    public View setContentFragment(View view){
        storageReference = FirebaseStorage.getInstance().getReference();
        String zdjecieID = getArguments().getString("zdjecie");
        storageReference = storageReference.child(zdjecieID+".jpg");
        imageView = view.findViewById(R.id.contentImageView);

        try {
            fileLocal = File.createTempFile("image", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        storageReference.getFile(fileLocal)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        Bitmap bitmap = BitmapFactory.decodeFile(fileLocal.getAbsolutePath());
                        imageView.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                imageView.setImageResource(R.drawable.brakzdjecia);
            }
        });


        textView = view.findViewById(R.id.contentTextView);
        String nazwa = getArguments().getString("nazwa");
        textView.setText(nazwa);
        textView.setTextSize(36);
        return view;
    }

}
