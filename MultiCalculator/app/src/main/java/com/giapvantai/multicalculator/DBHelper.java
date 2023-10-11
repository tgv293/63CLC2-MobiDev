package com.giapvantai.multicalculator;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HISTORY.DB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "history";
    private static final String COLUMN_CALCULATOR_NAME = "calculator_name";
    private static final String COLUMN_EXPRESSION = "expression";
    private static final String TAG = "DATABASE OPERATIONS";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng lưu trữ lịch sử tính toán
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_CALCULATOR_NAME + " TEXT," +
                COLUMN_EXPRESSION + " TEXT);";
        db.execSQL(createTableQuery);
    }

    // Phương thức thêm dữ liệu vào bảng
    public void insert(String calculatorName, String expression) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CALCULATOR_NAME, calculatorName);
        contentValues.put(COLUMN_EXPRESSION, expression);
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    // Phương thức truy vấn và hiển thị lịch sử tính toán
    @SuppressLint("Recycle")
    public ArrayList<String> showHistory(String calculatorName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        ArrayList<String> historyList = new ArrayList<>();
        String[] selectionArgs = {calculatorName};
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_CALCULATOR_NAME + " = ?";
        cursor = db.rawQuery(query, selectionArgs);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String expression = cursor.getString(cursor.getColumnIndex(COLUMN_EXPRESSION));
                historyList.add(expression);
            } while (cursor.moveToNext());
        }
        db.close();
        return historyList;
    }

    // Phương thức xóa dữ liệu trong bảng dựa trên tên máy tính
    public void deleteRecords(String calculatorName) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COLUMN_CALCULATOR_NAME + "=?";
        String[] whereArgs = {calculatorName};
        db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xử lý khi nâng cấp cơ sở dữ liệu (không cần thiết trong phiên bản hiện tại)
    }
}
