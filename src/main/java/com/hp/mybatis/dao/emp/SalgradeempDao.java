package com.hp.mybatis.dao.emp;

import com.hp.mybatis.bean.emp.Salgradeemp;
import com.hp.mybatis.bean.emp.SalgradeempQuery;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SalgradeempDao {
    long countByExample(SalgradeempQuery example);

    int deleteByExample(SalgradeempQuery example);

    int deleteByPrimaryKey(Integer grade);

    int insert(Salgradeemp record);

    int insertSelective(Salgradeemp record);

    List<Salgradeemp> selectByExample(SalgradeempQuery example);

    Salgradeemp selectByPrimaryKey(Integer grade);

    int updateByExampleSelective(@Param("record") Salgradeemp record, @Param("example") SalgradeempQuery example);

    int updateByExample(@Param("record") Salgradeemp record, @Param("example") SalgradeempQuery example);

    int updateByPrimaryKeySelective(Salgradeemp record);

    int updateByPrimaryKey(Salgradeemp record);
}