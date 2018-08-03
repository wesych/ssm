package org.wesc.ssm.dao.generator.base.sqlmap;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.wesc.ssm.dao.generator.base.SqlIds;

public class DeleteByIdsElementGenerator extends AbstractXmlElementGenerator {

    @SuppressWarnings("unused")
	private boolean isSimple;
    
    public DeleteByIdsElementGenerator(boolean isSimple) {
        super();
        this.isSimple = isSimple;
    }
    
    /*
     *
    <!-- 删除 子平台 -->
    <delete id="deleteByIds">
         delete from table_name where id in   
        <foreach collection="list" item = "id" open="(" separator="," close=")">
           #{id}
        </foreach>   
    </delete> 
     * 
     * 
     */
    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("delete"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", SqlIds.getInstance().getIdDeleteByIds())); //$NON-NLS-1$
        
        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("delete from "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        sb.append(" where ");
        sb.append(introspectedTable.getPrimaryKeyColumns().get(0).getActualColumnName());
        sb.append(" in ");
        answer.addElement(new TextElement(sb.toString()));

        
        XmlElement forEle = new XmlElement("foreach");
        forEle.addAttribute(new Attribute("collection", "list"));
        forEle.addAttribute(new Attribute("item", "id"));
        forEle.addAttribute(new Attribute("open", "("));
        forEle.addAttribute(new Attribute("separator", ","));
        forEle.addAttribute(new Attribute("close", ")"));
        
        forEle.addElement(new TextElement("#{id}"));
        
        answer.addElement(forEle);

        parentElement.addElement(answer);
    }
    

}
