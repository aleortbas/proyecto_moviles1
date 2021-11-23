package com.example.footboocking;

/*
* RecyclerView.Adapter
* RecyclerView.ViewHolder
* */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.adpaterViewHolder>{

    private Context mCtx;
    private List<Product> adapterList;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(InfolocalActivity infolocalActivity) {
    }

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

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

        holder.imageView.setImageBitmap(convert(product.getImage()));
        holder.textViewTitle.setText("Id base de datos:" + String.valueOf(product.getId()));
        holder.textViewDesc.setText("Estado de la cancha"+ product.getDisponible());
        holder.textViewRating.setText("Id del local" + String.valueOf(product.getIdLocal()));
        holder.textViewPrice.setText("Nombre" + String.valueOf(product.getNombre()));

    }
    public static Bitmap convert(String base64Str) throws IllegalArgumentException
    {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",")  + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
