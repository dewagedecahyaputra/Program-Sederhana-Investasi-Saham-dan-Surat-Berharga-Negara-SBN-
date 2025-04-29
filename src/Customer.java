import java.util.List;
import java.util.ArrayList;

public class Customer extends User {
    List<Saham> sahamDimiliki = new ArrayList<>();
    List<Integer> jumlahSaham = new ArrayList<>();
    List<PortofolioSBN> sbnDimiliki = new ArrayList<>();

    public Customer(String username, String password) {
        super(username, password, "customer");
    }

    public void tambahSaham(Saham saham, int jumlah) {
        for (int i = 0; i < sahamDimiliki.size(); i++) {
            if (sahamDimiliki.get(i).kode.equals(saham.kode)) {
                jumlahSaham.set(i, jumlahSaham.get(i) + jumlah);
                return;
            }
        }
        sahamDimiliki.add(saham);
        jumlahSaham.add(jumlah);
    }

    public boolean kurangiSaham(Saham saham, int jumlah) {
        for (int i = 0; i < sahamDimiliki.size(); i++) {
            if (sahamDimiliki.get(i).kode.equals(saham.kode)) {
                if (jumlahSaham.get(i) >= jumlah) {
                    jumlahSaham.set(i, jumlahSaham.get(i) - jumlah);
                    if (jumlahSaham.get(i) == 0) {
                        sahamDimiliki.remove(i);
                        jumlahSaham.remove(i);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void beliSbn(SuratBerhargaNegara sbn, double nominal) {
        for (PortofolioSBN p : sbnDimiliki) {
            if (p.sbn.nama.equals(sbn.nama)) {
                p.nominal += nominal;
                return;
            }
        }
        sbnDimiliki.add(new PortofolioSBN(sbn, nominal));
    }
}
