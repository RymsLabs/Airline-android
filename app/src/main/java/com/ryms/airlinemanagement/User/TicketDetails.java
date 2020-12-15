package com.ryms.airlinemanagement.User;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.ryms.airlinemanagement.Config;
import com.ryms.airlinemanagement.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TicketDetails extends AppCompatActivity {
    String TAG = "GenerateQRCode";
    ImageView qrCode;
    EditText ticket_id;
    Button generate;
    String inputValue;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    int ticketId = 1;
    TextView isCheckedIn, departure, arrival, arrival_time, departure_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details);

        qrCode = (ImageView) findViewById(R.id.qrCode);
        generate = (Button) findViewById(R.id.generate);
        isCheckedIn = (TextView) findViewById(R.id.isCheckedIn);
        departure = (TextView) findViewById(R.id.departure);
        arrival = (TextView) findViewById(R.id.arrival);
        arrival_time = (TextView) findViewById(R.id.departureTime);
        departure_time = (TextView) findViewById(R.id.arrivalTime);

        getTicketDetails();

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputValue = String.valueOf(ticketId).trim();
                if (inputValue.length() > 0) {
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3 / 4;

                    qrgEncoder = new QRGEncoder(
                            inputValue, null,
                            QRGContents.Type.TEXT,
                            smallerDimension);
                    try {
                        bitmap = qrgEncoder.encodeAsBitmap();
                        qrCode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        Log.v(TAG, e.toString());
                    }
                } else {
                    ticket_id.setError("Required");
                }
            }
        });
    }

    public void getTicketDetails() {

        OkHttpClient client = new OkHttpClient();

        /** Creating a request obj to request to a url */
        Request request = new Request.Builder()
                .url(Config.TICKET_DETAILS + ticketId)
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
                    TicketDetails.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Toast.makeText(TicketDetails.this, finalJsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                JSONObject message;
                String ic, dep, a, at, dt;
                try {
                    message = jsonObject.getJSONObject("message");
                    ic = message.getString("isCheckedIn");
                    dep = message.getString("departure");
                    a = message.getString("arrival");
                    at = message.getString("arrival_time");
                    dt = message.getString("departure_time");
                    if(ic=="1"){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                isCheckedIn.setText("True");
                                departure.setText(dep);
                                arrival.setText(a);
                                arrival_time.setText(at);
                                departure_time.setText(dt);
                            }
                        });
                    }
                    else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                isCheckedIn.setText("False");
                                departure.setText(dep);
                                arrival.setText(a);
                                arrival_time.setText(at);
                                departure_time.setText(dt);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}