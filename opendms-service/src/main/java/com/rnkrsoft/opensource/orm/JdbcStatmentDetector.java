package com.rnkrsoft.opensource.orm;

/**
 * Created by rnkrsoft.com on 2019/5/12.
 */
public class JdbcStatmentDetector {
    public static boolean isQuery(String sql){
        String temp = sql.toUpperCase().trim();
        if (isStatement(temp)){
            return temp.startsWith("SELECT");
        }else{
            return false;
        }
    }

    static boolean isStatement(String sql){
        return sql.startsWith("SELECT")
                || sql.startsWith("DELETE")
                || sql.startsWith("UPDATE")
                || sql.startsWith("INSERT" )
                || sql.startsWith("CREATE")
                || sql.startsWith("DROP")
                || sql.startsWith("TRUNCATE")
                ;
    }
}
