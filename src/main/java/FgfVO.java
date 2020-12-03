import java.util.UUID;

public class FgfVO {
    private String pk;
    private String sentense;
    private String category_l;
    private String category_m;
    private String category_s;


    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = "<__no__>" + pk;
    }

    public String getSentense() {
        return sentense;
    }

    public void setSentense(String sentense) {
        this.sentense = "<__sentense__>" + sentense;
    }

    public String getCategory_l() {
        return category_l;
    }

    public void setCategory_l(String category_l) {
        this.category_l = "<__cls_l__>" + category_l;
    }

    public String getCategory_m() {
        return category_m;
    }

    public void setCategory_m(String category_m) {
        this.category_m = "<__cls_m__>" +  category_m;
    }

    public String getCategory_s() {
        return category_s;
    }

    public void setCategory_s(String category_s) {
        this.category_s = "<__cls_s__>" + category_s;
    }


}
