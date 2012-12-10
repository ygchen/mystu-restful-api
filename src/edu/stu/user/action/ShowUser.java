package edu.stu.user.action;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import edu.stu.user.bean.UserContact;

public class ShowUser extends AbstractAction {

	public String execute()
	{

		UserContact uc=this.getUserService().getUserContact(getUserId());
		this.setJsonResult(JSONObject.toJSONString(uc,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteMapNullValue));
		return JSON;
	}
	
	public static void  main(String[] args) {
		
		UserContact uc=new UserContact();
		System.err.println(JSONObject.toJSONString(uc,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteMapNullValue));
	}
}
