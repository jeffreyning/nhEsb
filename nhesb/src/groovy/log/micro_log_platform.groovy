package groovy.log


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import com.nh.esb.core.NhCmdRequest;
import com.nh.micro.rule.engine.core.*;
import groovy.json.*;
import groovy.template.MicroMvcTemplate;
import com.project.util.HttpClientUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;

class MicroLogManager extends MicroMvcTemplate{
	private Properties proper=new Properties();
	private static Logger logger=Logger.getLogger(MicroLogManager.class);
	public String pageName="";
	public String tableName="";
	public String esUrl="";
	{
		proper.load(this.class.getClassLoader().getResourceAsStream("/nhesb.properties"));
		esUrl=proper.getProperty("esUrl");
	}


	
	public String getPageName(HttpServletRequest httpRequest){
		return pageName;
	}
	public String getTableName(HttpServletRequest httpRequest){
		return tableName;
	}

	public void queryLog(GInputParam gInputParam,GOutputParam gOutputParam,GContextParam gContextParam){
		HttpServletRequest httpRequest = gContextParam.getContextMap().get("httpRequest");
		HttpServletResponse httpResponse = gContextParam.getContextMap().get("httpResponse");
		
		String page=httpRequest.getParameter("page");
		String rows=httpRequest.getParameter("rows");
		String sort=httpRequest.getParameter("sort");
		String order=httpRequest.getParameter("order");
		
		Integer pageInt=Integer.valueOf(page);
		Integer rowsInt=Integer.valueOf(rows);
		Integer fromInt=(pageInt-1)*rowsInt;
		
		String indexName=httpRequest.getParameter("index_name");
		String fromSysId=httpRequest.getParameter("_fromSysId");
		String queryStr="";
		
		String fromQuery="";
		if(fromSysId!=null && !fromSysId.equals("")){
			fromQuery="{\"term\":{\"default_field\":\"esbmsg.fromSysId\",\"query\":\""+fromSysId+"\"}}";
		}
		if(fromQuery.length()>0){
			if(queryStr.length()>0){
				queryStr=queryStr+",";
			}
			queryStr=queryStr+fromQuery;
		}
		
		String toSysId=httpRequest.getParameter("_toSysId");
		String toQuery="";
		if(toSysId!=null && !toSysId.equals("")){
			toQuery="{\"query_string\":{\"default_field\":\"esbmsg.toSysId\",\"query\":\""+toSysId+"\"}}";
		}
		if(toQuery.length()>0){
			if(queryStr.length()>0){
				queryStr=queryStr+",";
			}
			queryStr=queryStr+toQuery;
		}
		
		String requestId=httpRequest.getParameter("_requestId");
		String reqIdQuery="";
		if(requestId!=null && !requestId.equals("")){
			reqIdQuery="{\"query_string\":{\"default_field\":\"esbmsg.requestId\",\"query\":\""+requestId+"\"}}";
		}
		if(reqIdQuery.length()>0){
			if(queryStr.length()>0){
				queryStr=queryStr+",";
			}
			queryStr=queryStr+reqIdQuery;
		}
				
		String bizId=httpRequest.getParameter("_bizId");
		String bizQuery="";
		if(bizId!=null && !bizId.equals("")){
			bizQuery="{\"query_string\":{\"default_field\":\"esbmsg.bizId\",\"query\":\""+bizId+"\"}}";
		}
		if(bizQuery.length()>0){
			if(queryStr.length()>0){
				queryStr=queryStr+",";
			}
			queryStr=queryStr+bizQuery;
		}
		
		String cmdName=httpRequest.getParameter("_cmdName");
		String cmdNameQuery="";
		if(cmdName!=null && !cmdName.equals("")){
			cmdNameQuery="{\"query_string\":{\"default_field\":\"esbmsg.cmdName\",\"query\":\""+cmdName+"\"}}";
		}
		if(cmdNameQuery.length()>0){
			if(queryStr.length()>0){
				queryStr=queryStr+",";
			}
			queryStr=queryStr+cmdNameQuery;
		}
				
		String subName=httpRequest.getParameter("_subName");
		String subNameQuery="";
		if(subName!=null && !subName.equals("")){
			subNameQuery="{\"query_string\":{\"default_field\":\"esbmsg.subName\",\"query\":\""+subName+"\"}}";
		}
		if(subNameQuery.length()>0){
			if(queryStr.length()>0){
				queryStr=queryStr+",";
			}
			queryStr=queryStr+subNameQuery;
		}
		
		String logMsg=httpRequest.getParameter("_log_msg");
		String msgQuery="";
		if(logMsg!=null && !logMsg.equals("")){
			msgQuery="{\"query_string\":{\"default_field\":\"esbmsg.cmdData\",\"query\":\""+logMsg+"\"}}";
		}
		

		if(msgQuery.length()>0){
			if(queryStr.length()>0){
				queryStr=queryStr+",";
			}
			queryStr=queryStr+msgQuery;
		}

		
		String es_url=getEsUrl();
		String url=es_url+"/"+indexName+"/_search";
		
		String sortStr="";
		if(sort!=null && !"".equals(sort)){
			sortStr="\"esbmsg.timestamp\":{\"order\":\""+order+"\"}";
		}
		String data="{\"query\":{\"bool\":{\"must\":["+queryStr+"],\"must_not\":[],\"should\":[]}},\"from\":"+fromInt+",\"size\":"+rowsInt+",\"aggs\":{},\"sort\":{"+sortStr+"}}";
		System.out.println(data);
				//Map paramMap=new HashMap();
		//paramMap.put("query", null);
		String ret=HttpClientUtil.post4ES(url, data);
		System.out.println(ret);
		Gson gson = new Gson();
		Map tempMap=gson.fromJson(ret,Map.class);
		Map retMap=new HashMap();
		Map statusMap=tempMap.get("hits");
		Long total=0l;
		List retList=new ArrayList();
		if(statusMap!=null){
			total=statusMap.get("total");
			List tempList=statusMap.get("hits");
			int size=tempList.size();
			for(int i=0;i<size;i++){
				Map rowMap=tempList.get(i);
				Map sourceMap=rowMap.get("_source");
				
				Map fieldMap=new HashMap();
				fieldMap.put("cmdName", sourceMap.get("cmdName"));
				fieldMap.put("cmdData", sourceMap.get("cmdData"));
				fieldMap.put("bizId", sourceMap.get("bizId"));
				fieldMap.put("callType", sourceMap.get("callType"));
				fieldMap.put("ext1", sourceMap.get("ext1"));
				fieldMap.put("ext2", sourceMap.get("ext2"));
				fieldMap.put("ext3", sourceMap.get("ext3"));
				fieldMap.put("ext4", sourceMap.get("ext4"));
				fieldMap.put("ext5", sourceMap.get("ext5"));
				
				fieldMap.put("fromModule", sourceMap.get("fromModule"));
				fieldMap.put("fromSysId", sourceMap.get("fromSysId"));
				fieldMap.put("remark", sourceMap.get("remark"));
				fieldMap.put("requestId", sourceMap.get("requestId"));
				fieldMap.put("sendTime", sourceMap.get("sendTime"));
				
				fieldMap.put("subName", sourceMap.get("subName"));
				fieldMap.put("toSysId", sourceMap.get("toSysId"));
				fieldMap.put("timestamp", sourceMap.get("timestamp"));

				retList.add(fieldMap);
			} 
		} 
		retMap.put("total", total);
		retMap.put("rows", retList);
		JsonBuilder jsonBuilder=new JsonBuilder(retMap);
		String retStr=jsonBuilder.toString();
		System.out.println(retStr);
		httpResponse.getOutputStream().write(retStr.getBytes("UTF-8"));
		httpRequest.setAttribute("forwardFlag", "true");
	}

	
	public void saveLog(GInputParam gInputParam,GOutputParam gOutputParam,GContextParam gContextParam){
		HttpServletRequest httpRequest = gContextParam.getContextMap().get("httpRequest");
		String recvMsg=httpRequest.getParameter("recvMsg");
		saveLog(recvMsg);
	}
	
	public void saveLog(String recvMsg){
		String es_url=getEsUrl();
		String indexName=createIndexName();
		boolean exitFlag=checkIndex(indexName);
		if(exitFlag==false){
			createIndex(indexName);
		}
		String url=es_url+"/"+indexName+"/esbmsg";
		

		Map tempMap=new JsonSlurper().parseText(recvMsg);
		Map logMap=new HashMap();
		
		logMap.put("cmdName", tempMap.get("cmdName"));
		logMap.put("cmdData", "\""+new JsonBuilder(tempMap.get("cmdData")).toString()+"\"");
		logMap.put("bizId", tempMap.get("bizId"));
		logMap.put("callType", tempMap.get("callType"));
		logMap.put("ext1", tempMap.get("ext1"));
		logMap.put("ext2", tempMap.get("ext2"));
		logMap.put("ext3", tempMap.get("ext3"));
		logMap.put("ext4", tempMap.get("ext4"));
		logMap.put("ext5", tempMap.get("ext5"));
		logMap.put("fromModule", tempMap.get("fromModule"));
		logMap.put("fromSysId", tempMap.get("fromSysId"));
		logMap.put("remark", tempMap.get("remark"));
		logMap.put("requestId", tempMap.get("requestId"));
		logMap.put("sendTime", tempMap.get("sendTime"));
		logMap.put("subName", tempMap.get("subName"));
		logMap.put("toSysId", tempMap.get("toSysId"));
		logMap.put("timestamp", new Date().getTime());
		
		String data=new JsonBuilder(logMap).toString();
		System.out.println(data);

		String ret=HttpClientUtil.post4ES(url,data);
		System.out.println(ret);
	}
	
	
	public void queryLogList(GInputParam gInputParam,GOutputParam gOutputParam,GContextParam gContextParam){
		HttpServletRequest httpRequest = gContextParam.getContextMap().get("httpRequest");
		HttpServletResponse httpResponse = gContextParam.getContextMap().get("httpResponse");
		String es_url=getEsUrl();

		String url=es_url+"/_stats/indices";
		String ret=HttpClientUtil.get(url);
		System.out.println(ret);
		Gson gson = new Gson();
		Map tempMap=gson.fromJson(ret,Map.class);
		Map retMap=new HashMap();
		Map statusMap=tempMap.get("indices");
		int total=0;
		List midList=new ArrayList();
		List retList=new ArrayList();
		if(statusMap!=null){
			Set<String> ks=statusMap.keySet();
			for(String key:ks){
				midList.add(key);
			}
		}
		Collections.sort(midList);
		for(String key:midList){
			Map tMap=new HashMap();
			tMap.put("index_name", key);
			retList.add(tMap);
		}
		
		retMap.put("total", retList.size());
		retMap.put("rows", retList);
		JsonBuilder jsonBuilder=new JsonBuilder(retMap);
		String retStr=jsonBuilder.toString();
		System.out.println(retStr);
		httpResponse.getOutputStream().write(retStr.getBytes("UTF-8"));
		httpRequest.setAttribute("forwardFlag", "true");
	}
	
	private String getEsUrl(){
		return this.esUrl;
	}
	
	public String createIndexName(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String indexName="nhesb_"+sdf.format(new Date());
		return indexName;
	}
	public boolean checkIndex(String indexName){
		String es_url=getEsUrl();
		String url=es_url+"/"+indexName+"/_stats/indices";
		String ret=HttpClientUtil.get(url);
		if(ret.contains(indexName)){
			return true;
		}
		return false;
	}
	
	public void createIndex(String indexName){
		String es_url=getEsUrl();
		String url=es_url+"/"+indexName;
		String ret=HttpClientUtil.put(url,new HashMap());
	}
}
