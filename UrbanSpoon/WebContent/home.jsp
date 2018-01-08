<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ts.urbanspoon.dto.*"%>


<table border="1">


<c:forEach var="restList" items="${restaurantsList}">
<tr>
<td><c:out value="${restList.id}" /></td>
<td><c:out value="${restList.govtRegistrationId}" /></td>
<td><c:out value="${restList.name}" /></td>
<td><c:out value="${restList.logoName}" /></td>


<c:forEach var="branchList" items="${restList.branchesList}">

<td><c:out value="${branchList.id}" /></td>
<td><c:out value="${branchList.location}" /></td>
<td><c:out value="${branchList.city}" /></td>
<td><c:out value="${branchList.state}" /></td>
<td><c:out value="${branchList.country}" /></td>
<td><c:out value="${branchList.postalCode}" /></td>
<td><c:out value="${branchList.email}" /></td>
<td><c:out value="${branchList.mobileNo}" /></td>

</c:forEach>
</tr>
</c:forEach>
</table>

<div id="login">
    <h3>Login</h3>
    <form name="login_form" action="UrbanSpoonController" method="post">
        <input type="hidden" name="action" value="login"> <label>UserId</label>:<input
            type="text" name="user_id"><br> <label>Password</label>:<input
            type="password" name="password"><br> <label>Login
            As</label>: <input type="radio" name="loginAs" value="user">User <input
            type="radio" name="loginAs" value="restaurant">Restaurant<br>
        <input type="submit" value="login">
    </form>
</div>

<div id="user_registration">
    <h3>User Register</h3>
    <form name="user_registration_form" action="UrbanSpoonController" method="post">
        <input type="hidden" name="action" value="user_registration">
        <input type="text" name="firstName" placeholder="Enter FirstName" required /><br> 
        <input type="text" name="lastName"    placeholder="Enter LastName" required /><br> 
            <input type="radio" name="gender" value="MALE" />Male <input type="radio" name="gender" value="FEMALE" />Female<br> <br>
             <input type="email" name="email" placeholder="Enter Email" required /><br>
        <br> <input type="password" name="password" placeholder="Enter Password" required /><br> <br>
         <input    type="date" name="date" /> <input type="number" name="mobileNumber" placeholder="Enter MobileNumber" required /><br> <br>
          <input type="submit" value="register">
    </form>
</div>

<div id="restaurant_registration">
    <h3>Restaurant Register</h3>
    <form name="restaurant_registration_form" action="UrbanSpoonController" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="restaurant_registration">
        <input type="text" name="govt_registration_id" placeholder="Enter GovtRegistrationId" required /><br> 
        <input type="text" name="name" placeholder="Enter Name" required /><br> 
        <input type="password" name="password" placeholder="Enter Password" required /><br> 
    	<input type="file" name="logo_Name" placeholder="Enter LogoName" required /><br>
        <br> <br>
         <input type="submit" value="register">
    </form>
</div>