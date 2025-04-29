public class PortofolioSBN {
    SuratBerhargaNegara sbn;
    double nominal;

    public PortofolioSBN(SuratBerhargaNegara sbn, double nominal) {
        this.sbn = sbn;
        this.nominal = nominal;
    }

    public double hitungKuponBulanan() {
        return (sbn.bunga / 12.0) * 0.9 * nominal;
    }
}
