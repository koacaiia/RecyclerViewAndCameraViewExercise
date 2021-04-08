package fine.koacaiia.recyclerviewandcameraviewexercise;

public class MnfCargoList {
    String date,bl,count,remark,qty,plt,cbm,des;
    public MnfCargoList(){

    }

    public MnfCargoList(String date, String bl, String count, String remark, String qty, String plt, String cbm, String des) {
        this.date = date;
        this.bl = bl;
        this.count = count;
        this.remark = remark;
        this.qty = qty;
        this.plt = plt;
        this.cbm = cbm;
        this.des = des;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBl() {
        return bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
