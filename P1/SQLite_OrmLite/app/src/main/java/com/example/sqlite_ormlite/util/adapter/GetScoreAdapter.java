package com.example.sqlite_ormlite.util.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sqlite_ormlite.R;
import com.example.sqlite_ormlite.model.Score;

import java.text.SimpleDateFormat;
import java.util.List;

/***
 * Classe Adapter de listView de l'activité score
 */
public class GetScoreAdapter extends BaseAdapter {

    Context context;
    List<Score> listScore;
    List<String> listNomPays;
    List<Drawable> listDrawPays;
    LayoutInflater inflate;

    public GetScoreAdapter(Context context, List<Score> listScore, List<Drawable> listDrawPays, List<String> listNomPays) {
        this.context = context;
        this.listScore = listScore;
        this.listDrawPays = listDrawPays;
        this.listNomPays = listNomPays;
        this.inflate = LayoutInflater.from(context);

        // Add log statement to check if listScore has data
        Log.i("Adapter", "Number of scores in adapter: " + listScore.size());
    }


    @Override
    public int getCount() {
        return listScore.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        if (inflate == null)
//            inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        //Convertir la vue de l'adapter
//        if(view == null){
//            view = inflate.inflate(R.layout.activity_get_score_adapter, viewGroup,false);
//        }
//
//        // Affection des valeurs
//        ImageView img = view.findViewById(R.id.imageViewPays);
//        img.setImageDrawable(getDrawDepuisNomPays(listScore.get(i).getUser().getPays()));
//
//        TextView prenom = view.findViewById(R.id.textViewPrenom);
//        prenom.setText(listScore.get(i).getUser().getFirstName());
//
//        TextView nom = view.findViewById(R.id.textViewNom);
//        nom.setText(listScore.get(i).getUser().getLastName());
//
//        TextView score = view.findViewById(R.id.textViewScore);
//        score.setText(listScore.get(i).getScore()+"");
//
//        //Afficher la date format dd-MM-yyyy
//        TextView when = view.findViewById(R.id.textViewWhen);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        String dateTime = simpleDateFormat.format(listScore.get(i).getWhen().getTime());
//        when.setText(dateTime+"");
//
//        return view;
//    }
@Override
public View getView(int i, View view, ViewGroup viewGroup) {
    if (inflate == null)
        inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    // Convertir la vue de l'adapter
    if (view == null) {
        view = inflate.inflate(R.layout.activity_get_score_adapter, viewGroup, false);
    }

    // Affection des valeurs
    ImageView img = view.findViewById(R.id.imageViewPays);
    img.setImageDrawable(getDrawDepuisNomPays(listScore.get(i).getUser().getPays()));

    TextView prenom = view.findViewById(R.id.textViewPrenom);
    prenom.setText(listScore.get(i).getUser().getFirstName());

    TextView nom = view.findViewById(R.id.textViewNom);
    nom.setText(listScore.get(i).getUser().getLastName());

    TextView score = view.findViewById(R.id.textViewScore);

    // Use SpannableString for formatting the score
    SpannableString spannableScore = new SpannableString(String.valueOf(listScore.get(i).getScore()));
    spannableScore.setSpan(new RelativeSizeSpan(1.5f), 0, spannableScore.length(), 0);
    spannableScore.setSpan(new UnderlineSpan(), 0, spannableScore.length(), 0);
    score.setText(spannableScore);

    // Afficher la date format dd-MM-yyyy
    TextView when = view.findViewById(R.id.textViewWhen);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String dateTime = simpleDateFormat.format(listScore.get(i).getWhen().getTime());
    when.setText(dateTime);

    return view;
}


    public Drawable getDrawDepuisNomPays(String nom){
        int i = listNomPays.indexOf(nom);
        Drawable draw = listDrawPays.get(i);

        return draw;
    }

}
