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
	 * ��ֹ���ͨ��new��������
	 */
	private MybatisUtil() {
	}

	/**
	 * ��ȡSqlSession
	 */
	public static SqlSession getSqlSession() {
		// �ӵ�ǰ�߳��л�ȡSqlSession����
		SqlSession sqlSession = threadLocal.get();
		// ���SqlSession����Ϊ��
		if (sqlSession == null) {
			// ��SqlSessionFactory�ǿյ�����£���ȡSqlSession����
			sqlSession = sqlSessionFactory.openSession();
			// ��SqlSession�����뵱ǰ�̰߳���һ��
			threadLocal.set(sqlSession);
		}
		// ����SqlSession����
		return sqlSession;
	}

	/**
	 * �ر�SqlSession�뵱ǰ�̷ֿ߳�
	 */
	public static void closeSqlSession() {
		// �ӵ�ǰ�߳��л�ȡSqlSession����
		SqlSession sqlSession = threadLocal.get();
		// ���SqlSession����ǿ�
		if (sqlSession != null) {
			// �ر�SqlSession����
			System.out.println("�ر�����");
			sqlSession.close();
			// �ֿ���ǰ�߳���SqlSession����Ĺ�ϵ��Ŀ������GC�������
			threadLocal.remove();
		}
	}

}
