package com.keerthi.chatapp;

import com.keerthi.datalayer.DatabaseManager;
import com.opensymphony.xwork2.ActionSupport;

public class RequestAction extends ActionSupport{
   
	private int reqUserId;
	private int resUserId;
	private int isresponse;
	
	
	public String execute() throws Exception {
		boolean addRequest=DatabaseManager.getInstance().addRequestDetails(reqUserId,resUserId,isresponse);
		System.out.println(isresponse+"*****");
		if(addRequest) {
			System.out.println("response added successfully");
			 return SUCCESS;
		}
		else {
			System.out.println("response failed");
			return ERROR;
		}
	    
	}
	
	
	public String updateRequest() {
		System.out.println(reqUserId+" "+resUserId+" "+isresponse);
		boolean updateRequest=DatabaseManager.getInstance().updateRequestDetails(reqUserId,resUserId,isresponse);
		if(updateRequest) {
			System.out.println("response updated successfully");
			 return SUCCESS;
		}
		else {
			System.out.println("response updated failed");
			return ERROR;
		}
	    
	}
	
	
	public int getReqUserId() {
		return reqUserId;
	}
	public void setReqUserId(int reqUserId) {
		this.reqUserId = reqUserId;
	}
	public int getResUserId() {
		return resUserId;
	}
	public void setResUserId(int resUserId) {
		this.resUserId = resUserId;
	}
	public int isIsresponse() {
		return isresponse;
	}
	public void setIsresponse(int isresponse) {
		this.isresponse = isresponse;
	}
	
	
	
}
