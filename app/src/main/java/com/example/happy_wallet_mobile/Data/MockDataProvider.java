package com.example.happy_wallet_mobile.Data;

import com.example.happy_wallet_mobile.Model.*;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.DailyTransactionHeader;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.TransactionItem;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.TransactionUiModel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

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
        String[] paths = {
                "ic_bell", "ic_paper_plane_tilt", "ic_house", "ic_gear_six", "ic_wallet",
                "ic_chats_circle", "ic_image_square_fill", "ic_pen", "ic_plus_solid", "ic_users_three"
        };
        for (int i = 0; i < paths.length; i++) {
            icons.add(new Icon(i + 1, paths[i]));
        }
        return icons;
    }

    public static List<Category> getMockCategories() {
        List<Category> categories = new ArrayList<>();
        String[] names = {
                "Ăn uống", "Du lịch", "Sức khỏe", "Mua sắm", "Giáo dục",
                "Giải trí", "Gia đình", "Thể thao", "Công việc", "Khác"
        };
        String[] colorCodes = {
                "#FF5722", "#4CAF50", "#2196F3", "#9C27B0", "#FFC107",
                "#E91E63", "#3F51B5", "#009688", "#FF9800", "#795548"
        };
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

            // Random ngày trong 0 đến 4 ngày trước
            Calendar calendar = Calendar.getInstance();
            int dayOffset = random.nextInt(5); // 0 - 4
            calendar.add(Calendar.DAY_OF_MONTH, -dayOffset);
            Date transactionDate = calendar.getTime();

            // Random số tiền: dương hoặc âm
            int amountValue = (random.nextInt(50) + 1) * 1000;
            BigDecimal amount = BigDecimal.valueOf(amountValue);
            if (random.nextBoolean()) {
                amount = amount.negate(); // chi tiêu
            }

            transactions.add(new Transaction(
                    i,
                    1, // userId
                    catId,
                    catId, // iconId
                    titles[i % titles.length],
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

    public static List<String> getMockColors() {
        List<String> colors = new ArrayList<>();
        Collections.addAll(colors,
                "#FF6F61", // Coral Red
                "#6A5ACD", // Slate Blue
                "#00BCD4", // Cyan
                "#FFD700", // Gold
                "#8BC34A", // Light Green

                "#BA68C8", // Light Purple
                "#FF8A65", // Light Orange
                "#4DD0E1", // Turquoise
                "#A1887F", // Warm Grey
                "#9575CD", // Medium Purple

                "#F06292", // Pink
                "#AED581", // Light Olive
                "#7986CB", // Soft Indigo
                "#FFB74D", // Apricot Orange
                "#81D4FA", // Sky Blue

                "#E57373", // Light Red
                "#90A4AE", // Blue Grey
                "#C5E1A5", // Lime Green
                "#CE93D8", // Lavender
                "#FFCC80"  // Peach
        );
        return colors;
    }


}
