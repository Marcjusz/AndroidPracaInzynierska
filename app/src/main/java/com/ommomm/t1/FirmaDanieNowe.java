package com.ommomm.t1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirmaDanieNowe extends Fragment {

    private TextView textView;

    public FirmaDanieNowe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_firma_danie_nowe, null);
        textView = view.findViewById(R.id.inputDanieLokal);
        textView.setText(getArguments().getString("lokalID"));
        return view;
    }

}
