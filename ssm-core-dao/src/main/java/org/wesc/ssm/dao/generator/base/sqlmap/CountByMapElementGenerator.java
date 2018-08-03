package org.wesc.ssm.dao.generator.base.sqlmap;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.wesc.ssm.dao.generator.base.SqlIds;
import org.wesc.ssm.dao.generator.base.Utils;

public class CountByMapElementGenerator extends AbstractXmlElementGenerator {
    private String tableAlias;

    public CountByMapElementGenerator(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", SqlIds.getInstance().getIdCountByMap())); //$NON-NLS-1$
        answer.addAttribute(new Attribute("resultType", "java.lang.Integer"));

        String parameterType = "map"; //$NON-NLS-1$

        answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
                parameterType));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select count(1)"); //$NON-NLS-1$

        answer.addElement(new TextElement(sb.toString()));

        sb.setLength(0);
        sb.append("from "); //$NON-NLS-1$
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        sb.append(" ");
        sb.append(Utils.getEmptyStringIfNull(this.tableAlias));
        answer.addElement(new TextElement(sb.toString()));

        
        XmlElement whereInclude = new XmlElement("include");
        whereInclude.addAttribute(new Attribute("refid", SqlIds.getInstance().getIdMapWhereClause()));

        XmlElement where = new XmlElement("where");
        where.addElement(whereInclude);
        
        answer.addElement(where);
        
        if (context.getPlugins()
                .sqlMapSelectByPrimaryKeyElementGenerated(answer,
                        introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
