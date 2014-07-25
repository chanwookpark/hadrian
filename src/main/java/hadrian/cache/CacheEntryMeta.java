package hadrian.cache;

/**
 * Created by chanwook on 2014. 7. 1..
 */
public class CacheEntryMeta {
    private String key;
    private int expireTime;

    public void key(String key) {
        this.key = key;
    }

    public void expireTime(int time) {
        this.expireTime = time;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public String getKey() {
        return key;
    }
}
