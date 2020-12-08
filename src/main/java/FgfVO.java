import java.lang.reflect.Field;
import java.util.UUID;

public class FgfVO {

    private StringBuilder pk;
    private StringBuilder sentence;
    private StringBuilder category_l;
    private StringBuilder category_m;
    private StringBuilder category_m_code;
    private StringBuilder category_s;
    private StringBuilder category_s_code;

    public String getPk() {
        return pk.toString();
    }

    public String getSentence() {
        return sentence.toString();
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

    public String getCategory_m_code() {
        return category_m_code.toString();
    }

    public String getCategory_s_code() {
        return category_s_code.toString();
    }

    public static class Builder {
        private static final String ESCAPE_ENTER = "\n";
        private StringBuilder pk;
        private StringBuilder sentence;
        private StringBuilder category_l;
        private StringBuilder category_m;
        private StringBuilder category_m_code;
        private StringBuilder category_s;
        private StringBuilder category_s_code;
        Field[] field =   FgfVO.class.getDeclaredFields();

        public Builder () {
            this.pk = new StringBuilder();
            this.sentence = new StringBuilder();
            this.category_l = new StringBuilder();
            this.category_m = new StringBuilder();
            this.category_m_code = new StringBuilder();
            this.category_s = new StringBuilder();
            this.category_s_code = new StringBuilder();
        }

        public Builder setPk(String pk) {
            this.pk.insert(0,"<__"+ field[0].getName() +"__>").append(pk).append(UUID.randomUUID()).append(ESCAPE_ENTER);
            return this;
        }

        public Builder setSentence(String sentence) {
            this.sentence.insert(0,"<__"+ field[1].getName() +"__>").append(sentence).append(ESCAPE_ENTER);
            return this;
        }

        public Builder setCategory_l(String category_l) {
            this.category_l.insert(0,"<__"+field[2].getName()  +"__>").append(category_l).append(ESCAPE_ENTER);
            return this;
        }

        public Builder setCategory_m(String category_m) {
            this.category_m.insert(0,"<__"+ field[3].getName() +"__>").append(category_m).append(ESCAPE_ENTER);
            return this;
        }

        public Builder setCategory_m_code(String category_m_code) {
            this.category_m_code.insert(0,"<__"+ field[4].getName()  +"__>").append(category_m_code).append(ESCAPE_ENTER);
            return this;
        }

        public Builder setCategory_s(String category_s) {
            this.category_s.insert(0, "<__"+ field[5].getName()  +"__>").append(category_s).append(ESCAPE_ENTER);
            return this;
        }

        public Builder setCategory_s_code(String category_s_code) {
            this.category_s_code.insert(0, "<__"+ field[6].getName() +"__>").append(category_s_code).append(ESCAPE_ENTER);
            return this;
        }

        public FgfVO build() {
            FgfVO fgfVO = new FgfVO();
            fgfVO.pk = pk;
            fgfVO.sentence = sentence;
            fgfVO.category_l = category_l;
            fgfVO.category_m = category_m;
            fgfVO.category_m_code = category_m_code;
            fgfVO.category_s = category_s;
            fgfVO.category_s_code = category_s_code;
            return fgfVO;
        }
    }
}
