package com.nh.esb.manweb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.nh.esb.core.NhEsbAddress;

@Component
public class AddressDao {
@Resource
private JdbcTemplate dbTemplate;
public List getAddressList(){
	List retList=null;
	String sql="select * from nhesb_service_address order by sysid asc";
	retList=dbTemplate.queryForList(sql);
	return retList;
}
public List<NhEsbAddress> getAddressList4Bean(){
	List retList=null;
	String sql="select * from nhesb_service_address order by sysid asc";
	retList=dbTemplate.query(sql,new RowMapper<NhEsbAddress>(){

		public NhEsbAddress mapRow(ResultSet resultSet, int arg1) throws SQLException {
			NhEsbAddress address=new NhEsbAddress();
			address.setUuid(resultSet.getString("uuid"));
			address.setIp(resultSet.getString("ip"));
			address.setPort(resultSet.getString("port"));
			address.setSysid(resultSet.getString("sysid"));
			address.setUrl(resultSet.getString("url"));
			return address;
		}});
	return retList;
}
}
