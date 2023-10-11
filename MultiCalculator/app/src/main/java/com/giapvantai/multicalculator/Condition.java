package com.giapvantai.multicalculator;

public class Condition {

    // Phương thức này trả về mô tả điều kiện dựa trên chỉ số BMI (result).
    public String getCategory(float result) {
        if (result < 15) {
            return "Severe Thinness";
        } else if (result <= 16) {
            return "Moderate Thinness";
        } else if (result <= 18.5) {
            return "Mild Thinness";
        } else if (result <= 25) {
            return "Normal";
        } else if (result <= 30) {
            return "Overweight";
        } else if (result <= 35) {
            return "Obese Class I";
        } else if (result <= 40) {
            return "Obese Class II";
        } else {
            return "Obese Class III";
        }
    }
}

