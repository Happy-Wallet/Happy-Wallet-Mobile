package com.example.happy_wallet_mobile.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_wallet_mobile.Model.Transaction;
import com.example.happy_wallet_mobile.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TransactionRecyclerViewAdapter extends RecyclerView.Adapter<TransactionRecyclerViewAdapter.ViewHolder> {
    List<Transaction> transactionList;

    public TransactionRecyclerViewAdapter(List<Transaction> transactions) {
        this.transactionList = transactions;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout flIconBackground;
        ImageView ivIcon;
        TextView tvTitle, tvDetail, tvAmount;

        public ViewHolder(View itemView) {
            super(itemView);
            flIconBackground = itemView.findViewById(R.id.flIconBackground);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDetail = itemView.findViewById(R.id.tvDetail);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Transaction t = transactionList.get(position);
        holder.tvTitle.setText(t.getTitle());
        holder.tvDetail.setText(t.getDescription());
        NumberFormat format = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
        holder.tvAmount.setText(format.format(t.getAmount()));
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }
}
