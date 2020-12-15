package com.ryms.airlinemanagement.Admin.Fetch.FetchPilot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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
import okhttp3.Response;

public class FetchPilots extends AppCompatActivity {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    final ArrayList<FetchPilotsModel> modelArrayList = new ArrayList<>();
    FetchPilotsAdapter fetchPilotsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_pilot);

        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.recyclerViewFP);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        fetchPilotsAdapter = new FetchPilotsAdapter(modelArrayList);
        recyclerView.setAdapter(fetchPilotsAdapter);

        fetchAllPilots();
    }

    public void fetchAllPilots() {

        modelArrayList.clear();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(Config.FETCH_ALL_PILOTS)
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
                    FetchPilots.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Toast.makeText(FetchPilots.this, finalJsonObject.getString("message"), Toast.LENGTH_LONG).show();
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
                        FetchPilotsModel model = new FetchPilotsModel();
                        model.firstName = temp.getString("firstName");
                        model.lastName = temp.getString("lastName");
                        model.flyingSince = "2008";
                        model.flightId = temp.getString("flightId");
                        model.departureFP = temp.getString("departure");
                        model.arrivalFP = temp.getString("arrival");
                        model.departureTime = temp.getString("departure_time");
                        model.arrivalTime = temp.getString("arrival_time");
                        modelArrayList.add(model);
                    }
                    FetchPilots.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fetchPilotsAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}