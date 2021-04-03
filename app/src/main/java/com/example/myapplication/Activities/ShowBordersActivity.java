package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.Adapter.CountryAdapter;
import com.example.myapplication.Objects.Country;
import com.example.myapplication.Objects.MySingleton;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowBordersActivity extends AppCompatActivity {

    String countryName, countryNameShort, countryNativeName;
    ArrayList<String> borders;
    final String COUNTRY_INFO = "https://restcountries.eu/rest/v2/alpha/";
    ArrayList<Country> countryBorders;
    CountryAdapter countryAdapter;
    RecyclerView recyclerViewBorders;
    TextView textViewNoBorders, textViewNativeName, textViewCountryName, textViewCountryShort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_borders);

        textViewCountryName = findViewById(R.id.textViewCountryName);
        textViewCountryShort = findViewById(R.id.textViewCountryShort);
        borders = new ArrayList<>();
        countryBorders = new ArrayList<>();
        recyclerViewBorders = findViewById(R.id.recyclerViewBorders);
        textViewNoBorders = findViewById(R.id.textViewNoBorders);
        textViewNativeName = findViewById(R.id.textViewNativeName);

        getData();
        setData();
    }

    private void getData() {
        if(getIntent().hasExtra("country"))
        {
            countryName = getIntent().getStringExtra("country");
            countryNameShort = getIntent().getStringExtra("country_short");
            countryNativeName = getIntent().getStringExtra("country_native_name");
            borders = getIntent().getStringArrayListExtra("borders");
            if(borders.size()==0)
            {
                textViewNoBorders.setVisibility(View.VISIBLE);
            }
            else
            {
                textViewNoBorders.setVisibility(View.GONE);
                getBordersInfo(borders);
            }
        }
    }

    private void getBordersInfo(ArrayList<String> borders) {

        for(int index = 0; index<borders.size(); index++)
        {
            String url = COUNTRY_INFO + borders.get(index);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        ArrayList<String> listBordersJsonToArray = new ArrayList<>();
                        JSONArray jsonCountry = response.getJSONArray("borders");

                        for (int j = 0; j < jsonCountry.length(); j++)
                        {
                            listBordersJsonToArray.add(jsonCountry.get(j).toString());
                        }

                        Country country = new Country(
                                response.getString("name"),
                                response.getString("alpha2Code"),
                                response.getString("nativeName"),
                                listBordersJsonToArray);
                        countryBorders.add(country);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ShowBordersActivity.this, "That didn't work! WINDOW 2", Toast.LENGTH_SHORT).show();
                }
            });
            MySingleton.getInstance(ShowBordersActivity.this).addToRequestQueue(request);
        }
    }

    private void setData() {
        textViewCountryName.setText(countryName);
        textViewCountryShort.setText(countryNameShort);
        textViewNativeName.setText(countryNativeName);

        try {
            Thread.sleep(500);
            {
                countryAdapter = new CountryAdapter(countryBorders, ShowBordersActivity.this);
                recyclerViewBorders.setAdapter(countryAdapter);
                recyclerViewBorders.setLayoutManager(new LinearLayoutManager(ShowBordersActivity.this));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}