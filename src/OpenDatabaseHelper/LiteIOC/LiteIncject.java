package OpenDatabaseHelper.LiteIOC;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by WangYH on 2017/1/24.
 */
public class LiteIncject {
    public static void inject(Serializable obj, Map<String, Object> container){

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean flag = field.isAccessible();
            field.setAccessible(true);
            Inject inject = field.getAnnotation(Inject.class);
            if (inject != null) {
                String name = inject.value();
                if (name.isEmpty()) {
                    name = field.getName();
                }
                if (container.containsKey(name)) {
                    try {
                        Object value = container.get(name);
                        value = inject.handler().newInstance().excute(value);
                        field.set(obj,value);
                    } catch (IllegalAccessException | ClassCastException | InstantiationException ignored) {
                    }
                }
            }
            field.setAccessible(flag);
        }
    }
}
