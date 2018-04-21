package OpenDatabaseHelper.Util;

import java.sql.ResultSet;

/**
 * Created by WangYH on 2016/7/25.
 */
public interface IDataJob {
    void call(ResultSet o);
}
