import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class Saham {
    String kode, namaPerusahaan;
    double harga;

    public Saham(String kode, String namaPerusahaan, double harga) {
        this.kode = kode;
        this.namaPerusahaan = namaPerusahaan;
        this.harga = harga;
    }
}

class SuratBerhargaNegara {
    String nama;
    double bunga;
    int jangkaWaktu;
    String tanggalJatuhTempo;
    double kuotaNasional;

    public SuratBerhargaNegara(String nama, double bunga, int jangkaWaktu, String tanggalJatuhTempo, double kuotaNasional) {
        this.nama = nama;
        this.bunga = bunga;
        this.jangkaWaktu = jangkaWaktu;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.kuotaNasional = kuotaNasional;
    }
}

class Customer extends User {
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

class PortofolioSBN {
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

class User {
    String username, password, role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

class InvestasiApp {
    static Scanner scanner = new Scanner(System.in);
    static List<Saham> sahamList = new ArrayList<>();
    static List<SuratBerhargaNegara> sbnList = new ArrayList<>();
    static List<User> users = Arrays.asList(
    new User("admin", "admin123", "admin"),
    new Customer("customer", "cust123")
);

    public static void main(String[] args) {
        boolean run = true;
        while (run) {
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Pilih: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) login();
            else if (choice == 2) run = false;
        }
    }

    public static void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                if (user.role.equals("admin")) adminMenu();
                else customerMenu(user);
                return;
            }
        }
        System.out.println("Login gagal!");
    }

    public static void adminMenu() {
        boolean running = true;
        while (running) {
            System.out.println("1. Saham");
            System.out.println("2. SBN");
            System.out.println("3. Lihat Saham");
            System.out.println("4. Lihat SBN");
            System.out.println("5. Logout");
            System.out.print("Pilih: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) adminSahamMenu();
            else if (choice == 2) adminSbnMenu();
            else if (choice == 3) lihatSaham();
            else if (choice == 4) lihatSbn();
            else running = false;
        }
    }

    public static void adminSahamMenu() {
        boolean running = true;
        while (running) {
            System.out.println("1. Tambah Saham");
            System.out.println("2. Ubah Harga Saham");
            System.out.println("3. Kembali");
            System.out.print("Pilih: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) tambahSaham();
            else if (choice == 2) ubahHargaSaham();
            else running = false;
        }
    }

    public static void tambahSaham() {
        System.out.print("Kode Saham: ");
        String kode = scanner.nextLine();
        System.out.print("Nama Perusahaan: ");
        String nama = scanner.nextLine();
        System.out.print("Harga Saham: ");
        double harga = scanner.nextDouble();
        scanner.nextLine();

        sahamList.add(new Saham(kode, nama, harga));
        System.out.println("Saham berhasil ditambahkan!");
    }

    public static void ubahHargaSaham() {
        System.out.print("Kode Saham: ");
        String kode = scanner.nextLine();

        for (Saham saham : sahamList) {
            if (saham.kode.equals(kode)) {
                System.out.print("Harga Baru: ");
                saham.harga = scanner.nextDouble();
                scanner.nextLine();
                System.out.println("Harga saham berhasil diperbarui!");
                return;
            }
        }
        System.out.println("Saham tidak ditemukan!");
    }

    public static void adminSbnMenu() {
        boolean running = true;
        while (running) {
            System.out.println("1. Tambah SBN");
            System.out.println("2. Kembali");
            System.out.print("Pilih: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) tambahSbn();
            else running = false;
        }
    }

    public static void tambahSbn() {
        System.out.print("Nama SBN: ");
        String nama = scanner.nextLine();
        System.out.print("Bunga (%): ");
        double bunga = scanner.nextDouble();
        System.out.print("Jangka Waktu (tahun): ");
        int jangkaWaktu = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Tanggal Jatuh Tempo: ");
        String tanggalJatuhTempo = scanner.nextLine();
        System.out.print("Kuota Nasional: ");
        double kuotaNasional = scanner.nextDouble();
        scanner.nextLine();

        sbnList.add(new SuratBerhargaNegara(nama, bunga, jangkaWaktu, tanggalJatuhTempo, kuotaNasional));
        System.out.println("SBN berhasil ditambahkan!");
    }

    public static void lihatSaham() {
        if (sahamList.isEmpty()) {
            System.out.println("Belum ada saham yang tersedia.");
        } else {
            System.out.println("Daftar Saham:");
            for (Saham saham : sahamList) {
                System.out.println("Kode: " + saham.kode + ", Nama: " + saham.namaPerusahaan + ", Harga: Rp" + saham.harga);
            }
        }
    }

    public static void lihatSbn() {
        if (sbnList.isEmpty()) {
            System.out.println("Belum ada SBN yang tersedia.");
        } else {
            System.out.println("Daftar SBN:");
            for (SuratBerhargaNegara sbn : sbnList) {
                System.out.println("Nama: " + sbn.nama + ", Bunga: " + sbn.bunga + "%, Jangka Waktu: " + sbn.jangkaWaktu +
                        " tahun, Jatuh Tempo: " + sbn.tanggalJatuhTempo + ", Kuota: " + sbn.kuotaNasional);
            }
        }
    }

    public static void customerMenu(User user) {
    Customer customer = (Customer) user;
    boolean running = true;
    while (running) {
        System.out.println("1. Beli Saham");
        System.out.println("2. Jual Saham");
        System.out.println("3. Beli SBN");
        System.out.println("4. Simulasi SBN");
        System.out.println("5. Portofolio");
        System.out.println("6. Logout");
        System.out.print("Pilih: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 : beliSaham(customer);
            break;
            case 2 : jualSaham(customer);
            break;
            case 3 : beliSbn(customer);
            break;
            case 4 : simulasiSbn(customer);
            break;
            case 5 : lihatPortofolio(customer);
            break;
            default : running = false;
        }
    }
}

public static void beliSaham(Customer customer) {
    lihatSaham();
    System.out.print("Masukkan kode saham yang ingin dibeli: ");
    String kode = scanner.nextLine();

    for (Saham s : sahamList) {
        if (s.kode.equalsIgnoreCase(kode)) {
            System.out.print("Masukkan jumlah lembar saham: ");
            int jumlah = scanner.nextInt();
            scanner.nextLine();
            customer.tambahSaham(s, jumlah);
            System.out.println("Pembelian saham berhasil!");
            return;
        }
    }
    System.out.println("Kode saham tidak ditemukan.");
}

public static void jualSaham(Customer customer) {
    if (customer.sahamDimiliki.isEmpty()) {
        System.out.println("Anda belum memiliki saham.");
        return;
    }

    System.out.println("Saham yang Anda miliki:");
    for (int i = 0; i < customer.sahamDimiliki.size(); i++) {
        Saham s = customer.sahamDimiliki.get(i);
        System.out.println((i + 1) + ". " + s.kode + " - " + s.namaPerusahaan + " (" + customer.jumlahSaham.get(i) + " lembar)");
    }

    System.out.print("Pilih saham yang ingin dijual (nomor): ");
    int pilihan = scanner.nextInt() - 1;
    scanner.nextLine();
    if (pilihan < 0 || pilihan >= customer.sahamDimiliki.size()) {
        System.out.println("Pilihan tidak valid.");
        return;
    }

    System.out.print("Masukkan jumlah lembar yang ingin dijual: ");
    int jumlah = scanner.nextInt();
    scanner.nextLine();

    if (customer.kurangiSaham(customer.sahamDimiliki.get(pilihan), jumlah)) {
        System.out.println("Saham berhasil dijual.");
    } else {
        System.out.println("Gagal menjual saham: jumlah tidak mencukupi.");
    }
}

public static void beliSbn(Customer customer) {
    lihatSbn();
    System.out.print("Masukkan nama SBN: ");
    String nama = scanner.nextLine();

    for (SuratBerhargaNegara sbn : sbnList) {
        if (sbn.nama.equalsIgnoreCase(nama)) {
            System.out.print("Masukkan nominal pembelian: ");
            double nominal = scanner.nextDouble();
            scanner.nextLine();

            if (nominal <= sbn.kuotaNasional) {
                sbn.kuotaNasional -= nominal;
                customer.beliSbn(sbn, nominal);
                System.out.println("Pembelian SBN berhasil!");
            } else {
                System.out.println("Kuota nasional tidak mencukupi.");
            }
            return;
        }
    }
    System.out.println("SBN tidak ditemukan.");
}

public static void simulasiSbn(Customer customer) {
    System.out.print("Masukkan nominal investasi: ");
    double nominal = scanner.nextDouble();
    System.out.print("Masukkan bunga tahunan (%): ");
    double bunga = scanner.nextDouble();
    scanner.nextLine();

    double kupon = (bunga / 12.0) * 0.9 * nominal;
    System.out.printf("Simulasi kupon bulanan: Rp%.2f\n", kupon);
}

public static void lihatPortofolio(Customer customer) {
    System.out.println("===== Portofolio =====");
    System.out.println("Saham:");
    double totalPembelian = 0;
    double totalPasar = 0;

    for (int i = 0; i < customer.sahamDimiliki.size(); i++) {
        Saham s = customer.sahamDimiliki.get(i);
        int jumlah = customer.jumlahSaham.get(i);
        double nominalBeli = s.harga * jumlah;
        System.out.printf("- %s (%s): %d lembar, Total: Rp%.2f\n", s.namaPerusahaan, s.kode, jumlah, nominalBeli);
        totalPembelian += nominalBeli;
        totalPasar += s.harga * jumlah;
    }

    System.out.printf("Total nilai pasar saham: Rp%.2f\n", totalPasar);

    System.out.println("\nSBN:");
    for (PortofolioSBN sbn : customer.sbnDimiliki) {
        System.out.printf("- %s: Rp%.2f, Kupon/bulan: Rp%.2f\n", sbn.sbn.nama, sbn.nominal, sbn.hitungKuponBulanan());
    }
}

}
