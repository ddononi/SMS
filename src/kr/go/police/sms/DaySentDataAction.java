package kr.go.police.sms;

import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	일일 발송 건수 가져오기
 */
public class DaySentDataAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SmsDAO dao = new SmsDAO();
		
		
		String[] days = new String[31];
		Calendar cal = Calendar.getInstance();
		
		String depth = request.getParameter("depth");		// 종류
		String type = request.getParameter("type");			//  전송 타입
		String code = request.getParameter("code");
		String year = request.getParameter("year");
		int  month =  Integer.valueOf(request.getParameter("month"));
		
		cal.set(Calendar.YEAR, Integer.valueOf(year));
		cal.set(Calendar.MONTH, month -1);
		int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);	// 해당월의 최대일
		
		if(depth.equalsIgnoreCase("ps")){					// 경찰서별
			for(int i = 1; i <= maxDay; i++){
				days[i-1] = dao.PsStatisticsCount(type, Integer.valueOf(code), year, month, i);
			}
		}else if(depth.equalsIgnoreCase("dept")){		// 부서별
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
