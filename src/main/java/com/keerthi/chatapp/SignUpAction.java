package com.keerthi.chatapp;


import com.opensymphony.xwork2.ActionSupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.keerthi.datalayer.DatabaseManager;

public class SignUpAction extends ActionSupport {

	private int id;
	private String name;
	private String username;
	private String email;
	private String password;
	private String repassword;
	private String error;
	private File file;
    private String fileFileName;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }
	

	
	public SignUpAction() {
	}

	public SignUpAction(int id, String name, String username, String email, String repassword) {
		this.setId(id);
		this.name = name;
		this.username = username;
		this.email = email;
		this.repassword = repassword;
	}

	public String execute() {
		System.out.println("hi111");
		if (password.equals(repassword)) {
			boolean isValidUser = DatabaseManager.getInstance().addUser(name, username, email, repassword);
			if (isValidUser) {
				System.out.println("signup-successfull");
				return SUCCESS;
			} else {
				System.out.println("signup failed");
				error="Password Mismatch";
				return ERROR;
			}
		} else {
			System.out.println("signup failed");
			error="Password Mismatch";
			return ERROR;
		}

	}

	
	public String updateImage() {
	    try {
	        HttpServletRequest request = ServletActionContext.getRequest();
	        String filePath = request.getSession().getServletContext().getRealPath("");

	        System.out.println(filePath);
	        File newDirectory = new File("C:\\Users\\Windows\\eclipse-workspace\\ChatApplication\\src\\main\\webapp\\uploads\\" + id);

	        if (!newDirectory.exists()) {
	            newDirectory.mkdirs();
	        }

	        File destinationFile = new File(newDirectory, fileFileName);

	        try (FileInputStream fis = new FileInputStream(file);
	             FileOutputStream fos = new FileOutputStream(destinationFile)) {

	            byte[] buffer = new byte[1024];
	            int length;
	            while ((length = fis.read(buffer)) > 0) {
	                fos.write(buffer, 0, length);
	            }

	            System.out.println("File transferred successfully to: " + destinationFile.getAbsolutePath());
	            System.out.println(destinationFile.getCanonicalPath());
	            System.out.println(destinationFile.getPath());
	            System.out.println(fileFileName);
                DatabaseManager.getInstance().updateImage(id, "./uploads/"+id+"/"+fileFileName);
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	            return ERROR;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ERROR;
	    }

	    return SUCCESS;
	}
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}