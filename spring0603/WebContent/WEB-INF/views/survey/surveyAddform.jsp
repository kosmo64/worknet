<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>surveyAddform</title>
<style>
	#container { width: 450px; margin: auto;}
	table{ border-collapse: collapse; width: 100%}
	th{ background-color: #ff9933}
	tbody img { width: 100px;}
	th,td {padding: 8px; text-align: left; border-bottom: 1px solid #ddd;}
	tr:hover {background: #ff9933}
</style>
</head>
<body>
<div id="container">
	<form method="post" action="addsurvey" >
	<table>
		<thead>
			<tr><th colspan="2">Tboard ���ۼ� ��</th></tr>
		</thead>
		<tbody>
			<tr><th>����</th><td><input type="text" name="sub" id="sub"></td></tr>
			<tr><th>���׼�</th><td><input type="number" name="code" id="code"></td></tr>
			<tr><th>��������1</th><td><input type="text" name="surveytitle" class="surveytitle"></td></tr>
			<tr><th>��������2</th><td><input type="text" name="surveytitle" class="surveytitle"></td></tr>
			<tr><th>��������3</th><td><input type="text" name="surveytitle" class="surveytitle"></td></tr>
			<tr><th>��������4</th><td><input type="text" name="surveytitle" class="surveytitle"></td></tr>
			<tr><th>��������5</th><td><input type="text" name="surveytitle" class="surveytitle"></td></tr>
		</tbody>
		<tfoot>
		<tr><th colspan="2">
		<input type="submit" value="send">
		&nbsp; <input type="button" value="list" 
		onclick="location='surveylist'">
		</th></tr>
		</tfoot>
	</table>
	</form>
	</div>
</body>
</html>