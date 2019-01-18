package com.hp.mybatis.dao.emp;

import com.hp.mybatis.bean.emp.EmpEmp;
import com.hp.mybatis.bean.emp.EmpEmpQuery;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface EmpEmpDao {
    long countByExample(EmpEmpQuery example);

    int deleteByExample(EmpEmpQuery example);

    int deleteByPrimaryKey(Integer empno);

    int insert(EmpEmp record);

    int insertSelective(EmpEmp record);

    List<EmpEmp> selectByExample(EmpEmpQuery example);

    EmpEmp selectByPrimaryKey(Integer empno);

    int updateByExampleSelective(@Param("record") EmpEmp record, @Param("example") EmpEmpQuery example);

    int updateByExample(@Param("record") EmpEmp record, @Param("example") EmpEmpQuery example);

    int updateByPrimaryKeySelective(EmpEmp record);

    int updateByPrimaryKey(EmpEmp record);
}