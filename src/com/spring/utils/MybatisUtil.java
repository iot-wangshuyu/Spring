package com.spring.utils;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MybatisUtil {
	private static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>();
	@Autowired
	private static SqlSessionFactory sqlSessionFactory;
	

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public static void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		MybatisUtil.sqlSessionFactory = sqlSessionFactory;
	}

	/**
	 * 禁止外界通过new方法创建
	 */
	private MybatisUtil() {
	}

	/**
	 * 获取SqlSession
	 */
	public static SqlSession getSqlSession() {
		// 从当前线程中获取SqlSession对象
		SqlSession sqlSession = threadLocal.get();
		// 如果SqlSession对象为空
		if (sqlSession == null) {
			// 在SqlSessionFactory非空的情况下，获取SqlSession对象
			sqlSession = sqlSessionFactory.openSession();
			// 将SqlSession对象与当前线程绑定在一起
			threadLocal.set(sqlSession);
		}
		// 返回SqlSession对象
		return sqlSession;
	}

	/**
	 * 关闭SqlSession与当前线程分开
	 */
	public static void closeSqlSession() {
		// 从当前线程中获取SqlSession对象
		SqlSession sqlSession = threadLocal.get();
		// 如果SqlSession对象非空
		if (sqlSession != null) {
			// 关闭SqlSession对象
			System.out.println("关闭连接");
			sqlSession.close();
			// 分开当前线程与SqlSession对象的关系，目的是让GC尽早回收
			threadLocal.remove();
		}
	}

}
