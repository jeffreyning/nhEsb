package com.nh.esb.manweb.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nh.esb.manweb.dao.AddressDao;

@Controller
@RequestMapping(value="address")
public class AddressController {
@Resource
private AddressDao addressDao;
@RequestMapping(value="addressList")
public String addressList(HttpServletRequest request,Model data){
	List addressList=addressDao.getAddressList();
	data.addAttribute("addressList", addressList);
	return "/jsp/addressList.jsp";
}
}
