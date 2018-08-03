package org.wesc.ssm.dao.generator.base;

public class SqlIds {
    private final static SqlIds SQL_ID = new SqlIds();
    public static SqlIds getInstance(){
        return SQL_ID;
    }
    
    public String getIdMapWhereClause(){
        return "Map_Where_Clause";
    }
    
    public String getIdSelectById(){
        return "selectById";
    }
    

    public String getIdSelectByMap() {
        return "selectByMap";
    }

    public String getIdOrderByClause() {
        return "Order_By_Clause";
    }

    public String getIdCountByMap() {
        return "countByMap";
    }

    public String getIdDeleteById() {
        return "deleteById";
    }
    
    public String getIdDeleteByIds() {
        return "deleteByIds";
    }

    public String getIdDeleteByMap() {
        return "deleteByMap";
    }

    public String getIdUpdateSelectiveById() {
        return "updateSelectiveById";
    }

    public String getIdUpdateSelectiveByMap() {
        return "updateSelectiveByMap";
    }
}
