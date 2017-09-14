package com.spring.aop;

import java.lang.reflect.Field;  
import java.lang.reflect.Method;  
import javassist.ClassClassPath;  
import javassist.ClassPool;  
import javassist.CtClass;  
import javassist.CtMethod;  
import javassist.Modifier;  
import javassist.NotFoundException;  
import javassist.bytecode.CodeAttribute;  
import javassist.bytecode.LocalVariableAttribute;  
import javassist.bytecode.MethodInfo;  

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import com.spring.utils.GsonUtil;

//@Component  
//@Aspect 
public class LogAspect {
	
	private static String[] types = { "java.lang.Integer", "java.lang.Double",  
            "java.lang.Float", "java.lang.Long", "java.lang.Short",  
            "java.lang.Byte", "java.lang.Boolean", "java.lang.Char",  
            "java.lang.String", "int", "double", "long", "short", "byte",  
            "boolean", "char", "float" };  
	/**
	 * 在核心业务执行前执行，不能阻止核心业务的调用。
	 * 
	 * @param joinPoint
	 */
	@SuppressWarnings("unused")
	private void doBefore(JoinPoint joinPoint) {
		
		System.out.println("---------------");  
        String classType = joinPoint.getTarget().getClass().getName();  
        Class<?> clazz=null;
		try {
			clazz = Class.forName(classType);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        String clazzName = clazz.getName();  
        String clazzSimpleName = clazz.getSimpleName();  
        String methodName = joinPoint.getSignature().getName();  
          
        String[] paramNames=null;
		try {
			paramNames = getFieldsName(this.getClass(), clazzName, methodName);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        String logContent = writeLogInfo(paramNames, joinPoint);  
        System.out.println(logContent);
		System.out.println("-----doBefore().invoke-----");
		System.out.println(" 此处意在执行核心业务逻辑前，做一些安全性的判断等等");
		System.out.println(" 可通过joinPoint来获取所需要的内容");
		System.out.println("-----End of doBefore()------");
	}

	/**
	 * 手动控制调用核心业务逻辑，以及调用前和调用后的处理,
	 * 
	 * 注意：当核心业务抛异常后，立即退出，转向After Advice 执行完毕After Advice，再转到Throwing Advice
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("unused")
	private Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("-----doAround().invoke-----");
		System.out.println(" 此处可以做类似于Before Advice的事情");

		// 调用核心逻辑
		Object retVal = pjp.proceed();

		System.out.println(" 此处可以做类似于After Advice的事情");
		System.out.println("-----End of doAround()------");
		return retVal;
	}

	/**
	 * 核心业务逻辑退出后（包括正常执行结束和异常退出），执行此Advice
	 * 
	 * @param joinPoint
	 */
	@SuppressWarnings("unused")
	private void doAfter(JoinPoint joinPoint) {
		System.out.println("-----doAfter().invoke-----");
		System.out.println(" 此处意在执行核心业务逻辑之后，做一些日志记录操作等等");
		System.out.println(" 可通过joinPoint来获取所需要的内容");
		System.out.println("-----End of doAfter()------");
	}

	/**
	 * 核心业务逻辑调用正常退出后，不管是否有返回值，正常退出后，均执行此Advice
	 * 
	 * @param joinPoint
	 */
	@SuppressWarnings("unused")
	private void doReturn(JoinPoint joinPoint) {
		System.out.println("-----doReturn().invoke-----");
		System.out.println(" 此处可以对返回值做进一步处理");
		System.out.println(" 可通过joinPoint来获取所需要的内容");
		System.out.println("-----End of doReturn()------");
		Object object[] = joinPoint.getArgs(); // 获取被切函数 的参数
		for (int i = 0; i < object.length; i++) {
			System.out.println(GsonUtil.GsonString(object[i]));
		}
		
	}

	/**
	 * 核心业务逻辑调用异常退出后，执行此Advice，处理错误信息
	 * 
	 * @param joinPoint
	 * @param ex
	 */
	@SuppressWarnings("unused")
	private void doThrowing(JoinPoint joinPoint, Throwable ex) {
		System.out.println("-----doThrowing().invoke-----");
		System.out.println(" 错误信息：" + ex.getMessage());
		System.out.println(" 此处意在执行核心业务逻辑出错时，捕获异常，并可做一些日志记录操作等等");
		System.out.println(" 可通过joinPoint来获取所需要的内容");
		System.out.println("-----End of doThrowing()------");
	}
	
	 private static String writeLogInfo(String[] paramNames, JoinPoint joinPoint){  
	        Object[] args = joinPoint.getArgs();  
	        StringBuilder sb = new StringBuilder();  
	        boolean clazzFlag = true;  
	        for(int k=0; k<args.length; k++){  
	            Object arg = args[k];  
	            sb.append(paramNames[k]+"");  
	            // 获取对象类型  
	            String typeName = arg.getClass().getName();  
	              
	            for (String t : types) {  
	                if (t.equals(typeName)) {  
	                    sb.append("=" + arg+"; ");  
	                }  
	            }  
	            if (clazzFlag) {  
	                sb.append(getFieldsValue(arg));  
	            }  
	        }  
	        return joinPoint.getTarget().getClass().getName()+"."+joinPoint.getSignature().getName()+" -- "+sb.toString();  
	    }  
	/** 
     * 得到方法参数的名称 
     * @param cls 
     * @param clazzName 
     * @param methodName 
     * @return 
     * @throws NotFoundException 
     */  
    @SuppressWarnings("rawtypes")
	private static String[] getFieldsName(Class cls, String clazzName, String methodName) throws NotFoundException{  
        ClassPool pool = ClassPool.getDefault();  
        //ClassClassPath classPath = new ClassClassPath(this.getClass());  
        ClassClassPath classPath = new ClassClassPath(cls);  
        pool.insertClassPath(classPath);  
          
        CtClass cc = pool.get(clazzName);  
        CtMethod cm = cc.getDeclaredMethod(methodName);  
        MethodInfo methodInfo = cm.getMethodInfo();  
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();  
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);  
        if (attr == null) {  
            // exception  
        }  
        String[] paramNames = new String[cm.getParameterTypes().length];  
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;  
        for (int i = 0; i < paramNames.length; i++){  
            paramNames[i] = attr.variableName(i + pos); //paramNames即参数名  
        }  
        return paramNames;  
    }  
    /** 
     * 得到参数的值 
     * @param obj 
     */  
    public static String getFieldsValue(Object obj) {  
        Field[] fields = obj.getClass().getDeclaredFields();  
        String typeName = obj.getClass().getName();  
        for (String t : types) {  
            if(t.equals(typeName))  
                return "";  
        }  
        StringBuilder sb = new StringBuilder();  
        sb.append("【");  
        for (Field f : fields) {  
            f.setAccessible(true);  
            try {  
                for (String str : types) {  
                    if (f.getType().getName().equals(str)){  
                        sb.append(f.getName() + " = " + f.get(obj)+"; ");  
                    }  
                }  
            } catch (IllegalArgumentException e) {  
                e.printStackTrace();  
            } catch (IllegalAccessException e) {  
                e.printStackTrace();  
            }  
        }  
        sb.append("】");  
        return sb.toString();  
    }  

}
