package groovy.nhesb

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.nh.micro.rule.engine.core.*;

import groovy.template.MicroMvcTemplate;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import org.apache.log4j.Logger;

import groovy.json.*;

import com.project.util.ZooKeeperOperUtil;

class NhesbSysList extends MicroMvcTemplate{

	public String tableName="nhesb_sys_list";

	public String getTableName(HttpServletRequest httpRequest){
		return tableName;
	}
	
	public void getInfoBySysId(GInputParam gInputParam,GOutputParam gOutputParam,GContextParam gContextParam){
		HttpServletRequest httpRequest = gContextParam.getContextMap().get("httpRequest");
		HttpServletResponse httpResponse = gContextParam.getContextMap().get("httpResponse");
		String sysId=httpRequest.getParameter("sysId");
		String path="/systemRoot/"+sysId;
		List<String> childList=ZooKeeperOperUtil.getChild(path);
		List retList=new ArrayList();
		if(childList!=null){
			for(String nodeId:childList){
				String dataPath="/systemRoot/"+sysId+"/"+nodeId;
				byte[] data=ZooKeeperOperUtil.getData(dataPath);
				Map rowMap=new HashMap();
				rowMap.put("node_id", nodeId);
				rowMap.put("node_address", new String(data,"utf-8"));
				retList.add(rowMap);
			}
		}
		JsonBuilder jsonBuilder=new JsonBuilder(retList);
		String retStr=jsonBuilder.toString();
		httpResponse.getOutputStream().write(retStr.getBytes("UTF-8"));
		httpRequest.setAttribute("forwardFlag", "true");
	}

}
