import java.lang.reflect.Field;
import java.util.UUID;

public class FgfVO {
    private StringBuilder pk;
    private StringBuilder sentense;
    private StringBuilder category_l;
    private StringBuilder category_m;
    private StringBuilder category_s;

    public String getPk() {
        return pk.toString();
    }

    public String getSentense() {
        return sentense.toString();
    }

    public String getCategory_l() {
        return category_l.toString();
    }

    public String getCategory_m() {
        return category_m.toString();
    }

    public String getCategory_s() {
        return category_s.toString();
    }

    public static class Builder {
        private StringBuilder pk;
        private StringBuilder sentense;
        private StringBuilder category_l;
        private StringBuilder category_m;
        private StringBuilder category_s;
        Field[] field =   FgfVO.class.getDeclaredFields();

        public Builder () {
            this.pk = new StringBuilder();
            this.sentense = new StringBuilder();
            this.category_l = new StringBuilder();
            this.category_m = new StringBuilder();
            this.category_s = new StringBuilder();
        }

        public Builder setPk(String pk) {
            this.pk.insert(0,"<__"+ field[0].getName() +"__>").append(pk);
            return this;
        }

        public Builder setSentense(String sentense) {
            this.sentense.insert(0,"<__"+ field[1].getName() +"__>").append(sentense);
            return this;
        }

        public Builder setCategory_l(String category_l) {
            this.category_l.insert(0,"<__"+ field[2].getName() +"__>").append(category_l);
            return this;
        }

        public Builder setCategory_m(String category_m) {
            this.category_m.insert(0,"<__"+ field[3].getName() +"__>").append(category_m);
            return this;
        }

        public Builder setCategory_s(String category_s) {
            this.category_s.insert(0, "<__"+ field[4].getName() +"__>").append(category_s);
            return this;
        }

        public FgfVO build() {
            FgfVO fgfVO = new FgfVO();
            fgfVO.pk = pk;
            fgfVO.sentense = sentense;
            fgfVO.category_l = category_l;
            fgfVO.category_m = category_m;
            fgfVO.category_s = category_s;
            return fgfVO;
        }
    }
}
