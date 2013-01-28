package edu.stu.struts;
/*
 * $Id: JsonLibHandler.java 1097172 2011-04-27 16:36:54Z jogep $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;

import org.apache.struts2.StrutsConstants;
import org.apache.struts2.rest.handler.ContentTypeHandler;

import com.opensymphony.xwork2.inject.Inject;

/**
 * Handles JSON content using json-lib
 */
public class JSONHandler implements ContentTypeHandler {

    private static final String DEFAULT_CONTENT_TYPE = "application/json";
    private String defaultEncoding = "UTF-8";
    private JsonConfig jsonConfig;
    
    public JSONHandler()
    {
    	jsonConfig=new JsonConfig();
    	DefaultValueProcessor vp=new DefaultValueProcessor() {			
			@Override
			public Object getDefaultValue(Class arg0) {
				return JSONNull.getInstance();
			}
		};		
    	jsonConfig.registerDefaultValueProcessor(String.class, vp);    	
    	jsonConfig.registerDefaultValueProcessor(Integer.class,vp);
    	jsonConfig.registerDefaultValueProcessor(Long.class,vp);
    	jsonConfig.registerDefaultValueProcessor(Double.class,vp);
    	jsonConfig.registerDefaultValueProcessor(Float.class,vp);
    	    
    }
    
    public void toObject(Reader in, Object target) throws IOException {
        StringBuilder sb = new StringBuilder();
        char[] buffer = new char[1024];
        int len = 0;
        while ((len = in.read(buffer)) > 0) {
            sb.append(buffer, 0, len);
        }
        if (target != null && sb.length() > 0 && sb.charAt(0) == '[') {
            JSONArray jsonArray = JSONArray.fromObject(sb.toString());
            if (target.getClass().isArray()) {
                JSONArray.toArray(jsonArray, target, new JsonConfig());
            } else {
                JSONArray.toList(jsonArray, target, new JsonConfig());
            }

        } else {
            JSONObject jsonObject = JSONObject.fromObject(sb.toString());
            JSONObject.toBean(jsonObject, target, new JsonConfig());
        }
    }

    public String fromObject(Object obj, String resultCode, Writer stream) throws IOException {
        if (obj != null) {
            if (isArray(obj)) {
                JSONArray jsonArray = JSONArray.fromObject(obj,this.jsonConfig);
                stream.write(jsonArray.toString());
            } else {
                JSONObject jsonObject = JSONObject.fromObject(obj,this.jsonConfig);
                stream.write(jsonObject.toString());
            }
        }
        return null;


    }

    private boolean isArray(Object obj) {
        return obj instanceof Collection || obj.getClass().isArray();
    }

    public String getContentType() {
        return DEFAULT_CONTENT_TYPE+";charset=" + this.defaultEncoding;
    }
    
    public String getExtension() {
        return "json";
    }
    
    @Inject(StrutsConstants.STRUTS_I18N_ENCODING)
    public void setDefaultEncoding(String val) {
        this.defaultEncoding = val;
    }
}
