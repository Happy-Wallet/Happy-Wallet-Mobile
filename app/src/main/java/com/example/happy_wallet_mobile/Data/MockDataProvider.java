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
        for (int i = 1; i <= 10; i++) {
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
        String[] colors = {"#FF0000", "#00FF00", "#0000FF", "#FF9900", "#6600CC"};
        String[] paths = {"ic_bell", "ic_chat_circle", "ic_plus_solid", "ic_gear_six", "ic_house"};
        for (int i = 1; i <= paths.length; i++) {
            icons.add(new Icon(i, colors[i % colors.length], paths[i - 1]));
        }
        return icons;
    }

    public static List<Category> getMockCategories() {
        List<Category> categories = new ArrayList<>();
        String[] names = {"Ăn uống", "Du lịch", "Sức khỏe", "Học tập", "Gia đình"};
        for (int i = 1; i <= names.length; i++) {
            categories.add(new Category(
                    i,
                    1,
                    i,
                    names[i - 1],
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
        String[] titles = {"Mua cơm", "Đi taxi", "Khám bệnh", "Mua sách", "Mua sữa"};
        String[] descriptions = {"Trưa", "Grab", "Phòng khám", "Tiki", "Vinmart"};
        for (int i = 1; i <= 15; i++) {
            transactions.add(new Transaction(
                    i,
                    1,
                    (i % 5) + 1,
                    (i % 5) + 1,
                    titles[i % titles.length],
                    BigDecimal.valueOf((random.nextInt(100) + 1) * 1000),
                    descriptions[i % descriptions.length],
                    new Date(),
                    null
            ));
        }
        return transactions;
    }

    public static List<SavingGoal> getMockSavingGoals() {
        List<SavingGoal> goals = new ArrayList<>();
        String[] names = {"MacBook", "Xe máy", "Du lịch", "Quỹ khẩn cấp", "Học phí"};
        for (int i = 1; i <= names.length; i++) {
            goals.add(new SavingGoal(
                    i,
                    1,
                    i,
                    names[i - 1],
                    BigDecimal.valueOf(random.nextInt(5_000_000)),
                    BigDecimal.valueOf(10_000_000 + random.nextInt(10_000_000)),
                    "Tiết kiệm cho " + names[i - 1],
                    new Date(),
                    new Date(),
                    null
            ));
        }
        return goals;
    }

    public static List<Group> getMockGroups() {
        List<Group> groups = new ArrayList<>();
        String[] names = {"Nhóm học tập", "Gia đình", "Công ty", "Câu lạc bộ", "Bạn bè"};
        for (int i = 1; i <= names.length; i++) {
            boolean hasTarget = i % 2 == 0;
            double targetAmount = hasTarget ? 1_000_000 + random.nextInt(5_000_000) : 0;
            groups.add(new Group(
                    i,
                    i,
                    names[i - 1],
                    500_000 + random.nextInt(2_000_000),
                    hasTarget,
                    targetAmount,
                    "Mô tả cho " + names[i - 1],
                    new Date(),
                    new Date(),
                    null
            ));
        }
        return groups;
    }
}
