package com.spring.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;  
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.shuyu.bean.Hello;
import com.shuyu.bean.Person;
import com.shuyu.bean.UserBean;
import com.shuyu.dao.MybatisDao;
import com.shuyu.dao.impl.MybaitsDaoimpl;
import com.spring.service.AopService;
import com.spring.service.MybatisService;
import com.spring.service.impl.MybatisServiceImpl;
import com.spring.utils.GsonUtil;  

public class SpringTest {
	@Autowired
	private static  MybatisDao mybatisDao=new MybaitsDaoimpl();
	@Autowired
	private static MybatisService mybatisService=new MybatisServiceImpl();
	public static void main(String[] args) {
		 //1. 创建Spring 的IOC容器  
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Spring.xml");  
          
        //2. 从容器中获取 Bean 其实就是new 的过程  
//        Hello helloWorld = (Hello) ctx.getBean(Hello.class);  
        // 也可以是 HelloWorld helloWorld = ctx.getBean(HelloWorld.class);  
          
        //3. 执行函数  
//        helloWorld.getHello();
//        System.out.println( helloWorld.getHello());
        
        
//        UserBean bean = ctx.getBean(UserBean.class);
        
//        System.out.println(bean.toString());
        
//        Person bean2 = ctx.getBean(Person.class);
//        Person bean3 = ctx.getBean(Person.class);
//        System.out.println(bean2.hashCode());
//        System.out.println(bean3.hashCode());
//        System.out.println(bean2==bean3);
        
        
//        AopService bean4 = ctx.getBean(AopService.class);
//        bean4.login(123,345);
//        System.out.println();
//        bean4.writeABlog();
        
        List<UserBean> user = mybatisDao.getUser();
        System.out.println(GsonUtil.GsonString(user));
        mybatisService.TestTR();
        user = mybatisDao.getUser();
        System.out.println(GsonUtil.GsonString(user));
	}
	


}
