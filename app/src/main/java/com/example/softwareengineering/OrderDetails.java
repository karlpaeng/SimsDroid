package com.example.softwareengineering;

public class OrderDetails {

	public long orderId;
	public String date;
	public String time;
	private String productName;
	private int amount;
	private String priceXamount;
	public String total;

	public OrderDetails(){}



	public OrderDetails(long orderId, String date, String time, String productName, int amount, String priceXamount, String total){
		this.orderId = orderId;
		this.date = date;
		this.time = time;
		this.productName = productName;
		this.amount = amount;
		this.priceXamount = priceXamount;
		this.total = total;
	}

	@Override
	public String toString() {
		return "OrderDetails{" +
				"orderId=" + orderId +
				", date='" + date + '\'' +
				", time='" + time + '\'' +
				", productName='" + productName + '\'' +
				", amount=" + amount +
				", priceXamount='" + priceXamount + '\'' +
				", total='" + total + '\'' +
				'}';
	}

	//setters
	public void setOrderId(long orderId){
		this.orderId = orderId;
	}
	public void setDate(String date){
		this.date = date;
	}
	public void setTime(String time){
		this.time = time;
	}
	public void setProductName(String productName){
		this.productName = productName;
	}
	public void setAmount(int amount){
		this.amount = amount;
	}
	public void setPriceXamount(String priceXamount){
		this.priceXamount = priceXamount;
	}
	public void setTotal(String total){
		this.total = total;
	}



	//getters

	public long getOrderId(){
		return orderId;
	}
	public String getDate(){
		return date;
	}
	public String getTime(){
		return time;
	}
	public String getProductName(){
		return productName;
	}
	public int getAmount(){
		return amount;
	}
	public String getPriceXamount(){
		return priceXamount;
	}
	public String getTotal(){
		return total;
	}


}