//package com.example.happy_wallet_mobile.ViewModel.Group;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.ViewModel;
//
//import com.example.happy_wallet_mobile.Data.MockDataProvider;
//import com.example.happy_wallet_mobile.Model.Category;
//import com.example.happy_wallet_mobile.Model.Group;
//import com.example.happy_wallet_mobile.Model.GroupMember;
//import com.example.happy_wallet_mobile.Model.GroupTransaction;
//import com.example.happy_wallet_mobile.Model.Transaction;
//import com.example.happy_wallet_mobile.Model.User;
//import com.example.happy_wallet_mobile.Model.eType;
//import com.example.happy_wallet_mobile.View.Adapter.UIModel.GroupDailyTransactions.GroupTransactionHeader;
//import com.example.happy_wallet_mobile.View.Adapter.UIModel.GroupDailyTransactions.GroupTransactionItem;
//import com.example.happy_wallet_mobile.View.Adapter.UIModel.GroupDailyTransactions.GroupTransactionUiModel;
//import com.example.happy_wallet_mobile.View.Adapter.UIModel.IncomeExpenseMonth;
//
//import java.lang.reflect.Member;
//import java.math.BigDecimal;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.ZoneId;
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class GroupActivitiesViewModel extends ViewModel {
//
//    private final MutableLiveData<List<GroupTransactionUiModel>> groupedUiData = new MutableLiveData<>();
//    public LiveData<List<GroupTransactionUiModel>> getGroupedUiData() {
//        return groupedUiData;
//    }
//
//    public void buildGroupUiData(
//            List<GroupTransaction> transactions,
//            List<User> users,
//            List<Category> categories
//    ) {
//        // Null safety
//        transactions = (transactions != null) ? transactions : Collections.emptyList();
//        users = (users != null) ? users : Collections.emptyList();
//        categories = (categories != null) ? categories : Collections.emptyList();
//
//        // Build user & category map
//        Map<Integer, User> userMap = users.stream()
//                .collect(Collectors.toMap(User::getId, u -> u));
//        Map<Integer, Category> categoryMap = categories.stream()
//                .collect(Collectors.toMap(Category::getCategoryId, c -> c));
//
//        // Group by date
//        Map<String, List<GroupTransaction>> transactionsByDate = new TreeMap<>(Collections.reverseOrder());
//        for (GroupTransaction t : transactions) {
//            String dateStr = t.getCreatedDate().toInstant()
//                    .atZone(ZoneId.systemDefault())
//                    .toLocalDate()
//                    .toString();
//            transactionsByDate.computeIfAbsent(dateStr, k -> new ArrayList<>()).add(t);
//        }
//
//        List<GroupTransactionUiModel> uiModels = new ArrayList<>();
//        for (Map.Entry<String, List<GroupTransaction>> entry : transactionsByDate.entrySet()) {
//            String date = entry.getKey();
//            List<GroupTransaction> dayTransactions = entry.getValue();
//
//            BigDecimal totalAmount = dayTransactions.stream()
//                    .map(t -> t.getType() == eType.INCOME
//                            ? t.getAmount()
//                            : t.getAmount().negate())
//                    .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//            uiModels.add(new GroupTransactionHeader(date, totalAmount));
//
//            uiModels.add(new GroupTransactionItem(
//                    (user != null ? user.getUsername() : "Unknown"),
//                    category,
//                    t.getDescription(),
//                    t.getAmount(),
//                    t.getType() != null ? t.getType().name() : "unknown"
//            ));
//
//        }
//
//        groupedUiData.setValue(uiModels);
//    }
//
//
//
//
//    private final MutableLiveData<List<IncomeExpenseMonth>> monthlyData = new MutableLiveData<>();
//
//    public LiveData<List<IncomeExpenseMonth>> getMonthlyData() {
//        return monthlyData;
//    }
//    public void loadMonthlyData(List<GroupTransaction> groupTransactions) {
//        List<Transaction> transactions = GroupToUserTransaction(groupTransactions);
//        monthlyData.setValue(getMonthlyIncomeExpense(transactions));
//    }
//
//    private List<Transaction> GroupToUserTransaction(List<GroupTransaction> groupTransactions) {
//        if (groupTransactions == null) {
//            groupTransactions = Collections.emptyList();
//        }
//
//        List<Transaction> transactions = new ArrayList<>();
//        for (GroupTransaction gt : groupTransactions) {
//            Transaction t = new Transaction(
//                    gt.getTransactionId(),
//                    gt.getUserId(),
//                    gt.getType(),
//                    gt.getCategoryId(),
//                    gt.getAmount(),
//                    gt.getDescription(),
//                    gt.getCreatedDate(),
//                    gt.getDeletedDate()
//            );
//            transactions.add(t);
//        }
//        return transactions;
//    }
//
//
//
//    private List<IncomeExpenseMonth> getMonthlyIncomeExpense(List<Transaction> transactions) {
//        Map<String, BigDecimal> incomeMap = new HashMap<>();
//        Map<String, BigDecimal> expenseMap = new HashMap<>();
//
//        for (Transaction t : transactions) {
//            String monthYear = extractMonthYear(t.getDate());
//            BigDecimal amount = t.getAmount().abs();
//
//            if (t.getType() == eType.INCOME) {
//                incomeMap.put(monthYear,
//                        incomeMap.getOrDefault(monthYear, BigDecimal.ZERO).add(amount));
//            } else if (t.getType() == eType.EXPENSE) {
//                expenseMap.put(monthYear,
//                        expenseMap.getOrDefault(monthYear, BigDecimal.ZERO).add(amount));
//            }
//        }
//
//        Set<String> allMonths = new HashSet<>();
//        allMonths.addAll(incomeMap.keySet());
//        allMonths.addAll(expenseMap.keySet());
//
//        SimpleDateFormat sdf = new SimpleDateFormat("M/yyyy", Locale.getDefault());
//
//        List<IncomeExpenseMonth> result = new ArrayList<>();
//        for (String month : allMonths) {
//            try {
//                Date parsedDate = sdf.parse(month);
//
//                result.add(new IncomeExpenseMonth(
//                        parsedDate,
//                        incomeMap.getOrDefault(month, BigDecimal.ZERO),
//                        expenseMap.getOrDefault(month, BigDecimal.ZERO)
//                ));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//        // Sort theo ngày tăng dần
//        result.sort(Comparator.comparing(IncomeExpenseMonth::getDate));
//        return result;
//    }
//
//
//    private String extractMonthYear(Date date) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        int month = cal.get(Calendar.MONTH) + 1;
//        int year = cal.get(Calendar.YEAR);
//        return month + "/" + year;
//    }
//
//
//    private Date convertToDate(String monthYear) {
//        try {
//            return new SimpleDateFormat("M/yyyy", Locale.getDefault()).parse(monthYear);
//        } catch (ParseException e) {
//            return new Date(0);
//        }
//    }
//}
//
