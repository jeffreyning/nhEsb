package groovy.log


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import com.nh.micro.rule.engine.core.*;
import groovy.json.*;
import groovy.template.MicroMvcTemplate;
import com.project.util.HttpClientUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;

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

		String logMsg=httpRequest.getParameter("log_msg");
		String msgQuery="";
		if(logMsg!=null && !logMsg.equals("")){
			msgQuery="{\"query_string\":{\"default_field\":\"esbmsg.cmdData\",\"query\":\""+logMsg+"\"}}";
		}
		
		String queryStr="";
		
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
				Map fieldMap=sourceMap.get("@fields");
				String fullMsg=sourceMap.get("@message");
				fieldMap.put("fullMsg", fullMsg);
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
		String es_url=getEsUrl();
		String indexName=createIndexName();
		boolean exitFlag=checkIndex(indexName);
		if(exitFlag==false){
			createIndex(indexName);
		}
		String url=es_url+"/"+indexName+"/esbmsg";
		String temp="{\"c1\":\"v1\"}";
		String data="{\"cmdName\":\"ttt1\",\"cmdData\":\"data={"+temp+"}\"}";
		Map paramMap=new HashMap();
		Map sMap=new HashMap();
		sMap.put("c1", "v1");
		sMap.put("c2", "v2");
		paramMap.put("cmdData", new JsonBuilder(sMap).toString());
		paramMap.put("cmdName", "tttt2");
		data=new JsonBuilder(paramMap).toString();
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
