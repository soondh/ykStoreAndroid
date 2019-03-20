package yokastore.youkagames.com.yokastore.client.engine.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by songdehua on 2018/5/25.
 */

public class BeanFactory {

    private Map<String , String> map;

    private static BeanFactory beanFactory;

    public static BeanFactory getFactory(){
        if (beanFactory == null){
            beanFactory = new BeanFactory();
        }
        return beanFactory;
    }

    private BeanFactory(){
        map = new HashMap<>();
        map.put("CommonEngine","yokastore.youkagames.com.yokastore.client.engine.impl.CommonEngineImpl");
    }


    /**
     * 根据配置文件，传入接口名称，生成对应的实体类
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getInstance(Class<T> clazz){
        String simpleName = clazz.getSimpleName();
        String className = map.get(simpleName);
        Object instance;
        try {
            return (T) Class.forName(className).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}