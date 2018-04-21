package OpenDatabaseHelper.LiteIOC.SimpleInjectHandlerImp;

import OpenDatabaseHelper.LiteIOC.IInjectHandler;

/**
 * Created by WangYH on 2017/1/24.
 */
public class SimpleInjectHandler implements IInjectHandler {
    @Override
    public Object excute(Object x) {
        return x;
    }
}
