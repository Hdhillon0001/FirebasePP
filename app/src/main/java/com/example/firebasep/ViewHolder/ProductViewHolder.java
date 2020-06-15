package com.example.firebasep.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasep.Interfaces.itemClickListner;
import com.example.firebasep.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductDescription;
    public ImageView imageView;
    public itemClickListner listner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.productImage);
        txtProductDescription = (TextView) itemView.findViewById(R.id.productDescription);

    }

    public  void setItemClickListner(itemClickListner listner){

        this.listner = listner;
    }
    @Override
    public void onClick(View view) {
listner.onClick(view, getAdapterPosition(),false);
    }
}
