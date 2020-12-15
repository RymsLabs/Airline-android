package com.ryms.airlinemanagement.User.TicketHistory;

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

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyHolder> {

    public ArrayList<HistoryModel> modelArrayList = new ArrayList<>();

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView HisId, HisDep, HisArr, HisDT, HisAT;
        public ImageView imageView;

        public MyHolder(View view) {
            super(view);
            HisId = (TextView) view.findViewById(R.id.HisId);
            HisDep = (TextView) view.findViewById(R.id.HisDep);
            HisArr = (TextView) view.findViewById(R.id.HisArr);
            HisDT = (TextView) view.findViewById(R.id.HisDT);
            HisAT = (TextView) view.findViewById(R.id.HisAT);
            this.imageView = view.findViewById(R.id.backTile);
        }
    }

    public HistoryAdapter(ArrayList<HistoryModel> modelArrayList) {
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public HistoryAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_history_row, null);
        return new HistoryAdapter.MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        holder.imageView.setImageResource(modelArrayList.get(position).getImage());
        holder.HisId.setText(modelArrayList.get(position).HisId);
        holder.HisDep.setText(modelArrayList.get(position).HisDep);
        holder.HisArr.setText(modelArrayList.get(position).HisArr);
        holder.HisDT.setText(modelArrayList.get(position).HisDT);
        holder.HisAT.setText(modelArrayList.get(position).HisAT);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.imageView.getContext(), TicketDetails.class);
                intent.putExtra("ticketId", modelArrayList.get(position).ticketId);
                holder.imageView.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }
}

