package com.example.sqlite_ormlite.util.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sqlite_ormlite.R;

import java.util.ArrayList;
//https://stackoverflow.com/questions/6690326/android-getting-a-list-of-drawable-resource
public class SpinerPaysAdapter extends BaseAdapter {

    Context context;
    ArrayList<Drawable> drawsPays;
    ArrayList<String> charsPays;
    LayoutInflater inflate;

    public SpinerPaysAdapter(Context context, ArrayList<Drawable> listPays, ArrayList<String> charPays) {
        this.context = context;
        this.drawsPays = listPays;
        this.charsPays = charPays;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return drawsPays.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflate == null)
            inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Convertir la vue de l'adapter
        if(view == null){
            view = inflate.inflate(R.layout.activity_spiner_pays_adapter, viewGroup,false);
        }

        ImageView imgView = view.findViewById(R.id.imageViewPays);
        imgView.setImageDrawable(drawsPays.get(i));

        TextView textView = view.findViewById(R.id.textViewNomPays);
        String nom = charsPays.get(i);
        textView.setText(nom);

        return view;
    }
}
