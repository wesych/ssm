package org.wesc.ssm.dao.generator;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.Properties;
import java.util.Set;

public class CustomCommentGenerator implements CommentGenerator {

    @Override
    public void addConfigurationProperties(Properties properties) {
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable tbl, IntrospectedColumn col) {
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * " + field.getName());
        field.addJavaDocLine(" */");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        
    }

    /**
     * Adds a comment for a model class.  The Java code merger should
     * be notified not to delete the entire class in case any manual
     * changes have been made.  So this method will always use the
     * "do not delete" annotation.
     *
     * <p>Because of difficulties with the Java file merger, the default implementation
     * of this method should NOT add comments.  Comments should only be added if
     * specifically requested by the user (for example, by enabling table remark comments).
     *
     * @param topLevelClass     the top level class
     * @param introspectedTable
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        
    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
        //{@link #platformId}
        
        String mname = method.getName();
        if(mname.startsWith("get")){
            mname = mname.substring("get".length());
            mname = mname.substring(0, 1).toLowerCase() + mname.substring(1);
        }else if(mname.startsWith("is")){
            mname = mname.substring("is".length());
            mname = mname.substring(0, 1).toLowerCase() + mname.substring(1);
        }
        
        method.addJavaDocLine("/**");
        
        StringBuilder buf = new StringBuilder();
        buf.append(" * ");
        buf.append("{@link #");
        buf.append(mname);
        buf.append("}");
        method.addJavaDocLine(buf.toString());
        
        method.addJavaDocLine(" */");
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
        String mname = method.getName();
        if(mname.startsWith("set")){
            mname = mname.substring("set".length());
            mname = mname.substring(0, 1).toLowerCase() + mname.substring(1);
        }
        
        method.addJavaDocLine("/**");
        
        StringBuilder buf = new StringBuilder();
        buf.append(" * ");
        buf.append("{@link #");
        buf.append(mname);
        buf.append("}");
        method.addJavaDocLine(buf.toString());
        
        method.addJavaDocLine(" */");
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        
    }

    @Override
    public void addComment(XmlElement xmlElement) {
        
    }

    @Override
    public void addRootComment(XmlElement rootElement) {
        
    }

    /**
     * Adds a @Generated annotation to a method.
     *
     * @param method            the method
     * @param introspectedTable the introspected table
     * @param imports           the comment generator may add a required imported type to this list
     * @since 1.3.6
     */
    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports) {

    }

    /**
     * Adds a @Generated annotation to a method.
     *
     * @param method             the method
     * @param introspectedTable  the introspected table
     * @param introspectedColumn
     * @param imports            the comment generator may add a required imported type to this list
     * @since 1.3.6
     */
    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {

    }

    /**
     * Adds a @Generated annotation to a field.
     *
     * @param field             the field
     * @param introspectedTable the introspected table
     * @param imports           the comment generator may add a required imported type to this list
     * @since 1.3.6
     */
    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports) {

    }

    /**
     * Adds a @Generated annotation to a field.
     *
     * @param field              the field
     * @param introspectedTable  the introspected table
     * @param introspectedColumn the introspected column
     * @param imports            the comment generator may add a required imported type to this list
     * @since 1.3.6
     */
    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {

    }

    /**
     * Adds a @Generated annotation to a class.
     *
     * @param innerClass        the class
     * @param introspectedTable the introspected table
     * @param imports           the comment generator may add a required imported type to this list
     * @since 1.3.6
     */
    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports) {

    }

}
