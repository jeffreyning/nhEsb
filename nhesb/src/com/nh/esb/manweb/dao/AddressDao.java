package com.nh.esb.manweb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
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
public void createAddress(final Map param){
	String sql="insert into nhesb_service_address(uuid,ip,port,url,sysid) values(?,?,?,?,?)";
	dbTemplate.update(sql, new PreparedStatementSetter(){
		public void setValues(PreparedStatement statement) throws SQLException {
			statement.setString(1, (String) param.get("uuid"));
			statement.setString(2, (String) param.get("ip"));
			statement.setString(3, (String) param.get("port"));
			statement.setString(4, (String) param.get("url"));
			statement.setString(5, (String) param.get("sysid"));
			
		}
	});
	return ;
}
public void modifyAddress(final Map param){
	String sql="update nhesb_service_address set ip=?,port=?,url=?,sysid=? where uuid=?";
	dbTemplate.update(sql, new PreparedStatementSetter(){
		public void setValues(PreparedStatement statement) throws SQLException {
			statement.setString(5, (String) param.get("uuid"));
			statement.setString(1, (String) param.get("ip"));
			statement.setString(2, (String) param.get("port"));
			statement.setString(3, (String) param.get("url"));
			statement.setString(4, (String) param.get("sysid"));
			
		}
	});
	return ;
}
public void removeAddress(final String uuid){
	String sql="delete from nhesb_service_address where uuid=?";
	dbTemplate.update(sql, new PreparedStatementSetter(){
		public void setValues(PreparedStatement statement) throws SQLException {
			statement.setString(1, uuid);
		}
	});
	return ;
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
