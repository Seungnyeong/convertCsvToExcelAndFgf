import java.lang.reflect.Field;
import java.util.UUID;

public class FgfVO {
    private StringBuilder pk;
    private StringBuilder sentense;
    private StringBuilder category_l;
    private StringBuilder category_m;
    private StringBuilder category_s;
    private Field[] field =   FgfVO.class.getDeclaredFields();

    public FgfVO ()  {
        this.pk = new StringBuilder();
        this.sentense = new StringBuilder();
        this.category_l = new StringBuilder();
        this.category_m = new StringBuilder();
        this.category_s = new StringBuilder();
    }
    public String getPk() {
        return pk.toString();
    }

    public void setPk(String pk) {
        this.pk.insert(0,"<__"+ field[0].getName() +"__>").append(pk);
    }

    public String getSentense() {
        return sentense.toString();
    }

    public void setSentense(String sentense) {
        this.sentense.insert(0,"<__"+ field[1].getName() +"__>").append(sentense);
    }

    public String getCategory_l() {
        return category_l.toString();
    }

    public void setCategory_l(String category_l) {
        this.category_l.insert(0,"<__"+ field[2].getName() +"__>").append(category_l);
    }

    public String getCategory_m() {
        return category_m.toString();
    }

    public void setCategory_m(String category_m) {
        this.category_m.insert(0,"<__"+ field[3].getName() +"__>").append(category_m);
    }

    public String getCategory_s() {
        return category_s.toString();
    }

    public void setCategory_s(String category_s) {
        this.category_s.insert(0, "<__"+ field[4].getName() +"__>").append(category_s);
    }

}
