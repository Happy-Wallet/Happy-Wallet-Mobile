package com.example.happy_wallet_mobile.Data;

import com.example.happy_wallet_mobile.Data.Local.StaticDataProvider;
import com.example.happy_wallet_mobile.Model.*;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.IncomeExpenseMonth;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

//public class MockDataProvider {
//
//    private static final Random random = new Random();
//    private static List<User> cachedUsers = null;
//    private static List<GroupTransaction> cachedGroupTransactions;
//    private static List<Group> cachedGroups = null; // Cache for groups
//
//        // Helper to format date to YYYY-MM-DD string
//        private static String formatDateToString(Date date) {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//            return sdf.format(date);
//        }
//        return goals;
//    }
//
//
//
//
//
//    // ---------- MONTHLY INCOME / EXPENSE ----------
//    public static List<IncomeExpenseMonth> generateMonthlyStatsFromGroupTransactions(List<GroupTransaction> transactions) {
//        Map<String, BigDecimal> incomeMap = new HashMap<>();
//        Map<String, BigDecimal> expenseMap = new HashMap<>();
//        SimpleDateFormat sdf = new SimpleDateFormat("M/yyyy", Locale.getDefault());
//
//        for (GroupTransaction t : transactions) {
//            String key = sdf.format(t.getCreatedDate());
//            BigDecimal amount = t.getAmount();
//            if (amount.signum() >= 0) {
//                incomeMap.put(key, incomeMap.getOrDefault(key, BigDecimal.ZERO).add(amount));
//            } else {
//                expenseMap.put(key, expenseMap.getOrDefault(key, BigDecimal.ZERO).add(amount.abs()));
//            }
//
//            // ---------- USERS ----------
//
//
//            // ---------- CATEGORIES ----------
//
//
//            // ---------- GROUPS (FUNDS) ----------
//
//
//            // ---------- MONTHLY INCOME / EXPENSE ----------
//            return goals;
//        }
//    }
//
