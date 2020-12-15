package com.ryms.airlinemanagement.Admin.Fetch.FetchFlights;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryms.airlinemanagement.R;
import com.ryms.airlinemanagement.User.TicketDetails;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class FetchFlightsAdapter extends RecyclerView.Adapter<FetchFlightsAdapter.MyHolder> {

    public ArrayList<FetchFlightsModel> modelArrayList = new ArrayList<>();

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView flightIdFF, departureFF, arrivalFF, depTimeFF, arrTimeFF, seatsFF;
        public ImageView imageView;
        public MyHolder(View view) {
            super(view);
            flightIdFF = (TextView) view.findViewById(R.id.flightIdFF);
            departureFF = (TextView) view.findViewById(R.id.departureFF);
            arrivalFF = (TextView) view.findViewById(R.id.arrivalFF);
            depTimeFF = (TextView) view.findViewById(R.id.depTimeFF);
            arrTimeFF = (TextView) view.findViewById(R.id.arrTimeFF);
            seatsFF = (TextView) view.findViewById(R.id.seatsFF);
            this.imageView = view.findViewById(R.id.backTile);
        }
    }

    public FetchFlightsAdapter(ArrayList<FetchFlightsModel> modelArrayList) {
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public FetchFlightsAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fetch_flights_row, null);
        return new FetchFlightsAdapter.MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.imageView.setImageResource(modelArrayList.get(position).getImage());
        holder.flightIdFF.setText(modelArrayList.get(position).flightIdFF);
        holder.departureFF.setText(modelArrayList.get(position).departureFF);
        holder.arrivalFF.setText(modelArrayList.get(position).arrivalFF);
        holder.depTimeFF.setText(modelArrayList.get(position).depTimeFF);
        holder.arrTimeFF.setText(modelArrayList.get(position).arrTimeFF);
        holder.seatsFF.setText(modelArrayList.get(position).seatsFF);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(holder.imageView.getContext(), Reschedule.class);
                intent.putExtra("flightId", modelArrayList.get(position).flightIdFF);
                holder.imageView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }
}
