package org.wesc.ssm.dao.generator.base.sqlmap;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.wesc.ssm.dao.generator.base.SqlIds;
import org.wesc.ssm.dao.generator.base.Utils;

public class UpdateSelectiveByMapElementGenerator extends AbstractXmlElementGenerator {
    
    private String tableAlias;

    public UpdateSelectiveByMapElementGenerator(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("update"); //$NON-NLS-1$

        answer
                .addAttribute(new Attribute(
                        "id", SqlIds.getInstance().getIdUpdateSelectiveByMap())); //$NON-NLS-1$

        answer.addAttribute(new Attribute("parameterType", "map")); //$NON-NLS-1$ //$NON-NLS-2$

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("update "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        sb.append(" ");
        sb.append(Utils.getEmptyStringIfNull(tableAlias));
        answer.addElement(new TextElement(sb.toString()));

        XmlElement dynamicElement = new XmlElement("set"); //$NON-NLS-1$
        answer.addElement(dynamicElement);

        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getAllColumns()) {
            XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty("record.")); //$NON-NLS-1$
            sb.append(" != null"); //$NON-NLS-1$
            isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
            dynamicElement.addElement(isNotNullElement);

            sb.setLength(0);
            sb.append(MyBatis3FormattingUtilities
                    .getAliasedEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getParameterClause(
                    introspectedColumn, "record.")); //$NON-NLS-1$
            sb.append(',');

            isNotNullElement.addElement(new TextElement(sb.toString()));
        }

        
        addWhereElement(answer);

        if (context.getPlugins()
                .sqlMapUpdateByExampleSelectiveElementGenerated(answer,
                        introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
    
    
    
    
    

    public String getDottedAlias(){
        if(null == tableAlias || "".equals(tableAlias)){
            return "";
        }else{
            return tableAlias + ".";
        }
    }
    
    public void addWhereElement(XmlElement parentElement) {
        XmlElement whereEle = new XmlElement("where");
        parentElement.addElement(whereEle);
        
        List<IntrospectedColumn> allColumns = this.getIntrospectedTable().getAllColumns();
        for (IntrospectedColumn col : allColumns) {
            if (col.isStringColumn()) {
                addStringConditionEq(whereEle, col);
                addStringConditionNeq(whereEle, col);
                if (!col.isIdentity()) {
                    addStringConditionLike(whereEle, col);
                }
                addStringConditionIn(whereEle, col);
                addStringConditionCompare(whereEle, col);
            } else if (col.getFullyQualifiedJavaType().equals(FullyQualifiedJavaType.getDateInstance())) {
                addDatetimeCondition(whereEle, col);
            } else {
                addStringConditionEq(whereEle, col);
                addStringConditionNeq(whereEle, col);
                addStringConditionIn(whereEle, col);
            }
        }
    }

    protected void addDatetimeCondition(XmlElement whereEle, IntrospectedColumn col) {
        XmlElement ifGt = new XmlElement("if");
        ifGt.addAttribute(new Attribute("test", String.format("null != map.begin_%s", col.getJavaProperty())));
        ifGt.addElement(new TextElement(String.format("and %s%s &gt;= #{map.begin_%s}", this.getDottedAlias(),
                col.getActualColumnName(), col.getJavaProperty())));
        whereEle.addElement(ifGt);
        
        XmlElement iflt = new XmlElement("if");
        iflt.addAttribute(new Attribute("test", String.format("null != map.end_%s", col.getJavaProperty())));
        iflt.addElement(new TextElement(String.format("and %s%s &lt;= #{map.end_%s}", this.getDottedAlias(),
                col.getActualColumnName(), col.getJavaProperty())));
        whereEle.addElement(iflt);
    }

    protected void addStringConditionEq(XmlElement whereEle, IntrospectedColumn col) {
        XmlElement ifEq = new XmlElement("if");
        ifEq.addAttribute(new Attribute("test", String.format("null != map.%s", col.getJavaProperty())));
        ifEq.addElement(new TextElement(String.format("and %s%s = #{map.%s}", this.getDottedAlias(),
                col.getActualColumnName(), col.getJavaProperty())));
        whereEle.addElement(ifEq);
    }

    /**
     * WHERE:  string != #{string}
     */
    protected void addStringConditionNeq(XmlElement whereEle, IntrospectedColumn col) {
        XmlElement ifEq = new XmlElement("if");
        ifEq.addAttribute(new Attribute("test", String.format("null != map.ne_%s", col.getJavaProperty())));
        ifEq.addElement(new TextElement(String.format("and %s%s != #{map.ne_%s}", this.getDottedAlias(),
                col.getActualColumnName(), col.getJavaProperty())));
        whereEle.addElement(ifEq);
    }
    
    /**
     * WHERE: string like #{string} 
     */
    protected void addStringConditionLike(XmlElement whereEle, IntrospectedColumn col) {
        XmlElement ifEq = new XmlElement("if");
        ifEq.addAttribute(new Attribute("test", String.format("null != map.like_%s", col.getJavaProperty())));
        ifEq.addElement(new TextElement(String.format("and %s%s like CONCAT('%%', #{map.like_%s}, '%%')",
                this.getDottedAlias(), col.getActualColumnName(), col.getJavaProperty())));
        whereEle.addElement(ifEq);
    }

    /**
     * WHERE: string IN (...)
     */
    protected void addStringConditionIn(XmlElement whereEle, IntrospectedColumn col) {
        XmlElement ifList = new XmlElement("if");
        ifList.addAttribute(new Attribute("test", String.format("null != map.list_%s", col.getJavaProperty())));
        String foreach = String.format("<foreach collection=\"map.list_%s\" item=\"i\" separator=\",\">#{i}</foreach>", 
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
        ifGt.addAttribute(new Attribute("test", String.format("null != map.begin_%s", col.getJavaProperty())));
        ifGt.addElement(new TextElement(String.format("and %s%s &gt;= #{map.begin_%s}", this.getDottedAlias(),
                col.getActualColumnName(), col.getJavaProperty())));
        whereEle.addElement(ifGt);
        
        XmlElement iflt = new XmlElement("if");
        iflt.addAttribute(new Attribute("test", String.format("null != map.end_%s", col.getJavaProperty())));
        iflt.addElement(new TextElement(String.format("and %s%s &lt;= #{map.end_%s}", this.getDottedAlias(),
                col.getActualColumnName(), col.getJavaProperty())));
        whereEle.addElement(iflt);
    }
}
