<%@ page contentType="text/html; charset=UTF-8"%>
<html>

<script>
	var err = "<%=request.getAttribute("err")%>";
	if (err && err != "null") {
		alert(err);
	}
</script>
<body>

	<form name="from1"
		action="<%=request.getContextPath()%>/login1.do" method="post">
		<table width="300" border="1">

			<tr>
				<td colspan="2">登录窗口</td>
			</tr>
			<tr>
				<td>用户名</td>
				<td><input type="text" name="loginName" size="10"></td>
			</tr>

			<tr>
				<td>密码</td>
				<td><input type="password" name="password" size="10"></td>
			</tr>

			<tr>
				<td colspan="2"><input type="submit" name="submit" value="登录">
			</tr>

		</table>

	</form>
</body>
</html>
