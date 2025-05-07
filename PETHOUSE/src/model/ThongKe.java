  

package model;

public class ThongKe {
    private String nhan;
    private double tongDoanhThu;

    public ThongKe(String nhan, double tongDoanhThu) {
        this.nhan = nhan;
        this.tongDoanhThu = tongDoanhThu;
    }

    public String getNhan() {
        return nhan;
    }

    public double getTongDoanhThu() {
        return tongDoanhThu;
    }
}
