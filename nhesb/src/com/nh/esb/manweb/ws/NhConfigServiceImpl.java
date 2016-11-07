package com.nh.esb.manweb.ws;

import java.util.List;

import javax.annotation.Resource;

import com.nh.esb.core.INhConfigService;
import com.nh.esb.core.NhEsbAddress;
import com.nh.esb.manweb.dao.AddressDao;

public class NhConfigServiceImpl implements INhConfigService {
	@Resource
	private AddressDao addressDao;
	public List<NhEsbAddress> getAddressList() {
		List addressList=addressDao.getAddressList4Bean();	
		return addressList;
	}

}
