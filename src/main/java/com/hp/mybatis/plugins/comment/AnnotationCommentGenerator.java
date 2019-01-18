package com.hp.mybatis.plugins.comment;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * 注解式注释生成器
 */
public class AnnotationCommentGenerator extends AbstractCommentGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationCommentGenerator.class);

    private static final String SUPPRESS_ALL_ANNOTATIONS = "suppressAllAnnotations";
    private static final String FIELD_ANNOTATION_FULLY_QUALIFIED_NAMES = "fieldAnnotationFullyQualifiedNames";
    private static final String CLASS_ANNOTATION_FULLY_QUALIFIED_NAMES = "classAnnotationFullyQualifiedNames";

    private static final String FIELD_COMMENT_TEMPLATE = "/** %s */";
    private static final String CLASS_COMMENT_TEMPLATE = "/** %s */";

    private Properties properties = new Properties();

    /**
     * 保留官方的重要设置属性，这样更符合直觉
     */
    private boolean suppressAllComments;

    private boolean suppressAllAnnotations;
    private List<String> fieldAnnotationFullyQualifiedNames = new LinkedList<>();
    private List<String> classAnnotationFullyQualifiedNames = new LinkedList<>();

    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        suppressAllComments = StringUtility.isTrue(this.properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
        suppressAllAnnotations = StringUtility.isTrue(this.properties.getProperty(SUPPRESS_ALL_ANNOTATIONS));
        fieldAnnotationFullyQualifiedNames.addAll(Util.toList(this.properties.getProperty(FIELD_ANNOTATION_FULLY_QUALIFIED_NAMES)));
        classAnnotationFullyQualifiedNames.addAll(Util.toList(this.properties.getProperty(CLASS_ANNOTATION_FULLY_QUALIFIED_NAMES)));
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (!suppressAllComments && Util.isNotBlank(introspectedColumn.getRemarks())) {
            LOGGER.debug(String.format("field = %s, column = %s, columnRemarks = %s", field.getName(), introspectedColumn.getActualColumnName(), introspectedColumn
                    .getRemarks()));
            field.addJavaDocLine(String.format(FIELD_COMMENT_TEMPLATE, introspectedColumn.getRemarks()));
        }
        if (!suppressAllAnnotations) {
            LOGGER.debug(String.format("fieldAnnotationFullyQualifiedNames = %s]", fieldAnnotationFullyQualifiedNames));
            fieldAnnotationFullyQualifiedNames.forEach(fieldAnnotationFullyQualifiedName -> {
                field.addJavaDocLine(Util.atAnnotationName(fieldAnnotationFullyQualifiedName));
            });
        } else {
            LOGGER.debug(String.format("suppressAllComments = %b, suppressAllAnnotations = %b", suppressAllComments, suppressAllAnnotations));
        }
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (!suppressAllComments && Util.isNotBlank(introspectedTable.getRemarks())) {
            LOGGER.debug(String.format("class = %s, table = %s, tableRemarks = %s", topLevelClass.getType(), introspectedTable.getFullyQualifiedTable(), introspectedTable
                    .getRemarks()));
            topLevelClass.addJavaDocLine(String.format(CLASS_COMMENT_TEMPLATE, introspectedTable.getRemarks()));
        }
        if (!suppressAllAnnotations) {
            LOGGER.debug(String.format("classAnnotationFullyQualifiedNames = %s]", classAnnotationFullyQualifiedNames));
            classAnnotationFullyQualifiedNames.forEach(classAnnotationFullyQualifiedName -> {
                topLevelClass.addJavaDocLine(Util.atAnnotationName(classAnnotationFullyQualifiedName));
                topLevelClass.addImportedType(new FullyQualifiedJavaType(classAnnotationFullyQualifiedName));
            });
            fieldAnnotationFullyQualifiedNames.forEach(fieldAnnotationFullyQualifiedName -> {
                topLevelClass.addImportedType(new FullyQualifiedJavaType(fieldAnnotationFullyQualifiedName));
            });
        } else {
            LOGGER.debug(String.format("suppressAllComments = %b, suppressAllAnnotations = %b", suppressAllComments, suppressAllAnnotations));
        }
    }

    static class Util {

        static boolean isBlank(String string) {
            return string == null || string.trim().length() == 0;
        }

        static boolean isNotBlank(String string) {
            return !isBlank(string);
        }

        static List<String> toList(String commaSeparatedString) {
            List<String> list = new LinkedList<>();
            assert isNotBlank(commaSeparatedString);
            StringTokenizer stringTokenizer = new StringTokenizer(commaSeparatedString, ",");
            String token;
            while (stringTokenizer.hasMoreTokens()) {
                token = stringTokenizer.nextToken();
                list.add(token);
            }
            if (list.size() == 0) {
                list.add(commaSeparatedString);
            }
            return list;
        }

        static String atAnnotationName(String annotationFullyQualifiedName) {
            int lastIndexOfDot = annotationFullyQualifiedName.lastIndexOf(".");
            assert lastIndexOfDot != -1;
            String atAnnotationName = annotationFullyQualifiedName.substring(lastIndexOfDot + 1);
            assert isNotBlank(atAnnotationName);
            return String.format("@%s", atAnnotationName);
        }
    }
}
