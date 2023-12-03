package com.example.sprinklesbakery.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.sprinklesbakery.Classes.Category;
import com.example.sprinklesbakery.Classes.Cake;
import com.example.sprinklesbakery.Classes.Order;
import com.example.sprinklesbakery.Classes.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    public static final String CAKE_TABLE = "cake";
    public static final String USER_TABLE = "user";
    public static final String CATEGORY_TABLE = "category";
    public static final String CAKE_ORDER_TABLE = "cakeOrder";
    public static final String ID = "id";
    public static final String COLUMN_CAKE_NAME = "cakeName";
    public static final String COLUMN_CAKE_DESC = "cakeDesc";
    public static final String COLUMN_IMG_NAME = "imgName";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_CAT_ID = "catId";
    public static final String COLUMN_QTY = "quantity";
    public static final String COLUMN_CAT_NAME = "catName";
    public static final String COLUMN_CAT_DESC = "catDesc";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ROLE = "role";
    public static final String COLUMN_USERNAME = USER_TABLE + "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_USER_ID = USER_TABLE + "Id";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_ITEMS = "items";
    public static final String COLUMN_TOTAL = "total";
    public DatabaseHelper(@Nullable Context context) {
        super(context, "bloomRoom.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CAKE_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CAKE_NAME + " TEXT, " + COLUMN_CAKE_DESC + " TEXT, " + COLUMN_IMG_NAME + " TEXT, " + COLUMN_PRICE + " REAL, " + COLUMN_QTY + " INTEGER, " + COLUMN_CAT_ID + " INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CATEGORY_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CAT_NAME + " TEXT, " + COLUMN_CAT_DESC + " TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + USER_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_ROLE + " TEXT, " + COLUMN_USERNAME + " TEXT, " + COLUMN_EMAIL + " TEXT, " + COLUMN_PASSWORD + " TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CAKE_ORDER_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_ID + " INTEGER, " + COLUMN_ADDRESS + " TEXT, " + COLUMN_ITEMS + " TEXT, " + COLUMN_TOTAL + " REAL)");
        db.execSQL("INSERT INTO " + USER_TABLE + "(" + COLUMN_NAME + "," + COLUMN_ROLE + "," + COLUMN_USERNAME +  "," + COLUMN_EMAIL + "," + COLUMN_PASSWORD + ")" + " VALUES( 'Tom', 'Admin', 'tom', 'tom@sb.com','tom')");
        db.execSQL("INSERT INTO " + USER_TABLE + "(" + COLUMN_NAME + "," + COLUMN_ROLE + "," + COLUMN_USERNAME +  "," + COLUMN_EMAIL + "," + COLUMN_PASSWORD + ")" + " VALUES( 'Jane', 'Admin', 'jane', 'jane@sb.com','jane')");
        db.execSQL("INSERT INTO " + USER_TABLE + "(" + COLUMN_NAME + "," + COLUMN_ROLE + "," + COLUMN_USERNAME +  "," + COLUMN_EMAIL + "," + COLUMN_PASSWORD + ")" + " VALUES( 'Adam', 'User', 'adam', 'adam@gmail.com','adam')");
        db.execSQL("INSERT INTO " + USER_TABLE + "(" + COLUMN_NAME + "," + COLUMN_ROLE + "," + COLUMN_USERNAME +  "," + COLUMN_EMAIL + "," + COLUMN_PASSWORD + ")" + " VALUES( 'Jill', 'User', 'jill', 'jill@gmail.com','jill')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE + "(" + COLUMN_CAT_NAME + "," + COLUMN_CAT_DESC + ")" + " VALUES( 'Regulars', 'Your Everyday Cupcakes')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE + "(" + COLUMN_CAT_NAME + "," + COLUMN_CAT_DESC + ")" + " VALUES( 'Valentines', 'For that special someone')");
        db.execSQL("INSERT INTO " + CAKE_TABLE + "(" + COLUMN_CAKE_NAME + "," + COLUMN_CAKE_DESC + "," + COLUMN_IMG_NAME +  "," + COLUMN_PRICE + "," + COLUMN_QTY + "," + COLUMN_CAT_ID + ")" + " VALUES( 'Vanilla Cupcake', 'Sweet and simple, these classic vanilla cupcakes are perfect with a swirl of vanilla bean', 'vanilla', 1.00, 5, 1)");
        db.execSQL("INSERT INTO " + CAKE_TABLE + "(" + COLUMN_CAKE_NAME + "," + COLUMN_CAKE_DESC + "," + COLUMN_IMG_NAME +  "," + COLUMN_PRICE + "," + COLUMN_QTY + "," + COLUMN_CAT_ID + ")" + " VALUES( 'Chocolate Cupcake', 'Moist, rich, cupcakes with intense chocolate flavor in every bite', 'chocolate', 1.50, 10, 1)");
        db.execSQL("INSERT INTO " + CAKE_TABLE + "(" + COLUMN_CAKE_NAME + "," + COLUMN_CAKE_DESC + "," + COLUMN_IMG_NAME +  "," + COLUMN_PRICE + "," + COLUMN_QTY + "," + COLUMN_CAT_ID + ")" + " VALUES( 'Love Cupcake', 'Share the love!', 'classic_love', 1.50, 6, 2)");
        db.execSQL("INSERT INTO " + CAKE_TABLE + "(" + COLUMN_CAKE_NAME + "," + COLUMN_CAKE_DESC + "," + COLUMN_IMG_NAME +  "," + COLUMN_PRICE + "," + COLUMN_QTY + "," + COLUMN_CAT_ID + ")" + " VALUES( 'Hazelnut Love Cupcake', 'Unwrap the love, one hazelnut bite at a time!', 'hazel_love', 3.00, 6, 2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CAKE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CAKE_ORDER_TABLE);
        onCreate(db);
    }

    public boolean insertCake(Cake cake) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CAKE_NAME, cake.getCakeName());
        contentValues.put(COLUMN_CAKE_DESC, cake.getCakeDesc());
        contentValues.put(COLUMN_IMG_NAME, cake.getImgName());
        contentValues.put(COLUMN_CAT_ID, cake.getCatId());
        contentValues.put(COLUMN_QTY, cake.getQuantity());
        contentValues.put(COLUMN_PRICE, cake.getPrice());
        long insertToCake = sqLiteDatabase.insert(CAKE_TABLE, null, contentValues);


        if (insertToCake == -1) {
            return false;
        } else {
            Log.e(TAG, "insert data :" + insertToCake);
            return true;
        }

    }

    public List<Cake> getAllCakes() {
        List<Cake> returnList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + CAKE_TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String cakeName = cursor.getString(1);
                String cakeDesc = cursor.getString(2);
                String imgName = cursor.getString(3);
                double price = cursor.getDouble(4);
                int quantity = cursor.getInt(5);
                int catId = cursor.getInt(6);

                Cake cake = new Cake(id, cakeName, cakeDesc, imgName, price, quantity, catId);
                returnList.add(cake);
            } while (cursor.moveToNext());
        } else {
            Log.e(TAG, "data retrieval failed :");
        }
        cursor.close();
        sqLiteDatabase.close();
        return returnList;
    }

    public boolean deleteCake(Cake cake) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long deleteFromCake = sqLiteDatabase.delete(CAKE_TABLE, ID + " = " + cake.getId(), null);
        return deleteFromCake != -1;
    }

    public boolean updateCake(Cake cake) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CAKE_NAME, cake.getCakeName());
        contentValues.put(COLUMN_CAKE_DESC, cake.getCakeDesc());
        contentValues.put(COLUMN_IMG_NAME, cake.getImgName());
        contentValues.put(COLUMN_CAT_ID, cake.getCatId());
        contentValues.put(COLUMN_QTY, cake.getQuantity());
        contentValues.put(COLUMN_PRICE, cake.getPrice());
        long updateCake = sqLiteDatabase.update(CAKE_TABLE, contentValues, ID + " = " + cake.getId(), null);

        if (updateCake == -1) {
            return false;
        }

        Log.e(TAG, "update data :" + updateCake);
        return true;

    }

    public boolean insertCategory(Category category) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CAT_NAME, category.getCatName());
        contentValues.put(COLUMN_CAT_DESC, category.getCatDesc());
        long insertToCat = sqLiteDatabase.insert(CATEGORY_TABLE, null, contentValues);


        if (insertToCat == -1) {
            return false;
        } else {
            Log.e(TAG, "insert data :" + insertToCat);
            return true;
        }

    }

    public List<Category> getAllCategories() {
        List<Category> returnList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + CATEGORY_TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String catName = cursor.getString(1);
                String catDesc = cursor.getString(2);

                Category category = new Category(id, catName, catDesc);
                returnList.add(category);
            } while (cursor.moveToNext());
        } else {
            Log.e(TAG, "data retrieval failed :");
        }
        cursor.close();
        sqLiteDatabase.close();
        return returnList;
    }

    public boolean deleteCategory(Category category) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long deleteFromCat = sqLiteDatabase.delete(CATEGORY_TABLE, ID + " = " + category.getId(), null);

        return deleteFromCat != -1;
    }

    public boolean updateCategory(Category category) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CAT_NAME, category.getCatName());
        contentValues.put(COLUMN_CAT_DESC, category.getCatDesc());
        long updateCat = sqLiteDatabase.update(CATEGORY_TABLE, contentValues, ID + " = " + category.getId(), null);

        if (updateCat == -1) {
            return false;
        } else {
            Log.e(TAG, "update data :" + updateCat);
            return true;
        }
    }

    public boolean insertUser(User user){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, user.getName());
        contentValues.put(COLUMN_USERNAME, user.getUsername());
        contentValues.put(COLUMN_ROLE, user.getRole());
        contentValues.put(COLUMN_EMAIL, user.getEmail());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        long insertToUser = sqLiteDatabase.insert(USER_TABLE, null, contentValues);


        if (insertToUser == -1) {
            return false;
        } else {
            Log.e(TAG, "insert data :" + insertToUser);
            return true;
        }

    }

    public List<User> getAllUsers() {
        List<User> returnList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + USER_TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String userName = cursor.getString(1);
                String userRole = cursor.getString(2);
                String userUserName = cursor.getString(3);
                String userEmail = cursor.getString(4);
                String userPassword = cursor.getString(5);

                User user = new User(id, userName, userRole, userUserName, userEmail, userPassword);
                returnList.add(user);
            } while (cursor.moveToNext());
        } else {
            Log.e(TAG, "data retrieval failed :");
        }
        cursor.close();
        sqLiteDatabase.close();
        return returnList;
    }

    public boolean deleteUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long deleteFromUser = sqLiteDatabase.delete(USER_TABLE, ID + " = " + user.getId(), null);

        return deleteFromUser != -1;
    }

    public boolean updateUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, user.getName());
        contentValues.put(COLUMN_USERNAME, user.getUsername());
        contentValues.put(COLUMN_ROLE, user.getRole());
        contentValues.put(COLUMN_EMAIL, user.getEmail());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        long updateUser = sqLiteDatabase.update(USER_TABLE, contentValues, ID + " = " + user.getId(), null);

        if (updateUser == -1) {
            return false;
        } else {
            Log.e(TAG, "update data :" + updateUser);
            return true;
        }
    }

    public boolean insertOrder(Order order){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_ID, order.getUserId());
        contentValues.put(COLUMN_ADDRESS, order.getAddress());
        contentValues.put(COLUMN_ITEMS, order.getItems());
        contentValues.put(COLUMN_TOTAL, order.getTotal());
        long insertToOrder = sqLiteDatabase.insert(CAKE_ORDER_TABLE, null, contentValues);


        if (insertToOrder == -1) {
            return false;
        } else {
            Log.e(TAG, "insert data :" + insertToOrder);
            return true;
        }

    }

    public List<Order> getAllOrders() {
        List<Order> returnList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + CAKE_ORDER_TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int userId = cursor.getInt(1);
                String address = cursor.getString(2);
                String items = cursor.getString(3);
                double total = cursor.getDouble(4);

                Order order = new Order(id, userId, address, items, total);
                returnList.add(order);
            } while (cursor.moveToNext());
        } else {
            Log.e(TAG, "data retrieval failed :");
        }
        cursor.close();
        sqLiteDatabase.close();
        return returnList;
    }

    public boolean deleteOrder(Order order) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long deleteFromUser = sqLiteDatabase.delete(CAKE_ORDER_TABLE, ID + " = " + order.getId(), null);

        return deleteFromUser != -1;
    }

    public boolean updateOrder(Order order) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_ID, order.getUserId());
        contentValues.put(COLUMN_ADDRESS, order.getAddress());
        contentValues.put(COLUMN_ITEMS, order.getItems());
        contentValues.put(COLUMN_TOTAL, order.getTotal());
        long updateOrder = sqLiteDatabase.update(CAKE_ORDER_TABLE, contentValues, ID + " = " + order.getId(), null);

        if (updateOrder == -1) {
            return false;
        } else {
            Log.e(TAG, "update data :" + updateOrder);
            return true;
        }
    }


}


