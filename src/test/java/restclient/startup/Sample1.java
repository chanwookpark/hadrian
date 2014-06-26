package restclient.startup;

/**
 * Created by chanwook on 2014. 6. 18..
 */
public class Sample1 {

    private long id;

    private String text1;

    public Sample1() {
    }

    public Sample1(long id, String text1) {
        this.id = id;
        this.text1 = text1;
    }

    public long getId() {
        return id;
    }

    public String getText1() {
        return text1;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }
}
