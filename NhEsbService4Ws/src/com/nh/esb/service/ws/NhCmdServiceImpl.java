package com.nh.esb.service.ws;

import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.nh.esb.core.INhCmdConst;
import com.nh.esb.core.INhCmdHandler;
import com.nh.esb.core.INhCmdService;
import com.nh.esb.core.NhCmdRequest;
import com.nh.esb.core.NhCmdResult;

/**
 * 
 * @author ninghao
 *
 */

@WebService
public class NhCmdServiceImpl implements INhCmdService,ApplicationContextAware {
	private static ApplicationContext context;//声明一个静态变量保存
	private static Boolean checkPassFlag=false;//检查用户密码开关
	private static Map passMap=new HashMap();
	public static Map getPassMap() {
		return passMap;
	}
	public static void setPassMap(Map passMap) {
		NhCmdServiceImpl.passMap = passMap;
	}
	public static Boolean getCheckPassFlag() {
		return checkPassFlag;
	}
	public  void setCheckPassFlag(Boolean checkPassFlag) {
		NhCmdServiceImpl.checkPassFlag = checkPassFlag;
	}
	@Override
	public void setApplicationContext(ApplicationContext contex)
	   throws BeansException {
	  this.context=contex;
	}
	public static ApplicationContext getContext(){
	  return context;
	}

	@Override
	public NhCmdResult execNhCmd(NhCmdRequest nhCmdRequest) {
		NhCmdResult result=new NhCmdResult();
		result.setRequestId(nhCmdRequest.getRequestId());
		if(checkPassFlag==true){
			String user=nhCmdRequest.getUser();
			String passWord=nhCmdRequest.getPassWord();
			String checkWord=(String) passMap.get(user);
			if(!passWord.equals(checkWord)){
				result.setResultStatus(INhCmdConst.STATUS_ERROR);
				result.setResultCode("password_error");
				return result;	
			}
		}
		String cmdName=nhCmdRequest.getCmdName();
		if(cmdName==null || "".equals(cmdName)){
			result.setResultStatus(INhCmdConst.STATUS_ERROR);
			result.setResultCode("cmdname_null");
			return result;
		}

		String handlerName="ws"+cmdName+"CmdHandler";
		INhCmdHandler handler=null;
		try{
			handler=(INhCmdHandler) getContext().getBean(handlerName);
		}catch(Exception ex){

			result.setResultStatus(INhCmdConst.STATUS_ERROR);
			result.setResultCode("handler_not_found");
			return result;			
		}
		try{
			handler.execHandler(nhCmdRequest,result);
		}catch(Exception ex2){
			result.setResultStatus(INhCmdConst.STATUS_ERROR);
			result.setResultCode("handler_exec_error");
			return result;	
		}
		
		return result;
	}

}
