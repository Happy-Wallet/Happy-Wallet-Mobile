package com.example.happy_wallet_mobile.Data;

import com.example.happy_wallet_mobile.Model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MockDataProvider {

    private static final Random random = new Random();

    public static List<User> getMockUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
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

    public static List<Icon> getMockIcons() {
        List<Icon> icons = new ArrayList<>();
        String[] paths = {"ic_food", "ic_travel", "ic_health", "ic_shopping", "ic_education"};
        for (int i = 0; i < paths.length; i++) {
            icons.add(new Icon(i + 1, paths[i]));
        }
        return icons;
    }

    public static List<Category> getMockCategories() {
        List<Category> categories = new ArrayList<>();
        String[] names = {"Ăn uống", "Du lịch", "Sức khỏe", "Mua sắm", "Giáo dục"};
        String[] colorCodes = {"#FF5722", "#4CAF50", "#2196F3", "#9C27B0", "#FFC107"};
        for (int i = 0; i < names.length; i++) {
            categories.add(new Category(
                    i + 1,
                    1, // userId
                    i + 1, // iconId
                    colorCodes[i],
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
        String[] titles = {"Mua cơm", "Grab", "Mua thuốc", "Shopee", "Học phí"};
        String[] descriptions = {"Cơm trưa", "Đi làm", "Kháng sinh", "Áo quần", "Trường đại học"};
        for (int i = 1; i <= 15; i++) {
            int catId = (i % 5) + 1;
            transactions.add(new Transaction(
                    i,
                    1, // userId
                    catId,
                    catId, // iconId theo category
                    titles[i % titles.length],
                    BigDecimal.valueOf((random.nextInt(50) + 1) * 1000),
                    descriptions[i % descriptions.length],
                    new Date(),
                    null
            ));
        }
        return transactions;
    }

    public static List<SavingGoal> getMockSavingGoals() {
        List<SavingGoal> goals = new ArrayList<>();
        String[] names = {"MacBook", "Xe máy", "Chuyến đi Hà Giang", "Tiền cưới", "Quỹ học cao học"};
        for (int i = 1; i <= names.length; i++) {
            goals.add(new SavingGoal(
                    i,
                    1, // userId
                    i, // categoryId
                    names[i - 1],
                    BigDecimal.valueOf(random.nextInt(5_000_000)),
                    BigDecimal.valueOf(10_000_000 + random.nextInt(10_000_000)),
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
}
