package com.ryms.airlinemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ryms.airlinemanagement.Admin.Fetch.AdminMenu;
import com.ryms.airlinemanagement.Employee.EmployeeMenu;
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

public class Login extends AppCompatActivity {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    EditText email, password;
    RadioButton user, employee, admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        user = (RadioButton) findViewById(R.id.user);
        employee = (RadioButton) findViewById(R.id.employee);
        admin = (RadioButton) findViewById(R.id.admin);
    }

    public void goToUserMenu() {
        Intent intent = new Intent(this, UserMenu.class);
        startActivity(intent);
    }

    public void goToEmployeeMenu() {
        Intent intent = new Intent(this, EmployeeMenu.class);
        startActivity(intent);
    }

    public void goToAdminMenu() {
        Intent intent = new Intent(this, AdminMenu.class);
        startActivity(intent);
    }

    public void loginPressed(View view) {
        Log.d("LOGIN", ("Login Pressed"));
        final String emailTxt = email.getText().toString();
        final String passTxt = password.getText().toString();

        if (emailTxt == null || passTxt == null || emailTxt == "" || passTxt == "") {
            System.out.println("WRONG INPUTS");
            return;
        }

        OkHttpClient client = new OkHttpClient();

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email.getText().toString());
            jsonBody.put("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(String.valueOf(jsonBody), JSON);
        String requestUrl;
        if (user.isChecked()) {
            requestUrl = Config.USER_LOGIN;
            Log.d("LOGIN", ("Creating req with url: " + requestUrl));
        } else if (employee.isChecked()) {
            requestUrl = Config.EMPLOYEE_LOGIN;
            Log.d("LOGIN", ("Creating req with url: " + requestUrl));
        } else {
            requestUrl = Config.ADMIN_LOGIN;
            Log.d("LOGIN", ("Creating req with url: " + requestUrl));
        }

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
                    Login.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Incorrect username or password!", Toast.LENGTH_LONG).show();
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
                    JSONObject message;
                    int result;
                    try {
                        message = jsonObject.getJSONObject("message");
                        result = Integer.parseInt(message.getString("@result"));
                        if (result == 1) {
                            if (user.isChecked() == true) {
                                goToUserMenu();
                            } else if (employee.isChecked() == true) {
                                goToEmployeeMenu();
                            } else {
                                goToAdminMenu();
                            }
                        } else {
                            Log.d("Chalja", "kuch nahi hona");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void signUp(View view) {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }
}