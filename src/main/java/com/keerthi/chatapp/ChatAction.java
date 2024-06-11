package com.keerthi.chatapp;

import java.util.List;

import com.keerthi.datalayer.DatabaseManager;
import com.opensymphony.xwork2.ActionSupport;

public class ChatAction extends ActionSupport{
      
	private int id;
	private int sender_id;
	private int receiver_id;
	private String chat;
	private List<Chat> chatHistory;
	
	public String execute() {
		boolean isAddChats=DatabaseManager.getInstance().storeChats(sender_id,receiver_id,chat);
		if(isAddChats) {
			System.out.println("message send successfully");
			return SUCCESS;
		}
			
		else {
			System.out.println("error");
			System.out.println("Not send message");
			return ERROR;
		}
			
	}
	
	
	public String fetchChatHistory() {
	    System.out.println("Fetching chat history for sender_id: " + sender_id + " and receiver_id: " + receiver_id);
	    chatHistory = DatabaseManager.getInstance().getChatHistory(sender_id, receiver_id);
	    System.out.println("Chat history size: " + (chatHistory != null ? chatHistory.size() : "null"));
	    return SUCCESS;
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSender_id() {
		return sender_id;
	}
	public void setSender_id(int sender_id) {
		this.sender_id = sender_id;
	}
	public int getReceiver_id() {
		return receiver_id;
	}
	public void setReceiver_id(int receiver_id) {
		this.receiver_id = receiver_id;
	}
	public String getChat() {
		return chat;
	}
	public void setChat(String chat) {
		this.chat = chat;
	}

	public List<Chat> getChatHistory() {
		return chatHistory;
	}

	public void setChatHistory(List<Chat> chatHistory) {
		this.chatHistory = chatHistory;
	}
}
