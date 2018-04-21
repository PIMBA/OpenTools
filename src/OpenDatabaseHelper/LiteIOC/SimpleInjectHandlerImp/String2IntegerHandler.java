package OpenDatabaseHelper.LiteIOC.SimpleInjectHandlerImp;

import OpenDatabaseHelper.LiteIOC.IInjectHandler;

/**
 * Created by WangYH on 2017/1/30.
 */
public class String2IntegerHandler implements IInjectHandler<Integer>{

    @Override
    public Integer excute(Object x) {
        return Integer.parseInt((String)x);
    }
}
