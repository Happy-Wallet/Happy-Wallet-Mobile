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
        private static List<GroupTransaction> cachedGroupTransactions = null; // Khởi tạo là null
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
            // ... (không đổi)
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
                        i % 2 == 0, // is_active
                        new Date(),
                        new Date(),
                        null
                ));
            }
            return categories;
        }

        // ---------- TRANSACTIONS (Personal) ----------
        public static List<Transaction> getMockTransactions() {
            // ... (không đổi)
            List<Transaction> transactions = new ArrayList<>();
            String[] descriptions = {"Cơm trưa", "Đi làm", "Kháng sinh", "Áo quần", "Học phí"};

            for (int i = 1; i <= 100; i++) {
                int catId = (i % 10) + 1;
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
            // ... (không đổi)
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


        // ---------- MONTHLY INCOME / EXPENSE ----------
        public static List<IncomeExpenseMonth> generateMonthlyStatsFromGroupTransactions(List<GroupTransaction> transactions) {
            // ... (không đổi)
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

    }