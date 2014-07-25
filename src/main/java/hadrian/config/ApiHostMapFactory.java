package hadrian.config;

import hadrian.model.ApiHostMap;

/**
 * Created by chanwook on 2014. 6. 21..
 */
public interface ApiHostMapFactory {
    ApiHostMap loadHostMap();
}
