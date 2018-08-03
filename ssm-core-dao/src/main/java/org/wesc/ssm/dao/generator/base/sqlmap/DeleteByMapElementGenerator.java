package org.wesc.ssm.dao.generator.base.sqlmap;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.wesc.ssm.dao.generator.base.SqlIds;
import org.wesc.ssm.dao.generator.base.Utils;

public class DeleteByMapElementGenerator extends AbstractXmlElementGenerator {

    
    private String tableAlias;

    public DeleteByMapElementGenerator(String tableAlias) {
        super();
        this.tableAlias = tableAlias;
    }
    
    /*
     *
    <!-- 删除 子平台 -->
    <delete id="deleteByIds">
         delete from table_name 
         <include refid="Base_Where_Clause" />   
    </delete> 
     * 
     * 
     */
    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("delete"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", SqlIds.getInstance().getIdDeleteByMap())); //$NON-NLS-1$
        
        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("delete from "); //$NON-NLS-1$
        sb.append(Utils.getEmptyStringIfNull(tableAlias));
        sb.append(" using (");
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        sb.append(" as ");
        sb.append(Utils.getEmptyStringIfNull(tableAlias));
        sb.append(") ");
        answer.addElement(new TextElement(sb.toString()));
        
        XmlElement inc = new XmlElement("include");
        inc.addAttribute(new Attribute("refid", SqlIds.getInstance().getIdMapWhereClause()));

        XmlElement where = new XmlElement("where");
        where.addElement(inc);
        answer.addElement(where);

        parentElement.addElement(answer);
    }
    

}
