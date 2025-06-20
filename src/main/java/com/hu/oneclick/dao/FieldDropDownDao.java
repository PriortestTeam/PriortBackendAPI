package com.hu.oneclick.dao;

import com.hu.oneclick.model.entity.FieldDropDown;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 下拉菜单(FieldDropDown)表数据库访问层
 *
 * @author makejava
 * @since 2020-12-05 21:49:12
 */
public interface FieldDropDownDao {

    /**
     * 通过ID查询单条数据
     *
     * @param 主键
     * @return 实例对象
     */
    FieldDropDown queryById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<FieldDropDown> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param fieldDropDown 实例对象
     * @return 对象列表
     */
    List<FieldDropDown> queryAll(FieldDropDown fieldDropDown);

    /**
     * 新增数据
     *
     * @param fieldDropDown 实例对象
     * @return 影响行数
     */
    int insert(FieldDropDown fieldDropDown);

    /**
     * 修改数据
     *
     * @param fieldDropDown 实例对象
     * @return 影响行数
     */
    int update(FieldDropDown fieldDropDown);

    /**
     * 通过主键删除数据
     *
     * @param 主键
     * @return 影响行数
     */
    int deleteById(@Param("customFieldId") String customFieldId);

    FieldDropDown queryFieldTextById(@Param("customFieldId") String customFieldId,@Param("masterId") String masterId);
}
