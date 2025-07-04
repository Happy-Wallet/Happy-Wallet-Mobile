//package com.example.happy_wallet_mobile.View.Adapter;
//
//import android.content.Context;
//import android.graphics.PorterDuff;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.core.content.ContextCompat;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.happy_wallet_mobile.Model.eType;
//import com.example.happy_wallet_mobile.R;
//import com.example.happy_wallet_mobile.View.Adapter.UIModel.GroupDailyTransactions.GroupTransactionHeader;
//import com.example.happy_wallet_mobile.View.Adapter.UIModel.GroupDailyTransactions.GroupTransactionItem;
//import com.example.happy_wallet_mobile.View.Adapter.UIModel.GroupDailyTransactions.GroupTransactionUiModel;
//import com.example.happy_wallet_mobile.View.Utilities.CurrencyUtility;
//
//import java.math.BigDecimal;
//import java.text.NumberFormat;
//import java.util.List;
//import java.util.Locale;
//
//public class GroupTransactionsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private static final int TYPE_HEADER = 0;
//    private static final int TYPE_ITEM = 1;
//
//    private final Context context;
//    private final List<GroupTransactionUiModel> data;
//
//    public GroupTransactionsRecyclerViewAdapter(Context context, List<GroupTransactionUiModel> data) {
//        this.context = context;
//        this.data = data;
//    }
//
//    static class HeaderViewHolder extends RecyclerView.ViewHolder {
//        TextView tvDate, tvTotalAmount;
//        View flHeader;
//
//        public HeaderViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tvDate = itemView.findViewById(R.id.tvDate);
//            tvTotalAmount = itemView.findViewById(R.id.tvTotalAmount);
//            flHeader = itemView.findViewById(R.id.flHeader);
//        }
//    }
//
//    static class ItemViewHolder extends RecyclerView.ViewHolder {
//        TextView tvUserName, tvCategoryName, tvAmount;
//
//        public ItemViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tvUserName = itemView.findViewById(R.id.tvUserName);
//            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
//            tvAmount = itemView.findViewById(R.id.tvAmount);
//        }
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return data.get(position) instanceof GroupTransactionHeader ? TYPE_HEADER : TYPE_ITEM;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        int layoutId = (viewType == TYPE_HEADER)
//                ? R.layout.item_daily_transaction_header
//                : R.layout.item_group_member_activity;
//        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
//        return (viewType == TYPE_HEADER)
//                ? new HeaderViewHolder(view)
//                : new ItemViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        if (getItemViewType(position) == TYPE_HEADER) {
//            GroupTransactionHeader header = (GroupTransactionHeader) data.get(position);
//            HeaderViewHolder h = (HeaderViewHolder) holder;
//
//            h.tvDate.setText(header.getDate());
//            h.tvTotalAmount.setText(formatAmount(header.getTotalAmount()));
//
//            int bgColorRes = header.getTotalAmount().signum() >= 0
//                    ? R.color.Paolo_Veronese_Green
//                    : R.color.Radishical;
//            h.flHeader.getBackground().setColorFilter(
//                    ContextCompat.getColor(context, bgColorRes),
//                    PorterDuff.Mode.SRC_IN
//            );
//        } else {
//            GroupTransactionItem item = (GroupTransactionItem) data.get(position);
//            ItemViewHolder i = (ItemViewHolder) holder;
//
//
//            i.tvUserName.setText(item.getMember().getUserName());
//            i.tvCategoryName.setText(item.getCategory().getName());
//            i.tvAmount.setText(CurrencyUtility.format1(item.getTransaction().getAmount()));
//            i.tvAmount.setTextColor(ContextCompat.getColor(context,
//                    item.getTransaction().getType()== eType.INCOME ? R.color.Paolo_Veronese_Green : R.color.Radishical));
//
//            // Set màu chữ cho category
//            try {
//                int color = ContextCompat.getColor(context, item.getCategory().getColorRes());
//                i.tvCategoryName.setTextColor(color);
//            } catch (Exception e) {
//                i.tvCategoryName.setTextColor(ContextCompat.getColor(context, R.color.black));
//            }
//
//            // Nếu là item cuối trong group -> bo góc
//            boolean isLastInGroup = (position + 1 == data.size()) ||
//                    getItemViewType(position + 1) == TYPE_HEADER;
//            if (isLastInGroup) {
//                int bgRes = R.drawable.bg_bottom_rounded_20_white;
//                holder.itemView.setBackground(ContextCompat.getDrawable(context, bgRes));
//            } else {
//                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
//            }
//        }
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return data.size();
//    }
//
//    private String formatAmount(BigDecimal amount) {
//        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
//        return (amount.signum() >= 0 ? "+" : "-") + nf.format(amount.abs());
//    }
//}
