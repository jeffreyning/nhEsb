package com.nh.esb.service.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.jws.WebService;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.nh.esb.core.INhCmdConst;
import com.nh.esb.core.INhCmdHandler;
import com.nh.esb.core.INhCmdService;
import com.nh.esb.core.NhCmdRequest;
import com.nh.esb.core.NhCmdResult;
import com.nh.esb.core.PassUtil;


import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


/**
 * 
 * @author ninghao
 *
 */


public class NhNtCmdServiceImpl extends ChannelInboundHandlerAdapter implements INhCmdService,ApplicationContextAware {
	private static Logger logger=Logger.getLogger(NhNtCmdServiceImpl.class);
	private static ApplicationContext context;//声明一个静态变量保存
	private static Boolean checkPassFlag=false;//检查用户密码开关
	private static Map passMap=new HashMap();
	public static Map getPassMap() {
		return passMap;
	}
	public static void setPassMap(Map passMap) {
		NhNtCmdServiceImpl.passMap = passMap;
	}
	public static Boolean getCheckPassFlag() {
		return checkPassFlag;
	}
	public  void setCheckPassFlag(Boolean checkPassFlag) {
		NhNtCmdServiceImpl.checkPassFlag = checkPassFlag;
	}
	@Override
	public void setApplicationContext(ApplicationContext contex)
	   throws BeansException {
	  this.context=contex;
	}
	public static ApplicationContext getContext(){
	  return context;
	}

	private boolean checkPass(NhCmdRequest nhCmdRequest){
		try{
		String user=nhCmdRequest.getUser();
		String passWord=nhCmdRequest.getPassWord();
		String pass=(String) passMap.get(user);
		String md5Str=PassUtil.createMd5(nhCmdRequest,pass);
		if(md5Str.equals(passWord)){
			return true;
		}
		}catch(Exception e){
			logger.error("execNhCmd_password_md5_error requestId="+nhCmdRequest.getRequestId());
			return false;
		}
		return false;
	}
	@Override
	public NhCmdResult execNhCmd(NhCmdRequest nhCmdRequest) {
		String requestId=nhCmdRequest.getRequestId();
		logger.info("execNhCmd_getin requestId="+requestId);
		NhCmdResult result=new NhCmdResult();
		result.setRequestId(nhCmdRequest.getRequestId());
		if(checkPassFlag==true){

			if(!checkPass(nhCmdRequest)){
				result.setResultStatus(INhCmdConst.STATUS_ERROR);
				result.setResultCode("password_error");
				logger.error("execNhCmd_password_error requestId="+requestId);
				return result;	
			}
		}
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



    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    	if (msg instanceof HttpContent) { 
    		HttpContent httpContent = (HttpContent) msg;   

            ByteBuf buf = httpContent.content();
            String recvString=buf.toString(io.netty.util.CharsetUtil.UTF_8);
            buf.release();

            NhCmdResult nhCmdResult=null;
            
            nhCmdResult=callEsbHandler(recvString);
			String res=JSONObject.fromObject(nhCmdResult).toString();
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                    OK, Unpooled.wrappedBuffer(res.getBytes("UTF-8")));
            response.headers().set(CONTENT_TYPE, "text/plain");
            response.headers().set(CONTENT_LENGTH,
                    response.content().readableBytes());
            response.headers().set(CONNECTION, Values.KEEP_ALIVE);


            ctx.write(response);
            ctx.flush();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    public NhCmdResult callEsbHandler(String  recvString){
		NhCmdResult nhCmdResult=null;    	
		try {    	
	        Map jobj=(Map)JSONObject.fromObject(recvString);
			String cmdData=(String) jobj.get("cmdData");
			String cmdName=(String) jobj.get("cmdName");
			String requestId=UUID.randomUUID().toString();
			String tempId=(String) jobj.get("requestId");
			if(tempId!=null && !tempId.equals("")){
				requestId=tempId;
			}
			String bizId=(String) jobj.get("bizId");
			String fromSysId=(String) jobj.get("fromSysId");
			String toSysId=(String) jobj.get("toSysId");
			String user=(String) jobj.get("user");
			String callType=(String) jobj.get("callType");		
			String fromModule=(String) jobj.get("fromModule");
			String remark=(String) jobj.get("remark");
			String subName=(String) jobj.get("subName");
			String ext1=(String) jobj.get("ext1");
			String ext2=(String) jobj.get("ext2");
			String ext3=(String) jobj.get("ext3");
			String ext4=(String) jobj.get("ext4");
			String ext5=(String) jobj.get("ext5");
			String sendTime=(String) jobj.get("sendTime");
			
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
	
			nhCmdResult=execNhCmd(nhCmdRequest);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return nhCmdResult;
    }
}
