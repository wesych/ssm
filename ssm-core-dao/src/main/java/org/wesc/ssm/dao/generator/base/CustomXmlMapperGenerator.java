package org.wesc.ssm.dao.generator.base;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.ResultMapWithBLOBsElementGenerator;
import org.wesc.ssm.dao.generator.base.sqlmap.*;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * xml map文件生成器，主要功能是调用已有的语句生成代码。
 */
public class CustomXmlMapperGenerator extends XMLMapperGenerator
{
    public CustomXmlMapperGenerator(){
    }
    
    private void createTableAlias() {
        String fullTblName = this.getIntrospectedTable().getFullyQualifiedTableNameAtRuntime();
        StringBuilder buf = new StringBuilder();
        String[] parts = fullTblName.split("_");
        for (String part : parts) {
            buf.append(part.charAt(0));
        }
        
        this.tableAlias = buf.toString().toLowerCase();
        
        if (this.tableAlias.equals("is")) {
            this.tableAlias += "0";
        }
    }

    private String tableAlias;
    
    /**
     * 代码是从父类抄的，需要去ByExample指类的语句，添加ByMap语句。
     */
    protected XmlElement getSqlMapElement() {
        this.createTableAlias();
        
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(getString(
                "Progress.12", table.toString())); //$NON-NLS-1$
        XmlElement answer = new XmlElement("mapper"); //$NON-NLS-1$
        String namespace = introspectedTable.getMyBatis3SqlMapNamespace();
        answer.addAttribute(new Attribute("namespace", //$NON-NLS-1$
                namespace));

        context.getCommentGenerator().addRootComment(answer);

        /**
         * 字段-数据库列映射 BaseResultMap
         * 
         * 不需要加别名
         */
        addResultMapWithoutBLOBsElement(answer);
        addResultMapWithBLOBsElement(answer);
        
        /**
         * 动态SQL where条件 Map_Where_Clause
         * 
         * 需要添加别名
         */
        addMapWhereClauseElement(answer);
        
        /**
         * 添加一个orderBy的include Order_By_Clause
         * 
         * 不需要加别名
         */
        addOrderByClauseElement(answer);
        
        /**
         * 基础字段列表
         * 需要加别名
         */
        addBaseColumnListElement(answer);
        addBlobColumnListElement(answer);
        
        /**
         * 需要加别名
         */
        addSelectById(answer);
        /**
         * 需要加别名
         */
        addSelectByMap(answer);
        /**
         * 需要加别名
         */
        addCountByMap(answer);
        
        //不需要别名
        addInsertSelectiveElement(answer);
        
        //不需要别名
        addDeleteByIdElement(answer);
        //不需要别名
        addDeleteByIdsElement(answer);
        //需要别名
        addDeleteByMapElement(answer);
        
        //不需要别名
        addUpdateSelectiveByIdElement(answer);
        //需要别名
        addUpdateSelectiveByMap(answer);
        
        /*
        addSelectByExampleWithBLOBsElement(answer);
        addSelectByExampleWithoutBLOBsElement(answer);
        addSelectByPrimaryKeyElement(answer);
        addInsertElement(answer);
        addCountByExampleElement(answer);
        addUpdateByExampleSelectiveElement(answer);
        addUpdateByExampleWithBLOBsElement(answer);
        addUpdateByExampleWithoutBLOBsElement(answer);
        addUpdateByPrimaryKeySelectiveElement(answer);
        addUpdateByPrimaryKeyWithBLOBsElement(answer);
        addUpdateByPrimaryKeyWithoutBLOBsElement(answer);
        */
        return answer;
    }
    
    @Override
    protected void addBaseColumnListElement(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new AliasedBaseColumnListElementGenerator(this.tableAlias);
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }

    protected void addUpdateSelectiveByMap(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new UpdateSelectiveByMapElementGenerator(this.tableAlias);
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    protected void addUpdateSelectiveByIdElement(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new UpdateSelectiveByIdElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, answer);

    }

    protected void addDeleteByMapElement(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new DeleteByMapElementGenerator(this.tableAlias);
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    protected void addDeleteByIdElement(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new DeleteByIdElementGenerator(false);
        initializeAndExecuteGenerator(elementGenerator, answer);
    }
    
    protected void addDeleteByIdsElement(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new DeleteByIdsElementGenerator(false);
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    protected void addCountByMap(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new CountByMapElementGenerator(this.tableAlias);
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    protected void addOrderByClauseElement(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new OrderByElementGenerator(this.tableAlias);
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    protected void addSelectByMap(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new SelectByMapElementGenerator(this.tableAlias);
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    protected void addSelectById(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new SelectByIdPrimaryKeyElementGenerator(this.tableAlias);
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    protected void addMapWhereClauseElement(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new MapWhereClauseElementGenerator(this.tableAlias);
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }

    protected void addInsertSelectiveElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateInsertSelective()) {
            AbstractXmlElementGenerator elementGenerator = new InsertSelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    protected void addResultMapWithBLOBsElement(XmlElement parentElement) {
        if (introspectedTable.hasBLOBColumns()) {
            AbstractXmlElementGenerator elementGenerator = new ResultMapWithBLOBsElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
}
