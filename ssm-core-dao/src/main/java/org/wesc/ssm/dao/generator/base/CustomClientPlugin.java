package org.wesc.ssm.dao.generator.base;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class CustomClientPlugin extends org.mybatis.generator.api.PluginAdapter {
	private static final String PROPERTY_HAS_CONSTANTS = "hasConstants";
    private IntrospectedTable introspectedTable;
    @SuppressWarnings("unused")
	private Interface daoInterfaze;

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        super.initialized(introspectedTable);
        this.introspectedTable = introspectedTable;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
//        this.addToStringMethodToModelClass(topLevelClass);
        addSerialVersionUidToClass(topLevelClass);
        addFieldNameConstantsToClass(topLevelClass);

        topLevelClass.addImportedType(new FullyQualifiedJavaType("com.alibaba.fastjson.JSON"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("com.alibaba.fastjson.JSONObject"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.List"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.ArrayList"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.text.SimpleDateFormat"));
        
        IntrospectedColumn idColumn = introspectedTable.getPrimaryKeyColumns().get(0);
        
        // extends BaseEntity
        FullyQualifiedJavaType baseEntityClass = new FullyQualifiedJavaType("org.wesc.ssm.dao.generator.base.BaseEntity");
        baseEntityClass.addTypeArgument(idColumn.getFullyQualifiedJavaType());
        topLevelClass.setSuperClass(baseEntityClass);

        // implement XxxxxConstants
        if ("true".equals(introspectedTable.getTableConfigurationProperty(PROPERTY_HAS_CONSTANTS))) {
        	FullyQualifiedJavaType interfaze = new FullyQualifiedJavaType("org.wesc.ssm.dao.constant."
    				+ introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Constants");
        	topLevelClass.addImportedType(interfaze);
        	topLevelClass.addSuperInterface(interfaze);
        }
        
        // getIdPropertyName
        Method methodGetIdPropertyName = new Method("getIdPropertyName");
        methodGetIdPropertyName.setReturnType(new FullyQualifiedJavaType("java.lang.String"));
        methodGetIdPropertyName.setVisibility(JavaVisibility.PUBLIC);
        methodGetIdPropertyName.addAnnotation("@Override");
        methodGetIdPropertyName.addBodyLine("return \"" + idColumn.getJavaProperty() + "\";");
        topLevelClass.addMethod(methodGetIdPropertyName);
        
        // getIdValue
        Method methodGetIdValue = new Method("getIdValue");
        methodGetIdValue.setReturnType(idColumn.getFullyQualifiedJavaType());
        methodGetIdValue.setVisibility(JavaVisibility.PUBLIC);
        methodGetIdValue.addAnnotation("@Override");
        methodGetIdValue.addBodyLine("return " + idColumn.getJavaProperty() + ";");
        topLevelClass.addMethod(methodGetIdValue);
        
        // setIdValue
        Method methodSetIdValue = new Method("setIdValue");
        methodSetIdValue.setVisibility(JavaVisibility.PUBLIC);
        methodSetIdValue.addAnnotation("@Override");
        methodSetIdValue.addParameter(new Parameter(idColumn.getFullyQualifiedJavaType(), "id"));
        methodSetIdValue.addBodyLine("this." + idColumn.getJavaProperty() + " = id;");
        topLevelClass.addMethod(methodSetIdValue);
        
        // toJSON(Entity e)
        addStaticToJsonMethod(topLevelClass);
        
        // toJSON(List<Entity> list)
        addStaticToJsonListMethod(topLevelClass);
        
        // toJSON()
        addToJsonMethod(topLevelClass);
        
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    /**
     * Method:  public static JSONObject toJSON(Entity e);  
     */
    private void addStaticToJsonMethod(TopLevelClass topLevelClass) {
        boolean hasDateFields = false;
        ArrayList<String> lines = new ArrayList<>();
        lines.add("if (e == null) {");
        lines.add("return null;");
        lines.add("}");
        lines.add("JSONObject obj = (JSONObject) JSON.toJSON(e);");
        for (Field field: topLevelClass.getFields()) {
            if ("java.util.Date".equals(field.getType().getFullyQualifiedName())) {
                hasDateFields = true;
                String getterName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                lines.add(String.format("if (e.%s() != null) {", getterName));
                lines.add(String.format("obj.put(\"%sStr\", fmt.format(e.%s()));",field.getName(), getterName));
                lines.add("}");
            }
        }
        lines.add("return obj;");
        if (hasDateFields) {
            lines.add(0, "SimpleDateFormat fmt = new SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\");");
        }
        
        Method m = new Method("toJSON");
        m.setVisibility(JavaVisibility.PUBLIC);
        m.setStatic(true);
        m.addParameter(new Parameter(topLevelClass.getType(), "e"));
        m.setReturnType(new FullyQualifiedJavaType("com.alibaba.fastjson.JSONObject"));
        m.addBodyLines(lines);
        topLevelClass.addMethod(m);
    }

    /**
     * Method:  public static List<JSONObject> toJSON(List<Entity> e);
     */
    private void addStaticToJsonListMethod(TopLevelClass topLevelClass) {
        FullyQualifiedJavaType typeJSONObjectList = FullyQualifiedJavaType.getNewListInstance();
        typeJSONObjectList.addTypeArgument(new FullyQualifiedJavaType("com.alibaba.fastjson.JSONObject"));
        
        FullyQualifiedJavaType typeEntityList = FullyQualifiedJavaType.getNewListInstance();
        typeEntityList.addTypeArgument(topLevelClass.getType());
        
        Method m = new Method("toJSON");
        m.setVisibility(JavaVisibility.PUBLIC);
        m.setStatic(true);
        m.addParameter(new Parameter(typeEntityList, "list"));
        m.setReturnType(typeJSONObjectList);
        m.addBodyLine("List<JSONObject> retList = new ArrayList<>();");
        m.addBodyLine("for (int i = 0; i < list.size(); i++) {");
        m.addBodyLine("retList.add(toJSON(list.get(i)));");
        m.addBodyLine("}");
        m.addBodyLine("return retList;");
        topLevelClass.addMethod(m);
    }

    /**
     * Method:  public JSONObject toJSON();
     */
    private void addToJsonMethod(TopLevelClass topLevelClass) {
        FullyQualifiedJavaType typeJSONObjectList = FullyQualifiedJavaType.getNewListInstance();
        typeJSONObjectList.addTypeArgument(new FullyQualifiedJavaType("com.alibaba.fastjson.JSONObject"));
        
        FullyQualifiedJavaType typeEntityList = FullyQualifiedJavaType.getNewListInstance();
        typeEntityList.addTypeArgument(topLevelClass.getType());
        
        Method m = new Method("toJSON");
        m.setVisibility(JavaVisibility.PUBLIC);
        m.addAnnotation("@Override");
        m.setReturnType(new FullyQualifiedJavaType("com.alibaba.fastjson.JSONObject"));
        m.addBodyLine(String.format("return %s.toJSON(this);", topLevelClass.getType().getShortName()));
        topLevelClass.addMethod(m);
    }
    
    protected void addFieldNameConstantsToClass(TopLevelClass topClass) {
        // class Fields
        InnerClass fieldsInnerClass = new InnerClass(new FullyQualifiedJavaType("Fields"));
        fieldsInnerClass.setStatic(true);
        fieldsInnerClass.setVisibility(JavaVisibility.PUBLIC);
        topClass.addInnerClass(fieldsInnerClass);

        // class Query
        InnerClass queryInnerClass = new InnerClass(new FullyQualifiedJavaType("Query"));
        queryInnerClass.setStatic(true);
        queryInnerClass.setVisibility(JavaVisibility.PUBLIC);
        topClass.addInnerClass(queryInnerClass);
        
        // 处理每一个字段
        for (IntrospectedColumn col : introspectedTable.getAllColumns()) {
            fieldsInnerClass.addField(createConstField(
                    col.getActualColumnName().toUpperCase(),
                    col.getJavaProperty()));

            if (col.isStringColumn()) {
                queryInnerClass.addField(createConstField(
                        col.getActualColumnName().toUpperCase() + "__NE",
                        "ne_" + col.getJavaProperty()));
                
                if (!col.isIdentity()) {
                    queryInnerClass.addField(createConstField(
                            col.getActualColumnName().toUpperCase() + "__LIKE",
                            "like_" + col.getJavaProperty()));
                }

                queryInnerClass.addField(createConstField(
                        col.getActualColumnName().toUpperCase() + "__IN",
                        "list_" + col.getJavaProperty()));

                queryInnerClass.addField(createConstField(
                        col.getActualColumnName().toUpperCase() + "__BEGIN",
                        "begin_" + col.getJavaProperty()));

                queryInnerClass.addField(createConstField(
                        col.getActualColumnName().toUpperCase() + "__END",
                        "end_" + col.getJavaProperty()));
                
            } else {
                queryInnerClass.addField(createConstField(
                        col.getActualColumnName().toUpperCase() + "__NE",
                        "ne_" + col.getJavaProperty()));

                queryInnerClass.addField(createConstField(
                        col.getActualColumnName().toUpperCase() + "__IN",
                        "list_" + col.getJavaProperty()));

                queryInnerClass.addField(createConstField(
                        col.getActualColumnName().toUpperCase() + "__BEGIN",
                        "begin_" + col.getJavaProperty()));

                queryInnerClass.addField(createConstField(
                        col.getActualColumnName().toUpperCase() + "__END",
                        "end_" + col.getJavaProperty()));
            }
        }
    }

    private Field createConstField(String name, String value) {
        Field field = new Field();
        field.setVisibility(JavaVisibility.PUBLIC);
        field.setStatic(true);
        field.setFinal(true);
        field.setType(new FullyQualifiedJavaType("String"));
        field.setName(name);
        field.setInitializationString('"' + value + '"');
        return field;
    }
    
    protected void addSerialVersionUidToClass(TopLevelClass clazz) {
        Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setStatic(true);
        field.setFinal(true);
        field.setType(new FullyQualifiedJavaType("long"));
        field.setName("serialVersionUID");
        field.setInitializationString(generateSerialVerseionUid(clazz) + "L");
        clazz.addField(field);
    }
    
    private long generateSerialVerseionUid(TopLevelClass clazz) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(clazz.getType().getFullyQualifiedName());
            for (Field field : clazz.getFields()) {
                sb.append("##");
                sb.append(field.getType().getFullyQualifiedName());
                sb.append("##");
                sb.append(field.getName());
            }
            
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] data = md5.digest(sb.toString().getBytes("UTF-8"));
            
            long a = 0, b = 0;
            for (int i = 0; i < 8; i++) {
                a = (a << 8) + (data[i] & 0xff);
                b = (b << 8) + (data[i + 8] & 0xff);
            }
            long id = a ^ b;
            System.out.println(id);
            
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
//    protected void addToStringMethodToModelClass(TopLevelClass clazz){
//        Method toString = new Method();
//        toString.addAnnotation("@Override");
//        toString.setVisibility(JavaVisibility.PUBLIC);
//        toString.setReturnType(new FullyQualifiedJavaType("java.lang.String"));
//        toString.setName("toString");
//        
//        StringBuilder buf = new StringBuilder();
//
//        buf.append("return \"");
//        buf.append(clazz.getType().getShortName());
//        buf.append(" [\"");
//        
//        List<Field> fields = clazz.getFields();
//        int i=0;
//        for (Field field : fields) {
//            if(i == 0){
//                buf.append( " + \"");
//            }else{
//                buf.append(" + \", ");
//            }
//            buf.append(field.getName());
//            buf.append("=\" + ");
//            buf.append(field.getName());
//            i++;
//        }
//        
//        buf.append(" + \"]\";");
//        
//        toString.addBodyLine(buf.toString());
//        clazz.addMethod(toString);
//        
//    }

	public boolean validate(List<String> warnings) {
	    return true;
    }


//    @Override
//    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
//        List<GeneratedJavaFile> list = new ArrayList<GeneratedJavaFile>();
//        if (introspectedTable != null) {
//            GeneratedJavaFile testDaoFile = new HugedataMapperTestGenerator(this.introspectedTable, this.daoInterfaze, 
//                    this.getContext(), this.modelClazz).generateDaoTestFile();
//            list.add(testDaoFile);
//        }
//        return list;
//    }
	
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, 
            IntrospectedTable introspectedTable) {
        this.daoInterfaze = interfaze;
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }
}
