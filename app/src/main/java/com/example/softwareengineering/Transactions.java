package com.example.softwareengineering;

public class Transactions {

	private long orderId;
	private String date;
	private String time;


	public Transactions(){}

	public Transactions(long orderId, String date, String time){
		this.orderId = orderId;
		this.date = date;
		this.time = time;

	}

	@Override
	public String toString(){
//		return "Transaction { " +
//				"order ID = " + orderId + ", " +
//				"date = " + date + ", " +
//				"time = " + time + " } ";
		return "" + this.orderId + "     |     " + this.date + "     |     " + this.time;
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

}