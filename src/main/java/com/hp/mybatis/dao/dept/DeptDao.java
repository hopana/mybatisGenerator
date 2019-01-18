package com.hp.mybatis.dao.dept;

import com.hp.mybatis.bean.dept.Dept;
import com.hp.mybatis.bean.dept.DeptQuery;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface DeptDao {
    long countByExample(DeptQuery example);

    int deleteByExample(DeptQuery example);

    int deleteByPrimaryKey(Integer deptno);

    int insert(Dept record);

    int insertSelective(Dept record);

    List<Dept> selectByExample(DeptQuery example);

    Dept selectByPrimaryKey(Integer deptno);

    int updateByExampleSelective(@Param("record") Dept record, @Param("example") DeptQuery example);

    int updateByExample(@Param("record") Dept record, @Param("example") DeptQuery example);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);
}