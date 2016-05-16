package com.bank.vo;

public class Deal {
	private String dealNum;
	private String kind;
	private String content;
	private int amount;
	private int balance;
	private String dealdate;
	private String faccountNum;
	
	public String getDealNum() {
		return dealNum;
	}
	public void setDealNum(String dealNum) {
		this.dealNum = dealNum;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getDealdate() {
		return dealdate;
	}
	public void setDealdate(String dealdate) {
		this.dealdate = dealdate;
	}
	public String getFaccountNum() {
		return faccountNum;
	}
	public void setFaccountNum(String faccountNum) {
		this.faccountNum = faccountNum;
	}
		
}
