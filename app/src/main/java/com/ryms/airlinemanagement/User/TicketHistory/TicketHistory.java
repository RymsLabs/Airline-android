package com.ryms.airlinemanagement.User.TicketHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

public class TicketHistory extends AppCompatActivity {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    final ArrayList<HistoryModel> modelArrayList = new ArrayList<>();
    HistoryAdapter historyAdapter;
    String id="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_history);


        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        historyAdapter = new HistoryAdapter(modelArrayList);
        recyclerView.setAdapter(historyAdapter);

        getAllTickets();
    }

    public void getAllTickets() {

        modelArrayList.clear();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(Config.TICKET_HISTORY + id)
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
                    TicketHistory.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Toast.makeText(TicketHistory.this, finalJsonObject.getString("message"), Toast.LENGTH_LONG).show();
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
                        HistoryModel model = new HistoryModel();
                        model.HisId = temp.getString("ticketId");
                        model.HisDep = temp.getString("departure");
                        model.HisArr = temp.getString("arrival");
                        model.HisDT = temp.getString("departure_time");
                        model.HisAT = temp.getString("arrival_time");
                        model.ticketId = temp.getString("ticketId");
                        model.setImage(R.drawable.shadowfight);
                        modelArrayList.add(model);
                    }
                    TicketHistory.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            historyAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}