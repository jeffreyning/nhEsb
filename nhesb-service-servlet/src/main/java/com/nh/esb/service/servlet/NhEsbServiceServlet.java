package com.nh.esb.service.servlet;



import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.nh.esb.core.INhCmdConst;
import com.nh.esb.core.INhCmdHandler;
import com.nh.esb.core.INhCmdService;
import com.nh.esb.core.NhCmdRequest;
import com.nh.esb.core.NhCmdResult;

/**
 * Servlet implementation class NhEsbServiceServlet
 */
public class NhEsbServiceServlet extends HttpServlet implements INhCmdService {
	private static Logger logger=Logger.getLogger(NhEsbServiceServlet.class);
	private static final long serialVersionUID = 1L;
	
	public ApplicationContext getContext(){
		return WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NhEsbServiceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmdData=request.getParameter("cmdData");
		String cmdName=request.getParameter("cmdName");
		String requestId=UUID.randomUUID().toString();
		String tempId=request.getParameter("requestId");
		if(tempId!=null && !tempId.equals("")){
			requestId=tempId;
		}
		String bizId=request.getParameter("bizId");
		String fromSysId=request.getParameter("fromSysId");
		String toSysId=request.getParameter("toSysId");
		String user=request.getParameter("user");
		String callType=request.getParameter("callType");		
		String fromModule=request.getParameter("fromModule");
		String remark=request.getParameter("remark");
		String subName=request.getParameter("subName");
		String ext1=request.getParameter("ext1");
		String ext2=request.getParameter("ext2");
		String ext3=request.getParameter("ext3");
		String ext4=request.getParameter("ext4");
		String ext5=request.getParameter("ext5");
		String sendTime=request.getParameter("sendTime");
		
		NhCmdRequest nhCmdRequest=new NhCmdRequest();
		nhCmdRequest.setBizId(bizId);
		nhCmdRequest.setCallType(callType);
		nhCmdRequest.setCmdData(cmdData);
		nhCmdRequest.setCmdName(cmdName);
		nhCmdRequest.setExt1(ext1);
		nhCmdRequest.setExt2(ext2);
		nhCmdRequest.setExt3(ext3);
		nhCmdRequest.setExt4(ext4);
		nhCmdRequest.setExt5(ext5);
		nhCmdRequest.setFromModule(fromModule);
		nhCmdRequest.setFromSysId(fromSysId);
		nhCmdRequest.setRemark(remark);
		nhCmdRequest.setRequestId(requestId);
		nhCmdRequest.setSendTime(sendTime);
		nhCmdRequest.setSubName(subName);
		nhCmdRequest.setToSysId(toSysId);

		NhCmdResult nhCmdResult=null;
		try {
			NhServletCmdContextHolder.removeNhServletCmdContext();
			NhServletCmdContext context=new NhServletCmdContext();
			context.setHttpRequest(request);
			context.setHttpResponse(response);
			context.setHttpSession(request.getSession());
			NhServletCmdContextHolder.getNhServletCmdContext().set(context);
			nhCmdResult=execNhCmd(nhCmdRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("execNhCmd error", e);
			throw new ServletException("execNhCmd error");
		}finally{
			NhServletCmdContextHolder.removeNhServletCmdContext();
		}
		
		String resultCode=nhCmdResult.getResultCode();
		if(resultCode!=null && "forward".equals(resultCode)){
			
		}
/*		if(ext1!=null && !ext1.equals("")){
			String toPage=ext1;
			request.setAttribute("nhCmdResult", nhCmdResult);
			request.getRequestDispatcher(toPage).forward(request, response);
		}*/
		else{
			String retStr=JSONObject.fromObject(nhCmdResult).toString();
			response.getOutputStream().write(retStr.getBytes("UTF-8"));
		}

	
	}
	
	@Override
	public NhCmdResult execNhCmd(NhCmdRequest nhCmdRequest) throws Exception {
		String requestId=nhCmdRequest.getRequestId();
		logger.info("execNhCmd_getin requestId="+requestId);
		NhCmdResult result=new NhCmdResult();
		result.setRequestId(nhCmdRequest.getRequestId());
		String cmdName=nhCmdRequest.getCmdName();
		if(cmdName==null || "".equals(cmdName)){
			result.setResultStatus(INhCmdConst.STATUS_ERROR);
			result.setResultCode("cmdname_null");
			logger.error("execNhCmd_cmdname_null requestId="+requestId);
			return result;
		}

		String handlerName="ws"+cmdName+"CmdHandler";
		INhCmdHandler handler=null;
		try{

			handler=(INhCmdHandler) getContext().getBean(handlerName);
		}catch(Exception ex){

			result.setResultStatus(INhCmdConst.STATUS_ERROR);
			result.setResultCode("handler_not_found");
			logger.error("execNhCmd_handler_not_found requestId="+requestId+" handlerName="+handlerName);
			return result;			
		}
		try{
			handler.execHandler(nhCmdRequest,result);
		}catch(Exception ex2){
			result.setResultStatus(INhCmdConst.STATUS_ERROR);
			result.setResultCode("handler_exec_error");
			logger.error("execNhCmd_handler_exec_error requestId="+requestId,ex2);
			return result;	
		}
		logger.info("execNhCmd_return requestId="+requestId);
		return result;
	}

}
