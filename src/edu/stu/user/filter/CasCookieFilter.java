package edu.stu.user.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CasCookieFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)arg0;
		HttpServletResponse resp=(HttpServletResponse)arg1;
		Cookie[] cs=req.getCookies();
		if(cs==null || cs.length==0)
		{
			resp.sendError(403,"user not login!");
			return;
		}
		
		boolean hasCasCookie=false;
		String ticket=null;
		for(Cookie c:cs)
		{
			if("CASTGC".equals(c.getName().toUpperCase()))
			{
				ticket=c.getValue();
				hasCasCookie=true;
				break;
			}				
		}
		if(hasCasCookie)
		{
			
			String oldTicket=(String)req.getSession().getAttribute("ticket");
			if(oldTicket!=null && !oldTicket.equals(ticket))
			{
				req.getSession().invalidate();
			}	
			
			arg2.doFilter(arg0, arg1);
		}
		else
		{
			resp.sendError(403,"user not login!");
		}
		
	}
	

	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
