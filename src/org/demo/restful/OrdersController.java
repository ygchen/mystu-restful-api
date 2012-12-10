package org.demo.restful;

import java.util.Collection;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAwareSupport;

import edu.stu.user.service.UserService;

@Results({	
    @Result(name="success", type="redirectAction", params = {"actionName" , "orders"})
})
@Namespace(value="/rest/example")

public class OrdersController extends ValidationAwareSupport implements ModelDriven<Object>, Validateable{
    
    private Order model;
    private String userId;
    private Collection<Order> list;
    private OrdersService ordersService = new OrdersService();
    
    private UserService userService;
    
    
    
    public void setUserService(UserService userService) {
		this.userService = userService;
		System.err.println("userservice setted______________________-");
	}

	// GET /orders/1
    public HttpHeaders show() {
        return new DefaultHttpHeaders("show");
    }

    // GET /orders
    public HttpHeaders index() {
        list = ordersService.getAll();
        return new DefaultHttpHeaders("index")
            .disableCaching();
    }
    
    // GET /orders/1/edit
    public String edit() {
        return "edit";
    }

    // GET /orders/new
    public String editNew() {
        model = new Order();
        return "editNew";
    }

    // GET /orders/1/deleteConfirm
    public String deleteConfirm() {
        return "deleteConfirm";
    }

    // DELETE /orders/1
    public String destroy() {
        ordersService.remove(userId);
        addActionMessage("Order removed successfully");
        return "success";
    }

    // POST /orders
    public HttpHeaders create() {
        ordersService.save(model);
        addActionMessage("New order created successfully");
        return new DefaultHttpHeaders("success")
            .setLocationId(model.getId());
    }

    // PUT /orders/1    
    public String update() {
        ordersService.save(model);
        addActionMessage("Order updated successfully");
        return "success";
    }

    public void validate() {
        if (model.getClientName() == null || model.getClientName().length() ==0) {
            addFieldError("clientName", "The client name is empty");
        }
    }

    public void setUserId(String id) {
        if (id != null) {
            this.model = ordersService.get(id);
        }
        this.userId = id;
    }
    
    public Object getModel() {
    	if(list!=null)
    		return list ;
    	if( model==null)
    		model=new Order();
    	return model;
    }

    public void setStatusCode(int statusCode)
    {
    	System.err.println(statusCode);
    }
}
