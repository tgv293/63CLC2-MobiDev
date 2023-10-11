package com.giapvantai.multicalculator;

public class CalculateFactorial {

    private static final int MAX = 1000;  // Giới hạn kích thước mảng lưu trữ kết quả

    private final int[] res;  // Mảng lưu trữ kết quả
    private int resSize;      // Kích thước hiện tại của kết quả

    public CalculateFactorial() {
        res = new int[MAX];  // Khởi tạo mảng kết quả với kích thước tối đa
        resSize = 1;         // Khởi tạo kích thước của kết quả là 1 (giá trị mặc định)
    }

    public int getResSize() {
        return resSize;  // Phương thức trả về kích thước của kết quả
    }

    public int[] factorial(int n) {
        // Khởi tạo kết quả với giá trị ban đầu là 1
        res[0] = 1;

        // Áp dụng công thức đơn giản của giai thừa: n! = 1 * 2 * 3 * 4...*n
        for (int x = 2; x <= n; x++) {
            resSize = multiply(x, res, resSize);  // Gọi phương thức multiply để tính tích của x và kết quả hiện tại
        }

        return res;  // Trả về mảng lưu trữ kết quả của giai thừa
    }

    private int multiply(int x, int[] result, int resultSize) {
        int carry = 0;  // Biến lưu trữ giá trị dư khi nhân

        // Lặp qua từng phần tử của kết quả hiện tại và nhân với x, sau đó cộng giá trị dư
        for (int i = 0; i < resultSize; i++) {
            int prod = result[i] * x + carry;
            result[i] = prod % 10;  // Lưu chữ số cuối cùng của kết quả vào mảng
            carry = prod / 10;     // Lưu giá trị dư cho lần nhân tiếp theo
        }

        // Xử lý giá trị dư còn lại sau khi nhân xong
        while (carry != 0) {
            result[resultSize] = carry % 10;  // Lưu chữ số cuối cùng của giá trị dư vào mảng kết quả
            carry = carry / 10;              // Lấy phần nguyên của giá trị dư cho lần nhân tiếp theo
            resultSize++;                     // Tăng kích thước của kết quả
        }

        return resultSize;  // Trả về kích thước mới của kết quả
    }
}

