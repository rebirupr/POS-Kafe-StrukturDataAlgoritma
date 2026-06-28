package service;

import java.util.ArrayList;

import model.Transaksi;

// Class untuk membuat laporan penjualan.
public class ReportService {

    private TransactionService transactionService;

    // Constructor
    public ReportService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Menampilkan seluruh laporan.
    public void tampilkanLaporan() {

    }

    // Menghitung total pendapatan.
    public int hitungTotalPendapatan() {
        return 0;
    }

    // Menampilkan produk terlaris.
    public void tampilkanProdukTerlaris() {

    }

}