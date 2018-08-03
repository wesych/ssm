package org.wesc.ssm.dao.generator.base.sqlmap;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import org.wesc.ssm.dao.generator.base.SqlIds;
import org.wesc.ssm.dao.generator.base.Utils;

public class SelectByMapElementGenerator extends AbstractXmlElementGenerator {
    
    private String tableAlias;

    public SelectByMapElementGenerator(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", SqlIds.getInstance().getIdSelectByMap())); //$NON-NLS-1$
        
        if (introspectedTable.getRules().generateResultMapWithBLOBs()) {
            answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
                    introspectedTable.getResultMapWithBLOBsId()));
        } else {
            answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
                    introspectedTable.getBaseResultMapId()));
        }

        String parameterType = "map";
        answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
                parameterType));

        context.getCommentGenerator().addComment(answer);

        answer.addElement(new TextElement("select"));
        answer.addElement(getBaseColumnListElement());
        if (introspectedTable.hasBLOBColumns()) {
            answer.addElement(new TextElement(",")); //$NON-NLS-1$
            answer.addElement(getBlobColumnListElement());
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("from "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        sb.append(" ");
        sb.append(Utils.getEmptyStringIfNull(this.tableAlias));
        answer.addElement(new TextElement(sb.toString()));

        
        XmlElement whereInclude = new XmlElement("include");
        whereInclude.addAttribute(new Attribute("refid", SqlIds.getInstance().getIdMapWhereClause()));

        XmlElement where = new XmlElement("where");
        where.addElement(whereInclude);
        
        answer.addElement(where);
        
        XmlElement orderByEle = new XmlElement("include");
        orderByEle.addAttribute(new Attribute("refid", SqlIds.getInstance().getIdOrderByClause()));
        answer.addElement(orderByEle);
        
        parentElement.addElement(answer);
    }
}
