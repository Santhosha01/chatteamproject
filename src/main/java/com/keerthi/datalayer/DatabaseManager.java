package com.keerthi.datalayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.keerthi.chatapp.Chat;
import com.keerthi.chatapp.SignUpAction;
import com.keerthi.chatapp.UserWithResponse;
import com.keerthi.chatapp.headerAction;

public class DatabaseManager {
	
	
		
	public static String url="jdbc:mysql://localhost:3306/chatapp?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
	public static String usernamee="root";
	public static String passwordd="root";
	static
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	private static DatabaseManager databaseManager;
	
	
	public static DatabaseManager getInstance() {
        if(databaseManager==null)
        	databaseManager=new DatabaseManager();
        
        return databaseManager;
    }
	
	public boolean validateUser(String userName,String password)  {
		String userExistQuery="select * from signup where username=? AND password=?";
		
		try(Connection con=DriverManager.getConnection(url, usernamee, passwordd)) {
		    PreparedStatement ps=con.prepareStatement(userExistQuery);
		    ps.setString(1,userName);
		    ps.setString(2, password);
		    ResultSet rs=ps.executeQuery();
		    return rs.next();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	public boolean addUser(String name, String username, String email, String password)  {
		
		if(userExists(username,password))
			return false;
		
		String insertUser="insert into signup (name,username,email,password) values(?,?,?,?)";
		
		try(Connection con=DriverManager.getConnection(url,usernamee,passwordd)) {
			
			PreparedStatement stmt=con.prepareStatement(insertUser);
			stmt.setString(1, name);
            stmt.setString(2, username);
            stmt.setString(3, email);
            stmt.setString(4, password);
            int row=stmt.executeUpdate();
            return row>0;
			
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	private static boolean userExists(String username, String password) {
        String sql = "SELECT COUNT(*) FROM signup WHERE Username = ? OR Email = ?";
        try (Connection conn = DriverManager.getConnection(url, usernamee, passwordd);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 
	}

	public List<UserWithResponse> searchFriends(String search, int loginUserId) {
	    List<UserWithResponse> userList = new ArrayList<>();
	    System.out.println("name1" + search);
	    String searchQuery = "SELECT s.id, s.name, s.username, s.email, s.password, " +
                "CASE WHEN r.isresponse IS NULL THEN 0 ELSE r.isresponse END AS isresponse " +
                "FROM signup s " +
                "LEFT JOIN reqres r ON (s.id = r.reqUserId OR s.id = r.resUserId) " +
                "AND (r.reqUserId = ? OR r.resUserId = ?) " +
                "WHERE s.username LIKE ?";
	    try (Connection con = DriverManager.getConnection(url, usernamee, passwordd)) {
	        System.out.println("name22");
	        PreparedStatement stmt = con.prepareStatement(searchQuery);
	        stmt.setInt(1, loginUserId);
	        stmt.setInt(2, loginUserId);
	        stmt.setString(3, "%" + search + "%");
	        ResultSet resultSet = stmt.executeQuery();
	        while (resultSet.next()) {
	            UserWithResponse user = new UserWithResponse(
	                resultSet.getInt("id"),
	                resultSet.getString("name"),
	                resultSet.getString("username"),
	                resultSet.getString("email"),
	                resultSet.getString("password"),
	                resultSet.getInt("isresponse")
	            );
	            userList.add(user);
	            System.out.println(resultSet.getString("username") + "namesss");
	        }
	        System.out.println("name333");
	        for (UserWithResponse x : userList) {
	            System.out.print(x.getEmail() + "isresponse"+x.getIsResponse()+"iiiiii");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return userList;
	}


	public SignUpAction getLoginUser(String userName, String password) {
		SignUpAction signup;
		String userExistQuery="select * from signup where username=? AND password=?";
	
	try(Connection con=DriverManager.getConnection(url, usernamee, passwordd)) {
	    PreparedStatement ps=con.prepareStatement(userExistQuery);
	    ps.setString(1,userName);
	    ps.setString(2, password);
	    ResultSet resultSet=ps.executeQuery();
	    while(resultSet.next()) {
	    	signup=new SignUpAction(
					 resultSet.getInt("id"),
					 resultSet.getString("name"),
					 resultSet.getString("userName"),
					 resultSet.getString("email"),
					 resultSet.getString("password")
					 );
	    	 System.out.println(signup.getId()+"id123");
	    	return signup;
	    }
	    
	   
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	return null;
	}

	public boolean storeChats(int sender_id, int receiver_id, String chat) {
	    String query = "insert into chats (sender_id, receiver_id, chat) values (?, ?, ?)";
	    try (Connection con = DriverManager.getConnection(url, usernamee, passwordd)) {
	        PreparedStatement stmt = con.prepareStatement(query);
	        stmt.setInt(1, sender_id);
	        stmt.setInt(2, receiver_id);
	        stmt.setString(3, chat);
	        int rows = stmt.executeUpdate();
	        return rows > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public boolean addRequestDetails(int reqUserId, int resUserId, int isResponse) throws Exception{
		
		System.out.println(isResponse+"*****");
		String query="insert into reqres (reqUserId,resUserId,isresponse) values(?,?,?)";
		
		Connection con=DriverManager.getConnection(url,usernamee,passwordd);
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1,reqUserId);
		ps.setInt(2,resUserId);
		ps.setInt(3,isResponse);
		int rows=ps.executeUpdate();
		return rows>0;
	}
	
	public List<SignUpAction> getResponseUser(int loginUserid) {
		 String query = "SELECT s.id, s.name, s.username, s.email, s.password " +
                 "FROM reqres r " +
                 "JOIN signup s ON r.reqUserId = s.id " +
                 "WHERE r.resUserId = ? AND r.isresponse = 1";
        
        List<SignUpAction> users = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(url, usernamee, passwordd)) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, loginUserid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SignUpAction user = new SignUpAction();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));

                users.add(user);
            }
            System.out.println(users.size()+"size of response in login user");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

	public boolean updateRequestDetails(int requestUserId, int responseUserId, int isResponse) {
	    String updateQuery = "UPDATE reqres SET isresponse = ? WHERE reqUserId = ? AND resUserId = ?";
	    insertUpdatefrnd(responseUserId,requestUserId,isResponse);
	    try (Connection con = DriverManager.getConnection(url, usernamee, passwordd)) {
	        PreparedStatement ps = con.prepareStatement(updateQuery);
	        ps.setInt(1, isResponse);
	        ps.setInt(2, responseUserId);
	        ps.setInt(3, requestUserId);

	        System.out.println("Executing query: " + updateQuery);
	        System.out.println("With parameters: isResponse=" + isResponse + ", reqUserId=" + requestUserId + ", resUserId=" + responseUserId);

	        int rows = ps.executeUpdate();
	        System.out.println("Number of rows updated: " + rows);

	        return rows > 0;
	    } catch (Exception e) {
	    	System.out.println("error"+e);
	        e.printStackTrace();
	        return false;
	    }
	}

	private void insertUpdatefrnd(int responseUserId, int requestUserId, int isResponse) {
		System.out.println(isResponse+"*****");
		String query="insert into reqres (reqUserId,resUserId,isresponse) values(?,?,?)";
		
		Connection con;
		try {
			con = DriverManager.getConnection(url,usernamee,passwordd);
			PreparedStatement ps=con.prepareStatement(query);
			ps.setInt(1,requestUserId);
			ps.setInt(2,responseUserId);
			ps.setInt(3,isResponse);
			int rows=ps.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	
	}

	public List<SignUpAction> getFriendsList(int loginUserid) {
		
		List<SignUpAction> friends=new ArrayList<>();
		
		String query = "SELECT s.id, s.name, s.username, s.email, s.password " +
                "FROM reqres r " +
                "JOIN signup s ON r.resUserId = s.id " +
                "WHERE r.reqUserId = ? AND r.isresponse = 2";
		 try (Connection con = DriverManager.getConnection(url, usernamee, passwordd)) {
	            PreparedStatement ps = con.prepareStatement(query);
	            ps.setInt(1, loginUserid);
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                SignUpAction user = new SignUpAction();
	                user.setId(rs.getInt("id"));
	                user.setName(rs.getString("name"));
	                user.setUsername(rs.getString("username"));
	                user.setEmail(rs.getString("email"));
	                user.setPassword(rs.getString("password"));

	                friends.add(user);
	            }
	            for(SignUpAction x:friends)
	            	System.out.println(x.getName());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return friends;
		
	}
	
	
	
	  public List<Chat> getChatHistory(int senderId, int receiverId) {
	        List<Chat> chatHistory = new ArrayList<>();
	        String query="SELECT * FROM chats WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) ORDER BY created_at ASC";
	        try (Connection conn = DriverManager.getConnection(url, usernamee, passwordd)) {
	             PreparedStatement stmt = conn.prepareStatement(query);
	            stmt.setInt(1, senderId);
	            stmt.setInt(2, receiverId);
	            stmt.setInt(3, receiverId);
	            stmt.setInt(4, senderId);

	            ResultSet rs = stmt.executeQuery(); 
	                while (rs.next()) {
	                    Chat chat = new Chat();
	                    chat.setId(rs.getInt("id"));
	                    chat.setSender_id(rs.getInt("sender_id"));
	                    chat.setReceiver_id(rs.getInt("receiver_id"));
	                    chat.setChat(rs.getString("chat"));
	                    //chat.setCreated_at(rs.getTimestamp("created_at"));
	                    chatHistory.add(chat);
	            }  
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return chatHistory;
	    }
	  
	  
	  public void updateImage(int id, String desFile) {
			String updateQuery = "UPDATE signup SET image = ? WHERE id=?";
	    try (Connection con = DriverManager.getConnection(url, usernamee, passwordd)) {
	        PreparedStatement ps = con.prepareStatement(updateQuery);
	        ps.setString(1, desFile);
	        ps.setInt(2, id);	        
	        ps.executeUpdate();
	        System.out.println("Image uploaded Successfully");
	    } catch (Exception e) {
	    	System.out.println("error"+e);
	        e.printStackTrace();
	    }
	}

  public String getImagePath(String username,String password) {
  	
  	String getQuery="SELECT image from signup  where username=? AND password=?";
  	String picture=null; 
  	try (Connection con = DriverManager.getConnection(url, usernamee, passwordd)) {
	        PreparedStatement ps = con.prepareStatement(getQuery);
	        ps.setString(1, username);
	        ps.setString(2, password);	        
	        ResultSet rs= ps.executeQuery();
	       
	        if(rs.next()) {
	        	 picture=rs.getString("image");
	        }
	        System.out.println("Image return Successfully");
	    } catch (Exception e) {
	    	System.out.println("error"+e);
	        e.printStackTrace();
	    }
  	
  	return picture;
  	
  }
}
	

