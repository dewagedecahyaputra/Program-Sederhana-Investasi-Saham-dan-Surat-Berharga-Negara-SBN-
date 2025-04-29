import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class InvestasiApp {
    static Scanner scanner = new Scanner(System.in);
    static List<User> users = new ArrayList<>(Arrays.asList(
        new User("admin", "admin", "admin"),
        new Customer("cust", "cust")
    ));
    static List<Saham> daftarSaham = new ArrayList<>(Arrays.asList(
        new Saham("BBRI", "Bank Rakyat Indonesia", 5000),
        new Saham("TLKM", "Telkom Indonesia", 4000),
        new Saham("ASII", "Astra International", 6000)
    ));
    static List<SuratBerhargaNegara> daftarSBN = new ArrayList<>(Arrays.asList(
        new SuratBerhargaNegara("ORI021", 0.06, 3, "15/07/2026", 10000000000.0),
        new SuratBerhargaNegara("SR018", 0.0575, 3, "10/03/2027", 5000000000.0)
    ));

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== APLIKASI INVESTASI =====");
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            User user = login(username, password);
            if (user == null) {
                System.out.println("Login gagal!");
                continue;
            }

            if (user.role.equals("admin")) {
                menuAdmin();
            } else if (user.role.equals("customer")) {
                menuCustomer((Customer) user);
            }
        }
    }

    static User login(String username, String password) {
        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                return user;
            }
        }
        return null;
    }

    static void menuAdmin() {
        while (true) {
            System.out.println("\n===== MENU ADMIN =====");
            System.out.println("1. Tambah Saham");
            System.out.println("2. Tambah SBN");
            System.out.println("3. Lihat Daftar Saham");
            System.out.println("4. Lihat Daftar SBN");
            System.out.println("5. Logout");
            System.out.print("Pilih menu: ");
            String pilihan = scanner.nextLine();

            switch (pilihan) {
                case "1":
                    System.out.print("Kode saham: ");
                    String kode = scanner.nextLine();
                    System.out.print("Nama perusahaan: ");
                    String namaPerusahaan = scanner.nextLine();
                    System.out.print("Harga saham: ");
                    double harga = Double.parseDouble(scanner.nextLine());
                    daftarSaham.add(new Saham(kode, namaPerusahaan, harga));
                    System.out.println("Saham berhasil ditambahkan.");
                    break;
                case "2":
                    System.out.print("Nama SBN: ");
                    String nama = scanner.nextLine();
                    System.out.print("Bunga (contoh 0.06): ");
                    double bunga = Double.parseDouble(scanner.nextLine());
                    System.out.print("Jangka waktu (tahun): ");
                    int jangka = Integer.parseInt(scanner.nextLine());
                    System.out.print("Tanggal jatuh tempo: ");
                    String jatuhTempo = scanner.nextLine();
                    System.out.print("Kuota nasional: ");
                    double kuota = Double.parseDouble(scanner.nextLine());
                    daftarSBN.add(new SuratBerhargaNegara(nama, bunga, jangka, jatuhTempo, kuota));
                    System.out.println("SBN berhasil ditambahkan.");
                    break;
                case "3":
                    tampilkanDaftarSaham();
                    break;
                case "4":
                    tampilkanDaftarSBN();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    static void menuCustomer(Customer customer) {
        while (true) {
            System.out.println("\n===== MENU CUSTOMER =====");
            System.out.println("1. Beli Saham");
            System.out.println("2. Jual Saham");
            System.out.println("3. Lihat Portofolio Saham");
            System.out.println("4. Beli SBN");
            System.out.println("5. Lihat Portofolio SBN");
            System.out.println("6. Logout");
            System.out.print("Pilih menu: ");
            String pilihan = scanner.nextLine();

            switch (pilihan) {
                case "1":
                    tampilkanDaftarSaham();
                    System.out.print("Masukkan kode saham: ");
                    String kodeBeli = scanner.nextLine();
                    Saham sahamBeli = cariSaham(kodeBeli);
                    if (sahamBeli != null) {
                        System.out.print("Jumlah lembar: ");
                        int jumlah = Integer.parseInt(scanner.nextLine());
                        customer.tambahSaham(sahamBeli, jumlah);
                        System.out.println("Saham berhasil dibeli.");
                    } else {
                        System.out.println("Saham tidak ditemukan.");
                    }
                    break;
                case "2":
                    tampilkanPortofolioSaham(customer);
                    System.out.print("Masukkan kode saham: ");
                    String kodeJual = scanner.nextLine();
                    Saham sahamJual = cariSaham(kodeJual);
                    if (sahamJual != null) {
                        System.out.print("Jumlah lembar: ");
                        int jumlah = Integer.parseInt(scanner.nextLine());
                        boolean sukses = customer.kurangiSaham(sahamJual, jumlah);
                        if (sukses) {
                            System.out.println("Saham berhasil dijual.");
                        } else {
                            System.out.println("Jumlah tidak mencukupi.");
                        }
                    } else {
                        System.out.println("Saham tidak ditemukan.");
                    }
                    break;
                case "3":
                    tampilkanPortofolioSaham(customer);
                    break;
                case "4":
                    tampilkanDaftarSBN();
                    System.out.print("Masukkan nama SBN: ");
                    String namaSbn = scanner.nextLine();
                    SuratBerhargaNegara sbn = cariSBN(namaSbn);
                    if (sbn != null) {
                        System.out.print("Nominal pembelian: ");
                        double nominal = Double.parseDouble(scanner.nextLine());
                        customer.beliSbn(sbn, nominal);
                        System.out.println("SBN berhasil dibeli.");
                    } else {
                        System.out.println("SBN tidak ditemukan.");
                    }
                    break;
                case "5":
                    tampilkanPortofolioSBN(customer);
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    static Saham cariSaham(String kode) {
        for (Saham saham : daftarSaham) {
            if (saham.kode.equalsIgnoreCase(kode)) {
                return saham;
            }
        }
        return null;
    }

    static SuratBerhargaNegara cariSBN(String nama) {
        for (SuratBerhargaNegara sbn : daftarSBN) {
            if (sbn.nama.equalsIgnoreCase(nama)) {
                return sbn;
            }
        }
        return null;
    }

    static void tampilkanDaftarSaham() {
        System.out.println("\n===== DAFTAR SAHAM =====");
        for (Saham saham : daftarSaham) {
            System.out.printf("%s - %s: Rp%.2f\n", saham.kode, saham.namaPerusahaan, saham.harga);
        }
    }

    static void tampilkanDaftarSBN() {
        System.out.println("\n===== DAFTAR SBN =====");
        for (SuratBerhargaNegara sbn : daftarSBN) {
            System.out.printf("%s - Bunga: %.2f%%, Jangka Waktu: %d tahun, Jatuh Tempo: %s, Kuota: Rp%.0f\n",
                sbn.nama, sbn.bunga * 100, sbn.jangkaWaktu, sbn.tanggalJatuhTempo, sbn.kuotaNasional);
        }
    }

    static void tampilkanPortofolioSaham(Customer customer) {
        System.out.println("\n===== PORTOFOLIO SAHAM =====");
        for (int i = 0; i < customer.sahamDimiliki.size(); i++) {
            Saham saham = customer.sahamDimiliki.get(i);
            int jumlah = customer.jumlahSaham.get(i);
            System.out.printf("%s - %s: %d lembar\n", saham.kode, saham.namaPerusahaan, jumlah);
        }
    }

    static void tampilkanPortofolioSBN(Customer customer) {
        System.out.println("\n===== PORTOFOLIO SBN =====");
        for (PortofolioSBN p : customer.sbnDimiliki) {
            System.out.printf("%s - Nominal: Rp%.2f, Kupon Bulanan: Rp%.2f\n",
                p.sbn.nama, p.nominal, p.hitungKuponBulanan());
        }
    }
}
