package com.nh.esb.manweb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nh.esb.manweb.dao.AddressDao;

@Controller
@RequestMapping(value="address")
public class AddressController {
@Resource
private AddressDao addressDao;
@RequestMapping(value="addressList")
public @ResponseBody Map addressList(HttpServletRequest request){
	List addressList=addressDao.getAddressList();
	Map retMap=new HashMap();
	retMap.put("rows", addressList);
	retMap.put("total", addressList.size());
	return retMap;
}

@RequestMapping(value="createAddress")
public @ResponseBody void createAddress(HttpServletRequest request){
	String submitFlag=request.getParameter("submitFlag");
	String ip=request.getParameter("ip");
	String port=request.getParameter("port");
	String url=request.getParameter("url");
	String sysid=request.getParameter("sysid");
	String uuid=request.getParameter("uuid");
	if(submitFlag.equals("add")){
		uuid=UUID.randomUUID().toString();
		Map param=new HashMap();
		param.put("uuid", uuid);
		param.put("ip", ip);
		param.put("port", port);
		param.put("url", url);
		param.put("sysid", sysid);
		addressDao.createAddress(param);
	}else{
		Map param=new HashMap();
		param.put("uuid", uuid);
		param.put("ip", ip);
		param.put("port", port);
		param.put("url", url);
		param.put("sysid", sysid);
		addressDao.modifyAddress(param);	
	}
	return ;
}
@RequestMapping(value="removeAddress")
public @ResponseBody void removeAddress(HttpServletRequest request){
	String uuid=request.getParameter("uuid");
	addressDao.removeAddress(uuid);
	return ;
}
}
