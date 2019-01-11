package com.ommomm.t1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    private ArrayList<String> listaOfertID;
    private ArrayList<BazaOferta> bazaOfertas;

    public ViewPagerAdapter(FragmentManager fm, ArrayList<String> listaOfertID, ArrayList<BazaOferta> bazaOfertas) {
        super(fm);
        this.listaOfertID = listaOfertID;
        this.bazaOfertas = bazaOfertas;
    }

    @Override
    public Fragment getItem(int position) {
        KlientSzukaj klientSzukaj = new KlientSzukaj();
        Bundle bundle = new Bundle();
        bundle.putString("nazwa", bazaOfertas.get(position).getNazwa());
        bundle.putString("cena", bazaOfertas.get(position).getCena_regularna());
        bundle.putString("rebat", bazaOfertas.get(position).getRabat());
        bundle.putString("zdjecie", bazaOfertas.get(position).getZdjecie());
        klientSzukaj.setArguments(bundle);
        position++;
        return klientSzukaj;
    }

    @Override
    public int getCount() {
        return listaOfertID.size();
    }
}
