package com.example.happy_wallet_mobile.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_wallet_mobile.Model.Transaction;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.DailyTransactionList;

import java.util.List;

public class DailyTransactionsRecyclerViewAdapter extends RecyclerView.Adapter<DailyTransactionsRecyclerViewAdapter.GroupViewHolder>{

    private List<DailyTransactionList> groups;

    public DailyTransactionsRecyclerViewAdapter(List<DailyTransactionList> groups) {
        this.groups = groups;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_daily_transaction_header, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        holder.bind(groups.get(position));
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    static class GroupViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate, tvTotal;
        private LinearLayout transactionContainer;

        GroupViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            transactionContainer = itemView.findViewById(R.id.transactionContainer);
        }

        void bind(DailyTransactionList group) {
            tvDate.setText(group.getDate());
            tvTotal.setText(group.getTotalAmount());

            transactionContainer.removeAllViews();
            LayoutInflater inflater = LayoutInflater.from(itemView.getContext());

            for (Transaction transaction : group.getTransactions()) {
                View itemView = inflater.inflate(R.layout.item_transaction, transactionContainer, false);
                TextView tvTitle = itemView.findViewById(R.id.tvTitle);
                TextView tvDetail = itemView.findViewById(R.id.tvDetail);
                TextView tvAmount = itemView.findViewById(R.id.tvAmount);

                tvTitle.setText(transaction.getTitle());
                tvDetail.setText(transaction.getNote());
                tvAmount.setText((transaction.getType().equals("expenditure") ? "-" : "+") + transaction.getAmount());

                transactionContainer.addView(itemView);
            }
        }
    }
}
