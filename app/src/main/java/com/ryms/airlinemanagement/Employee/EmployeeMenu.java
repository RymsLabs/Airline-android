package com.ryms.airlinemanagement.Employee;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.ryms.airlinemanagement.Config;
import com.ryms.airlinemanagement.Employee.QrCodeScanner;
import com.ryms.airlinemanagement.Login;
import com.ryms.airlinemanagement.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EmployeeMenu extends AppCompatActivity {

    EditText tid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_menu);

        tid = (EditText) findViewById(R.id.tid);
    }

    public void goQr(View view) {
        Intent intent = new Intent(this, QrCodeScanner.class);
        startActivity(intent);
        Dexter.withContext(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();
    }

    public void check(View view) {

        OkHttpClient client = new OkHttpClient();

        String requestUrl = Config.CHECK_IN_USER + tid.getText();

        Request request = new Request.Builder()
                .url(requestUrl)
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
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                JSONObject jsonObject = null;
                if (!response.isSuccessful()) {
                    EmployeeMenu.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Incorrect Id", Toast.LENGTH_LONG).show();
                        }
                    });
                    throw new IOException("Unexpected code " + response);
                } else {
                    String res = response.body().string();
                    Log.d("JSON RES", res);
                    try {
                        jsonObject = new JSONObject(res);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JSONObject finalJsonObject = jsonObject;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String type;
                            try {
                                type = finalJsonObject.getString("type");
                                if (type.equals("success")) {
                                    JSONObject message = finalJsonObject.getJSONObject("message");
                                    String result = message.getString("@result");
                                    Toast.makeText(EmployeeMenu.this, result, Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(EmployeeMenu.this, "Checked in : Failure Get Out", Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }
}