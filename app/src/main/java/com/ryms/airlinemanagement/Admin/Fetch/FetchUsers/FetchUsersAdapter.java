package com.ryms.airlinemanagement.Admin.Fetch.FetchUsers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryms.airlinemanagement.Admin.Fetch.FetchFlights.FetchFlightsAdapter;
import com.ryms.airlinemanagement.Admin.Fetch.FetchFlights.FetchFlightsModel;
import com.ryms.airlinemanagement.R;

import java.util.ArrayList;

public class FetchUsersAdapter extends RecyclerView.Adapter<FetchUsersAdapter.MyHolder> {

    public ArrayList<FetchUsersModel> modelArrayList = new ArrayList<>();

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView firstName, lastName, email, address, password, uid, birthDate;
        public ImageView imageView;

        public MyHolder(View view) {
            super(view);
            firstName = (TextView) view.findViewById(R.id.firstName);
            lastName = (TextView) view.findViewById(R.id.lastName);
            email = (TextView) view.findViewById(R.id.email);
            address = (TextView) view.findViewById(R.id.address);
            password = (TextView) view.findViewById(R.id.password);
            uid = (TextView) view.findViewById(R.id.uid);
            birthDate = (TextView) view.findViewById(R.id.birthDate);
            this.imageView = view.findViewById(R.id.backTile);
        }
    }

    public FetchUsersAdapter(ArrayList<FetchUsersModel> modelArrayList) {
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public FetchUsersAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fetch_users_row, null);
        return new FetchUsersAdapter.MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.imageView.setImageResource(modelArrayList.get(position).getImage());
        holder.firstName.setText(modelArrayList.get(position).firstName);
        holder.lastName.setText(modelArrayList.get(position).lastName);
        holder.email.setText(modelArrayList.get(position).email);
        holder.address.setText(modelArrayList.get(position).address);
        holder.password.setText(modelArrayList.get(position).password);
        holder.uid.setText(modelArrayList.get(position).uid);
        holder.birthDate.setText(modelArrayList.get(position).birthDate);
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }
}
