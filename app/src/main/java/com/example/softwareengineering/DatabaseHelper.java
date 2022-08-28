package com.example.softwareengineering;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.session.PlaybackState;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

import java.lang.String;


public class DatabaseHelper extends SQLiteOpenHelper {


	public DatabaseHelper(@Nullable Context context) {
		super(context, "inventory.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createProductsTableStatement = "CREATE TABLE products_table (prod_id INTEGER PRIMARY KEY AUTOINCREMENT, barcode TEXT, product_name TEXT, price TEXT, stock INTEGER)";
		String createUserTableStatement = "CREATE TABLE user_login (user_id INTEGER PRIMARY KEY, username TEXT, password TEXT, sq_1 TEXT, sa_1 TEXT, sq_2 TEXT, sa_2 TEXT)";
		String createCurrentOrderTableStatement = "CREATE TABLE current_order (ord_id INTEGER PRIMARY KEY, barcode TEXT, product_name TEXT, price TEXT, amount INTEGER)";
		String createHistoryTableStatement = "CREATE TABLE transac_hist (item_id INTEGER PRIMARY KEY AUTOINCREMENT, order_id INTEGER, date TEXT, time TEXT, product_name TEXT, amount INTEGER, price_x_amt TEXT, total TEXT)";
		String createOrderIDTableStatement = "CREATE TABLE transac_order_id (id INTEGER PRIMARY KEY AUTOINCREMENT, temp_id TEXT)";
		String createTempTableStatement = "CREATE TABLE temp_update (prod_id INTEGER PRIMARY KEY, barcode TEXT, product_name TEXT, price TEXT, stock INTEGER)";
		String createNoBarcodeStatement = "CREATE TABLE nobc (prod_id INTEGER PRIMARY KEY, barcode TEXT, product_name TEXT, price TEXT, stock INTEGER)";


		db.execSQL(createProductsTableStatement);
		db.execSQL(createUserTableStatement);
		db.execSQL(createCurrentOrderTableStatement);
		db.execSQL(createHistoryTableStatement);
		db.execSQL(createOrderIDTableStatement);
		db.execSQL(createTempTableStatement);
		db.execSQL(createNoBarcodeStatement);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


	}


	public boolean ifInventoryProductExists(String barcode1) {
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT * FROM products_table WHERE barcode = '" + barcode1 + "'";
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			return true;
		}
		return false;
	}


	public boolean addInventoryProduct(ProductModel productModel) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put("product_name", productModel.getProductName());
		cv.put("barcode", productModel.getBarcode());
		cv.put("price", productModel.getPrice());
		cv.put("stock", productModel.getQuantity());

		long i = db.insert("products_table", null, cv);
		if (i == -1) return false;

		return true;

	}

	public void editInventoryProduct() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM temp_update LIMIT 1", null);

		cursor.moveToFirst();
		int id = cursor.getInt(0);
		String barcode = cursor.getString(1);
		String newName = cursor.getString(2);
		String newPrice = cursor.getString(3);
		int newStock = cursor.getInt(4);

		db.execSQL("UPDATE products_table SET product_name = '" + newName + "', price = '" + newPrice + "', stock = " + newStock + " WHERE prod_id = " + id + ";");
		db.execSQL("DELETE FROM temp_update");
		cursor.close();
		db.close();
	}


	public void removeInventoryProduct(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		db.execSQL("DELETE FROM products_table WHERE prod_id = " + id);
	}


	public List<ProductModel> getEveryOne() {

		List<ProductModel> returnList = new ArrayList<>();
		String queryString = "SELECT * FROM products_table";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(queryString, null);
		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(0);
				String productName = cursor.getString(1);
				String barcode = cursor.getString(2);
				String price = cursor.getString(3);
				int qty = cursor.getInt(4);

				ProductModel newProduct = new ProductModel(id, productName, barcode, price, qty);
				returnList.add(newProduct);
			} while (cursor.moveToNext());
		} else {

		}
		cursor.close();
		db.close();
		return returnList;
	}


	public boolean ifTempAlreadyExists() {
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT * FROM temp_update LIMIT 1";
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			cursor.close();
			return true;
		}
		cursor.close();
		return false;
	}


	public void newTempUpdate(ProductModel productModel) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		if (ifTempAlreadyExists()) {
			db.execSQL("UPDATE temp_update " +
					"SET prod_id = " + productModel.getId() + "," +
					" product_name = '" + productModel.getProductName() + "'," +
					" price = '" + productModel.getPrice() + "'," +
					" barcode = '" + productModel.getBarcode() + "'," +
					" stock = " + productModel.getQuantity());
		} else {

			cv.put("prod_id", productModel.getId());
			cv.put("product_name", productModel.getProductName());
			cv.put("price", productModel.getPrice());
			cv.put("barcode", productModel.getBarcode());
			cv.put("stock", productModel.getQuantity());

		}


		db.insert("temp_update", null, cv);
		db.close();

	}


	public ProductModel getTempObject() {
		String queryString = "SELECT * FROM temp_update LIMIT 1";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(queryString, null);

		int id = 0;
		String barcode = "";
		String name = "";
		String price = "";
		int qty = 0;

		if (cursor.moveToFirst()) {
			id = cursor.getInt(0);
			barcode = cursor.getString(1);
			name = cursor.getString(2);
			price = cursor.getString(3);
			qty = cursor.getInt(4);
		}

		ProductModel prod = new ProductModel(id, barcode, name, price, qty);
		cursor.close();
		db.close();
		return prod;
	}

	public List<ProductModel> searchInventoryProducts(String term) {
		List<ProductModel> returnList = new ArrayList<>();
		String queryString = "SELECT * FROM products_table WHERE product_name LIKE '%" + term + "%'";
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(queryString, null);

		if (cursor.moveToFirst()) {
			do {
				int idNew = cursor.getInt(0);
				String barcodeNew = cursor.getString(1);
				String nameNew = cursor.getString(2);
				String priceNew = cursor.getString(3);
				int amountNew = cursor.getInt(4);

				ProductModel newProduct = new ProductModel(idNew, barcodeNew, nameNew, priceNew, amountNew);
				returnList.add(newProduct);

			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return returnList;
	}


	//creating order product section
	public boolean ifOrderBarcodeExists(String barcode) {
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT * FROM current_order WHERE barcode = '" + barcode + "'";
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			cursor.close();
			return true;
		}
		cursor.close();
		return false;
	}


	public boolean addOrderProduct(ProductModel productModel) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put("ord_id", productModel.getId());
		cv.put("barcode", productModel.getBarcode());
		cv.put("product_name", productModel.getProductName());
		cv.put("price", productModel.getPrice());
		cv.put("amount", productModel.getQuantity());

		long i = db.insert("current_order", null, cv);


		db.close();
		if (i == -1) return false;

		return true;
	}

	public ArrayList<ProductModel> viewAllOrderProducts() {
		ArrayList<ProductModel> returnList = new ArrayList<>();
		String queryString = "SELECT * FROM current_order";
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(queryString, null);

		if (cursor.moveToFirst()) {
			do {
				int idNew = cursor.getInt(0);
				String barcodeNew = cursor.getString(1);
				String nameNew = cursor.getString(2);
				String priceNew = cursor.getString(3);
				int amountNew = cursor.getInt(4);

				ProductModel newProduct = new ProductModel(idNew, barcodeNew, nameNew, priceNew, amountNew);
				returnList.add(newProduct);

			} while (cursor.moveToNext());
		} else {

		}

		cursor.close();
		db.close();
		return returnList;
	}

	public ProductModel searchBarcode(String term) {
		String queryString = "SELECT * FROM products_table WHERE barcode = '" + term + "' LIMIT 1";
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(queryString, null);

		cursor.moveToFirst();

		int idNew = cursor.getInt(0);
		String barcodeNew = cursor.getString(1);
		String nameNew = cursor.getString(2);
		String priceNew = cursor.getString(3);
		int amountNew = cursor.getInt(4);

		ProductModel newProduct = new ProductModel(idNew, barcodeNew, nameNew, priceNew, amountNew);

		cursor.close();
		db.close();
		return newProduct;
	}



	public boolean ifOrderNameExists(String name) {
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT * FROM current_order WHERE product_name = '" + name + "'";
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			cursor.close();
			db.close();
			return true;
		}
		cursor.close();
		db.close();
		return false;
	}

	public void clearCurrentOrder() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DELETE FROM current_order;");
		db.close();
	}

	public void removeOrderProduct(ProductModel productModel) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DELETE FROM current_order WHERE ord_id = " + productModel.getId());
		db.close();
	}

	public boolean nobcCheck() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM nobc", null);
		if (cursor.moveToFirst()) {
			cursor.close();
			db.close();
			return true;
		}
		cursor.close();
		db.close();
		return false;
	}

	public void addNoBc(ProductModel pm) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();


		cv.put("prod_id", pm.getId());
		cv.put("barcode", pm.getBarcode());
		cv.put("product_name", pm.getProductName());
		cv.put("price", pm.getPrice());
		cv.put("stock", pm.getQuantity());

		db.insert("nobc", null, cv);
	}

	public ProductModel getNoBC() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM nobc", null);
		ProductModel returnPM = new ProductModel();
		if (cursor.moveToFirst()) {
			returnPM = new ProductModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4));
			db.execSQL("DELETE FROM nobc");

		}
		cursor.close();
		db.close();
		return returnPM;
	}


	public boolean ifUserAlreadyExists() {
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT * FROM user_login WHERE user_id = 1";
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			cursor.close();
			return true;
		}
		cursor.close();
		return false;
	}

	public boolean setUsernamePW(String user, String pw) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		long i = 0;

		if (ifUserAlreadyExists()) {
			db.execSQL("UPDATE user_login " +
					"SET username = '" + user +
					"', password = '" + pw +
					"' WHERE user_id = 1");
		} else {
			cv.put("username", user);
			cv.put("password", pw);

			i = db.insert("user_login", null, cv);
		}


		db.close();
		if (i == -1) return false;

		return true;
	}


	public boolean setQnA(String q1, String a1, String q2, String a2) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		long i = 0;

		if (ifUserAlreadyExists()) {
			db.execSQL("UPDATE user_login " +
					"SET sq_1 = '" + q1 +
					"', sa_1 = '" + a1 +
					"', sq_2 = '" + q2 +
					"', sa_2 = '" + a2 +
					"' WHERE user_id = 1");
		} else {

			cv.put("sq_1", q1);
			cv.put("sa_1", a1);
			cv.put("sq_2", q2);
			cv.put("sa_2", a2);

			i = db.insert("user_login", null, cv);
		}

		db.close();
		if (i == -1) return false;
		return true;
	}


	public boolean checkLogin(String username, String password) {
		String queryString = "SELECT * FROM user_login WHERE user_id = 1";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(queryString, null);

		if (cursor.moveToFirst()) {

			String username1 = cursor.getString(1);
			String password1 = cursor.getString(2);

			cursor.close();
			db.close();

			if (username.equals(username1) && password.equals(password1)) return true;

		}

		cursor.close();
		db.close();
		return false;
	}

	public UserLogin getAllLoginInfo() {
		String queryString = "SELECT * FROM user_login WHERE user_id = 1";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(queryString, null);
		UserLogin user = new UserLogin();
		if (cursor.moveToFirst()) {

			String username = cursor.getString(1);
			String password = cursor.getString(2);
			String sq1 = cursor.getString(3);
			String sa1 = cursor.getString(4);
			String sq2 = cursor.getString(5);
			String sa2 = cursor.getString(6);
			user = new UserLogin(1, username, password, sq1, sa1, sq2, sa2);
		} else {

		}

		cursor.close();
		db.close();
		return user;
	}

	public boolean newUserLogin(UserLogin userlogin) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		long i = 0;

		if (ifUserAlreadyExists()) {
			db.execSQL("UPDATE user_login " +
					"SET username = '" + userlogin.getUsername() +
					"', password = '" + userlogin.getPassword() +
					"', sq_1 = '" + userlogin.getSq1() +
					"', sa_1 = '" + userlogin.getSa1() +
					"', sq_2 = '" + userlogin.getSq2() +
					"', sa_2 = '" + userlogin.getSa2() +
					"' WHERE user_id = 1");
		} else {
			cv.put("user_id", 1);
			cv.put("username", userlogin.getUsername());
			cv.put("password", userlogin.getPassword());
			cv.put("sq_1", userlogin.getSq1());
			cv.put("sa_1", userlogin.getSa1());
			cv.put("sq_2", userlogin.getSq2());
			cv.put("sa_2", userlogin.getSa2());
			i = db.insert("user_login", null, cv);
		}
		db.close();
		if (i == -1) return false;

		return true;
	}

	private long newTransacOrderID() {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put("temp_id", "string");
		long i = db.insert("transac_order_id", null, cv);

		if (i == -1) {

			db.close();
			return 0;
		} else {
			Cursor cursor = db.rawQuery("SELECT id FROM transac_order_id ORDER BY id DESC LIMIT 1", null);
			cursor.moveToFirst();
			long returnstr = cursor.getInt(0);
			cursor.close();

			return returnstr;
		}
	}

	public long getLastOrderID() {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery("SELECT id FROM transac_order_id ORDER BY id DESC LIMIT 1", null);
		cursor.moveToFirst();
		long returnstr = cursor.getInt(0);
		cursor.close();
		db.close();
		return returnstr;

	}

	private void completeOrderUpdateStock() {

		String queryString = "SELECT * FROM current_order";
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.rawQuery(queryString, null);
		Cursor idk = db.rawQuery("SELECT * FROM products_table", null);
		if (cursor.moveToFirst()) {
			do {
				long orderid = cursor.getInt(0);
				String orderName = cursor.getString(2);
				String orderBC = cursor.getString(1);
				int orderAmount = cursor.getInt(4);
				idk = db.rawQuery("SELECT * FROM products_table WHERE prod_id = " + orderid + "", null);
				idk.moveToFirst();
				int newAmt = idk.getInt(4) - orderAmount;
				if (newAmt < 0) newAmt = 0;
				db.execSQL("UPDATE products_table SET stock = " + newAmt + " WHERE prod_id = " + orderid);

			} while (cursor.moveToNext());
		} else {


		}

		db.execSQL("DELETE FROM current_order;");
		cursor.close();
		idk.close();

	}
	public String orderTotal() {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT price FROM current_order", null);
		double total = 0;
		if (cursor.moveToFirst()) {

			do {
				total = total + Double.parseDouble(cursor.getString(0));
			} while (cursor.moveToNext());
		}

		return "" + total;

	}
	public boolean addTransacRecord(String date, String time) {
		SQLiteDatabase db = this.getWritableDatabase();
		long orderID = newTransacOrderID();

		Cursor cursor = db.rawQuery("SELECT * FROM current_order", null);
		if (cursor.moveToFirst()) {
			String save = "";
			double totalVar = 0;
			do {
				save = cursor.getString(3);
				totalVar += Double.parseDouble(save);

			} while (cursor.moveToNext());


			cursor.moveToFirst();
			do {
				ContentValues cv = new ContentValues();
				cv.put("order_id", orderID);
				cv.put("date", date);
				cv.put("time", time);
				cv.put("product_name", cursor.getString(2));
				cv.put("amount", cursor.getInt(4));

				save = cursor.getString(3);
				double pxa = Double.parseDouble(save);
				cv.put("price_x_amt", pxa);
				cv.put("total", totalVar);

				long i = db.insert("transac_hist", null, cv);
			} while (cursor.moveToNext());


			completeOrderUpdateStock();
			cursor.close();
			//db.close();
			return true;
		} else {
			cursor.close();
			//db.close();
			return false;
		}

	}
	public List<String> viewSingleOrder(long orderID){
		List<String> returnList = new ArrayList<>();
		String queryString = "SELECT * FROM transac_hist WHERE order_id = " + orderID;
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(queryString, null);

		if(cursor.moveToFirst()){
			do{

				Long orderId = new Long(cursor.getInt(1));
				String date = cursor.getString(2);
				String time = cursor.getString(3);
				String productName = cursor.getString(4);
				int amount = cursor.getInt(5);
				String priceXamount = cursor.getString(6);
				String total = cursor.getString(7);


				//OrderDetails details = new OrderDetails(orderId, date, time, productName, amount, priceXamount, total);
				String details = String.format(" %-40s%-35d%-5s", productName, amount, priceXamount);
				returnList.add(details);

			}while(cursor.moveToNext());
		}


		cursor.close();
		db.close();
		return returnList;
	}
	public OrderDetails getOD(long id){
		String queryString = "SELECT * FROM transac_hist WHERE order_id = " + id;
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(queryString, null);

		cursor.moveToFirst();


				Long orderId = new Long(cursor.getInt(1));
				String date = cursor.getString(2);
				String time = cursor.getString(3);
				String productName = cursor.getString(4);
				int amount = cursor.getInt(5);
				String priceXamount = cursor.getString(6);
				String total = cursor.getString(7);


				OrderDetails details = new OrderDetails(orderId, date, time, productName, amount, priceXamount, total);

				return details;


	}
	public List<Transactions> viewTransacHistory(){
		List<Transactions> returnList = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		String queryString = "SELECT DISTINCT order_id, date, time FROM transac_hist ORDER BY order_id DESC";


		Cursor cursor = db.rawQuery(queryString, null);

		if(cursor.moveToFirst()){
			do{
				Transactions str = new Transactions(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
				//String str = String.format(" %-20d%-20s%-5s", cursor.getInt(0), cursor.getString(1), cursor.getString(2));
				returnList.add(str);
			}while(cursor.moveToNext());
		}







		cursor.close();
		db.close();
		return returnList;
	}


}

