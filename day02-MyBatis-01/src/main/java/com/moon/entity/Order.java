package com.moon.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单实体类
 * @author MoonZero
 */
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; //int(11) NOT NULL AUTO_INCREMENT,
	private Integer userId; //int(11) NOT NULL COMMENT '下单用户id',
	private String number; //varchar(32) NOT NULL COMMENT '订单号',
	private Date createtime; //datetime NOT NULL COMMENT '创建订单时间',
	private String note; //varchar(100) DEFAULT NULL COMMENT '备注',

	// 建立订单与用户一对一关系
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "Orders [id=" + id + ", userId=" + userId + ", number=" + number + ", createtime=" + createtime
				+ ", note=" + note + "]";
	}
}
