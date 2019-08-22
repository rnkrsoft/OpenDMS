import com.rnkrsoft.framework.orm.jdbc.NameMode;
import com.rnkrsoft.framework.orm.jdbc.WordMode;
import com.rnkrsoft.framework.orm.jdbc.mysql.DataEngineType;
import com.rnkrsoft.framework.orm.untils.SqlScriptUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by rnkrsoft.com on 2019/6/30.
 */
public class Test1 {
    @Test
    public void test1() throws IOException {
        SqlScriptUtils.generate("./target/sql.sql", NameMode.entity, "", NameMode.entity, "", NameMode.entity, "", DataEngineType.AUTO, WordMode.lowerCase, WordMode.lowerCase, true, true, "com.rnkrsoft.opensource.dms.jdbc");

    }
}
