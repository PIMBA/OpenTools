package OpenDatabaseHelper.LiteIOC.SimpleInjectHandlerImp;

import OpenDatabaseHelper.LiteIOC.IInjectHandler;

/**
 * Created by WangYH on 2017/1/27.
 */
public class String2BooleanHandler implements IInjectHandler<Boolean> {
    @Override
    public Boolean excute(Object x) {
        return Boolean.parseBoolean((String)x);
    }
}
