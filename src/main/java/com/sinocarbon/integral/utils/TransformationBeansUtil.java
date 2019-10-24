package com.sinocarbon.integral.utils;

import java.lang.reflect.Method;
import java.util.List;

public class TransformationBeansUtil {

	@SuppressWarnings("unchecked")

	public static <S, T> List<T> populateList(List<S> source, List<T> target, Class<?> targetClass) {

		for (int i = 0; i < source.size(); i++) {

			try {

				Object object = targetClass.newInstance();

				target.add((T) object);

				populate(source.get(i), object);

			} catch (Exception e) {

				continue;// 某个方法反射异常

			}

		}

		return target;

	}

	/**
	 * 
	 * 将dto和entity之间的属性互相转换,dto中属性一般为String等基本类型,
	 * 
	 * 但是entity中可能有复合主键等复杂类型,需要注意同名问题
	 * 
	 * @param src
	 * 
	 * @param target
	 * 
	 */

	public static Object populate(Object source, Object target) {

		Method[] srcMethods = source.getClass().getMethods();

		Method[] targetMethods = target.getClass().getMethods();

		for (Method m : srcMethods) {

			String srcName = m.getName();

			if (srcName.startsWith("get")) {

				try {

					Object result = m.invoke(source);

					for (Method mm : targetMethods) {

						String targetName = mm.getName();

						if (targetName.startsWith("set") && targetName.substring(3, targetName.length())

								.equals(srcName.substring(3, srcName.length()))) {

							mm.invoke(target, result);

						}

					}

				} catch (Exception e) {

				}

			}

		}

		return target;

	}

}
