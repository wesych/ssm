package org.wesc.ssm.dao.generator.base.sqlmap;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.wesc.ssm.dao.generator.base.SqlIds;

import java.util.List;

public class OrderByElementGenerator extends AbstractXmlElementGenerator {

    private String tableAlias;
    public OrderByElementGenerator(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public String getDottedAlias(){
        if(null == tableAlias || "".equals(tableAlias)){
            return "";
        }else{
            return tableAlias + ".";
        }
    }
    
    /*
     * 
     * 
     * 生成排序条件
     * 
     * <sql id="Order_By_Clause" >
     *     <if test="null != order" >
     *      order by 
     *      <foreach collection="order" item="orderItem" separator="," >
     *         <if test="'id' == orderItem.column" >
     *           aw.id
     *         </if>
     *         <if test="'appId' == orderItem.column" >
     *           aw.app_id
     *         </if>
     *         <if test="'appverId' == orderItem.column" >
     *           aw.appver_id
     *         </if>
     *         <if test="'name' == orderItem.column" >
     *            aw.name
     *         </if>
     *         <if test="'widgetUrl' == orderItem.column" >
     *            aw.widget_url
     *         </if>
     *         <if test="'width' == orderItem.column" >
     *            aw.width
     *          </if>
     *           ${orderItem.dir}
     *       </foreach>
     *     </if>
     *  </sql>
     * 
     * 
     * 
     * 
     */
    @Override
    public void addElements(XmlElement parentElement) {
        SqlIds si = SqlIds.getInstance();
        
        XmlElement xml = new XmlElement("sql");
        parentElement.addElement(xml);
        xml.addAttribute(new Attribute("id", si.getIdOrderByClause()));
        
        context.getCommentGenerator().addComment(xml);
        
        XmlElement ifEle = new XmlElement("if");
        ifEle.addAttribute(new Attribute("test", "null != order"));
        
        XmlElement trimEle = new XmlElement("trim");
        trimEle.addAttribute(new Attribute("prefix", "order by "));

        ifEle.addElement(trimEle);
        
        XmlElement foreachEle = new XmlElement("foreach");
        foreachEle.addAttribute(new Attribute("collection", "order"));
        foreachEle.addAttribute(new Attribute("item", "orderItem"));
        foreachEle.addAttribute(new Attribute("separator", ","));
        
        trimEle.addElement(foreachEle);
        
        List<IntrospectedColumn> allColumns = this.getIntrospectedTable().getAllColumns();
        for (IntrospectedColumn col : allColumns) {
            addStringConditionEq(foreachEle, col);
        }
        xml.addElement(ifEle);
    }

    protected void addStringConditionEq(XmlElement whereEle, IntrospectedColumn col) {
        XmlElement ifEq = new XmlElement("if");
        if(col.getJavaProperty().length() ==1){
            ifEq.addAttribute(new Attribute("test", 
                    String.format("'%s'.toString() == orderItem.column", col.getJavaProperty())));
        }else{
            ifEq.addAttribute(new Attribute("test", 
                    String.format("'%s' == orderItem.column", col.getJavaProperty())));
        }
        
        ifEq.addElement(new TextElement(
                String.format("%s%s ${orderItem.dir}",
                        this.getDottedAlias(), col.getActualColumnName())));
        
        whereEle.addElement(ifEq);
    }
}
