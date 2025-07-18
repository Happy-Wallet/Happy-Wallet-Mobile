package com.example.happy_wallet_mobile.View.Adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_wallet_mobile.Model.eType;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.UserDailyTransactions.DailyTransactionHeader;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.UserDailyTransactions.TransactionItem;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.UserDailyTransactions.TransactionUiModel;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;
import java.text.NumberFormat;
import java.util.List;

public class DailyTransactionsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private final Context context;
    private List<TransactionUiModel> data = new ArrayList<>();

    public void updateData(List<TransactionUiModel> data){
        if(data != null)
            this.data = data;
        else
            this.data = new ArrayList<>();
        notifyDataSetChanged();
    }


    public DailyTransactionsRecyclerViewAdapter(Context context, List<TransactionUiModel> data) {
        this.context = context;
        this.data = data;
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvTotalAmount;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTotalAmount = itemView.findViewById(R.id.tvTotalAmount);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        FrameLayout flIconBackground;
        ImageView ivIcon;
        TextView tvTitle, tvDetail, tvAmount;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            flIconBackground = itemView.findViewById(R.id.flIconBackground);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDetail = itemView.findViewById(R.id.tvDetail);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position) instanceof DailyTransactionHeader ? TYPE_HEADER : TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = (viewType == TYPE_HEADER)
                ? R.layout.item_daily_transaction_header
                : R.layout.item_transaction;
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return (viewType == TYPE_HEADER)
                ? new HeaderViewHolder(view)
                : new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            DailyTransactionHeader header = (DailyTransactionHeader) data.get(position);
            HeaderViewHolder h = (HeaderViewHolder) holder;

            h.tvDate.setText(header.getDate());
            h.tvTotalAmount.setText(formatAmount(header.getTotalAmount()));

            // Set background tint based on total amount sign
            View flHeader = h.itemView.findViewById(R.id.flHeader);
            int bgColorRes = header.getTotalAmount().signum() >= 0
                    ? R.color.Paolo_Veronese_Green
                    : R.color.Radishical;
            flHeader.getBackground().setColorFilter(
                    ContextCompat.getColor(context, bgColorRes),
                    PorterDuff.Mode.SRC_IN
            );
        } else {
            TransactionItem item = (TransactionItem) data.get(position);
            ItemViewHolder i = (ItemViewHolder) holder;

            if (getItemViewType(position) == TYPE_ITEM) {
                boolean isLastInGroup = (position + 1 == data.size()) ||
                        getItemViewType(position + 1) == TYPE_HEADER;

                if (isLastInGroup){
                    int bgRes = R.drawable.bg_bottom_rounded_20_white;
                    holder.itemView.setBackground(ContextCompat.getDrawable(context, bgRes));
                }

            }


            // Set title & description
            i.tvTitle.setText(item.getCategory().getName());
            i.tvDetail.setText(item.getTransaction().getDescription());

            // Set icon
            int iconResId = item.getCategory().getIconRes();
            if (iconResId != 0) {
                i.ivIcon.setImageResource(iconResId);
                i.ivIcon.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);
            }


            // Set amount
            i.tvAmount.setText(CurrencyUtility.format1(item.getTransaction().getAmount()));

            // set text color
            i.tvAmount.setTextColor(ContextCompat.getColor(context,
                    item.getTransaction().getType() == eType.INCOME ? R.color.Paolo_Veronese_Green : R.color.Radishical));

            try {
                int color = ContextCompat.getColor(context, item.getCategory().getColorRes());
                i.tvTitle.setTextColor(color);
                i.tvDetail.setTextColor(color);
                i.flIconBackground.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            } catch (Exception ignored) {}
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private String formatAmount(BigDecimal amount) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return (amount.signum() >= 0 ? "+" : "-") + nf.format(amount.abs());
    }
}
