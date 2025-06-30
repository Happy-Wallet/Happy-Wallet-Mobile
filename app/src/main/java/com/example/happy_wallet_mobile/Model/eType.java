package com.example.happy_wallet_mobile.Model;

public enum eType {
    INCOME,
    EXPENSE,
    SAVING_GOAL;

    public static eType fromString(String value) {
        switch (value.toLowerCase()) {
            case "income":
                return INCOME;
            case "expense":
                return EXPENSE;
            case "savinggoal":
                return SAVING_GOAL;
            default:
                throw new IllegalArgumentException("Unknown type: " + value);
        }
    }

    public String toDbValue() {
        switch (this) {
            case INCOME:
                return "income";
            case EXPENSE:
                return "expense";
            case SAVING_GOAL:
                return "savingGoal";
            default:
                throw new IllegalArgumentException();
        }
    }
}
