package com.spring.service;

import org.springframework.stereotype.Service;

import com.shuyu.bean.UserBean;

@Service
public class AopService {
	// 用户登入
	public void login(int user,int pass) {
		System.out.println("登入成功");
		UserBean user1=new UserBean();
		user1.setId(user);
		user1.setPassword(String.valueOf(pass));
//		return user1;
	}

	// 用户退出
	public void loginOut() {
		System.out.println("用户退出系统");
	}

	// 用户操作
	public void writeABlog() {
		System.out.println("用户编写博客");
	}

	// 用户操作
	public void deleteABlog() {
		System.out.println("用户删除博客");
	}

}
