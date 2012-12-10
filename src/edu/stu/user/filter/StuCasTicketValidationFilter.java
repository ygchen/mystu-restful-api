package edu.stu.user.filter;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;

public class StuCasTicketValidationFilter extends
		Cas20ProxyReceivingTicketValidationFilter {
	@Override
	 protected void onSuccessfulValidation(final HttpServletRequest request, final HttpServletResponse response, final Assertion assertion) {
			String key;
			
			Map<String,Object> attrs=assertion.getPrincipal().getAttributes();
			Iterator<String> itr=attrs.keySet().iterator();
			while(itr.hasNext())
			{
				key=itr.next();
				
				System.err.println(key+"="+attrs.get(key));
			}
			
			Cookie[] cs=request.getCookies();
			for(Cookie c:cs)
			{
				if("CASTGC".equals(c.getName().toUpperCase()))
				{
					request.getSession().setAttribute("ticket",c.getValue());
					break;
				}				
			}
	  }
}
