/*
 *  Copyright 2006 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.hp.mybatis.plugins;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.internal.ExtendedDAOMethodNameCalculator;
import org.mybatis.generator.internal.rules.Rules;

/**
 * 自定义DAO的方法名
 *
 * @author hupan
 * @date 2019/01/21
 */
public class CustomizeDAOMethodNameCalculator extends ExtendedDAOMethodNameCalculator {

    /**
     * 构造函数
     */
    public CustomizeDAOMethodNameCalculator() {
        super();
    }

    /**
     * [insert]方法名称
     * @param introspectedTable
     * @return
     */
    @Override
    public String getInsertMethodName(IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert"); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable().getDomainObjectName());

        return sb.toString();
    }

    /**
     * 1. if this will be the only updateById, then the result should be
     * updateById. 2. If the other method is enabled, but there are
     * seperate base and blob classes, then the method name should be
     * updateById 3. Else the method name should be
     * updateByIdWithoutBLOBs
     */
    @Override
    public String getUpdateByPrimaryKeyWithoutBLOBsMethodName(IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();

        sb.append("update"); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable().getDomainObjectName());

        Rules rules = introspectedTable.getRules();

        if (!rules.generateUpdateByPrimaryKeyWithBLOBs()) {
            sb.append("ById"); //$NON-NLS-1$
        } else if (rules.generateRecordWithBLOBsClass()) {
            sb.append("ById"); //$NON-NLS-1$
        } else {
            sb.append("ByIdWithoutBLOBs"); //$NON-NLS-1$
        }

        return sb.toString();
    }

    /**
     * 1. if this will be the only updateById, then the result should be
     * updateById. 2. If the other method is enabled, but there are
     * seperate base and blob classes, then the method name should be
     * updateById 3. Else the method name should be
     * updateByIdWithBLOBs
     */
    @Override
    public String getUpdateByPrimaryKeyWithBLOBsMethodName(IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("update"); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable().getDomainObjectName());

        Rules rules = introspectedTable.getRules();

        if (!rules.generateUpdateByPrimaryKeyWithoutBLOBs()) {
            sb.append("ById"); //$NON-NLS-1$
        } else if (rules.generateRecordWithBLOBsClass()) {
            sb.append("ById"); //$NON-NLS-1$
        } else {
            sb.append("ByIdWithBLOBs"); //$NON-NLS-1$
        }

        return sb.toString();
    }

    @Override
    public String getDeleteByExampleMethodName(IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("delete"); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        sb.append("ByExample"); //$NON-NLS-1$

        return sb.toString();
    }

    @Override
    public String getDeleteByPrimaryKeyMethodName(IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("delete"); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        sb.append("ById"); //$NON-NLS-1$

        return sb.toString();
    }

    /**
     * 1. if this will be the only selectByExample, then the result should be
     * selectByExample. 2. Else the method name should be
     * selectByExampleWithoutBLOBs
     */
    @Override
    public String getSelectByExampleWithoutBLOBsMethodName(IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select"); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        sb.append("ByExample"); //$NON-NLS-1$

        Rules rules = introspectedTable.getRules();

        if (rules.generateSelectByExampleWithBLOBs()) {
            sb.append("WithoutBLOBs"); //$NON-NLS-1$
        }

        return sb.toString();
    }

    /**
     * 1. if this will be the only selectByExample, then the result should be
     * selectByExample. 2. Else the method name should be
     * selectByExampleWithBLOBs
     */
    @Override
    public String getSelectByExampleWithBLOBsMethodName(IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select"); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        sb.append("ByExample"); //$NON-NLS-1$

        Rules rules = introspectedTable.getRules();

        if (rules.generateSelectByExampleWithoutBLOBs()) {
            sb.append("WithBLOBs"); //$NON-NLS-1$
        }

        return sb.toString();
    }

    @Override
    public String getSelectByPrimaryKeyMethodName(IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select"); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        sb.append("ById"); //$NON-NLS-1$

        return sb.toString();
    }

    @Override
    public String getUpdateByPrimaryKeySelectiveMethodName(IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("update"); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        sb.append("ByIdSelective"); //$NON-NLS-1$

        return sb.toString();
    }

    @Override
    public String getCountByExampleMethodName(IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("count"); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        sb.append("ByExample"); //$NON-NLS-1$

        return sb.toString();
    }

    @Override
    public String getUpdateByExampleSelectiveMethodName(IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("update"); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        sb.append("ByExampleSelective"); //$NON-NLS-1$

        return sb.toString();
    }

    @Override
    public String getUpdateByExampleWithBLOBsMethodName(IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("update"); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable().getDomainObjectName());

        Rules rules = introspectedTable.getRules();

        if (!rules.generateUpdateByExampleWithoutBLOBs()) {
            sb.append("ByExample"); //$NON-NLS-1$
        } else if (rules.generateRecordWithBLOBsClass()) {
            sb.append("ByExample"); //$NON-NLS-1$
        } else {
            sb.append("ByExampleWithBLOBs"); //$NON-NLS-1$
        }

        return sb.toString();
    }

    @Override
    public String getUpdateByExampleWithoutBLOBsMethodName(IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();

        sb.append("update"); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable().getDomainObjectName());

        Rules rules = introspectedTable.getRules();

        if (!rules.generateUpdateByExampleWithBLOBs()) {
            sb.append("ByExample"); //$NON-NLS-1$
        } else if (rules.generateRecordWithBLOBsClass()) {
            sb.append("ByExample"); //$NON-NLS-1$
        } else {
            sb.append("ByExampleWithoutBLOBs"); //$NON-NLS-1$
        }

        return sb.toString();
    }

    @Override
    public String getInsertSelectiveMethodName(IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert"); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        sb.append("Selective"); //$NON-NLS-1$

        return sb.toString();
    }
}
