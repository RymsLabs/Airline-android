package com.ryms.airlinemanagement.User.FlightSearch;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryms.airlinemanagement.Admin.Fetch.FetchUsers.FetchUsersAdapter;
import com.ryms.airlinemanagement.Admin.Fetch.FetchUsers.FetchUsersModel;
import com.ryms.airlinemanagement.R;
import com.ryms.airlinemanagement.User.Booking;
import com.ryms.airlinemanagement.User.TicketDetails;

import java.util.ArrayList;

public class FlightSearchAdapter extends RecyclerView.Adapter<FlightSearchAdapter.MyHolder> {

    public ArrayList<FlightSearchModel> modelArrayList = new ArrayList<>();

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView FlightId, Departure, Arrival, DepartmentTime, ArrivalTime, Seats;
        public ImageView imageView;


        public MyHolder(View view) {
            super(view);
            FlightId = (TextView) view.findViewById(R.id.FlightId);
            Departure = (TextView) view.findViewById(R.id.Departure);
            Arrival = (TextView) view.findViewById(R.id.Arrival);
            DepartmentTime = (TextView) view.findViewById(R.id.DepartmentTime);
            ArrivalTime = (TextView) view.findViewById(R.id.ArrivalTime);
            Seats = (TextView) view.findViewById(R.id.Seats);
            this.imageView = view.findViewById(R.id.backTile);
        }
    }

    public FlightSearchAdapter(ArrayList<FlightSearchModel> modelArrayList) {
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public FlightSearchAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_search_row, null);
        return new FlightSearchAdapter.MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.imageView.setImageResource(modelArrayList.get(position).getImage());
        holder.FlightId.setText(modelArrayList.get(position).FlightId);
        holder.Departure.setText(modelArrayList.get(position).Departure);
        holder.Arrival.setText(modelArrayList.get(position).Arrival);
        holder.DepartmentTime.setText(modelArrayList.get(position).DepartmentTime);
        holder.ArrivalTime.setText(modelArrayList.get(position).ArrivalTime);
        holder.Seats.setText(modelArrayList.get(position).Seats);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.imageView.getContext(), Booking.class);
                intent.putExtra("flightId", modelArrayList.get(position).FlightId);
                intent.putExtra("uid",modelArrayList.get(position).uid);
                holder.imageView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }
}
