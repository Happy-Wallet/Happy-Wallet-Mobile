package com.example.happy_wallet_mobile.Data;

import com.example.happy_wallet_mobile.Data.Local.StaticDataProvider;
import com.example.happy_wallet_mobile.Model.*;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.IncomeExpenseMonth;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class MockDataProvider {

    private static final Random random = new Random();
    private static List<User> cachedUsers = null;
    private static List<GroupTransaction> cachedGroupTransactions;

    // ---------- USERS ----------
    public static List<User> getMockUsers() {
        if (cachedUsers != null) return cachedUsers;
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
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
        cachedUsers = users;
        return users;
    }

    // ---------- CATEGORIES ----------
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
                    i + 29,
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

    // ---------- TRANSACTIONS ----------
    public static List<Transaction> getMockTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        List<Category> categories = getMockCategories();
        String[] descriptions = {"Cơm trưa", "Đi làm", "Kháng sinh", "Áo quần", "Học phí"};

        for (int i = 30; i <= 40; i++) {
            int randomCatIndex = random.nextInt(categories.size());
            int catId = categories.get(randomCatIndex).getCategoryId();

            int month = random.nextInt(12);
            int day = random.nextInt(28) + 1;
            Calendar calendar = Calendar.getInstance();
            calendar.set(2025, month, day);
            Date transactionDate = calendar.getTime();

            BigDecimal amount = BigDecimal.valueOf((random.nextInt(50) + 1) * 1000);
            eType type = random.nextBoolean() ? eType.EXPENSE : eType.INCOME;

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


    // ---------- SAVING GOALS ----------
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
                    i, 1, i,
                    names[i - 1], descriptions[i - 1],
                    BigDecimal.valueOf(currentValue),
                    BigDecimal.valueOf(targetValue),
                    new Date()
            ));
        }
        return goals;
    }

    // ---------- GROUPS ----------
    public static List<Group> getMockGroups() {
        List<Group> groups = new ArrayList<>();
        String[] names = {"Nhóm bạn bè", "Gia đình", "Công ty", "CLB thiện nguyện", "Đội học tập"};

        Map<Integer, BigDecimal> groupAmountMap = new HashMap<>();
        for (int i = 1; i <= names.length; i++) {
            groups.add(new Group(
                    i,
                    i,
                    names[i - 1],
                    BigDecimal.ZERO, // sẽ set sau
                    i % 2 == 0,
                    i % 2 == 0 ? BigDecimal.valueOf(1_000_000 + random.nextInt(4_000_000)) : BigDecimal.ZERO,
                    "Nhóm " + names[i - 1],
                    new Date(),
                    new Date(),
                    null
            ));
            groupAmountMap.put(i, BigDecimal.ZERO);
        }

        List<GroupTransaction> transactions = getMockGroupTransactions(groupAmountMap);

        for (Group g : groups) {
            g.setCurrentAmount(groupAmountMap.getOrDefault(g.getId(), BigDecimal.ZERO));
        }

        cachedGroupTransactions = transactions;
        return groups;
    }

    public static List<GroupMember> getMockGroupMembers() {
        List<GroupMember> members = new ArrayList<>();
        for (int groupId = 1; groupId <= 5; groupId++) {
            for (int i = 0; i < 4; i++) {
                String role = (i==1 ? eRole.ADMIN : eRole.MEMBER).toString();
                int userId = ((groupId - 1) * 4 + i) % 12 + 1;
                members.add(new GroupMember(
                        groupId,
                        userId,
                        role,
                        new Date(),
                        new Date(),
                        null
                ));
            }
        }
        return members;
    }

    public static List<GroupTransaction> getMockGroupTransactions(Map<Integer, BigDecimal> groupAmountMap) {
        List<GroupTransaction> transactions = new ArrayList<>();
        String[] descriptions = {"Mua chung", "Tiệc nhóm", "Đóng phí", "Mua dụng cụ", "Chi phí khác"};
        List<GroupMember> members = getMockGroupMembers();
        Random random = new Random();

        int id = 1;
        for (int month = Calendar.JANUARY; month <= Calendar.DECEMBER; month++) {
            // Mỗi tháng chọn từ 5 - 10 ngày ngẫu nhiên
            Set<Integer> daysInMonth = new HashSet<>();
            int numberOfDays = 5 + random.nextInt(6); // 5 -> 10 ngày
            while (daysInMonth.size() < numberOfDays) {
                daysInMonth.add(1 + random.nextInt(28));
            }

            for (int day : daysInMonth) {
                for (GroupMember member : members) {
                    Calendar cal = Calendar.getInstance();
                    cal.set(2025, month, day);

                    BigDecimal amount = BigDecimal.valueOf((random.nextInt(50) + 1) * 10_000);
                    eType type = random.nextBoolean() ? eType.EXPENSE : eType.INCOME;
                    BigDecimal signedAmount = type == eType.EXPENSE ? amount.negate() : amount;

                    groupAmountMap.put(member.getGroupId(),
                            groupAmountMap.get(member.getGroupId()).add(signedAmount));

                    transactions.add(new GroupTransaction(
                            id++, member.getGroupId(), member.getUserId(),
                            random.nextInt(10) + 1,
                            amount,
                            descriptions[random.nextInt(descriptions.length)],
                            cal.getTime(),
                            new Date(),
                            null,
                            type
                    ));
                }
            }
        }
        return transactions;
    }


    public static List<GroupTransaction> getMockGroupTransactions() {
        if (cachedGroupTransactions == null) {
            getMockGroups(); // tự tạo transactions
        }
        return cachedGroupTransactions;
    }

    // ---------- MONTHLY INCOME / EXPENSE ----------
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