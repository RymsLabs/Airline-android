package com.ryms.airlinemanagement.Admin.Fetch.FetchUsers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ryms.airlinemanagement.Admin.Fetch.FetchFlights.FetchFlights;
import com.ryms.airlinemanagement.Admin.Fetch.FetchFlights.FetchFlightsAdapter;
import com.ryms.airlinemanagement.Admin.Fetch.FetchFlights.FetchFlightsModel;
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

public class FetchUsers extends AppCompatActivity {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    final ArrayList<FetchUsersModel> modelArrayList = new ArrayList<>();
    FetchUsersAdapter fetchUsersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_users);

        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.recyclerViewFU);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        fetchUsersAdapter = new FetchUsersAdapter(modelArrayList);
        recyclerView.setAdapter(fetchUsersAdapter);

        fetchAllUsers();
    }

    public void fetchAllUsers() {

        modelArrayList.clear();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(Config.FETCH_ALL_USERS)
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
                    FetchUsers.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Toast.makeText(FetchUsers.this, finalJsonObject.getString("message"), Toast.LENGTH_LONG).show();
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
                        FetchUsersModel model = new FetchUsersModel();
                        model.firstName = temp.getString("firstName");
                        model.lastName = temp.getString("lastName");
                        model.email = temp.getString("email");
                        model.address = temp.getString("address");
                        model.password = temp.getString("password");
                        model.uid = temp.getString("uid");
                        model.birthDate = temp.getString("birthDate");
                        modelArrayList.add(model);
                    }
                    FetchUsers.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fetchUsersAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}