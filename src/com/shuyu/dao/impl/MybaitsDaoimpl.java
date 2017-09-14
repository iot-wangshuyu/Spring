package com.shuyu.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shuyu.bean.UserBean;
import com.shuyu.dao.MybatisDao;
import com.spring.utils.MybatisUtil;

@Repository
public class MybaitsDaoimpl implements MybatisDao{
	
	

	@Override
	public List<UserBean> getUser() {
		List<UserBean> list=new ArrayList<>();
		SqlSession session=MybatisUtil.getSqlSession();
		try {
//			list= session.selectList("UserMapper.selectAllUser", UserMapper.class);
			System.out.println("≤È—Ø");
			MybatisDao mapper = session.getMapper(MybatisDao.class);
			list= mapper.getUser();
			session.commit();
		} catch (Exception e) {
			session.rollback();
		}finally {
			MybatisUtil.closeSqlSession();
		}
		return list;
	}

	@Override
	public int updateUser(UserBean user) {
		SqlSession session=MybatisUtil.getSqlSession();
		int updateUser=0;
		try {
			MybatisDao mapper = session.getMapper(MybatisDao.class);
			updateUser = mapper.updateUser(user);
			session.commit();
		} catch (Exception e) {
			session.rollback();
		}finally {
			MybatisUtil.closeSqlSession();
		}
		return updateUser;
	}
	

}
