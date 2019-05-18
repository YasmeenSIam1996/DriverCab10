package com.example.drivercab10.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drivercab10.R;
import com.example.drivercab10.SelectLangActivity;
import com.example.drivercab10.model.Language;

import java.util.List;

public class LanguageAdapter extends BaseAdapter {
    Context context;
    List<Language> list;
    LayoutInflater inflater;

    public LanguageAdapter(Context context, List<Language> list) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.item_lang, null);
        final ImageView imgLang = ((SelectLangActivity) context).findViewById(R.id.imgLang);

        TextView tvName = convertView.findViewById(R.id.tvLangName);
        ImageView imgLangFlag = convertView.findViewById(R.id.imgLangFlag);

        tvName.setText(list.get(position).getName());
        imgLangFlag.setImageResource(list.get(position).getImg());

        return convertView;
    }


    public void saveLocale(String lang) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("lang", lang);
        editor.apply();
    }
}
