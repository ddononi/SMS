 <%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	    
<%@ page import="kr.go.police.board.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%
	BoardDAO dao  = new BoardDAO();
	List<BoardBean> list = (List<BoardBean>)dao.getRecentNoticeList(5);
%>    
<c:set var="list"  value ="<%=list %>" />
    	<div id="subWrap">
        	<div class="user_box">
            	<ul>
                	<li><strong><%=session.getAttribute("name") %> ��</strong> �ݰ����ϴ�.</li>
                    <li>�̴��� ���� �Ǽ� : <span><%=session.getAttribute("sendLimit") %></span> ��</li>
                </ul>
                <div class="btn">
            		<a href="./LogoutAction.ac"><img src="./images/boder/btn_logout.gif" alt="�α׾ƿ�" border="0"/></a>
                    <a href="./MyInfoAction.ac"><img src="./images/boder/btn_myinfo.gif" alt="��������" border="0"/></a>
                    <a href="./MyMessageAction.sm"><img src="./images/boder/btn_myletter.gif" alt="��������" border="0"/></a>                
                </div>
            </div>
            <div class="notice_box">
            	<div class="notice_box_title">
                	<h2><img src="./images/boder/title_notice.gif" alt="��������" /></h2>
                    <a href="./NoticeListAction.bo"><img src="./images/boder/btn_more.gif" alt="more" /></a>
                </div> 
                <ul>
					<!-- �������� -->
					<c:forEach var="data"  items="${list}" >
						<li><img src="./images/boder/bullet.gif" /> 
                        		<a href="./BoardDetailView.bo?index=${data.index}" > 
                   					${data.title}</a></li>
					</c:forEach>	
                </ul>
            </div>
        </div>