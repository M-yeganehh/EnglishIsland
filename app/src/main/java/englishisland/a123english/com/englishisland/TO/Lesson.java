package englishisland.a123english.com.englishisland.TO;

import java.io.Serializable;

/**
 * Created by Mohamad on 6/27/2016.
 */
public class Lesson implements Serializable {
    String name;
    int pic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
}
