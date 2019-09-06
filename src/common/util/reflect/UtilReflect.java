package common.util.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UtilReflect {
	private String className;
	private String methodName;
	private Object[] params;
	private Class<?>[] paramType;
	private Class<?> cl;
	private Method method;
	private Object obj;

	public UtilReflect(Object obj, String clName, String mName, Object[] param,
			Class<?>... pType) {
		this.className = clName;
		this.methodName = mName;
		this.params = param;
		this.paramType = pType;
		this.obj = obj;
	}

	// <action>com.ts.app.AppTableView.addTab(String tabText)</action>
	// 集成单体模式的映射执行
	public UtilReflect(String action, Object[] params) {
		if (!"".equals(action) && (action.lastIndexOf(".") >= 0)) {
			String classAction = action;
			String paramAction = "";
			if (action.indexOf("(") >= 0) {
				classAction = action.substring(0, action.indexOf("("));
				paramAction = action.substring(action.indexOf("(") + 1);
				// System.out.println(paramAction);
				paramAction = paramAction.substring(0, paramAction.length()-1);
			}

			this.className = classAction.substring(0, classAction
					.lastIndexOf("."));
			if (this.className.endsWith("getInstance")) {
				this.className = className.substring(0, this.className
						.lastIndexOf("."));
				this.methodName = "getInstance";
				this.obj = getInstance();// 获取单体对象
			}
			// System.out.println(className + " " + methodName);
			this.params = params;
			// System.out.println(classAction);
			this.methodName = classAction.substring(classAction
					.lastIndexOf(".") + 1);
			// System.out.println(methodName);
			this.paramType = null;
			if (!"".equals(paramAction)) {
				String[] param = paramAction.split(",");
				Class<?>[] pType = new Class<?>[param.length];
				for (int i = 0; i < param.length; i++) {
					try {
						pType[i] = Class.forName(param[i].split(" ")[0]);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				this.paramType = pType;
			}
		}
	}

	// 对象执行方法
	public Object invoke() {
		Object object = null;
		try {
			if (className == null)
				return null;
			cl = Class.forName(className);
			if (this.paramType == null)
				method = cl.getMethod(methodName);
			else
				method = cl.getMethod(methodName, this.paramType);

			if (obj == null)
				obj = (Object) cl.newInstance();
			if (paramType == null)
				object = method.invoke(obj);
			else
				object = method.invoke(obj, params);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return object;
	}

	// 单体模式获取对象实例
	private Object getInstance() {
		Object object = null;
		try {
			cl = Class.forName(className);
			method = cl.getMethod(methodName);
			object = method.invoke(cl);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return object;
	}

	public Class<?> getCl() {
		return cl;
	}

	public Method getMethod() {
		return method;
	}

	public Object getObj() {
		return obj;
	}

}
