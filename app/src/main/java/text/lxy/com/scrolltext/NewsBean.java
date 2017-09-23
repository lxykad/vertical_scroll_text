package text.lxy.com.scrolltext;

import java.io.Serializable;

/**
 * Created by lxy on 2017/9/23.
 */

public class NewsBean implements Serializable{

    public String title;

    public NewsBean(String title) {
        this.title = title;
    }
}
