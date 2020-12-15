package com.ryms.airlinemanagement.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ryms.airlinemanagement.Config;
import com.ryms.airlinemanagement.R;
import com.ryms.airlinemanagement.User.FlightSearch.FlightSearch;
import com.ryms.airlinemanagement.User.FlightSearch.FlightSearchModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Booking extends AppCompatActivity {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    String flightId;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        Bundle bundle = getIntent().getExtras();
        flightId = bundle.getString("flightId");
        uid = bundle.getInt("uid");
    }

    public void book(View view) {
        bookTickets();
    }

    public void bookTickets() {

        OkHttpClient client = new OkHttpClient();

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("userId", uid);
            jsonBody.put("flightId", flightId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(String.valueOf(jsonBody), JSON);
        String requestUrl = Config.BOOK_FLIGHT;

        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .build();
        Log.d("Body", body.toString());
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
                    Booking.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Toast.makeText(Booking.this, finalJsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    JSONObject message;
                    String result;
                    try {
                        message = jsonObject.getJSONObject("message");
                        result = message.getString("@result");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast toast;
                                if(result.equals("Ticket Booked")){
                                    toast = Toast.makeText(Booking.this, "Booking successful", Toast.LENGTH_LONG);
                                }
                                else{
                                    toast = Toast.makeText(Booking.this, "You have already booked your ticket", Toast.LENGTH_LONG);
                                }
                                toast.show();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}