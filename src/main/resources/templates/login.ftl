<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<title>车辆信息管理</title> 
	<link href="static/css/login.css" type="text/css" rel="stylesheet" /> 
	<link href="static/css/bootstrap.min.css" type="text/css" rel="stylesheet" /> 
	<script type="text/javascript" src="static/easyui/1.5.2/jquery.min.js"></script>
	<script type="text/javascript">
		$(function(){
			if(location.search.indexOf('error')>0){
				$("#err_msg").html("用户名或密码错误");
			}
		 });
		 function checkForm(){
		 	if($("input[name='username']").val().length > 0){
		 		if($("input[name='password']").val().length > 0){
		 			$("#login-form").submit();
		 		}else{
		 			$("#err_msg").html("请输入密码");
		 		}
		 	}else{
		 		$("#err_msg").html("请输入用户名");
		 	}
		 }
	</script>
</head> 
<body> 
	<div class="box">
	     <div class="cnt">
	        <p id="huanying"><span id="cnt_one">用户登录</span></p>
	        <hr>
	        <div>
	           <form action="dologin" method="post" id="login-form" class="bs-example bs-example-form" role="form">
			      <div class="input-group">
				      <span class="input-group-addon"><img src="./static/images/account.png"></span>
				      <input type="text" name="username" class="form-control" placeholder="请输入账号">
			      </div><br>
			      <div class="input-group">
				      <span class="input-group-addon"><img src="./static/images/suo.png"></span>
				      <input type="password" name="password" class="form-control" placeholder="请输入密码" onkeydown="if(event.keyCode==13){checkForm();}">
			      </div><br>
			      <div id="err_msg" class="error-msg"></div>
		       </form>
	        </div>
	        <div style="margin-top:30px;">
	           <input class="form-control btn btn-info" style="height:40px;" type="button" value="登录" onclick="checkForm()">
	        </div>
	     </div> 
	  </div>
</body>
</html>