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
            new User("customer", "cust123", "customer")
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
                else customerMenu();
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

    public static void customerMenu() {
        System.out.println("Fitur Customer belum diimplementasikan!");
    }
}
