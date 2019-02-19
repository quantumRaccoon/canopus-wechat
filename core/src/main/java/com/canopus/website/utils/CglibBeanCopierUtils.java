package com.canopus.website.utils;


import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: dai-ych
 * @Date: 2019/1/25 14:20
 * @Description: PO 转换工具（浅复制）
 */
@Slf4j
public class CglibBeanCopierUtils {

    /**
     * 下划线字符串
     */
    private static String BEAN_COPIER_UNDERLINE = "_";

    private static Lock initLock = new ReentrantLock(true); // 公平锁

    private static Map<String, BeanCopier> beanCopierMap = new HashMap<String, BeanCopier>();


    /**
     * 初始化 BeanCopier
     *
     * @param source 资源类
     * @param target 目标类
     */
    private static BeanCopier initCopier(Class source, Class target) {
        initLock.lock();
        BeanCopier find = beanCopierMap.get(source.getName() + BEAN_COPIER_UNDERLINE + target.getName());
        if (find != null) {
            initLock.unlock();
            return find;
        }
        BeanCopier beanCopier = BeanCopier.create(source, target, false);
        beanCopierMap.put(source.getName() + "_" + target.getName(), beanCopier);
        initLock.unlock();
        return beanCopier;
    }


    /**
     * 获取BeanCopier
     */
    private static BeanCopier getBeanCopier(Class source, Class target) {
        System.out.println(source.getName());
        BeanCopier beanCopier = beanCopierMap.get(source.getName() + BEAN_COPIER_UNDERLINE + target.getName());
        if (beanCopier != null) {
            return beanCopier;
        }
        return initCopier(source, target);
    }


    /**
     * Pojo 类型转换（浅复制，字段名&类型相同则被复制）
     *
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        if (null == source) {
            return null;
        }
        BeanCopier beanCopier = getBeanCopier(source.getClass(), targetClass);
        try {
            T target = targetClass.newInstance();
            beanCopier.copy(source, target, null);
            return target;

        } catch (Exception e) {
            log.error("对象拷贝失败,{}", e.getMessage());
            throw new RuntimeException("对象拷贝失败" + source + "_" + targetClass);
        }
    }


    /**
     * Pojo 类型转换（浅复制，字段名&类型相同则被复制）
     *
     * @param source
     * @param targetClass
     * @param <E>
     * @return
     */
    public static <E> List<E> copyProperties(List source, Class<E> targetClass) {
        if (source == null) {
            return null;
        }
        try {
            if (source.isEmpty()) {
                return source.getClass().newInstance();
            }
            List result = source.getClass().newInstance();
            for (Object each : source) {
                result.add(copyProperties(each, targetClass));
            }
            return result;
        } catch (Exception e) {
            log.error("对象拷贝失败,{}", e);
            throw new RuntimeException("对象拷贝失败" + source + "_" + targetClass);
        }
    }

    /**
     * Pojo 类型转换（浅复制，字段名&类型相同则被复制）
     */
//    public static <T> PageList<T> convert(PageList source,Class<T> targetClass){
//        if(source==null){
//            return null;
//        }
//        List<T> list  = convert((List)source,targetClass);
//        PageList<T> result = new PageList<>(source.getPaginator());
//        result.addAll(list);
//        return result;
//    }


    /**
     * bean属性转换
     */
//    public static void copyProperties(Object source,Object target){
//        String beanKey = generateKey(source.getClass(),target.getClass());
//        BeanCopier copier = null;
//        if (!beanCopierMap.containsKey(beanKey)) {
//            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
//            beanCopierMap.put(beanKey, copier);
//        }else {
//            copier = beanCopierMap.get(beanKey);
//        }
//        copier.copy(source, target, null);
//    }
    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }
    /*注：
    (1)相同属性名，且类型不匹配时候的处理，ok，但是未满足的属性不拷贝；
    (2)get和set方法不匹配的处理，创建拷贝的时候报错，无法拷贝任何属性(当且仅当sourceClass的get方法超过set方法时出现)
    (3)BeanCopier
    初始化例子：BeanCopier copier = BeanCopier.create(Source.class, Target.class, useConverter=true)
    第三个参数userConverter,是否开启Convert,默认BeanCopier只会做同名，同类型属性的copier,否则就会报错.
    copier = BeanCopier.create(source.getClass(), target.getClass(), false);
    copier.copy(source, target, null);
    (4)修复beanCopier对set方法强限制的约束
    改写net.sf.cglib.beans.BeanCopier.Generator.generateClass(ClassVisitor)方法
    将133行的
    MethodInfo write = ReflectUtils.getMethodInfo(setter.getWriteMethod());
    预先存一个names2放入
     109        Map names2 = new HashMap();
     110        for (int i = 0; i < getters.length; ++i) {
     111          names2.put(setters[i].getName(), getters[i]);
                }
    调用这行代码前判断查询下，如果没有改writeMethod则忽略掉该字段的操作，这样就可以避免异常的发生。*/


}
