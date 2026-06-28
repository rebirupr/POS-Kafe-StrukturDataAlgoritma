package model;

import java.time.LocalDateTime; // Untuk waktu "Pembelian" yang real time
import java.util.ArrayList;

// Menyimpan seluruh informasi satu transaksi yang sudah selesai dibayar.
// Data transaksi akan digunakan untuk mencetak struk, menyimpan riwayat,
// menghitung pendapatan, dan membuat laporan penjualan.
public class Transaksi {

    // ID unik transaksi.
    private String idTransaksi;

    // Nomor antrean pelanggan.
    private String nomorAntrean;

    // Seluruh item yang dibeli pelanggan.
    private ArrayList<ItemKeranjang> daftarItem;

    // Total harga seluruh belanja.
    private int totalHarga;

    // Waktu transaksi dilakukan.
    private LocalDateTime waktuTransaksi;

    // Constructor digunakan ketika proses checkout berhasil.
    public Transaksi(String idTransaksi,
                     String nomorAntrean,
                     ArrayList<ItemKeranjang> daftarItem,
                     int totalHarga,
                     LocalDateTime waktuTransaksi) {

        this.idTransaksi = idTransaksi;
        this.nomorAntrean = nomorAntrean;
        this.daftarItem = daftarItem;
        this.totalHarga = totalHarga;
        this.waktuTransaksi = waktuTransaksi;
    }

    // Mengembalikan ID transaksi.
    public String getIdTransaksi() {
        return idTransaksi;
    }

    // Mengembalikan nomor antrean pelanggan.
    public String getNomorAntrean() {
        return nomorAntrean;
    }

    // Mengembalikan seluruh item yang dibeli pelanggan.
    public ArrayList<ItemKeranjang> getDaftarItem() {
        return daftarItem;
    }

    // Mengembalikan total harga transaksi.
    public int getTotalHarga() {
        return totalHarga;
    }

    // Mengembalikan waktu transaksi dilakukan.
    public LocalDateTime getWaktuTransaksi() {
        return waktuTransaksi;
    }

    // Menampilkan ringkasan transaksi.
    @Override
    public String toString() {
        return "Transaksi " + idTransaksi +
               " | Antrean: " + nomorAntrean +
               " | Total: Rp" + totalHarga;
    }
}