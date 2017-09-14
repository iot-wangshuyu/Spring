package com.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shuyu.bean.UserBean;
import com.shuyu.dao.MybatisDao;
import com.spring.service.MybatisService;

@Service
public class MybatisServiceImpl implements MybatisService {
	private MybatisDao mybatisDao;
	
	


	public MybatisDao getMybatisDao() {
		return mybatisDao;
	}




	public void setMybatisDao(MybatisDao mybatisDao) {
		this.mybatisDao = mybatisDao;
	}




	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 3)
	@Override
	public int TestTR() {
		UserBean user = new UserBean(1, "tom", "00");
		mybatisDao.updateUser(user);
		int a = 3 / 0;
		UserBean user2 = new UserBean(2, "jack", "00");
		mybatisDao.updateUser(user2);
		return 0;
	}

}
