 <%@page import="kr.go.police.account.AccountDAO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	    
<%@ page import="kr.go.police.account.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%
	AccountDAO dao = new AccountDAO();
	int rencentUser = (Integer)dao.getRecentJoinUserSize();
%>    
<c:set var="recent_user_size" value="<%=rencentUser%>" />
    	<div id="subWrap">
        	<div class="manager_box">
            	<ul>
                	<li><strong>�����ڴ�</strong> �ݰ����ϴ�.</li>
                    <li>�ű� ȸ�� ���� �� : <span>${recent_user_size}</span> ��</li>                   
                </ul>
                <div class="btn">
            		<a href="./LogoutAction.ac"><img src="./images/boder/btn_logout.gif" alt="�α׾ƿ�" border="0"/></a>                               
                </div>
            </div>
     </div>