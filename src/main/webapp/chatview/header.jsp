<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.keerthi.chatapp.headerAction" %>
<%@ page import="com.keerthi.chatapp.SignUpAction" %>
<%@ page import="com.keerthi.chatapp.LoginAction" %>
  <%@ page import="com.keerthi.chatapp.UserWithResponse"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>chatApp</title>
<style>
/* Existing styles */
* { box-sizing: border-box; }
body {
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
}
.SearchBar input {
  height: 30px;
  width: 250px;
  position: relative;
  top: -28px;
}
.header {
   position: relative;
    bottom: 80px;
    right: 200px;
    width: 1600px;
  height: 70px;
  overflow: hidden;
  background: #fff;
  padding: 10px 10px;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
.header a {
  position: relative;
  bottom: 80px;
  right: 200px;
  float: left;
  color: black;
  text-align: center;
  padding: 12px;
  text-decoration: none;
  font-size: 18px;
  line-height: 25px;
  border-radius: 4px;
}
.header a.logo {
  font-size: 25px;
  font-weight: bold;
}
.header-right {
  float: right;
    width: 20%;
}
@media screen and (max-width: 500px) {
  .header a {
    float: none;
    display: block;
    text-align: left;
  }
  .header-right {
    float: none;
  }
}
.img-card {
  width: 20px;
  height: 25px;
  margin-left: -42px;
  cursor: pointer;
}
.modal {
  display: none;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgb(0, 0, 0);
  background-color: rgba(0, 0, 0, 0.4);
}
.modal-content {
  background-color: #fefefe;
  margin: 15% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 40%;
}
.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}
.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

@keyframes blink {
  0% { opacity: 1; }
  50% { opacity: 0; }
  100% { opacity: 1; }
}

.blinking {
  animation: blink 1s infinite;
}

.badge {
    position: absolute;
    top: 2px;
    right: 342px;
    background-color: gray;
    color: white;
    border-radius: 50%;
    padding: 5px;
    font-size: 9px;
}

label {
  background-color: indigo;
  color: white;
  padding: 0.5rem;
  font-family: sans-serif;
  border-radius: 0.3rem;
  cursor: pointer;
  margin-top: 1rem;
}
#profileImage{
    width: 43px;
    cursor: pointer;
    position: relative;
    bottom: 65px;
    left: 16px;
    height: 44px;
}
img {
    border-radius: 50%;
  }
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<%
LoginAction l = new LoginAction();
SignUpAction signupAction1 = l.getSignupAction();
int reqUserId = signupAction1 != null ? signupAction1.getId() : 0;

List<SignUpAction> responseList = l.getGetLoginUserResponse();
String image=l.getFilePath();
headerAction h = new headerAction();
List<UserWithResponse> searchre = h.getSearchResults();
if (searchre != null) {
    System.out.println(searchre.size() + "-----");
}
%>
<div class="header">
  <a href="#default" class="logo">Logo</a>
  
    <div class="header-right">
    <img id="notificationIcon" src="./image/notification.png" alt="notify" class="img-card">
    <span id="notificationBadge" class="badge"></span>

    <form action="search" id="searchForm" class="SearchBar "  onsubmit="return handleSearchSubmit(event)">
      <input type="text" name="search" id="searchInput">
    </form>
   
   <div class="header-right">
    <% if(image!=null){%>
    	<img id="profileImage" src="<%= image%>" alt="Avatar">
    <%}
    else{%>
     <img id="profileImage" src="./image/avatar.webp" alt="Avatar">
    <% }
    %>
   </div>
     
  </div>
</div>

<!-- The search Modal -->

<div id="searchModal" class="modal">
  <div class="modal-content">
    <span class="close">&times;</span>
    <h2>Friends List</h2>
    <% if (searchre != null && !searchre.isEmpty()) { %>
    <table id="resultsTable">
      <% for (UserWithResponse signUp : searchre) { %>
      <tr>
        <td><%= signUp.getName() %></td>
         <% if(signUp.getIsResponse()==0 || signUp.getIsResponse()==3) { %>
        <td>
          <input type="button" name="request" value="request"
            onclick="sendRequest('<%= signUp.getId() %>', 1,this)"
            style="width: 100%; margin-left: 40px;">
        </td>
         <% } %>
         
         <% if(signUp.getIsResponse()==1 && reqUserId!=signUp.getId()) {  %>
        <td>
          <input type="button" name="requesting" value="requesting"
            style="width: 100%; margin-left: 40px;">
        </td>
         <% } %>
         
          <% if(signUp.getIsResponse()==2)  {   %>
        <td>
          <a href="chatpage.jsp" >
            <input type="button" value="chat" name="chat" style="width: 100%; margin-left: 40px;">
          </a>
        </td>
         <% } %>
      </tr>
      <% } %>
    </table>
    <% }  else { %>
    <h3> No user found </h3>
    <% } %>
  </div>
</div>


<!-- The notification Modal -->

<div id="responseModal" class="modal">
  <div class="modal-content">
    <span class="close">&times;</span>
    <% if (responseList != null && !responseList.isEmpty()) { %>
    <h2>List</h2>
  
    <table id="responseTable">
      <% for (SignUpAction responses : responseList) { %>
      <tr>
        <td><%= responses.getName() %></td>
        <td>
          <input type="button" name="Accept" value="Accept"
            onclick="sendResponse('<%= responses.getId() %>', 2,this)"
            style="width: 100%; margin-left: 40px;">
        </td>
        <td>
          <input type="button" value="Cancel" name="Cancel"
            onclick="sendResponse('<%= responses.getId() %>', 3,this)"
            style="width: 100%; margin-left: 40px;">
        </td>
      </tr>
      <% } %>
    </table>
    <% 
      } else { 
    %>
    <h3>No notifications</h3>
    <% 
      } 
    %>
  </div>
</div>



<div id="userModal" class="modal">
  <div class="modal-content" style="margin: 3% 81%; width: 18%;height: 50%;">
    <span class="close"></span>
    <h2></h2>
       <div style="height:30px; margin-top:40px;">
       <%= signupAction1.getName()%> -   <%= signupAction1.getUsername() %>
       </div>
       <hr>
       <div style="height: 40px; margin-top: 35px;">
       <%= signupAction1.getEmail() %>
       </div>
       <hr>
       <div style="height: 40px; margin-top: 35px;">
        <input type="file" name="upload-pic" id="file" id="upload-pic" style="display: none;" hidden>
        <label class="profilepic" for="upload-pic" id="upload">update profile</label>
       </div>
       <hr>
        <div style="height: 40px;     margin: -57px 149px;">
        <input type="button" name="logout" id="logout" hidden>
        <a href="http://localhost:8080/ChatApplication/"><label class="">logout</label></a>
       </div>
  </div>
</div>

<script>

document.getElementById("upload").addEventListener('click',function(e){
	document.getElementById("file").click();     
	})
	document.getElementById("file").addEventListener('change',function(e){
	var file=e.target.files[0];
	var reader=new FileReader();
	var num=<%= reqUserId%>
	reader.onload=function(e){
	 document.getElementById("profileImage").src=e.target.result;
	}
	var formData = new FormData();
    formData.append("file", file);
    formData.append("id", num);
    sendImage(formData);
	reader.readAsDataURL(file);
	})
	
	window.sendImage=function(formData) {
    $.ajax({
        url: "sendImage", 
        type: "POST", 
        data: formData,  
        processData: false, 
        contentType: false, 
        success: function(response) { 
            console.log('File uploaded successfully:');
        },
        error: function(xhr, status, error) { 
            console.error('Failed to upload file:', error);
        }
    });
}
$(document).ready(function() {
	
<% if (responseList != null) { %>
  var responseListSize = '<%= responseList.size() %>';

  var notificationIcon = document.getElementById('notificationIcon');
  var notificationBadge = document.getElementById('notificationBadge');
  
  if (responseListSize > 0) {
    console.log("isblinking");
    notificationBadge.classList.add('blinking');
    notificationBadge.innerText = responseListSize;
    notificationBadge.style.display = 'block'; 
  } else {
    console.log("Not isblinking");
    notificationBadge.classList.remove('blinking');
    notificationBadge.style.display = 'none'; 
  }
  <% } %>
  
  // Handle search modal
  <% if (searchre != null) { %>
  var searchModal = document.getElementById('searchModal');
  var searchCloseBtn = searchModal.getElementsByClassName('close')[0];

  searchModal.style.display = 'block';

  searchCloseBtn.onclick = function() {
    searchModal.style.display = 'none';
  }

  window.onclick = function(event) {
    if (event.target == searchModal) {
      searchModal.style.display = 'none';
    }
  }

  window.sendRequest = function(receiverId, isResponse,button) {
    $.ajax({
      url: 'sendRequest',
      method: 'POST',
      data: {
        reqUserId: '<%= reqUserId %>',
        resUserId: receiverId,
        isresponse:isResponse
      },
      success: function(response) { 
        button.value = 'Requesting.';
        console.log("Request sent successfully for user ID: " + receiverId);
      },
      error: function(xhr, status, error) {
        alert('Failed to send request: ' + error);
      }
    });
  }
  <% } %>

  // Handle response modal
  <% if (responseList!=null) { %>
  var responseModal = document.getElementById('responseModal');
  var responseCloseBtn = responseModal.getElementsByClassName('close')[0];

  notificationIcon.onclick = function() {
    responseModal.style.display = 'block';
  }

  responseCloseBtn.onclick = function() {
    responseModal.style.display = 'none';
  }

  window.onclick = function(event) {
    if (event.target == responseModal) {
      responseModal.style.display = 'none';
    }
  }

  window.sendResponse = function(receiverId, isResponse,button) {
    $.ajax({
      url: 'sendResponse',
      method: 'POST',
      data: {
        reqUserId: '<%= reqUserId %>',
        resUserId: receiverId,
        isresponse: isResponse
      },
      success: function(response) {
    	if(isResponse==2)
    		button.value="Following";
        console.log("Response sent successfully for user ID: " + receiverId + " with response: " + isResponse);
      },
      error: function(xhr, status, error) {
        alert('Failed to send response: ' + error);
      }
    });
  }
  <% } %>
  
//Handle profile image click to show user modal
  var profileImage = document.getElementById('profileImage');
  var userModal = document.getElementById('userModal');
  var userModalCloseBtn = userModal.getElementsByClassName('close')[0];
  
  profileImage.onclick = function() {
    userModal.style.display = 'block';
  }

  userModalCloseBtn.onclick = function() {
    userModal.style.display = 'none';
  }

  window.onclick = function(event) {
    if (event.target == userModal) {
      userModal.style.display = 'none';
    }
  }
 
 
});
</script>
<jsp:include page="chatpage.jsp" />
</body>
</html>
