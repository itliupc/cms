<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<title>Shiro</title> 
	<link href="static/css/login.css" type="text/css" rel="stylesheet" /> 
	<script type="text/javascript" src="static/easyui/1.5.2/jquery.min.js"></script>
	<script type="text/javascript">
		$(function(){
			if(location.search.indexOf('error')>0){
				$("#err_msg").html("用户名或密码错误");
			}
		 })
	</script>
</head> 
<body> 
	<div class="login">
	    <div class="message">用户登录</div>
	    <div id="darkbannerwrap"></div>
	    <form action="dologin" method="post">
			<input name="username" placeholder="用户名" type="text" />
			<hr class="hr15" />
			<input name="password" placeholder="密码" type="password" />
			<hr class="hr15" />
			 <div id="err_msg" class="error-msg"></div>
			<input value="登录" style="width:100%;" type="submit" />
			<hr class="hr20" />
		</form>
	</div>
</body>
</html>