
<%@ page language="java" pageEncoding="UTF-8"%>  
<body>

普通用户可访问<a href="<%=request.getContextPath()%>/user/getUserInfo">用户信息页面</a>  
<br/>  
<br/>  
管理员可访问<a href="<%=request.getContextPath()%>/user/listUser">用户列表页面</a>  
<br/>  
<br/>  
<a href="<%=request.getContextPath()%>/security/logout" target="_blank">Logout</a>

</body>