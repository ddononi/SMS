<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.*" %>
<%@ page import="sms.address.AddressEver" %>
<%
	String f_id = (String)session.getAttribute("f_id");
	String html = AddressEver.getInstance().getMyList(f_id);
%>
<table class="ads_tbl myList" cellpadding="0" cellspacing="0">
		<colgroup>
			<col width="65%" />
			<col width="35%" />
		</colgroup>
		<thead>
			<tr><th>�׷��</th>
				<th style="border-right: none;">�ο�</th></tr>
		</thead>
		<tbody><%=html%></tbody>
</table>