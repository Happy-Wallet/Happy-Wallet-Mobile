package com.example.happy_wallet_mobile.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_wallet_mobile.View.Adapter.UIModel.IncomeExpenseMonth;
import com.example.happy_wallet_mobile.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MonthIAEAdapter extends RecyclerView.Adapter<MonthIAEAdapter.ViewHolder> {
    private List<IncomeExpenseMonth> data;

    public MonthIAEAdapter(List<IncomeExpenseMonth> data) {
        this.data = data;
    }

    public void update(List<IncomeExpenseMonth> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View viewIncome, viewExpense;
        TextView tvMonth;

        public ViewHolder(View itemView) {
            super(itemView);
            viewIncome = itemView.findViewById(R.id.viewIncome);
            viewExpense = itemView.findViewById(R.id.viewExpense);
            tvMonth = itemView.findViewById(R.id.tvMonth);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_income_expense, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        IncomeExpenseMonth item = data.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("M/yyyy", Locale.getDefault());
        holder.tvMonth.setText(sdf.format(item.getDate()));

        BigDecimal max = getMaxValue();
        BigDecimal hundred = new BigDecimal("100");
        int maxHeight = 100;

        if (max.compareTo(BigDecimal.ZERO) == 0) {
            max = BigDecimal.ONE; // tránh chia 0
        }

        BigDecimal incomeRatio = item.getIncome().divide(max, 4, RoundingMode.HALF_UP);
        BigDecimal expenseRatio = item.getExpense().divide(max, 4, RoundingMode.HALF_UP);

        int incomeHeight = incomeRatio.multiply(hundred).intValue();
        int expenseHeight = expenseRatio.multiply(hundred).intValue();

        ViewGroup.LayoutParams incomeParams = holder.viewIncome.getLayoutParams();
        incomeParams.height = incomeHeight;
        holder.viewIncome.setLayoutParams(incomeParams);

        ViewGroup.LayoutParams expenseParams = holder.viewExpense.getLayoutParams();
        expenseParams.height = expenseHeight;
        holder.viewExpense.setLayoutParams(expenseParams);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private BigDecimal getMaxValue() {
        BigDecimal max = BigDecimal.ZERO;

        for (IncomeExpenseMonth item : data) {
            if (item.getIncome().compareTo(max) > 0) {
                max = item.getIncome();
            }
            if (item.getExpense().compareTo(max) > 0) {
                max = item.getExpense();
            }
        }

        return max.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ONE : max;
    }

}
