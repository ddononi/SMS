package kr.go.police.sms;

import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	���� �߼� �Ǽ� ��������
 */
public class DaySentDataAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SmsDAO dao = new SmsDAO();
		
		
		String[] days = new String[31];
		Calendar cal = Calendar.getInstance();
		
		String depth = request.getParameter("depth");		// ����
		String type = request.getParameter("type");			//  ���� Ÿ��
		String code = request.getParameter("code");
		String year = request.getParameter("year");
		int  month =  Integer.valueOf(request.getParameter("month"));
		
		cal.set(Calendar.YEAR, Integer.valueOf(year));
		cal.set(Calendar.MONTH, month -1);
		int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);	// �ش���� �ִ���
		
		if(depth.equalsIgnoreCase("ps")){					// ��������
			for(int i = 1; i <= maxDay; i++){
				days[i-1] = dao.PsStatisticsCount(type, Integer.valueOf(code), year, month, i);
			}
		}else if(depth.equalsIgnoreCase("dept")){		// �μ���
			for(int i = 1; i <= maxDay; i++){
				days[i-1] = dao.DeptStatisticsCount(type, Integer.valueOf(code), year, month, i);
			}	
		}else{
			for(int i = 1; i <= maxDay; i++){
				days[i-1] = dao.UserStatisticsCount(type, code, year, month, i);
			}		
		}
		
		String dayStr = SMSUtil.implodeArray(days, ",");
		response.setContentType("text/html;charset=euc-kr");
		PrintWriter out = response.getWriter();
		out.println(dayStr);
		out.close();
		return null;			
	}

}
