package service;

import java.util.ArrayList;
import java.util.HashMap;
import model.ItemKeranjang;
import model.Transaksi;

// Class untuk membuat laporan penjualan.
public class ReportService {

    private TransactionService transactionService;

    // Constructor
    public ReportService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Menampilkan seluruh laporan penjualan.
    // Kompleksitas: O(n + m)
    // n = jumlah transaksi
    // m = jumlah item yang terjual
    public void tampilkanLaporan() {

        System.out.println("\n========== LAPORAN PENJUALAN ==========");
        System.out.println("Jumlah Transaksi : " + getJumlahTransaksi());
        System.out.println("Jumlah Item      : " + hitungJumlahItemTerjual());
        System.out.println("Total Pendapatan : Rp" + hitungTotalPendapatan());

        tampilkanProdukTerlaris();

    }

    // Menghitung total pendapatan.
    // Kompleksitas: O(n)
    public int hitungTotalPendapatan() {

        int total = 0;

        for (Transaksi transaksi : transactionService.getRiwayatTransaksi()) {
            total += transaksi.getTotalHarga();
        }

        return total;

    }

    // Menghitung total item yang berhasil terjual.
    // Kompleksitas: O(m)
    public int hitungJumlahItemTerjual() {

        int jumlah = 0;

        for (Transaksi transaksi : transactionService.getRiwayatTransaksi()) {

            for (ItemKeranjang item : transaksi.getDaftarItem()) {

                jumlah += item.getJumlah();

            }

        }

        return jumlah;

    }

    // Mengembalikan jumlah transaksi.
    // Kompleksitas: O(1)
    public int getJumlahTransaksi() {
        return transactionService.getJumlahTransaksi();
    }

    // Menampilkan produk terlaris.
    // Kompleksitas: O(m)
    public void tampilkanProdukTerlaris() {

        ArrayList<Transaksi> riwayat = transactionService.getRiwayatTransaksi();

        if (riwayat.isEmpty()) {

            System.out.println("Produk Terlaris : -");
            return;

        }

        HashMap<String, Integer> frekuensi = new HashMap<>();

        for (Transaksi transaksi : riwayat) {

            for (ItemKeranjang item : transaksi.getDaftarItem()) {

                String namaProduk = item.getProduk().getNama();

                frekuensi.put(
                        namaProduk,
                        frekuensi.getOrDefault(namaProduk, 0) + item.getJumlah());

            }

        }

        String produkTerlaris = "";
        int jumlahTerjual = 0;

        for (String nama : frekuensi.keySet()) {

            if (frekuensi.get(nama) > jumlahTerjual) {

                produkTerlaris = nama;
                jumlahTerjual = frekuensi.get(nama);

            }

        }

        System.out.println("Produk Terlaris : " + produkTerlaris);
        System.out.println("Jumlah Terjual  : " + jumlahTerjual);

    }

}