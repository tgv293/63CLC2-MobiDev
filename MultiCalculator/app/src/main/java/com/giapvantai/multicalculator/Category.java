package com.giapvantai.multicalculator;

public class Category {

    // Phương thức này trả về mô tả danh mục dựa trên chỉ số BMI (result).
    public String getCategory(float result) {
        if (result < 15) {
            return "very severely underweight";
        } else if (result <= 16) {
            return "severely underweight";
        } else if (result <= 18.5) {
            return "underweight";
        } else if (result <= 25) {
            return "normal (healthy weight)";
        } else if (result <= 30) {
            return "overweight";
        } else if (result <= 35) {
            return "moderately obese";
        } else if (result <= 40) {
            return "severely obese";
        } else {
            return "very severely obese";
        }
    }
}

