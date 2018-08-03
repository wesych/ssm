package org.wesc.ssm.dao.generator.base;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;
import org.mybatis.generator.config.PropertyRegistry;

public class CustomJavaMapperGenerator extends JavaMapperGenerator {

	private static final String PROPERTY_MANAGEMENT_INTERFACE = "managementInterface";

    public CustomJavaMapperGenerator() {
        super(true);
    }

    public CustomJavaMapperGenerator(boolean requiresXMLGenerator) {
        super(requiresXMLGenerator);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        progressCallback.startTask(getString("Progress.17", //$NON-NLS-1$
                introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);

        /**
         * 按照配置文件添加父类/父接口
         */
        String rootInterface = introspectedTable.getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        if (!stringHasValue(rootInterface)) {
            rootInterface = context.getJavaClientGeneratorConfiguration().getProperty(
                    PropertyRegistry.ANY_ROOT_INTERFACE);
        }
        if (stringHasValue(rootInterface)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(String.format(rootInterface + "<%s, %s>",
                    this.introspectedTable.getFullyQualifiedTable().getDomainObjectName(),
                    this.introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType()));
            interfaze.addSuperInterface(fqjt);
            interfaze.addImportedType(fqjt);
            interfaze.addImportedType(new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType()));
        }
        
        // ManagementMapper接口
        String managementInterface = context.getJavaClientGeneratorConfiguration().getProperty(PROPERTY_MANAGEMENT_INTERFACE);
        if (stringHasValue(managementInterface)) {
            List<IntrospectedColumn> columns = introspectedTable.getPrimaryKeyColumns();
            if (columns.size() == 1) {
            	IntrospectedColumn pkcol = columns.get(0);
            	
                FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(String.format(managementInterface + "<%s,%s>",
                        this.introspectedTable.getFullyQualifiedTable().getDomainObjectName(),
                        pkcol.getFullyQualifiedJavaType()));
                interfaze.addSuperInterface(fqjt);
                interfaze.addImportedType(fqjt);
                interfaze.addImportedType(new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType()));
            }
        }

        
		addSelectById(interfaze);
		addSelectByMap(interfaze);
		addSelectByMapWithRowBounds(interfaze);
		addCountByMap(interfaze);
		addInsertSelective(interfaze);
		addDeleteById(interfaze);
		addDeleteByIds(interfaze);
		addDeleteByMap(interfaze);
		addUpdateSelectiveById(interfaze);
		addUpdateSelectiveByMap(interfaze);

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().clientGenerated(interfaze, null, introspectedTable)) {
            answer.add(interfaze);
        }

        List<CompilationUnit> extraCompilationUnits = getExtraCompilationUnits();
        if (extraCompilationUnits != null) {
            answer.addAll(extraCompilationUnits);
        }

        return answer;
    }

    protected void addUpdateSelectiveByMap(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        method.setReturnType(new FullyQualifiedJavaType(Integer.class.getName()));

        method.setName(SqlIds.getInstance().getIdUpdateSelectiveByMap());

        FullyQualifiedJavaType paramType = this.getIntrospectedTable().getRules().calculateAllFieldsClass();
        importedTypes.add(paramType);
        Parameter fp = new Parameter(paramType, "obj");
        importedTypes.add(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));
        fp.addAnnotation("@Param(\"record\")");
        method.addParameter(fp);

        FullyQualifiedJavaType paramMapType = new FullyQualifiedJavaType(Map.class.getName());
        importedTypes.add(paramMapType);
        paramMapType.addTypeArgument(new FullyQualifiedJavaType(String.class.getName()));
        paramMapType.addTypeArgument(new FullyQualifiedJavaType(Object.class.getName()));
        Parameter pmap = new Parameter(paramMapType, "param");
        pmap.addAnnotation("@Param(\"map\")");
        method.addParameter(pmap);

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }

    protected void addUpdateSelectiveById(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        method.setReturnType(new FullyQualifiedJavaType(Integer.class.getName()));

        method.setName(SqlIds.getInstance().getIdUpdateSelectiveById());

        FullyQualifiedJavaType paramType = this.getIntrospectedTable().getRules().calculateAllFieldsClass();
        importedTypes.add(paramType);
        method.addParameter(new Parameter(paramType, "obj"));

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);

    }

    protected void addDeleteByMap(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        method.setReturnType(new FullyQualifiedJavaType(Integer.class.getName()));

        method.setName(SqlIds.getInstance().getIdDeleteByMap());

        FullyQualifiedJavaType paramMapType = new FullyQualifiedJavaType(Map.class.getName());
        importedTypes.add(paramMapType);
        paramMapType.addTypeArgument(new FullyQualifiedJavaType(String.class.getName()));
        paramMapType.addTypeArgument(new FullyQualifiedJavaType(Object.class.getName()));
        method.addParameter(new Parameter(paramMapType, "map"));

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }

    protected void addDeleteByIds(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        method.setReturnType(new FullyQualifiedJavaType(Integer.class.getName()));

        method.setName(SqlIds.getInstance().getIdDeleteByIds());

        List<IntrospectedColumn> introspectedColumns = introspectedTable.getPrimaryKeyColumns();
        boolean multiKey = introspectedColumns.size() > 1;
        if (multiKey) {// List<Map>
            FullyQualifiedJavaType ptype = new FullyQualifiedJavaType(List.class.getName());
            FullyQualifiedJavaType mtype = new FullyQualifiedJavaType(Map.class.getName());
            mtype.addTypeArgument(new FullyQualifiedJavaType(String.class.getName()));
            mtype.addTypeArgument(new FullyQualifiedJavaType(Object.class.getName()));
            ptype.addTypeArgument(mtype);
            Parameter p = new Parameter(ptype, "ids");

            method.addParameter(p);

        } else {// List<Type>
            IntrospectedColumn col = introspectedColumns.get(0);
            FullyQualifiedJavaType ptype = new FullyQualifiedJavaType(List.class.getName());
            ptype.addTypeArgument(col.getFullyQualifiedJavaType());
            Parameter p = new Parameter(ptype, "ids");

            method.addParameter(p);
        }

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }

    protected void addDeleteById(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        method.setReturnType(new FullyQualifiedJavaType(Integer.class.getName()));

        method.setName(SqlIds.getInstance().getIdDeleteById());

        // no primary key class - fields are in the base class
        // if more than one PK field, then we need to annotate the
        // parameters
        // for MyBatis3
        List<IntrospectedColumn> introspectedColumns = introspectedTable.getPrimaryKeyColumns();
        boolean annotate = introspectedColumns.size() > 1;
        if (annotate) {
            importedTypes.add(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param")); //$NON-NLS-1$
        }
        StringBuilder sb = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
            importedTypes.add(type);
            Parameter parameter = new Parameter(type, introspectedColumn.getJavaProperty());
            if (annotate) {
                sb.setLength(0);
                sb.append("@Param(\""); //$NON-NLS-1$
                sb.append(introspectedColumn.getJavaProperty());
                sb.append("\")"); //$NON-NLS-1$
                parameter.addAnnotation(sb.toString());
            }
            method.addParameter(parameter);
        }

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);

    }

    private void addInsertSelective(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        method.setReturnType(new FullyQualifiedJavaType(Integer.class.getName()));

        method.setName(this.getIntrospectedTable().getInsertSelectiveStatementId());

        FullyQualifiedJavaType paramType = this.getIntrospectedTable().getRules().calculateAllFieldsClass();
        importedTypes.add(paramType);
        method.addParameter(new Parameter(paramType, "obj"));

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);

    }

    private void addCountByMap(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        method.setReturnType(new FullyQualifiedJavaType(Integer.class.getName()));

        method.setName(SqlIds.getInstance().getIdCountByMap());

        FullyQualifiedJavaType paramMapType = new FullyQualifiedJavaType(Map.class.getName());
        importedTypes.add(paramMapType);
        paramMapType.addTypeArgument(new FullyQualifiedJavaType(String.class.getName()));
        paramMapType.addTypeArgument(new FullyQualifiedJavaType(Object.class.getName()));
        method.addParameter(new Parameter(paramMapType, "map"));

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);

    }

    private void addSelectByMap(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType objType = introspectedTable.getRules().calculateAllFieldsClass();
        FullyQualifiedJavaType listType = new FullyQualifiedJavaType(List.class.getName());
        listType.addTypeArgument(objType);

        method.setReturnType(listType);

        importedTypes.add(objType);
        importedTypes.add(listType);

        method.setName(SqlIds.getInstance().getIdSelectByMap());

        FullyQualifiedJavaType paramMapType = new FullyQualifiedJavaType(Map.class.getName());
        importedTypes.add(paramMapType);
        paramMapType.addTypeArgument(new FullyQualifiedJavaType(String.class.getName()));
        paramMapType.addTypeArgument(new FullyQualifiedJavaType(Object.class.getName()));
        method.addParameter(new Parameter(paramMapType, "map"));

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);

    }

    private void addSelectByMapWithRowBounds(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType objType = introspectedTable.getRules().calculateAllFieldsClass();
        FullyQualifiedJavaType listType = new FullyQualifiedJavaType(List.class.getName());
        listType.addTypeArgument(objType);
        

        method.setReturnType(listType);

        importedTypes.add(objType);
        importedTypes.add(listType);

        method.setName(SqlIds.getInstance().getIdSelectByMap());

        FullyQualifiedJavaType paramMapType = new FullyQualifiedJavaType(Map.class.getName());
        importedTypes.add(paramMapType);
        paramMapType.addTypeArgument(new FullyQualifiedJavaType(String.class.getName()));
        paramMapType.addTypeArgument(new FullyQualifiedJavaType(Object.class.getName()));
        method.addParameter(new Parameter(paramMapType, "map"));

        FullyQualifiedJavaType rbType = new FullyQualifiedJavaType("org.apache.ibatis.session.RowBounds");
        importedTypes.add(rbType);
        method.addParameter(new Parameter(rbType, "rb"));

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);

    }

    private void addSelectById(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType returnType = introspectedTable.getRules().calculateAllFieldsClass();
        method.setReturnType(returnType);
        importedTypes.add(returnType);

        method.setName(SqlIds.getInstance().getIdSelectById());

        // no primary key class - fields are in the base class
        // if more than one PK field, then we need to annotate the
        // parameters
        // for MyBatis3
        List<IntrospectedColumn> introspectedColumns = introspectedTable.getPrimaryKeyColumns();
        boolean annotate = introspectedColumns.size() > 1;
        if (annotate) {
            importedTypes.add(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param")); //$NON-NLS-1$
        }
        StringBuilder sb = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
            importedTypes.add(type);
            Parameter parameter = new Parameter(type, introspectedColumn.getJavaProperty());
            if (annotate) {
                sb.setLength(0);
                sb.append("@Param(\""); //$NON-NLS-1$
                sb.append(introspectedColumn.getJavaProperty());
                sb.append("\")"); //$NON-NLS-1$
                parameter.addAnnotation(sb.toString());
            }
            method.addParameter(parameter);
        }

        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

        if (context.getPlugins().clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable)) {
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }

    @Override
    public AbstractXmlGenerator getMatchedXMLGenerator() {
        return new CustomXmlMapperGenerator();
    }

}
