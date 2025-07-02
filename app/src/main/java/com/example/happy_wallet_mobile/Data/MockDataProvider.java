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
    private static List<Group> cachedGroups = null; // Cache for groups

    // Helper to format date to YYYY-MM-DD string
    private static String formatDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return sdf.format(date);
    }

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
                    i % 2 == 0
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

    // ---------- GROUPS (FUNDS) ----------
    public static List<Group> getMockGroups() {
        if (cachedGroups != null) return cachedGroups;

        List<Group> groups = new ArrayList<>();
        String[] names = {"Nhóm bạn bè", "Gia đình", "Công ty", "CLB thiện nguyện", "Đội học tập"};
        List<Category> categories = getMockCategories(); // Get categories to assign categoryName

        Map<Integer, BigDecimal> groupAmountMap = new HashMap<>(); // To calculate currentAmount
        Map<Integer, List<GroupMember>> groupMembersMap = new HashMap<>(); // To store members per group

        for (int i = 1; i <= names.length; i++) {
            int fundId = i;
            String groupName = names[i - 1];
            String description = "Nhóm " + groupName;
            boolean hasTarget = random.nextBoolean(); // Randomly assign hasTarget
            BigDecimal targetAmount = null;
            String targetEndDate = null;
            // LOẠI BỎ: Integer categoryId = null;
            // LOẠI BỎ: String categoryName = null;

            if (hasTarget) {
                targetAmount = BigDecimal.valueOf(1_000_000 + random.nextInt(4_000_000));
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MONTH, random.nextInt(6) + 1); // Target date 1-6 months in future
                targetEndDate = formatDateToString(cal.getTime());
            }

            // LOẠI BỎ: Logic gán category (vì category_id và categoryName đã bị loại bỏ)
            /*
            if (!categories.isEmpty()) {
                Category randomCategory = categories.get(random.nextInt(categories.size()));
                categoryId = randomCategory.getCategoryId();
                categoryName = randomCategory.getName();
            }
            */

            // Create mock members for this group (including the creator as Admin)
            List<GroupMember> members = generateMockGroupMembersForFund(fundId);
            groupMembersMap.put(fundId, members);

            // Mock creator info (assuming the first member is the creator/admin)
            String creatorEmail = "creator" + fundId + "@example.com";
            String creatorUsername = "Creator" + fundId;
            if (!members.isEmpty()) {
                // If there's an admin, use their info as creator
                GroupMember adminMember = members.stream()
                        .filter(m -> "Admin".equals(m.getRole()))
                        .findFirst()
                        .orElse(members.get(0)); // Fallback to first member if no admin
                creatorEmail = adminMember.getEmail();
                creatorUsername = adminMember.getUsername();
            }

            groups.add(new Group(
                    fundId,
                    groupName,
                    BigDecimal.ZERO, // Will be set after transactions
                    hasTarget,
                    targetAmount,
                    description,
                    new Date(), // createdAt
                    new Date(), // updatedAt
                    null,       // deletedAt
                    targetEndDate,
                    creatorEmail,
                    creatorUsername,
                    // LOẠI BỎ: categoryName,
                    members // Add the generated members list
            ));
            groupAmountMap.put(fundId, BigDecimal.ZERO);
        }

        // Generate transactions and calculate currentAmount
        List<GroupTransaction> transactions = getMockGroupTransactions(groupAmountMap);
        cachedGroupTransactions = transactions; // Cache for other methods

        for (Group g : groups) {
            g.setCurrentAmount(groupAmountMap.getOrDefault(g.getId(), BigDecimal.ZERO));
        }

        cachedGroups = groups;
        return groups;
    }

    // New/Updated: Generate mock members for a specific fund
    public static List<GroupMember> generateMockGroupMembersForFund(int fundId) {
        List<GroupMember> members = new ArrayList<>();
        List<User> mockUsers = getMockUsers(); // Get all mock users

        // Ensure at least one admin (the creator)
        // For simplicity, let's say user 1 is always the creator/admin for fund 1, user 2 for fund 2, etc.
        int creatorUserId = fundId;
        User creatorUser = mockUsers.stream()
                .filter(u -> u.getId() == creatorUserId)
                .findFirst()
                .orElse(mockUsers.get(0)); // Fallback if user ID not found

        members.add(new GroupMember(
                creatorUser.getId(),
                "Admin",
                "accepted",
                creatorUser.getEmail(),
                creatorUser.getUserName())
        );

        // Add some random members (not including the creator again)
        Set<Integer> addedUserIds = new HashSet<>();
        addedUserIds.add(creatorUser.getId());

        int numAdditionalMembers = random.nextInt(3) + 1; // 1 to 3 additional members
        for (int i = 0; i < numAdditionalMembers; i++) {
            User randomUser;
            do {
                randomUser = mockUsers.get(random.nextInt(mockUsers.size()));
            } while (addedUserIds.contains(randomUser.getId())); // Ensure unique members

            members.add(new GroupMember(
                    randomUser.getId(),
                    "Member",
                    "accepted",
                    randomUser.getEmail(),
                    randomUser.getUserName()
            ));
            addedUserIds.add(randomUser.getId());
        }
        return members;
    }


    public static List<GroupTransaction> getMockGroupTransactions(Map<Integer, BigDecimal> groupAmountMap) {
        List<GroupTransaction> transactions = new ArrayList<>();
        String[] descriptions = {"Mua chung", "Tiệc nhóm", "Đóng phí", "Mua dụng cụ", "Chi phí khác"};
        List<Group> groups = getMockGroups(); // Get groups to iterate through them
        Random random = new Random();

        int transactionId = 1;
        for (Group group : groups) {
            List<GroupMember> members = group.getMembers(); // Use members from the group
            if (members == null || members.isEmpty()) continue; // Skip if no members

            for (int month = Calendar.JANUARY; month <= Calendar.DECEMBER; month++) {
                Set<Integer> daysInMonth = new HashSet<>();
                int numberOfDays = 5 + random.nextInt(6); // 5 -> 10 days
                while (daysInMonth.size() < numberOfDays) {
                    daysInMonth.add(1 + random.nextInt(28));
                }

                for (int day : daysInMonth) {
                    // Each member can make a transaction
                    for (GroupMember member : members) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(2025, month, day);

                        BigDecimal amount = BigDecimal.valueOf((random.nextInt(50) + 1) * 10_000);
                        eType type = random.nextBoolean() ? eType.EXPENSE : eType.INCOME;
                        BigDecimal signedAmount = type == eType.EXPENSE ? amount.negate() : amount;

                        // Update group's current amount
                        groupAmountMap.put(group.getId(),
                                groupAmountMap.getOrDefault(group.getId(), BigDecimal.ZERO).add(signedAmount));

                        transactions.add(new GroupTransaction(
                                transactionId++,
                                group.getId(), // fund_id
                                member.getUserId(), // user_id of the member making transaction
                                random.nextInt(10) + 1, // categoryId (mock)
                                amount,
                                descriptions[random.nextInt(descriptions.length)],
                                cal.getTime(), // createdDate
                                new Date(), // updatedDate
                                null,       // deletedDate
                                type
                        ));
                    }
                }
            }
        }
        return transactions;
    }


    public static List<GroupTransaction> getMockGroupTransactions() {
        if (cachedGroupTransactions == null) {
            getMockGroups(); // This will trigger transaction generation
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