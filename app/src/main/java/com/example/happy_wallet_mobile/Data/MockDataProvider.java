package com.example.happy_wallet_mobile.Data;

import com.example.happy_wallet_mobile.Data.Local.StaticDataProvider;
import com.example.happy_wallet_mobile.Model.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class MockDataProvider {

    private static final Random random = new Random();

    public static List<User> getMockUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            users.add(new User(
                    i,
                    "user" + i + "@example.com",
                    "user" + i,
                    "hashed_pw",
                    new Date(),
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
        List<Integer> colorList = StaticDataProvider.getColorList();
        List<Integer> iconList = StaticDataProvider.getIconList();

        for (int i = 0; i < names.length; i++) {
            eType type = (i % 3 == 0) ? eType.EXPENSE : (i % 3 == 1) ? eType.INCOME : eType.SAVING_GOAL;
            categories.add(new Category(
                    i + 1,
                    1,
                    colorList.get(i % colorList.size()),
                    iconList.get(i % iconList.size()),
                    type,
                    names[i],
                    i % 2 == 0,
                    new Date(),
                    new Date(),
                    null
            ));
        }
        return categories;
    }

    public static List<Transaction> getMockTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String[] descriptions = {"Cơm trưa", "Đi làm", "Kháng sinh", "Áo quần", "Học phí"};

        for (int i = 1; i <= 100; i++) {
            int catId = (i % 10) + 1;

            // Random tháng từ 0 đến 5 (JANUARY -> JUNE)
            int month = random.nextInt(12);
            int day = random.nextInt(28) + 1; // để tránh out of range
            Calendar calendar = Calendar.getInstance();
            calendar.set(2025, month, day);
            Date transactionDate = calendar.getTime();

            BigDecimal amount = BigDecimal.valueOf((random.nextInt(50) + 1) * 1000);
            boolean isExpense = random.nextBoolean();
            if (isExpense) amount = amount.negate();

            eType type = isExpense ? eType.EXPENSE : eType.INCOME;

            transactions.add(new Transaction(
                    i,
                    1,
                    type,
                    catId,
                    amount,
                    descriptions[i % descriptions.length],
                    transactionDate,
                    null
            ));
        }
        return transactions;
    }


    public static List<SavingGoal> getMockSavingGoals() {
        List<SavingGoal> goals = new ArrayList<>();
        String[] names = {"MacBook", "Xe máy", "Chuyến đi Hà Giang", "Tiền cưới", "Quỹ học cao học"};
        String[] descriptions = {
                "Tiết kiệm để mua MacBook phục vụ công việc",
                "Mua xe máy mới thay chiếc cũ",
                "Đi du lịch Hà Giang cùng bạn bè",
                "Chi phí chuẩn bị cho lễ cưới",
                "Dành cho học cao học sau này"
        };

        for (int i = 1; i <= names.length; i++) {
            int targetValue = 5_000_000 + random.nextInt(45_000_000);
            int currentValue = random.nextInt((int) (targetValue * 0.8));

            goals.add(new SavingGoal(
                    i,
                    1, // userId
                    i, // categoryId
                    names[i - 1],
                    descriptions[i - 1],
                    BigDecimal.valueOf(currentValue),
                    BigDecimal.valueOf(targetValue),
                    new Date()
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
                    i,
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
        String[] roles = {"Admin", "Thành viên", "Kế toán", "Quản lý"};

        for (int i = 0; i < 10; i++) {
            members.add(new GroupMember(
                    (i % 5) + 1,
                    (i % 5) + 1,
                    roles[random.nextInt(roles.length)],
                    new Date(),
                    new Date(),
                    null
            ));
        }
        return members;
    }

    public static List<GroupTransaction> getMockGroupTransactions() {
        List<GroupTransaction> transactions = new ArrayList<>();
        String[] descriptions = {"Mua chung", "Tiệc nhóm", "Đóng phí", "Mua dụng cụ", "Chi phí khác"};

        int id = 1;
        for (int month = Calendar.JANUARY; month <= Calendar.JUNE; month++) {
            for (int i = 0; i < 3; i++) {
                Calendar cal = Calendar.getInstance();
                cal.set(2025, month, random.nextInt(28) + 1);

                BigDecimal amount = BigDecimal.valueOf((random.nextInt(50) + 1) * 10_000);
                if (random.nextBoolean()) amount = amount.negate();

                transactions.add(new GroupTransaction(
                        id++,
                        (i % 5) + 1,
                        (i % 5) + 1,
                        (i % 10) + 1,
                        amount,
                        descriptions[i % descriptions.length],
                        new Date(),
                        new Date(),
                        null
                ));
            }
        }
        return transactions;
    }

    public static List<IncomeExpenseMonth> generateMonthlyStatsFromGroupTransactions(List<GroupTransaction> transactions) {
        Map<String, BigDecimal> incomeMap = new HashMap<>();
        Map<String, BigDecimal> expenseMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("M/yyyy", Locale.getDefault());

        for (GroupTransaction t : transactions) {
            String key = sdf.format(t.getCreatedDate());
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

    public static List<IncomeExpenseMonth> getMonthlyIncomeExpense() {
        return generateMonthlyStatsFromGroupTransactions(getMockGroupTransactions());
    }
}
