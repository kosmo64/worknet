<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<!DOCTYPE html>
<html>
<head>
<%--
- ������ �ʿ��� �ҽ��� �� ���� �Խ��ǿ� �������� Ȯ�� �� �� �ֵ���
- �Խ�, ���� ��ũ�� ��  ���� 
* �������� �������� ���� ���α׷� �����ϱ�
 --%>
<meta charset="EUC-KR">
<title>surveyDetail.jsp</title>
<style>
	#container { width: 450px; margin: auto;}
	table{ border-collapse: collapse; width: 100%}
	th{ background-color: #c0c0c0}
	tbody img { width: 100px;}
	th,td {padding: 8px; text-align: left; border-bottom: 1px solid #ddd;}
	tr:hover {background: #f5f5f5}
</style>
</head>
<body>
<div id="container">
	<table>
		<thead>
			<tr><th colspan="2">SurveyDetail</th></tr>
		</thead>
		<tbody>
 <c:forEach var="e" items="${list }" varStatus="i">
 
		   <c:if test="${i.index == 0 }">
		   <tr><th>����</th>
		   <td><input type="text" name="sub" id="sub" value="${e.sub }" 
		   readonly="readonly">
		    <input type="hidden" name="num" value="${e.num }" id="num">
		   </td></tr>
 		   </c:if>
 		   
			<tr><th>${i.index+1}�� �������� ${e.surveytype }</th>
			<td>
			${e.surveytitle } => ${e.surveycnt }
			</td></tr>
</c:forEach>
		</tbody>
		<tfoot>
		<tr><th colspan="2"><input type="button" value="list" 
		onclick="location='surveylist'">
		<%-- jQuery�� ����ؼ� url�� �ѱ��  --%>
		<input type="button" value="delete" id="delete"
		>
		</th></tr>
		</tfoot>
	</table>
	</div>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script>
	$(function(){
		$('#delete').click(function(){
			if(confirm('������ ���� �Ͻðڽ��ϱ�?')){
				var numv = $('#num').val();
				alert("���� ó���� �̵��ϱ� "+numv);
			}
		});
	});
	</script>
</body>
</html>