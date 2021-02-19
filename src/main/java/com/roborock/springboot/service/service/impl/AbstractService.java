package com.roborock.springboot.service.service.impl;

import com.roborock.springboot.service.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author BoHanZhang
 * @Date Create in 2021/2/18 15:45
 * @Description
 */
public abstract class AbstractService<T> implements BaseService<T> {

    @Autowired
    private Mapper<T> mapper;

    /**
     * 根据id查询数据
     *
     * @param id
     * @return
     */
    public T queryById(String id) {
        return mapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    public List<T> queryAll() {
        return mapper.selectAll();
    }

    /**
     * 根据条件查询一条数据，如果有多条数据会抛出异常
     *
     * @param record
     * @return
     */
    public T queryOne(T record) {
        return mapper.selectOne(record);
    }

    /**
     * 根据条件查询数据列表
     *
     * @param record
     * @return
     */
    public List<T> queryListByWhere(T record) {
        return mapper.select(record);
    }

    /**
     * 新增数据，返回成功的条数
     *
     * @param record
     * @return
     */
    public Integer save(T record) {
        return mapper.insert(record);
    }

    /**
     * 新增数据，使用不为null的字段，返回成功的条数
     *
     * @param record
     * @return
     */
    public Integer saveSelective(T record) {
        return mapper.insertSelective(record);
    }

    /**
     * 修改数据，返回成功的条数
     *
     * @param record
     * @return
     */
    public Integer update(T record) {
        return mapper.updateByPrimaryKey(record);
    }

    /**
     * 修改数据，使用不为null的字段，返回成功的条数
     *
     * @param record
     * @return
     */
    public Integer updateSelective(T record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据id删除数据
     *
     * @param id
     * @return
     */
    public Integer deleteById(String id) {
        return mapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     *
     * @param clazz
     * @param property
     * @param values
     * @return
     */
    public Integer deleteByIds(Class<T> clazz, String property, List<Object> values) {
        Example example = new Example(clazz);
        example.createCriteria().andIn(property, values);
        return mapper.deleteByExample(example);
    }

    /**
     * 根据条件做删除
     *
     * @param record
     * @return
     */
    public Integer deleteByWhere(T record) {
        return mapper.delete(record);
    }
}
