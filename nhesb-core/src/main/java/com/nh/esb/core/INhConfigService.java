package com.nh.esb.core;

import java.util.List;

import javax.jws.WebService;
/**
 * 
 * @author ninghao
 *
 */
@WebService
public interface INhConfigService {
public List<NhEsbAddress> getAddressList();
}
