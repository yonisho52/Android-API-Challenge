package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activities.ShowBordersActivity;
import com.example.myapplication.Objects.Country;
import com.example.myapplication.R;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder> {

    private ArrayList<Country> countries;
    private Context mContext;

    public CountryAdapter(ArrayList<Country> countries, Context context) {
        this.countries = countries;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.one_line_country, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewCountryName.setText(countries.get(position).getName());
        holder.textViewCountryShort.setText(countries.get(position).getShortName());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowBordersActivity.class);
                intent.putExtra("country", countries.get(position).getName());
                intent.putExtra("country_short", countries.get(position).getShortName());
                intent.putExtra("borders", countries.get(position).getBorders());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCountryName, textViewCountryShort;
        ConstraintLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCountryName = itemView.findViewById(R.id.textViewLineCountry);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            textViewCountryShort = itemView.findViewById(R.id.textViewLineCountryShort);
        }
    }

    public void filterList(ArrayList<Country> filteredList) {
        countries = filteredList;
        notifyDataSetChanged();
    }
}
