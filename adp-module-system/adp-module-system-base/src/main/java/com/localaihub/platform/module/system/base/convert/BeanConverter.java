package com.localaihub.platform.module.system.base.convert;

import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 通用的Bean转换工具类，使用Spring的BeanUtils进行属性复制。
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/16 20:57
 */
public class BeanConverter {

    /**
     * 将source对象转换为目标类型的对象。
     *
     * @param source 源对象
     * @param targetClass 目标类型的Class
     * @param <T> 目标类型
     * @return 目标类型的对象
     */
    public static <T> T convertTo(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }

        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Conversion error: " + e.getMessage(), e);
        }
    }

    /**
     * 将source集合转换为目标类型的集合。
     *
     * @param sourceCollection 源集合
     * @param targetClass 目标类型的Class
     * @param <S> 源类型
     * @param <T> 目标类型
     * @return 目标类型的集合
     */
    public static <S, T> Set<T> convertToSet(Collection<S> sourceCollection, Class<T> targetClass) {
        if (sourceCollection == null) {
            return null;
        }

        return sourceCollection.stream()
                .map(source -> convertTo(source, targetClass))
                .collect(Collectors.toSet());
    }
}