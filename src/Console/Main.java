package Console;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Login menu
        System.out.println("--- Login ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Pengguna pengguna = Pengguna.getFromDatabaseByUsernameAndPassword(username, password);
        if (pengguna == null) {
            System.out.println("Username atau password salah.");
            scanner.close();
            return;
        }

        while (true) {
            System.out.println("\n--- Sistem Manajemen Kendaraan (GARASIKU) ---");
            System.out.println("1. Kelola Kendaraan");
            System.out.println("2. Kelola Riwayat Servis");
            System.out.println("3. Kelola Akun Pengguna");
            System.out.println("4. Keluar");
            System.out.print("Pilih menu: ");
            int menu = scanner.nextInt();
            scanner.nextLine();

            switch (menu) {
                case 1:
                    System.out.println("1. Tambah Kendaraan");
                    System.out.println("2. Edit Kendaraan");
                    System.out.println("3. Hapus Kendaraan");
                    System.out.print("Pilih menu: ");
                    int kendaraanMenu = scanner.nextInt();
                    scanner.nextLine();

                    switch (kendaraanMenu) {
                        case 1:
                            tambahKendaraan(scanner);
                            break;
                        case 2:
                            editKendaraan(scanner);
                            break;
                        case 3:
                            hapusKendaraan(scanner);
                            break;
                        default:
                            System.out.println("Menu tidak valid.");
                            break;
                    }
                    break;
                case 2:
                    kelolaRiwayatServis(scanner);
                    break;
                case 3:
                    kelolaAkunPengguna(scanner);
                    break;
                case 4:
                    System.out.println("Terima kasih telah menggunakan sistem ini.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Menu tidak valid.");
                    break;
            }
        }
    }

    private static void tambahKendaraan(Scanner scanner) {
        System.out.println("Jenis Kendaraan (1. Motor, 2. Mobil): ");
        int jenisKendaraan = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Nomor Rangka: ");
        String nomorRangka = scanner.nextLine();

        System.out.println("Nomor Plat: ");
        String nomorPlat = scanner.nextLine();

        System.out.println("Tahun Pembuatan: ");
        int tahunPembuatan = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Merek Kendaraan: ");
        String merekKendaraan = scanner.nextLine();

        int id = idGenerator.getAndIncrement();

        if (jenisKendaraan == 1) {
            System.out.println("Warna Motor: ");
            String warnaMotor = scanner.nextLine();

            System.out.println("Jenis Motor: ");
            String jenisMotor = scanner.nextLine();

            // Create and save Kendaraan
            Kendaraan kendaraan = new Kendaraan(id, nomorRangka, nomorPlat, tahunPembuatan, merekKendaraan);
            kendaraan.saveToDatabase();

            // Create and save Motor
            Motor motor = new Motor(id, nomorRangka, nomorPlat, tahunPembuatan, merekKendaraan, warnaMotor, jenisMotor);
            motor.saveToDatabase();
        } else if (jenisKendaraan == 2) {
            System.out.println("Warna Mobil: ");
            String warnaMobil = scanner.nextLine();

            System.out.println("Jenis Transmisi: ");
            String jenisTransmisi = scanner.nextLine();

            // Create and save Kendaraan
            Kendaraan kendaraan = new Kendaraan(id, nomorRangka, nomorPlat, tahunPembuatan, merekKendaraan);
            kendaraan.saveToDatabase();

            // Create and save Mobil
            Mobil mobil = new Mobil(id, nomorRangka, nomorPlat, tahunPembuatan, merekKendaraan, warnaMobil, jenisTransmisi);
            mobil.saveToDatabase();
        } else {
            System.out.println("Jenis kendaraan tidak valid.");
        }
    }

    private static void editKendaraan(Scanner scanner) {
        System.out.println("Masukkan ID Kendaraan yang akan diedit: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Nomor Rangka: ");
        String nomorRangka = scanner.nextLine();

        System.out.println("Nomor Plat: ");
        String nomorPlat = scanner.nextLine();

        System.out.println("Tahun Pembuatan: ");
        int tahunPembuatan = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Merek Kendaraan: ");
        String merekKendaraan = scanner.nextLine();

        Kendaraan kendaraan = new Kendaraan(id, nomorRangka, nomorPlat, tahunPembuatan, merekKendaraan);
        kendaraan.updateInDatabase();
        System.out.println("Kendaraan berhasil diedit.");
    }

    private static void hapusKendaraan(Scanner scanner) {
        System.out.println("Masukkan ID Kendaraan yang akan dihapus: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Kendaraan.deleteFromDatabase(id);
        System.out.println("Kendaraan berhasil dihapus.");
    }

    private static void kelolaRiwayatServis(Scanner scanner) {
        System.out.println("Masukkan ID Kendaraan: ");
        int kendaraanId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("1. Tambah Riwayat Servis");
        System.out.println("2. Lihat Riwayat Servis");
        System.out.print("Pilih menu: ");
        int riwayatMenu = scanner.nextInt();
        scanner.nextLine();

        if (riwayatMenu == 1) {
            System.out.println("Jenis Servis: ");
            String jenisServis = scanner.nextLine();

            System.out.println("Keterangan Servis: ");
            String keteranganServis = scanner.nextLine();

            System.out.println("Tanggal Mulai (yyyy-mm-dd): ");
            String tanggalMulaiStr = scanner.nextLine();
            Date tanggalMulai = java.sql.Date.valueOf(tanggalMulaiStr);

            System.out.println("Tanggal Selesai (yyyy-mm-dd): ");
            String tanggalSelesaiStr = scanner.nextLine();
            Date tanggalSelesai = java.sql.Date.valueOf(tanggalSelesaiStr);

            System.out.println("Kilometer Akhir: ");
            int kilometerAkhir = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.println("Biaya: ");
            float biaya = scanner.nextFloat();
            scanner.nextLine(); // Consume newline

            int riwayatId = idGenerator.getAndIncrement();

            // Create and save Riwayat Servis
            RiwayatServis riwayatServis = new RiwayatServis(riwayatId, jenisServis, keteranganServis, tanggalMulai, tanggalSelesai, kilometerAkhir, biaya, kendaraanId);
            riwayatServis.saveToDatabase();
        } else if (riwayatMenu == 2) {
            ArrayList<RiwayatServis> riwayatServisList = RiwayatServis.getByKendaraanId(kendaraanId);
            for (RiwayatServis riwayatServis : riwayatServisList) {
                System.out.println(riwayatServis);
            }
        } else {
            System.out.println("Menu tidak valid.");
        }
    }

    private static void kelolaAkunPengguna(Scanner scanner) {
        System.out.println("1. Ubah Pengguna");
        System.out.println("2. Tampilkan Pengguna");
        System.out.print("Pilih menu: ");
        int akunMenu = scanner.nextInt();
        scanner.nextLine();

        switch (akunMenu) {
            case 1:
                ubahPengguna(scanner);
                break;
            case 2:
                tampilkanPengguna(scanner);
                break;
            default:
                System.out.println("Menu tidak valid.");
                break;
        }
    }

    private static void ubahPengguna(Scanner scanner) {
        System.out.println("Masukkan ID Pengguna yang akan diubah: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Nama Pengguna: ");
        String namaPengguna = scanner.nextLine();

        System.out.println("Nomor Telepon: ");
        String noTelepon = scanner.nextLine();

        System.out.println("Password: ");
        String password = scanner.nextLine();

        Pengguna pengguna = new Pengguna(id, namaPengguna, noTelepon, password, null);
        pengguna.updateInDatabase();
        System.out.println("Pengguna berhasil diubah.");
    }

    private static void tampilkanPengguna(Scanner scanner) {
        System.out.println("Masukkan ID Pengguna yang akan ditampilkan: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Pengguna pengguna = Pengguna.getFromDatabaseById(id);
        if (pengguna != null) {
            System.out.println(pengguna);
        } else {
            System.out.println("Pengguna tidak ditemukan.");
        }
    }
}