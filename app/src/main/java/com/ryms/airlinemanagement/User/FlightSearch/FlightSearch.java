package com.ryms.airlinemanagement.User.FlightSearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ryms.airlinemanagement.Admin.Fetch.FetchUsers.FetchUsers;
import com.ryms.airlinemanagement.Admin.Fetch.FetchUsers.FetchUsersAdapter;
import com.ryms.airlinemanagement.Admin.Fetch.FetchUsers.FetchUsersModel;
import com.ryms.airlinemanagement.Config;
import com.ryms.airlinemanagement.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FlightSearch extends AppCompatActivity {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    final ArrayList<FlightSearchModel> modelArrayList = new ArrayList<>();
    FlightSearchAdapter flightSearchAdapter;
    EditText getDeparture, getArrival, getDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights_search);

        getDeparture = (EditText) findViewById(R.id.getDeparture);
        getArrival = (EditText) findViewById(R.id.getArrival);
        getDate = (EditText) findViewById(R.id.getDate);

        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.recyclerViewFS);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        flightSearchAdapter = new FlightSearchAdapter(modelArrayList);
        recyclerView.setAdapter(flightSearchAdapter);
    }

    public void search(View view){
        getCorrespondingFlights();
    }

    public void getCorrespondingFlights() {

        modelArrayList.clear();

        final String departureTxt = getDeparture.getText().toString();
        final String arrivalTxt = getArrival.getText().toString();
        final String dateTxt = getDate.getText().toString();

        OkHttpClient client = new OkHttpClient();

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("departure", departureTxt);
            jsonBody.put("arrival", arrivalTxt);
            jsonBody.put("dTime", dateTxt);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(String.valueOf(jsonBody), JSON);
        String requestUrl = Config.FLIGHT_FILTER;

        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if (e instanceof UnknownHostException) {
                    System.out.println("Please check your Internet connection.");
                } else {
                    System.out.println("Error executing login HTTP req.: ");
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (response.code() != 200) {
                    final JSONObject finalJsonObject = jsonObject;
                    FlightSearch.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Toast.makeText(FlightSearch.this, finalJsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                JSONArray message;
                try {
                    Log.d("JSON", jsonObject.toString());
                    message = jsonObject.getJSONArray("message");
                    JSONObject temp;
                    for (int i = 0; i < message.length(); i++) {
                        temp = message.getJSONObject(i);
                        FlightSearchModel model = new FlightSearchModel();
                        model.FlightId = temp.getString("flightId");
                        model.Departure= temp.getString("departure");
                        model.Arrival = temp.getString("arrival");
                        model.DepartmentTime = temp.getString("departure_time");
                        model.ArrivalTime = temp.getString("arrival_time");
                        model.Seats = temp.getString("seats");
                        modelArrayList.add(model);
                    }
                    FlightSearch.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            flightSearchAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}