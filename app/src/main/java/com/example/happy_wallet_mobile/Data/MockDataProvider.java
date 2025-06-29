package com.example.happy_wallet_mobile.Data;

import com.example.happy_wallet_mobile.Data.Local.StaticDataProvider;
import com.example.happy_wallet_mobile.Model.*;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.DailyTransactionHeader;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.TransactionItem;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.TransactionUiModel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class MockDataProvider {

    private static final Random random = new Random();

    public static List<User> getMockUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            users.add(new User(
                    i,
                    "user" + i,
                    "user" + i + "@example.com",
                    "user",
                    new Date(),
                    new Date(),
                    null
            ));
        }
        return users;
    }

    public static List<Category> getMockCategories() {
        List<Category> categories = new ArrayList<>();
        String[] names = {
                "Ăn uống", "Du lịch", "Sức khỏe", "Mua sắm", "Giáo dục",
                "Giải trí", "Gia đình", "Thể thao", "Công việc", "Khác"
        };

        // Danh sách màu từ R.color
        List<Integer> colorList = StaticDataProvider.getColorList();

        // Danh sách icon từ R.drawable
        List<Integer> iconList = StaticDataProvider.getIconList();

        for (int i = 0; i < names.length; i++) {
            categories.add(new Category(
                    i + 1,                   // categoryId
                    1,                       // userId
                    colorList.get(i % colorList.size()), // colorRes
                    iconList.get(i % iconList.size()),   // iconRes
                    names[i],               // name
                    i % 2 == 0,             // isDefault
                    new Date(),             // createdAt
                    new Date(),             // updatedAt
                    null                    // deletedAt
            ));
        }
        return categories;
    }


    public static List<Transaction> getMockTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String[] titles = {"Mua cơm", "Grab", "Mua thuốc", "Shopee", "Học phí"};
        String[] descriptions = {"Cơm trưa", "Đi làm", "Kháng sinh", "Áo quần", "Trường đại học"};

        for (int i = 1; i <= 15; i++) {
            int catId = (i % 5) + 1;

            Calendar calendar = Calendar.getInstance();
            int dayOffset = random.nextInt(5); // 0 - 4
            calendar.add(Calendar.DAY_OF_MONTH, -dayOffset);
            Date transactionDate = calendar.getTime();

            int amountValue = (random.nextInt(50) + 1) * 1000;
            BigDecimal amount = BigDecimal.valueOf(amountValue);
            if (random.nextBoolean()) {
                amount = amount.negate();
            }
            String type = amount.signum() < 0 ? "expense" : "income";

            transactions.add(new Transaction(
                    i,
                    1,
                    catId,
                    catId,
                    titles[i % titles.length],
                    amount,
                    descriptions[i % descriptions.length],
                    transactionDate,
                    null,
                    type
            ));
        }
        return transactions;
    }
    public static List<Transaction> getTransactions() {
        List<Transaction> list = new ArrayList<>();

        list.add(new Transaction(
                1, 1, 0, "Ăn sáng", new BigDecimal("150000"), "Ăn sáng tại quán", new Date(), null, "expense"));

        list.add(new Transaction(
                1, 2, 0, "Tiền nhà", new BigDecimal("2500000"), "Thanh toán tiền thuê", new Date(), null, "expense"));

        list.add(new Transaction(
                1, 3, 0, "Điện nước", new BigDecimal("500000"), "Trả tiền điện, nước", new Date(), null, "expense"));

        list.add(new Transaction(
                1, 4, 0, "Mua áo", new BigDecimal("200000"), "Mua áo phông", new Date(), null, "expense"));

        return list;
    }


    public static <Context> List<IncomeExpenseMonth> getMonthlyIncomeExpense(Context context) {
        List<GroupTransaction> groupTransactions = getMockGroupTransactions();
        return generateMonthlyStatsFromGroupTransactions(groupTransactions);
    }
    public static List<IncomeExpenseMonth> generateMonthlyStatsFromGroupTransactions(List<GroupTransaction> transactions) {
        Map<String, BigDecimal> incomeMap = new HashMap<>();
        Map<String, BigDecimal> expenseMap = new HashMap<>();
        Map<String, Date> monthToDateMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("M/yyyy", Locale.getDefault());

        for (GroupTransaction t : transactions) {
            String key = sdf.format(t.getCreatedDate());
            monthToDateMap.putIfAbsent(key, t.getCreatedDate());

            BigDecimal amount = t.getAmount();
            if (amount.signum() >= 0) {
                incomeMap.put(key, incomeMap.getOrDefault(key, BigDecimal.ZERO).add(amount));
            } else {
                expenseMap.put(key, expenseMap.getOrDefault(key, BigDecimal.ZERO).add(amount.abs()));
            }
        }

        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.JANUARY, 1);

        List<IncomeExpenseMonth> result = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Date monthDate = cal.getTime();
            String key = sdf.format(monthDate);

            result.add(new IncomeExpenseMonth(
                    monthDate,
                    incomeMap.getOrDefault(key, BigDecimal.ZERO),
                    expenseMap.getOrDefault(key, BigDecimal.ZERO)
            ));

            cal.add(Calendar.MONTH, 1);
        }

        return result;
    }

    public static List<SavingGoal> getMockSavingGoals() {
        List<SavingGoal> goals = new ArrayList<>();
        String[] names = {"MacBook", "Xe máy", "Chuyến đi Hà Giang", "Tiền cưới", "Quỹ học cao học"};
        for (int i = 1; i <= names.length; i++) {
            // Target: 5 triệu đến 50 triệu
            int targetValue = 5_000_000 + random.nextInt(45_000_000);
            BigDecimal targetAmount = BigDecimal.valueOf(targetValue);

            // Current: từ 0 đến 80% của target
            int currentValue = random.nextInt((int)(targetValue * 0.8));
            BigDecimal currentAmount = BigDecimal.valueOf(currentValue);

            goals.add(new SavingGoal(
                    i,
                    1, // userId
                    i, // categoryId
                    names[i - 1],
                    currentAmount,
                    targetAmount,
                    "Mục tiêu: " + names[i - 1],
                    new Date(),
                    new Date(),
                    null
            ));
        }
        return goals;
    }

    public static List<Group> getMockGroups() {
        List<Group> groups = new ArrayList<>();
        String[] names = {"Nhóm bạn bè", "Gia đình", "Công ty", "CLB thiện nguyện", "Đội học tập"};
        for (int i = 1; i <= names.length; i++) {
            boolean hasTarget = i % 2 == 0;
            double targetAmount = hasTarget ? 1_000_000 + random.nextInt(4_000_000) : 0;
            groups.add(new Group(
                    i,
                    i, // categoryId
                    names[i - 1],
                    200_000 + random.nextInt(2_000_000),
                    hasTarget,
                    targetAmount,
                    "Nhóm " + names[i - 1],
                    new Date(),
                    new Date(),
                    null
            ));
        }
        return groups;
    }

    public static List<GroupMember> getMockGroupMembers() {
        List<GroupMember> members = new ArrayList<>();
        int[] groupIds = {1, 2, 3, 4, 5};
        int[] userIds = {1, 2, 3, 4, 5};
        String[] roles = {"Admin", "Thành viên", "Kế toán", "Quản lý", "Thành viên"};

        for (int i = 0; i < 10; i++) {
            int groupId = groupIds[i % groupIds.length];
            int userId = userIds[random.nextInt(userIds.length)];
            String role = roles[random.nextInt(roles.length)];

            members.add(new GroupMember(
                    groupId,
                    userId,
                    role,
                    new Date(),
                    new Date(),
                    null
            ));
        }
        return members;
    }

    public static List<GroupTransaction> getMockGroupTransactions() {
        List<GroupTransaction> transactions = new ArrayList<>();
        String[] titles = {"Mua đồ nhóm", "Chi ăn uống", "Thu phí thành viên", "Mua vật dụng", "Khác"};
        String[] descriptions = {"Mua chung", "Tiệc nhóm", "Đóng phí", "Mua dụng cụ", "Chi phí phát sinh"};

        Random random = new Random();
        int id = 1;

        for (int month = Calendar.JANUARY; month <= Calendar.JUNE; month++) {
            for (int i = 0; i < 3; i++) {
                Calendar cal = Calendar.getInstance();
                cal.set(2025, month, random.nextInt(28) + 1);

                BigDecimal amount = BigDecimal.valueOf((random.nextInt(50) + 1) * 10000);
                if (random.nextBoolean()) amount = amount.negate();

                transactions.add(new GroupTransaction(
                        (i % 3) + 1,             // groupId
                        id++,                    // transactionId
                        (i % 5) + 1,             // userId
                        (i % 10) + 1,            // categoryId
                        amount,
                        titles[i % titles.length],
                        descriptions[i % descriptions.length],
                        cal.getTime(),
                        cal.getTime(),
                        null
                ));
            }
        }

        return transactions;
    }


}
