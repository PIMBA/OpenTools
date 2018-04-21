package OpenDatabaseHelper.LiteIOC;

import OpenDatabaseHelper.LiteIOC.SimpleInjectHandlerImp.SimpleInjectHandler;

import java.lang.annotation.*;

/**
 * Created by WangYH on 2017/1/24.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inject {
    String value() default "";
    Class clazz() default Object.class;
    Class<? extends IInjectHandler> handler() default SimpleInjectHandler.class;
}
