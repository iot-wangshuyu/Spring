package com.spring.service;

import org.springframework.stereotype.Service;

import com.shuyu.bean.UserBean;

@Service
public class AopService {
	// �û�����
	public void login(int user,int pass) {
		System.out.println("����ɹ�");
		UserBean user1=new UserBean();
		user1.setId(user);
		user1.setPassword(String.valueOf(pass));
//		return user1;
	}

	// �û��˳�
	public void loginOut() {
		System.out.println("�û��˳�ϵͳ");
	}

	// �û�����
	public void writeABlog() {
		System.out.println("�û���д����");
	}

	// �û�����
	public void deleteABlog() {
		System.out.println("�û�ɾ������");
	}

}
