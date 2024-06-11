<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
    <%@ page import="com.keerthi.chatapp.LoginAction" %>
    <%@ page import="com.keerthi.chatapp.SignUpAction"%>
    <%@ page import="com.keerthi.chatapp.UserWithResponse"%>
    <%@ page import="com.keerthi.chatapp.headerAction"%>
    <%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>chatApp</title>
<style>
.container {
    width: 1500px;
    height: 650px;
    margin: -66px -138px;
    overflow: hidden;
    background: #fff;
    padding: 10px 10px;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}

.form-container {
    max-width: 1000px;
    padding: 10px;
    display: flex;
    background-color: white;
    position: absolute;
    bottom: 50px;
    right: 40px;
}

.form-container textarea {
    width: 810px;
    padding: 15px;
    margin: 15px 0 22px 0;
    border: none;
    background: LightGray;
    resize: none;
    min-height: 55px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}

.form-container textarea:focus {
  background-color: #ddd;
  outline: none;
}
.form-container .btn {
    background-color: #04AA6D;
    color: white;
    padding: 10px 40px;
    border: none;
    cursor: pointer;
    opacity: 0.8;
    height: 50px;
    margin: 40px 20px;
}

.form-container .cancel {
  background-color: red;
}

.form-container .btn:hover, .open-button:hover {
  opacity: 1;
}

#chatDisplay {
    margin-top: 20px;
    overflow: auto;
    max-height: 400px;
}

/* For better visibility of the example */
body {
  margin: 80px 200px;
  background-color: #cccccc;
}

/* Message box starts here */
.container-msg {
  clear: both;
  position: relative;
  margin-bottom: 10px; 
  max-width: 80%; 
   white-space: inherit;
  word-break: break-word;
}
.container-msg .message-body {
  float: left;
  background-color: #e9dd6d;
  padding: 6px 8px;
  border-radius: 5px;
}

.container-msg .message-body p {
  margin: 0;
}

.message-sent {
  float: right;
  right: 200px;
}

.message-sent .message-body {
  background-color: #dcf8c6;
  border: 1px solid #dcf8c6;
}

.message-sent .arrow .outer {
  border-right-color: #dcf8c6;
}

.message-received {
float: left;
left: 650px;
}

.vl {
    border-right: 3px solid lightgray;
    height: 648px;
    position: absolute;
    left: 110px;
    top: 87px;
    overflow: auto;
}

.list-f {
   position: relative;
    right: 20px;
    align-content: center;
    text-align: center;
    height: 100px;
    margin: 29px;
    color: black;
    width: 300px;
    padding: 0.5rem;
    border-radius: 0.5rem;
    cursor: pointer;
    border-right: 4px solid #f50057;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
    }


</style>
</head>
<body>
<%
LoginAction l=new LoginAction();
SignUpAction signupAction1=l.getSignupAction();
int sender_id = signupAction1 != null ? signupAction1.getId() : 0;
headerAction h=new headerAction();
List<UserWithResponse> searchre=h.getSearchResults();
int receiver_id=0;

if(searchre!=null && receiver_id!=0)
receiver_id=searchre.get(0).getId();
else
System.out.println("No search result");

List<SignUpAction> friendsList=l.getFriendsList();
%>
<div class="container">
<div class="chat-popup" id="myForm">
  <form class="form-container" id="chatForm">
    <textarea placeholder="Type message.." name="msg" id="message" required></textarea>
    <button type="submit" class="btn">Send</button>
  </form>
  
  <div id="chatDisplay"></div>
 
</div>

<div class="vl">
<% for(SignUpAction frd:friendsList) { %>
<div class="list-f" onclick="selectFriend('<%= frd.getId() %>', '<%= frd.getName() %>')"><%= frd.getName() %></div>
<% } %>
</div>

</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
var selectedReceiverId = null;

function selectFriend(receiverId, receiverName) {
    selectedReceiverId = receiverId;
    $('#chatDisplay').empty();  
    fetchChatHistory(receiverId);
    $('#selectedFriend').text(receiverName);
}

function fetchChatHistory(receiverId) {
    var senderId = '<%= sender_id %>';
    
    $.ajax({
        url: 'fetchChatHistory',
        method: 'POST',
        data: {
            sender_id: senderId,
            receiver_id: receiverId
        },
        success: function(response) {
            console.log(response); 
            var chatHistory = response.chatHistory; 
            chatHistory.forEach(function(message) {
            	 var messageHtml;
            	    if (message.sender_id == senderId) {
            	        messageHtml = `
            	            <div class="container-msg message-sent">
            	                <div class="message-body">
            	                    <p>` + message.chat + `</p>
            	                </div>
            	            </div>`;
            	    } else if (message.sender_id == receiverId) {
            	        messageHtml = `
            	            <div class="container-msg message-received">
            	                <div class="message-body">
            	                    <p>` + message.chat + `</p>
            	                </div>
            	            </div>`;
            	    }
                $('#chatDisplay').append(messageHtml);
            });
        },

        error: function(xhr, status, error) {
        	console.log(error+"issue");
            alert('Failed to retrieve chat history: ' + error);
        }
    });
}
$(document).ready(function() {
    $('#chatForm').submit(function(event) {
        event.preventDefault(); 

        if (!selectedReceiverId) {
            alert('Please select a friend to chat with.');
            return;
        }

        var message = $('#message').val();
        var senderId = '<%= sender_id %>';

        $.ajax({
            url: 'sendChat',
            method: 'POST',
            data: {
                sender_id: senderId,
                receiver_id: selectedReceiverId,
                chat: message
            },
            success: function(response) {
                var messageHtml = `
                <div class="container-msg message-sent">
                    <div class="message-body">
                        <p>` + message + `</p>
                    </div>
                </div>`;
                $('#chatDisplay').append(messageHtml);
                $('#message').val('');
            },
            error: function(xhr, status, error) {
                alert('Failed to send message: ' + error);
            }
        });
    });
});
</script>

</body>
</html>