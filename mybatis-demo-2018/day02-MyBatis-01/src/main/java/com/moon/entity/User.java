package com.moon.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户实体类
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; //int(11) NOT NULL AUTO_INCREMENT
	private String username; //varchar(32) NOT NULL COMMENT '用户名称'
	private Date birthday; //date DEFAULT NULL COMMENT '生日'
	private String sex; //char(1) DEFAULT NULL COMMENT '性别'
	private String address; //varchar(256) DEFAULT NULL COMMENT '地址'

	// 建立用户与订单的一对多的关联关系(在MyBatis框架建议使用list比较方便)
	private List<Order> orderList;

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", birthday=" + birthday + ", sex=" + sex + ", address="
				+ address + "]";
	}

}
