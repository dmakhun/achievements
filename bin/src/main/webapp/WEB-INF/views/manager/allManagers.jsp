<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<c:url value="/resources/script/jquery-2.1.0.js" />"></script>
<script src="<c:url value="/resources/script/jquery-ui.js" />"></script>
<link rel="stylesheet"
	href="<c:url value="/resources/css/addAchiv.css" />" type="text/css" />
<script type="text/javascript">
//
    total = ${total};
    count = 1;
    finder=false;
    $(document).ready(onAction);
    
    /* $(document).ready(function(event){ 
  	$("#addManager").click(pass());    	    	 	    
      });  */      
    
    function onAction() {
    	
    	$("#buttonSubmit").click(displayDialog);    	  
    	$("#checker").click(searchCriteria);    	   	  
  		$("#pattern").keyup(function(){restartPaging(),search();}); 
  		$("#volume").change(function(){restartPaging(),search();});
  		$("#previous").click(minus);
  		$("#next").click(plus);      	        
	}    
    		  
	function pass() {
		$.ajax({
			url : '<c:url value="/admin/addManager"/>',
			type : "POST",
			data : $("form").serialize(),
			success : function(text) {
				$("#add").dialog("close");
				switch (text) {				
				case "fail":
					alert("Nonono");
					break;
				default:
					addLine(text);
				}		
			}
		});
	}
	
	function addLine(id) {
		var line = '<tr><td>' + $("form input[name=name]").val()
				+ '</td><td>' + $("form input[name=surname] ").val()
				+ '</td><td>' + $("form input[name=username]").val()
				+ '</td><td>' + $("form input[name=e-mail]").val()
				+ '</td><td>'
				+ '<button class="btn btn-default" onclick="location.href=\'<c:url value="/admin/removeManager/' + id + '" />\'">Remove manager</button>'
				+ '</td> </tr>';
		$($("table tr")[1]).after(line);
	}

	function search() {	
		
		$("#showPaging").css("display", "inline-table"); 
		 $("#totalResults").css("display", "none");
		if ($("#pattern").val() == "") {
			 $("#main").css("display", "inline-table"); 
			 $("#showPaging").css("display", "none");			 
			 $("#totalResults").css("display","");
				$(".manager-list").remove();
				total=${total};
				init();
			}
		$.ajax({
			url : '<c:url value="/admin/managers/search/" />'
					+ $("#pattern").val(),
			data : {
				"criteria" : $("#selector").val(),
				"volume" : $("#volume").val(),
				"pagination" : count,
				"additionFind" : finder
			},
			success : function(text) {
				$(".manager-list").remove();			
				$("#search").after(text);
				$("#main").css("display", "none");
				total = $("#listSize").val();
				init();
				buttonController(count);
			}
		});		
	}
	
  	function plus() {
  		count++;  
  		$("#currentPage").val(count); 
  		search();  		
	}
  	
	function minus() {
		count--;	
		$("#currentPage").val(count); 
		search();		
	}

	function init() {
		$("#totalPages").html(Math.ceil(total / $("#volume").val()));
		
	}
	function buttonController(numberToCheck){
		$("#next").attr("disabled", false);
		$("#previous").attr("disabled", false);
		if(total==0 || total==1){
			$("#next").attr("disabled", true);
			$("#presious").attr("disabled", true);
		}
		if(numberToCheck>=Math.ceil(total / $("#volume").val())){
			$("#next").attr("disabled", true);			
		}
		if(numberToCheck<=1){
			$("#previous").attr("disabled", true);		
		}	
	}
	
	function certain(){
	count=$("#currentPage").val();		
	if(count<1 || count>Math.ceil(total / $("#volume").val())){
		alert("Out of range!");			
		count =1;
		$("#currentPage").val(count);
		search();			
	}   
	search();	
	}
	
	function restartPaging(){
		count=1;
		$("#currentPage").val(count);
	}
	
	function searchCriteria() {
		count=1;
		$("#currentPage").val(count);	
		if($('#checker').is(":checked")){
			finder=true;
		}
		else 
			finder =false;
		search();
	}
	
	function displayDialog() {
		$("#add").dialog();
	}
	
</script>

<title>Managers</title>
</head>
<body onload="init();">

	<br>
	<c:if test="${status == 'success'}">
		<div class="alert alert-success">
			<strong>Success!</strong> Manager removed!
		</div>
	</c:if>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">Managers</div>
			<div class="panel-heading" id="search">
				<input id="pattern" name="criter"> <select
					id="selector" onchange="search();">
					<c:forEach var="user" items="${searchBy}" >
					<option value="${user.key}">${user.value}</option>
					</c:forEach>
				
				</select> <select id="volume">
				    <option value="1">1</option>
					<option value="5">5</option>
					<option value="10">10</option>
					<option value="15">15</option>
				</select>
							
				<input type="checkbox" id="checker">From first letter
				
			</div>
			<table id="main" class="table">
				<tr>
					<th>Name</th>
					<th>Surname</th>
					<th>Username</th>
					<th>Email</th>
				</tr>

				<c:forEach var="user" items="${userlist}">
					<tr>
						<td>${user.name}</td>
						<td>${user.surname}</td>
						<td>${user.username }</td>
						<td>${user.email }</td>
						<td><button class="btn btn-default"
								onclick="location.href='<c:url value="/admin/removeManager/${user.id}"/>'">Remove
								manager</button></td>

					</tr>
				</c:forEach>
			</table>
		</div>

		<div class="panel panel-default">
			<div class="panel-body">
				<button type="button" id="buttonSubmit" class="btn btn-success">Add manager</button>
					<div style="float: right; display: none " id="showPaging">
					<div class="form-control">
				<input type="text" id="currentPage" value="1" onchange="certain()"
					style="width: 30px;" onkeypress='return (event.charCode >= 48 && event.charCode <= 57) || event.charCode==13' /> of <span id="totalPages">
					</span></div>
				<button type="button" class="btn btn-default" id="previous">
					<span class="glyphicon glyphicon-arrow-left"></span></button>
				<button type="button" class="btn btn-default" id="next"><span class="glyphicon glyphicon-arrow-right"></span></button>
			</div>
			<span id="totalResults" class="input-group-addon" >Total results ${total}
					</span>
			</div>
		</div>
			
	</div>
	<div id="add" style="display: none">
		<form>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<input class="form-control" type="text" name="name"
							placeholder=<spring:message code="achievement.name"/> />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<input class="form-control" type="text" name="username"
							placeholder=<spring:message code="user.username"/> />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<input class="form-control" type="password" name="password"
							placeholder=<spring:message code="registration.password"/> />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<input class="form-control" type="text" name="surname"
							placeholder=<spring:message code="registration.secondname"/> />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<input class="form-control" type="text" name="e-mail"
							placeholder=<spring:message code="registration.email"/> />
					</div>
				</div>
			</div>
			<div class="row text-center">
				<div class="col-md-12">
					<div class="btn-group btn-group-lg">
						<button type="button" id="addManager" class="btn btn-success" onclick="pass();">Submit</button>
					</div>
				</div>
			</div>
		</form>
	</div>

</body>
</html>