package com.example.fagylaltpult;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

public class PultAdapter extends RecyclerView.Adapter<PultAdapter.ViewHolder> implements Filterable {
    private ArrayList<Pult> mFagylaltData;
    private ArrayList<Pult> mFagylaltDataAll;
    private Context mContext;
    private int lastPosition = -1;

    private OnNoteListener mOnNoteListener;


    public PultAdapter(Context context, ArrayList<Pult> itemsData, OnNoteListener onNoteListener) {
        this.mFagylaltData =  itemsData;
        this.mFagylaltDataAll =  itemsData;
        this.mContext = context;
        this.mOnNoteListener=onNoteListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false),mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pult currentItem = mFagylaltData.get(position);

        holder.bindTo(currentItem);

        if(holder.getAdapterPosition() > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {return mFagylaltData.size();}

    @Override
    public Filter getFilter() {
        return fagylaltFilter;
    }

    private Filter fagylaltFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Pult> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null ||charSequence.length() == 0){
                results.count = mFagylaltDataAll.size();
                results.values = mFagylaltDataAll;
            }else{
                String filterPattern = charSequence.toString().toLowerCase(Locale.ROOT);

                for (Pult fagyi: mFagylaltDataAll){
                    if(fagyi.getFagyiNeve().toLowerCase(Locale.ROOT).contains(filterPattern)){
                        filteredList.add(fagyi);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mFagylaltData = (ArrayList) filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTitleText;
        private ImageView mItemImage;
        private OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            mTitleText = itemView.findViewById(R.id.itemTitle);
            mItemImage = itemView.findViewById(R.id.itemImage);
            this.onNoteListener = onNoteListener;

            //itemView.setOnClickListener(this);
        }

        public void bindTo(Pult currentItem) {
            mTitleText.setText(currentItem.getFagyiNeve());
            //TODO: this ˇ

            //Glide.with(mContext).load(currentItem.getImageResource()).into(mItemImage);

        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }


    public interface OnNoteListener{
        void onNoteClick(int position);
    }

}
