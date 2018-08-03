package org.wesc.ssm.dao.generator.base.sqlmap;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.wesc.ssm.dao.generator.base.SqlIds;

public class MapWhereClauseElementGenerator extends AbstractXmlElementGenerator {

    private String tableAlias;
    public MapWhereClauseElementGenerator(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public String getDottedAlias(){
        if(null == tableAlias || "".equals(tableAlias)){
            return "";
        }else{
            return tableAlias + ".";
        }
    }
    
    /**
     * 自动生成一个动态where
     */
    /*
     * <sql id="Map_Where_Clause">
     * <where>
     *  <if test="null != fieldString">
     *      and field_string = #{fieldString}
     *  </if>
     *  <if test="null != fieldStringLike">
     *      and field_string like '%' || #{fieldStringLike} || '%'
     *  </if>
     *  
     *  <if test="null != fieldDateBegin">
     *      and field_date >= #{fieldDateBegin}
     *  </if>
     *  <if test="null != fieldDateEnd">
     *      and filed_date <= #{fieldDateEnd}
     *  </if>
     * </where>
     * </sql>
     * 
     */
    @Override
    public void addElements(XmlElement parentElement) {
        SqlIds si = SqlIds.getInstance();
        
        XmlElement xml = new XmlElement("sql");
        parentElement.addElement(xml);
        xml.addAttribute(new Attribute("id", si.getIdMapWhereClause()));
        
        context.getCommentGenerator().addComment(xml);
        
        List<IntrospectedColumn> allColumns = this.getIntrospectedTable().getAllColumns();
        for (IntrospectedColumn col : allColumns) {
            if (col.isStringColumn()) {
                addStringConditionEq(xml, col);
                addStringConditionNeq(xml, col);
                if (!col.isIdentity()) {
                    addStringConditionLike(xml, col);
                }
                addStringConditionIn(xml, col);
                addStringConditionCompare(xml, col);
            } else if (col.getFullyQualifiedJavaType().equals(FullyQualifiedJavaType.getDateInstance())) {
                addDatetimeCondition(xml, col);
            } else {
                addStringConditionEq(xml, col);
                addStringConditionNeq(xml, col);
                addStringConditionIn(xml, col);
            }
        }
    }

    protected void addDatetimeCondition(XmlElement whereEle, IntrospectedColumn col) {
        XmlElement ifGt = new XmlElement("if");
        ifGt.addAttribute(new Attribute("test", String.format("null != begin_%s", col.getJavaProperty())));
        ifGt.addElement(new TextElement(String.format("and %s%s &gt;= #{begin_%s}", this.getDottedAlias(),
                col.getActualColumnName(), col.getJavaProperty())));
        whereEle.addElement(ifGt);
        
        XmlElement iflt = new XmlElement("if");
        iflt.addAttribute(new Attribute("test", String.format("null != end_%s", col.getJavaProperty())));
        iflt.addElement(new TextElement(String.format("and %s%s &lt;= #{end_%s}", this.getDottedAlias(),
                col.getActualColumnName(), col.getJavaProperty())));
        whereEle.addElement(iflt);
    }

    protected void addStringConditionEq(XmlElement whereEle, IntrospectedColumn col) {
        XmlElement ifEq = new XmlElement("if");
        ifEq.addAttribute(new Attribute("test", String.format("null != %s", col.getJavaProperty())));
        ifEq.addElement(new TextElement(String.format("and %s%s = #{%s}", this.getDottedAlias(),
                col.getActualColumnName(), col.getJavaProperty())));
        whereEle.addElement(ifEq);
    }

    /**
     * WHERE:  string != #{string}
     */
    protected void addStringConditionNeq(XmlElement whereEle, IntrospectedColumn col) {
        XmlElement ifEq = new XmlElement("if");
        ifEq.addAttribute(new Attribute("test", String.format("null != ne_%s", col.getJavaProperty())));
        ifEq.addElement(new TextElement(String.format("and %s%s != #{ne_%s}", this.getDottedAlias(),
                col.getActualColumnName(), col.getJavaProperty())));
        whereEle.addElement(ifEq);
    }
    
    /**
     * WHERE: string like #{string} 
     */
    protected void addStringConditionLike(XmlElement whereEle, IntrospectedColumn col) {
        XmlElement ifEq = new XmlElement("if");
        ifEq.addAttribute(new Attribute("test", String.format("null != like_%s", col.getJavaProperty())));
        ifEq.addElement(new TextElement(String.format("and %s%s like CONCAT('%%', #{like_%s}, '%%')",
                this.getDottedAlias(), col.getActualColumnName(), col.getJavaProperty())));
        whereEle.addElement(ifEq);
    }

    /**
     * WHERE: string IN (...)
     */
    protected void addStringConditionIn(XmlElement whereEle, IntrospectedColumn col) {
        XmlElement ifList = new XmlElement("if");
        ifList.addAttribute(new Attribute("test", String.format("null != list_%s", col.getJavaProperty())));
        String foreach = String.format("<foreach collection=\"list_%s\" item=\"i\" separator=\",\">#{i}</foreach>", 
                col.getJavaProperty());
        ifList.addElement(new TextElement(
                String.format("and %s%s in (%s)", this.getDottedAlias(), col.getActualColumnName(), foreach)
        ));
        whereEle.addElement(ifList);
    }

    /**
     * WHERE: string > #{string} 
     * WHERE: string < #{string} 
     */
    protected void addStringConditionCompare(XmlElement whereEle, IntrospectedColumn col) {
        XmlElement ifGt = new XmlElement("if");
        ifGt.addAttribute(new Attribute("test", String.format("null != begin_%s", col.getJavaProperty())));
        ifGt.addElement(new TextElement(String.format("and %s%s &gt;= #{begin_%s}", this.getDottedAlias(),
                col.getActualColumnName(), col.getJavaProperty())));
        whereEle.addElement(ifGt);
        
        XmlElement iflt = new XmlElement("if");
        iflt.addAttribute(new Attribute("test", String.format("null != end_%s", col.getJavaProperty())));
        iflt.addElement(new TextElement(String.format("and %s%s &lt;= #{end_%s}", this.getDottedAlias(),
                col.getActualColumnName(), col.getJavaProperty())));
        whereEle.addElement(iflt);
    }
    

}
