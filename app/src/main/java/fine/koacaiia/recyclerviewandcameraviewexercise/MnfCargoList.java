package fine.koacaiia.recyclerviewandcameraviewexercise;

public class MnfCargoList {
    String bl;
    String remark;
    String date;
    String count;
    String des;
    String plt;
    String cbm;
    String qty;
    int intDate;

    public MnfCargoList(){

    }

    public String getBl() {
        return bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getPlt() {
        return plt;
    }

    public void setPlt(String plt) {
        this.plt = plt;
    }

    public String getCbm() {
        return cbm;
    }

    public void setCbm(String cbm) {
        this.cbm = cbm;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public int getIntDate() {
        return intDate;
    }

    public void setIntDate(int intDate) {
        this.intDate = intDate;
    }

    public MnfCargoList(String bl, String remark, String date, String count, String des, String plt, String cbm, String qty,
                        int intDate) {
        this.bl = bl;
        this.remark = remark;
        this.date = date;
        this.count = count;
        this.des = des;
        this.plt = plt;
        this.cbm = cbm;
        this.qty = qty;
        this.intDate = intDate;
    }
}
