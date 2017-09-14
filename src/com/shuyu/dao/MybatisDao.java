package com.shuyu.dao;

import java.util.List;

import com.shuyu.bean.UserBean;

public interface MybatisDao {
	
	List<UserBean> getUser();
	
	int updateUser(UserBean user);
}
