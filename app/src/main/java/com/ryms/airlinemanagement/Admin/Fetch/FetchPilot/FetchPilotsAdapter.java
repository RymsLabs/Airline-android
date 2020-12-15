package com.ryms.airlinemanagement.Admin.Fetch.FetchPilot;

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

import java.util.ArrayList;

public class FetchPilotsAdapter extends RecyclerView.Adapter<FetchPilotsAdapter.MyHolder> {

    public ArrayList<FetchPilotsModel> modelArrayList;

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView firstName, lastName, flyingSince, flightId, departureFP, arrivalFP, departureTime, arrivalTime;
        public ImageView imageView;

        public MyHolder(View view) {
            super(view);
            firstName = (TextView) view.findViewById(R.id.firstName);
            lastName = (TextView) view.findViewById(R.id.lastName);
            flyingSince = (TextView) view.findViewById(R.id.flyingSince);
            flightId = (TextView) view.findViewById(R.id.flightid);
            departureFP = (TextView) view.findViewById(R.id.departureFP);
            arrivalFP = (TextView) view.findViewById(R.id.ArrivalFP);
            departureTime = (TextView) view.findViewById(R.id.departureTime);
            arrivalTime = (TextView) view.findViewById(R.id.arrivalTime);
            this.imageView = view.findViewById(R.id.backTile);
        }
    }

    public FetchPilotsAdapter(ArrayList<FetchPilotsModel> modelArrayList) {
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public FetchPilotsAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fetch_pilots_row, null);
        return new FetchPilotsAdapter.MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.imageView.setImageResource(modelArrayList.get(position).getImage());
        holder.firstName.setText(modelArrayList.get(position).firstName);
        holder.lastName.setText(modelArrayList.get(position).lastName);
        holder.flyingSince.setText(modelArrayList.get(position).flyingSince);
        holder.flightId.setText(modelArrayList.get(position).flightId);
        holder.departureFP.setText(modelArrayList.get(position).departureFP);
        holder.arrivalFP.setText(modelArrayList.get(position).arrivalFP);
        holder.departureTime.setText(modelArrayList.get(position).departureTime);
        holder.arrivalTime.setText(modelArrayList.get(position).arrivalTime);
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }
}
