package OpenDatabaseHelper.LiteIOC;

/**
 * Created by WangYH on 2017/1/24.
 */
public interface IInjectHandler<To> {
    To excute(Object x);
}
