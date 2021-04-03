package com.example.myapplication.Activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.myapplication.Adapter.CountryAdapter;
import com.example.myapplication.Objects.Country;
import com.example.myapplication.Objects.MySingleton;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllCountriesMainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Country> countryArrayList;
    CountryAdapter countryAdapter;
    final String URL_ALL_COUNTRIES = "https://restcountries.eu/rest/v2/all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_countries);

        countryArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewCountries);

        RequestDataFromApi();

        EditText editTextSearch = findViewById(R.id.EditTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String textForSearch) {
        ArrayList<Country> filteredList = new ArrayList<>();

        for (Country country : countryArrayList) {
            if (country.getName().toLowerCase().contains(textForSearch.toLowerCase()) ||
                    country.getShortName().toLowerCase().contains(textForSearch.toLowerCase())) {
                filteredList.add(country);
            }
        }
        countryAdapter.filterList(filteredList);
    }

    public void RequestDataFromApi() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_ALL_COUNTRIES, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    for (int i = 0; i < response.length(); i++) {
                        ArrayList<String> list = new ArrayList<>();
                        JSONObject countries = response.getJSONObject(i);
                        JSONArray jsonCountry = countries.getJSONArray("borders");

                        for (int j = 0; j < jsonCountry.length(); j++) {
                            list.add(jsonCountry.get(j).toString());
                        }

                        Country country = new Country(
                                countries.getString("name"),
                                countries.getString("alpha2Code"),
                                countries.getString("nativeName"),
                                list);

                        countryArrayList.add(country);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                countryAdapter = new CountryAdapter(countryArrayList, AllCountriesMainActivity.this);
                recyclerView.setAdapter(countryAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(AllCountriesMainActivity.this));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AllCountriesMainActivity.this, "Cant Load Data", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(AllCountriesMainActivity.this).addToRequestQueue(request);
    }
}