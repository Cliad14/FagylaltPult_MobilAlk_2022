package com.example.fagylaltpult;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


public class FagylaltAdapter extends RecyclerView.Adapter<FagylaltAdapter.ViewHolder>   {
    private Fagylalt mFagylaltData;
    private Context mContext;

    public FagylaltAdapter(Context context, Fagylalt mFagylalt) {
        this.mFagylaltData = mFagylalt;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FagylaltAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.fagylalt_datas, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mTitleText;
        private ImageView mItemImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleText = itemView.findViewById(R.id.itemTitle);
            mItemImage = itemView.findViewById(R.id.itemImage);
        }

        public void bindTo(Fagylalt currentItem) {
            mTitleText.setText(currentItem.getNev());
            Glide.with(mContext).load(currentItem.getImageResource()).into(mItemImage);

        }
    }

}
