package com.roborock.springboot.service.service;

import java.util.List;

/*
 *   抽象业务类，封装常用操作
 * */
public interface BaseService<T> {

    /**
     * 根据id查询数据
     *
     * @param id
     * @return
     */
    T queryById(String id);

    /**
     * 查询所有数据
     *
     * @return
     */
    List<T> queryAll();

    /**
     * 根据条件查询一条数据，如果有多条数据会抛出异常
     *
     * @param record
     * @return
     */
    T queryOne(T record);

    /**
     * 根据条件查询数据列表
     *
     * @param record
     * @return
     */
    List<T> queryListByWhere(T record);

    /**
     * 新增数据，返回成功的条数
     *
     * @param record
     * @return
     */
    Integer save(T record);

    /**
     * 新增数据，使用不为null的字段，返回成功的条数
     *
     * @param record
     * @return
     */
    Integer saveSelective(T record);

    /**
     * 修改数据，返回成功的条数
     *
     * @param record
     * @return
     */
    Integer update(T record);

    /**
     * 修改数据，使用不为null的字段，返回成功的条数
     *
     * @param record
     * @return
     */
    Integer updateSelective(T record);

    /**
     * 根据id删除数据
     *
     * @param id
     * @return
     */
    Integer deleteById(String id);

    /**
     * 批量删除
     *
     * @param clazz
     * @param property
     * @param values
     * @return
     */
    Integer deleteByIds(Class<T> clazz, String property, List<Object> values);

    /**
     * 根据条件做删除
     *
     * @param record
     * @return
     */
    Integer deleteByWhere(T record);
}
