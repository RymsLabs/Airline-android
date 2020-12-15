package com.ryms.airlinemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ryms.airlinemanagement.User.UserMenu;

import org.jetbrains.annotations.NotNull;
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

public class Signup extends AppCompatActivity {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    EditText fname, lname, sEmail, sAddress, birthdate, sPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        sEmail = (EditText) findViewById(R.id.sEmail);
        sAddress = (EditText) findViewById(R.id.sAddress);
        birthdate = (EditText) findViewById(R.id.birthdate);
        sPassword = (EditText) findViewById(R.id.sPassword);
    }

    public void signUp(View view) {

        final String fNameTxt = fname.getText().toString();
        final String lNameTxt = lname.getText().toString();
        final String emailTxt = sEmail.getText().toString();
        final String addressTxt = sAddress.getText().toString();
        final String birthDateTxt = birthdate.getText().toString();
        final String passwordTxt = sPassword.getText().toString();

        OkHttpClient client = new OkHttpClient();

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("fname", fNameTxt);
            jsonBody.put("lname", lNameTxt);
            jsonBody.put("email", emailTxt);
            jsonBody.put("addr", addressTxt);
            jsonBody.put("password", passwordTxt);
            jsonBody.put("dob", birthDateTxt);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(String.valueOf(jsonBody), JSON);
        String requestUrl = Config.SIGN_UP;
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
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                JSONObject jsonObject = null;
                if (!response.isSuccessful()) {
                    Signup.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Incorrect username or password!", Toast.LENGTH_LONG).show();
                        }
                    });
                    throw new IOException("Unexpected code " + response);
                }else {
                    String res = response.body().string();
                    Log.d("JSON RES", res);
                    String result;
                    try {
                        result = jsonObject.getString("status");
                        if(result.equals("success")){
                            goToNext();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void goToNext() {
        Intent intent = new Intent(Signup.this, UserMenu.class);
        startActivity(intent);
    }
}