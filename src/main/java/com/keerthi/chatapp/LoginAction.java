package com.keerthi.chatapp;

import java.util.List;
import com.opensymphony.xwork2.ActionSupport;
import com.keerthi.datalayer.DatabaseManager;

public class LoginAction extends ActionSupport {

	private String userName;
	private String password;
	static SignUpAction signupAction;
	static List<SignUpAction> getLoginUserResponse;
	static private int LoginUserId;
	static private List<SignUpAction> friendsList;
	private String error;
	private static String filePath;

	public String execute() {
		System.out.println("Hello12345");
		boolean isValid = DatabaseManager.getInstance().validateUser(userName, password);
		System.out.println("Hello");
		setFilePath(DatabaseManager.getInstance().getImagePath(userName, password));
		if (isValid) {
			setSignupAction(DatabaseManager.getInstance().getLoginUser(userName, password));
			setLoginUserResponse(DatabaseManager.getInstance().getResponseUser(signupAction.getId()));
			setLoginUserId(signupAction.getId());
			setFriendsList(DatabaseManager.getInstance().getFriendsList(signupAction.getId()));
			System.out.println("login success");
			return SUCCESS;
		} else {
			System.out.println("login failed");
			error="Invalid User Name or Password";
			return ERROR;
		}

	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static SignUpAction getSignupAction() {
		return signupAction;
	}

	public static void setSignupAction(SignUpAction signupAction) {
		LoginAction.signupAction = signupAction;
	}

	public static List<SignUpAction> getGetLoginUserResponse() {
		return getLoginUserResponse;
	}

	public static void setLoginUserResponse(List<SignUpAction> getLoginUserResponse) {
		LoginAction.getLoginUserResponse = getLoginUserResponse;
	}

	public static int getLoginUserId() {
		return LoginUserId;
	}

	public static void setLoginUserId(int loginUserId) {
		LoginUserId = loginUserId;
	}

	public static List<SignUpAction> getFriendsList() {
		return friendsList;
	}

	public static void setFriendsList(List<SignUpAction> friendsList) {
		LoginAction.friendsList = friendsList;
	}

	public static String getFilePath() {
		return filePath;
	}

	public static void setFilePath(String filePath) {
		LoginAction.filePath = filePath;
	}
}

