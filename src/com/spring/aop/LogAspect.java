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
	 * �ں���ҵ��ִ��ǰִ�У�������ֹ����ҵ��ĵ��á�
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
		System.out.println(" �˴�����ִ�к���ҵ���߼�ǰ����һЩ��ȫ�Ե��жϵȵ�");
		System.out.println(" ��ͨ��joinPoint����ȡ����Ҫ������");
		System.out.println("-----End of doBefore()------");
	}

	/**
	 * �ֶ����Ƶ��ú���ҵ���߼����Լ�����ǰ�͵��ú�Ĵ���,
	 * 
	 * ע�⣺������ҵ�����쳣�������˳���ת��After Advice ִ�����After Advice����ת��Throwing Advice
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("unused")
	private Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("-----doAround().invoke-----");
		System.out.println(" �˴�������������Before Advice������");

		// ���ú����߼�
		Object retVal = pjp.proceed();

		System.out.println(" �˴�������������After Advice������");
		System.out.println("-----End of doAround()------");
		return retVal;
	}

	/**
	 * ����ҵ���߼��˳��󣨰�������ִ�н������쳣�˳�����ִ�д�Advice
	 * 
	 * @param joinPoint
	 */
	@SuppressWarnings("unused")
	private void doAfter(JoinPoint joinPoint) {
		System.out.println("-----doAfter().invoke-----");
		System.out.println(" �˴�����ִ�к���ҵ���߼�֮����һЩ��־��¼�����ȵ�");
		System.out.println(" ��ͨ��joinPoint����ȡ����Ҫ������");
		System.out.println("-----End of doAfter()------");
	}

	/**
	 * ����ҵ���߼����������˳��󣬲����Ƿ��з���ֵ�������˳��󣬾�ִ�д�Advice
	 * 
	 * @param joinPoint
	 */
	@SuppressWarnings("unused")
	private void doReturn(JoinPoint joinPoint) {
		System.out.println("-----doReturn().invoke-----");
		System.out.println(" �˴����ԶԷ���ֵ����һ������");
		System.out.println(" ��ͨ��joinPoint����ȡ����Ҫ������");
		System.out.println("-----End of doReturn()------");
		Object object[] = joinPoint.getArgs(); // ��ȡ���к��� �Ĳ���
		for (int i = 0; i < object.length; i++) {
			System.out.println(GsonUtil.GsonString(object[i]));
		}
		
	}

	/**
	 * ����ҵ���߼������쳣�˳���ִ�д�Advice�����������Ϣ
	 * 
	 * @param joinPoint
	 * @param ex
	 */
	@SuppressWarnings("unused")
	private void doThrowing(JoinPoint joinPoint, Throwable ex) {
		System.out.println("-----doThrowing().invoke-----");
		System.out.println(" ������Ϣ��" + ex.getMessage());
		System.out.println(" �˴�����ִ�к���ҵ���߼�����ʱ�������쳣��������һЩ��־��¼�����ȵ�");
		System.out.println(" ��ͨ��joinPoint����ȡ����Ҫ������");
		System.out.println("-----End of doThrowing()------");
	}
	
	 private static String writeLogInfo(String[] paramNames, JoinPoint joinPoint){  
	        Object[] args = joinPoint.getArgs();  
	        StringBuilder sb = new StringBuilder();  
	        boolean clazzFlag = true;  
	        for(int k=0; k<args.length; k++){  
	            Object arg = args[k];  
	            sb.append(paramNames[k]+"");  
	            // ��ȡ��������  
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
     * �õ��������������� 
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
            paramNames[i] = attr.variableName(i + pos); //paramNames��������  
        }  
        return paramNames;  
    }  
    /** 
     * �õ�������ֵ 
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
        sb.append("��");  
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
        sb.append("��");  
        return sb.toString();  
    }  

}
