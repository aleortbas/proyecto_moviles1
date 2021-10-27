package com.example.footboocking;

/*
* RecyclerView.Adapter
* RecyclerView.ViewHolder
* */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.adpaterViewHolder>{

    private Context mCtx;
    private List<Product> adapterList;

    public adapter(Context mCtx, List<Product> adapterList) {
        this.mCtx = mCtx;
        this.adapterList = adapterList;
    }

    @NonNull
    @Override
    public adpaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null);
        return new adpaterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adpaterViewHolder holder, int position) {
        Product product = adapterList.get(position);

        holder.textViewTitle.setText(product.getNombre());
        holder.textViewDesc.setText(product.getDisponible());
        holder.textViewRating.setText(String.valueOf(product.getIdLocal()));
        holder.textViewPrice.setText(String.valueOf(product.getDisponible()));

    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    class adpaterViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textViewTitle, textViewDesc, textViewRating, textViewPrice;

        public adpaterViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewRating = itemView.findViewById(R.id.textViewRating);
        }
    }
}
