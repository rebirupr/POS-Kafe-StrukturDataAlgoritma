import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import model.ItemKeranjang;
import model.Pelanggan;
import model.Produk;
import model.Transaksi;
import service.CartService;
import service.FileService;
import service.ProductService;
import service.QueueService;
import service.ReportService;
import service.TransactionService;

// Class utama yang menjalankan seluruh program kasir Seduh Kala.
// Tugas class ini cuma "menyambungkan" modul-modul yang udah dibuat anggota lain
// (ProductService, QueueService, CartService, TransactionService, ReportService, FileService)
// lewat tampilan menu di terminal, jadi gak ada logika besar baru di sini.
public class Main {

    static Scanner scanner = new Scanner(System.in);

    static ProductService productService = new ProductService();
    static QueueService queueService = new QueueService();
    static CartService cartService = new CartService();
    static TransactionService transactionService = new TransactionService();
    static ReportService reportService = new ReportService(transactionService);
    static FileService fileService = new FileService();

    public static void main(String[] args) {

        // Data produk & harga kustomisasi dimuat sekali aja waktu program pertama kali dijalankan
        productService.loadProduk();
        productService.loadHargaKustomisasi();

        System.out.println("==============================================");
        System.out.println("        KASIR SEDUH KALA - POS SYSTEM");
        System.out.println("==============================================");

        boolean lanjut = true;
        while (lanjut) {
            tampilkanMenuUtama();
            int pilihan = inputAngka("Pilih menu : ");

            switch (pilihan) {
                case 1:
                    tambahPelangganKeAntrean();
                    break;
                case 2:
                    layaniPelangganBerikutnya();
                    break;
                case 3:
                    lihatAntrean();
                    break;
                case 4:
                    lihatSemuaProduk();
                    break;
                case 5:
                    cariProduk();
                    break;
                case 6:
                    transactionService.tampilkanRiwayatTransaksi();
                    break;
                case 7:
                    reportService.tampilkanLaporan();
                    break;
                case 0:
                    lanjut = false;
                    System.out.println("\nTerima kasih, sampai jumpa lagi!");
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia, coba lagi.");
                    break;
            }
        }

        scanner.close();
    }

    // ======================== MENU UTAMA ========================

    private static void tampilkanMenuUtama() {
        System.out.println("\n================ MENU UTAMA ================");
        System.out.println("1. Tambah Pelanggan ke Antrean");
        System.out.println("2. Layani Pelanggan Berikutnya (Mulai Transaksi)");
        System.out.println("3. Lihat Antrean Pelanggan");
        System.out.println("4. Lihat Semua Produk");
        System.out.println("5. Cari Produk (ID / Nama)");
        System.out.println("6. Lihat Riwayat Transaksi");
        System.out.println("7. Lihat Laporan Penjualan");
        System.out.println("0. Keluar");
        System.out.println("=============================================");
    }

    // ======================== ANTREAN ========================

    private static void tambahPelangganKeAntrean() {
        System.out.print("Nama pelanggan : ");
        String nama = scanner.nextLine().trim();

        if (nama.isEmpty()) {
            System.out.println("Nama tidak boleh kosong.");
            return;
        }

        queueService.tambahAntrean(nama);
        System.out.println("Pelanggan \"" + nama + "\" berhasil ditambahkan ke antrean.");
        lihatAntrean();
    }

    private static void lihatAntrean() {
        if (queueService.isKosong()) {
            System.out.println("\nAntrean masih kosong.");
            return;
        }

        System.out.println("\n========== ANTREAN PELANGGAN ==========");
        for (Pelanggan p : queueService.lihatAntrean()) {
            System.out.println(p.getNomorAntrean() + " - " + p.getNama());
        }
    }

    private static void layaniPelangganBerikutnya() {
        if (queueService.isKosong()) {
            System.out.println("\nAntrean masih kosong, belum ada pelanggan yang bisa dilayani.");
            return;
        }

        Pelanggan pelanggan = queueService.layaniPelanggan();
        System.out.println("\nSedang melayani " + pelanggan.getNomorAntrean() + " - " + pelanggan.getNama());

        // Keranjang dipastikan kosong dulu sebelum transaksi pelanggan ini dimulai
        cartService.kosongkanKeranjang();

        menuTransaksi(pelanggan);
    }

    // ======================== TRANSAKSI / KASIR ========================

    private static void menuTransaksi(Pelanggan pelanggan) {
        boolean dalamTransaksi = true;

        while (dalamTransaksi) {
            System.out.println("\n------- TRANSAKSI : " + pelanggan.getNomorAntrean() + " (" + pelanggan.getNama() + ") -------");
            System.out.println("1. Tambah Item ke Keranjang");
            System.out.println("2. Lihat Keranjang");
            System.out.println("3. Undo / Void Item Terakhir");
            System.out.println("4. Checkout (Bayar)");
            System.out.println("5. Batalkan Transaksi Ini");
            System.out.println("0. Kembali ke Menu Utama");
            System.out.println("-----------------------------------------------------");

            int pilihan = inputAngka("Pilih menu : ");

            switch (pilihan) {
                case 1:
                    tambahItemKeKeranjang();
                    break;
                case 2:
                    lihatKeranjang();
                    break;
                case 3:
                    cartService.undoItem(productService);
                    break;
                case 4:
                    if (checkoutTransaksi(pelanggan)) {
                        dalamTransaksi = false;
                    }
                    break;
                case 5:
                    batalkanTransaksi();
                    dalamTransaksi = false;
                    break;
                case 0:
                    // Kalau keluar tapi keranjang masih ada isinya, stok yang sempat
                    // "dipesan" tadi harus dibalikin dulu, biar gak hangus sia-sia.
                    if (!cartService.getKeranjang().isEmpty()) {
                        System.out.println("Keranjang belum di-checkout, seluruh item dibatalkan otomatis.");
                        batalkanTransaksi();
                    }
                    dalamTransaksi = false;
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia, coba lagi.");
                    break;
            }
        }
    }

    private static void tambahItemKeKeranjang() {
        System.out.print("Masukkan ID Produk (barcode) : ");
        String id = scanner.nextLine().trim();

        Produk produk = productService.cariProdukById(id);

        if (produk == null) {
            System.out.println("Produk dengan ID \"" + id + "\" tidak ditemukan.");
            return;
        }

        System.out.println("Produk ditemukan : " + produk);

        int jumlah = inputAngka("Jumlah beli : ");

        if (!productService.stokCukup(id, jumlah)) {
            System.out.println("Stok tidak cukup. Sisa stok saat ini : " + produk.getStok());
            return;
        }

        Map<String, String> kustomisasi = new HashMap<>();
        kustomisasi.put("Ukuran", pilihOpsi("Ukuran", new String[] { "Regular", "Large" }));
        kustomisasi.put("Gula", pilihOpsi("Gula", new String[] { "Normal", "Less Sugar", "No Sugar" }));
        kustomisasi.put("Suhu", pilihOpsi("Suhu", new String[] { "Hot", "Ice" }));

        int hargaFinal = produk.getHargaDasar()
                + productService.getHargaKustomisasi(kustomisasi.get("Ukuran"))
                + productService.getHargaKustomisasi(kustomisasi.get("Gula"))
                + productService.getHargaKustomisasi(kustomisasi.get("Suhu"));

        ItemKeranjang item = new ItemKeranjang(produk, jumlah, kustomisasi, hargaFinal);
        cartService.tambahItem(item, productService);

        System.out.println(produk.getNama() + " x" + jumlah + " berhasil ditambahkan ke keranjang.");
    }

    // Menu pilihan kustomisasi sederhana berbasis nomor, dipakai berulang untuk Ukuran/Gula/Suhu
    private static String pilihOpsi(String namaKategori, String[] opsi) {
        System.out.println(namaKategori + " :");
        for (int i = 0; i < opsi.length; i++) {
            System.out.println((i + 1) + ". " + opsi[i]);
        }

        int pilihan = inputAngka("Pilih " + namaKategori + " : ");

        if (pilihan < 1 || pilihan > opsi.length) {
            System.out.println("Pilihan tidak valid, default ke \"" + opsi[0] + "\".");
            return opsi[0];
        }

        return opsi[pilihan - 1];
    }

    private static void lihatKeranjang() {
        Stack<ItemKeranjang> keranjang = cartService.getKeranjang();

        if (keranjang.isEmpty()) {
            System.out.println("\nKeranjang masih kosong.");
            return;
        }

        System.out.println("\n========== ISI KERANJANG ==========");
        int no = 1;
        for (ItemKeranjang item : keranjang) {
            int subtotal = item.getHargaFinal() * item.getJumlah();
            System.out.println(no + ". " + item.getProduk().getNama()
                    + " (" + item.getKustomisasi().get("Ukuran")
                    + ", " + item.getKustomisasi().get("Gula")
                    + ", " + item.getKustomisasi().get("Suhu") + ")");
            System.out.println("   Rp" + item.getHargaFinal() + " x " + item.getJumlah() + " = Rp" + subtotal);
            no++;
        }
        System.out.println("------------------------------------");
        System.out.println("Subtotal : Rp" + cartService.hitungSubtotal());
    }

    private static boolean checkoutTransaksi(Pelanggan pelanggan) {
        if (cartService.getKeranjang().isEmpty()) {
            System.out.println("Keranjang masih kosong, belum bisa checkout.");
            return false;
        }

        lihatKeranjang();

        int jumlahTransaksiSebelum = transactionService.getJumlahTransaksi();
        cartService.checkout(pelanggan, productService, transactionService);

        // Kalau jumlah transaksi bertambah, berarti checkout di atas berhasil
        // sehingga transaksi terakhir bisa disimpan juga ke file CSV.
        if (transactionService.getJumlahTransaksi() > jumlahTransaksiSebelum) {
            Transaksi transaksiTerakhir = transactionService.getRiwayatTransaksi()
                    .get(transactionService.getJumlahTransaksi() - 1);
            fileService.simpanTransaksi(transaksiTerakhir);
            fileService.simpanDetailTransaksi(transaksiTerakhir);
            return true;
        }

        return false;
    }

    private static void batalkanTransaksi() {
        Stack<ItemKeranjang> keranjang = cartService.getKeranjang();

        if (keranjang.isEmpty()) {
            System.out.println("Keranjang sudah kosong, tidak ada yang dibatalkan.");
            return;
        }

        // Semua item yang masih nyangkut di keranjang dikembalikan stoknya satu per satu,
        // soalnya stok udah kepotong sejak item itu dimasukkan (lihat tambahItem di CartService).
        for (ItemKeranjang item : keranjang) {
            productService.tambahStok(item.getProduk().getId(), item.getJumlah());
        }

        cartService.kosongkanKeranjang();
        System.out.println("Transaksi dibatalkan, seluruh stok yang terpakai sudah dikembalikan.");
    }

    // ======================== PRODUK ========================

    private static void lihatSemuaProduk() {
        System.out.println("\n========== DAFTAR PRODUK ==========");
        for (Produk p : productService.getSemuaProduk()) {
            System.out.println(p.getId() + " | " + p);
        }
    }

    private static void cariProduk() {
        System.out.println("\nCari berdasarkan :");
        System.out.println("1. ID Produk / Barcode");
        System.out.println("2. Nama Produk");

        int pilihan = inputAngka("Pilih : ");

        if (pilihan == 1) {
            System.out.print("Masukkan ID Produk : ");
            String id = scanner.nextLine().trim();
            Produk produk = productService.cariProdukById(id);

            if (produk == null) {
                System.out.println("Produk tidak ditemukan.");
            } else {
                System.out.println("Ditemukan : " + produk);
            }

        } else if (pilihan == 2) {
            System.out.print("Masukkan kata kunci nama : ");
            String keyword = scanner.nextLine().trim();
            List<Produk> hasil = productService.cariProdukByNama(keyword);

            if (hasil.isEmpty()) {
                System.out.println("Tidak ada produk yang cocok dengan \"" + keyword + "\".");
            } else {
                System.out.println("Hasil pencarian :");
                for (Produk p : hasil) {
                    System.out.println(p.getId() + " | " + p);
                }
            }

        } else {
            System.out.println("Pilihan tidak tersedia.");
        }
    }

    // ======================== UTIL INPUT ========================

    // Helper buat baca input angka dari Scanner, biar gak gampang crash kalau
    // user iseng masukin huruf. Kalau salah, terus diulang sampai bener.
    private static int inputAngka(String label) {
        while (true) {
            System.out.print(label);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid.");
            }
        }
    }

}
