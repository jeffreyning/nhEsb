package com.nh.esb.manweb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
