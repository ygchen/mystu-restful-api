package edu.stu.user.action;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import edu.stu.generic.StaticFileService;
 public class TempFileMonitor implements HttpSessionBindingListener {

	private StaticFileService staticFileService;
	private String url;
	private boolean used;
	
	public String getUrl() {
		return url;
	}

	public TempFileMonitor(StaticFileService _staticFileService,String _url)
	{
		this.staticFileService=_staticFileService;
		this.url=_url;
	}
	
	@Override
	public void valueBound(HttpSessionBindingEvent arg0) {
		

	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent arg0) {		
		if(!used)
		{
			this.staticFileService.delete(url);
		}
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
		

}
