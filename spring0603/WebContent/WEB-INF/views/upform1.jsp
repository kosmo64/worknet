<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>upform1.jsp</title>
<style>
#wrap {
	width: 300px;
	margin: auto;
}
</style>
</head>
<body>
	<div id="wrap">
	<%--  enctype="multipart/form-data" :���� ���ε带 ���� ���� , <input type="file" �ϰ��  
	     �̶� method�� �ݵ�� post�̾�� �Ѵ�. *********
	--%>
		<form method="post" action="upsave1" enctype="multipart/form-data">
			<input type="file" id="mfile" name="mfile"><br /> 
			<input type="submit" value="send">
		</form>
		
		
	</div>
	
	
	
	
</body>
</html>