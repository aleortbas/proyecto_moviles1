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

public class adapterRequest extends RecyclerView.Adapter<adapterRequest.adpaterViewHolder>{

    private Context mCtx;
    private List<solicitud> adapterList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public adapterRequest(Context mCtx, List<solicitud> adapterList) {
        this.mCtx = mCtx;
        this.adapterList = adapterList;
    }


    @NonNull
    @Override
    public adpaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout_request, null);
        return new adpaterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adpaterViewHolder holder, int position) {
        solicitud product = adapterList.get(position);


        holder.imageView.setImageBitmap(convert(product.getFoto()));
        holder.textViewTitle.setText(String.valueOf(product.getNombre()));
        holder.textViewDesc.setText(String.valueOf(product.getIdentificacion()));
        holder.textViewRating.setText(String.valueOf(product.getId_usuario()));
        holder.textViewPrice.setText(String.valueOf(product.getCamara_comercio()));
        if(product.getEstado() == 1){
            holder.textViewEstado.setText("Estado en espera" + String.valueOf(product.getEstado()));
        }else{
            holder.textViewEstado.setText("Estado revisado y nuevo rol asignado" + String.valueOf(product.getEstado()));
        }

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
        TextView textViewTitle, textViewDesc, textViewRating, textViewPrice, textViewEstado;

        public adpaterViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView2);
            textViewTitle = itemView.findViewById(R.id.textViewTitle2);
            textViewDesc = itemView.findViewById(R.id.textViewShortDesc2);
            textViewPrice = itemView.findViewById(R.id.textViewPrice2);
            textViewRating = itemView.findViewById(R.id.textViewRating2);
            textViewEstado = itemView.findViewById(R.id.text);

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
