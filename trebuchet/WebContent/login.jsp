<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/login.css" rel="stylesheet" type="text/css" />	
<script type="text/javascript" src="js/jquery/jquery-1.10.2.js"></script>  
<title>Trebuchet</title>
</head>
<body>
	<div class="login-container">
		<div class="login-box">
			<h1>Trebushet登录</h1>
			<div class="line"></div>
			<form action="login" method="post">
				<p class="user-name"><input class="txt" type="text" name="playerCode" /></p>
				<p class="pwd"><input class="txt" type="password" name="password" /></p>
				<p class="tip">
					<% if(request.getAttribute("errorMsg") != null){ %>
						<span><%= request.getAttribute("errorMsg")%></span>
					<% } %>
				</p>			
				<p class="login-btn">
					<input class="btn btn-large" type="button" value="注册" id="btn_register">
					<input class="btn btn-primary btn-large" type="submit" value="登入">
				</p>
			</form>
		</div>	
	</div>		
	<script type="template/javascript" id="tmpl_div_register">
		<div id="div_register">
			<p id="x_cancel_register">X</p>
			<div class="form_div">
				<form action="register" method="post" id="r_form">
					<p class="user-name">
						<span>注册账号:</span><input class="txt" type="text" id="r_playerCode" />
					</p>
					<p class="user-name">
						<span>昵称:</span><input class="txt" type="text" id="r_name" />
					</p>
					<p class="user-name">
						<span>注册邮箱:</span><input class="txt" type="text" id="r_Email" />
					</p>
					<p class="pwd">
						<span>注册密码:</span><input class="txt" type="password" id="r_password" />
					</p>
					<p class="pwd">
						<span>注册密码:</span><input class="txt" type="password" id="pswd2" />
					</p>					
					<p class="login-btn">
						<input class="btn btn-primary btn-large" type="button" id="btn_create_register" value="注册">
						<input class="btn btn-primary btn-large" type="button" id="btn_cancel_register" value="取消">
					</p>
				</form>
			</div>
		</div>
	</script>
	<script type="text/javascript">
		$("#btn_register").click(function(){
			show_register_div();
		});
		
		
	
		function show_register_div(){
			var hdHeight = document.documentElement.clientHeight > document.body.clientHeight ? 
					document.documentElement.clientHeight:document.body.clientHeight;
			var hideDiv =$('<div>').css('height',hdHeight+"px").css('width',document.body.clientWidth+"px")
			.attr("id","hideDiv");
			$("body").append(hideDiv);
			var register_div = $($("#tmpl_div_register").html())
			$("body").append(register_div);
			
			//取消注册
			$("#btn_cancel_register").click(function(){
				cancel_register();
			});
			
			//取消注册
			$("#x_cancel_register").click(function(){
				cancel_register();
			});
			
			$("#btn_create_register").click(function(){
				var r_form = $("#r_form");
				if(checkPasswordSetp1(r_form)){
					submit_register_info(r_form);
				}
			});
		}
		
		function cancel_register(){
			$("#hideDiv").remove();
			$("#div_register").remove();
		}
		
		function checkPasswordSetp1(r_form){
			var pswd1 = $("#r_password",r_form);
			var pswd2 = $("#pswd2",r_form);
			if(pswd1.val() != pswd2.val()){
				alert("两次密码不一致");
				return false;
			}
			return true;
		}
		
		function submit_register_info(r_form){
			var r_form = $("#r_form");
			var url=r_form.attr("action"); 
			var parameter = {
				type:"create",
				r_playerCode:$("#r_playerCode",r_form).val(),
				r_name:$("#r_name",r_form).val(),
				r_password:$("#r_password",r_form).val(),
				r_email:$("#r_Email",r_form).val()
			};
			$.ajax({
				type:"POST",
				async: true,        //异步
				contentType:"application/x-www-form-urlencoded; charset=UTF-8", //发送至服务器的数据类型, 这个是默认值
				url:		url,    
				data:		parameter,  
				dataType:	'json',    //服务器返回的数据类型
				success:function(data){    
					window.location.href = data.forward;
				},
				error:function(XMLHttpRequest, textStatus, errorThrown) {
					alert("页面请求失败！\r\n\r\nHttp状态码："+XMLHttpRequest.status);   //弹出错误信息
				}
			});
		}
	</script>
</body>
</html>