package edu.stu.util;

import java.util.ArrayList;
import java.util.List;

public class PageResult<T> {
    private int flag;
    private List<T> values;
    private int pageNo;
    private int pageSize;
    private int total;
    private int pageCount;
    private int length;
    private List values2;
    public PageResult()
    {
    	
    }
    public PageResult(int pageNo,int pageSize)
    {
    	setPageNo(pageNo);
    	setPageSize(pageSize);
    }
    public int getLength() {
    	if(values==null)
    		return 0;
        this.length = values.size();
        return this.length;
    }

    public int getFlag() {
        return flag;
    }

    public List<T> getValues() {
    	if(values==null)
    		return new ArrayList<T>(1);
        return values;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getTotal() {
        return total;
    }

    public int getPageCount() {
        return pageCount;
    }

    public List getValues2() {
        return values2;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getEnd() {
        return pageCount;
    }

    public int getStart() {
        return 1;
    }

    public int getNext() {
        return this.pageNo < this.pageCount ? pageNo + 1 : this.pageCount;
    }

    public int getPrevious() {
        return this.pageNo > 1 ? pageNo - 1 : 1;
    }

    public void setValues(List<T> values) {

        this.values = values;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public void setTotal(int total) {
        this.total = total;
        resetPage();
    }

//    public void setPageCount(int pageCount) {
//        this.pageCount = pageCount;
//    }

    public void setValues2(List values2) {
        this.values2 = values2;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        resetPage();
    }
    
    List<Page> pages;
    public List<Page> getPages()
    {
    	if(pages!=null)
    		return pages;
    	pages=new ArrayList<Page>(10);
    	boolean smallFinished=false;
    	boolean largerFinished=false;
    	for(int i=1;i<=pageCount;i++)
    	{
    		if(i==1 || i==pageNo || i==pageCount)
    		{
    			pages.add(new Page(String.valueOf(i)));
    			continue;
    		}
    		if(i<pageNo)
    		{
	    		if(i==pageNo-1 )
	    		{
	    			pages.add(new Page(String.valueOf(i)));	    			
	    		}
	    		else
	    		{
	    			if(!smallFinished)
	    			{
	    				pages.add(new Page("..."));
	    				smallFinished=true;
	    			}
	    		}
    		}
    		else
    		{
	    		if( i==pageNo+1)
	    		{
	    			pages.add(new Page(String.valueOf(i)));	    			
	    		}
	    		else
	    		{
	    			if(!largerFinished)
	    			{
	    				pages.add(new Page("..."));
	    				largerFinished=true;
	    			}
	    		}
    		}
    	}
    	//增加到前10页和到后10页
    	if(pageNo>10)
    	{
    		pages.add(0,new Page(String.valueOf(pageNo-10),"<<"));
    	}
    	
    	
    	if(pageNo+10<pageCount)
    	{
    		pages.add(new Page(String.valueOf(pageNo+10),">>"));
    	}
    		
    		
    	return pages;
    }

    private void resetPage() {
        if(total==0 || pageSize==0)
            return;
        pageCount=((total % pageSize == 0) ?
                     total / pageSize :
                     (total + pageSize) / pageSize);
        if (pageNo > pageCount) {
            pageNo=pageCount;
        }
    }
    
    
    
    public static class Page{
    	private String page;
    	private String label;
    	Page(String page)
    	{
    		this(page,null);
    	}
    	
    	Page(String page,String label)
    	{
    		this.page=page;
    		this.label=label;
    	}
    	public String getPage()
    	{
    		return page;
    	}
    	
    	public String getLabel()
    	{
    		return label;
    	}
    	public String toString()
    	{
    		if(label==null)
    			return page;
    		else
    			return label;
    	}
    	    	
    }
    
    public static void main(String[] args)
    {
    	PageResult<Integer> rs=new PageResult<Integer>();
    	rs.setPageNo(129);
    	rs.setPageSize(10);
    	rs.setTotal(1290);
    	List<Integer> list=new ArrayList<Integer>();
    	list.add(1);
    	list.add(2);
    	list.add(3);
    	list.add(4);
    	rs.setValues(list);
    	
    	for(Object o:rs.getValues())
    	{
    		System.out.println(o);
    	}
    }
}
