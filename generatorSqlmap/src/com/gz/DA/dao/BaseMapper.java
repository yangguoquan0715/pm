package com.gz.DA.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

/**
 * Created by Administrator on 2015/11/12.
 */
public interface BaseMapper<T, E,ID>{
	
    int countByExample(E example);

    int deleteByExample(E example);

    int deleteByPrimaryKey(ID id);

    int insert(T record);

    int insertSelective(T record);

    List<T> selectByExample(E example, PageBounds pageBounds);

    T selectByPrimaryKey(ID id);

    int updateByExampleSelective(@Param("record") T record, @Param("example") E example);

    int updateByExample(@Param("record") T record, @Param("example") E example);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}
