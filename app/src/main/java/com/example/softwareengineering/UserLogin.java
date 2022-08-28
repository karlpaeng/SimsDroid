package com.example.softwareengineering;

public class UserLogin {
	int userID;
	String username;
	String password;
	String sq1;
	String sa1;
	String sq2;
	String sa2;

	public UserLogin(){}

	public UserLogin(int userID, String username, String password, String sq1, String sa1, String sq2, String sa2){
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.sq1 = sq1;
		this.sa1 = sa1;
		this.sq2 = sq2;
		this.sa2 = sa2;
	}

	@Override
	public String toString() {
		return "UserLogin{" +
				"userID=" + userID +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", sq1='" + sq1 + '\'' +
				", sa1='" + sa1 + '\'' +
				", sq2='" + sq2 + '\'' +
				", sa2='" + sa2 + '\'' +
				'}';
	}

	//setters
	public void setUserID(int userID){
		this.userID = userID;
	}
	public void setUsername(String username){
		this.username = username;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public void setSq1(String sq1){
		this.sq1 = sq1;
	}
	public void setSa1(String sa1){
		this.sa1 = sa1;
	}
	public void setSq2(String sq2){
		this.sq2 = sq2;
	}
	public void setSa2(String sa2){
		this.sa2 = sa2;
	}
	//getters



	public int getUserID(){
		return userID;
	}
	public String getUsername(){
		return username;
	}
	public String getPassword(){
		return password;
	}
	public String getSq1(){
		return sq1;
	}
	public String getSa1(){
		return sa1;
	}
	public String getSq2(){
		return sq2;
	}
	public String getSa2(){
		return sa2;
	}
}











