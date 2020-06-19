package com.example.firebasep.ViewHolder;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasep.Interfaces.itemClickListner;
import com.example.firebasep.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public EditText txtProductDescription;
    public ImageView imageView;
    private itemClickListner listner;

    public ProductViewHolder( @NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.productImage);
        txtProductDescription =  itemView.findViewById(R.id.productDescription);

    }

    private  void setClickLinster (itemClickListner listner){

        this.listner = listner;
    }
    @Override
    public void onClick(View view) {

        listner.onClick(view, getAdapterPosition(),false);

    }
}
