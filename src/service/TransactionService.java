package service;

import java.util.ArrayList;

import model.Transaksi; // Memanggil modul/class Transaksi pada folder model

// Class untuk mengelola seluruh transaksi yang telah selesai dilakukan.
public class TransactionService {

    // Menyimpan seluruh riwayat transaksi selama program berjalan.
    private ArrayList<Transaksi> riwayatTransaksi;

    // Constructor
    public TransactionService() {
        riwayatTransaksi = new ArrayList<>();
    }

    // Menambahkan transaksi baru ke dalam riwayat.
    // Kompleksitas: O(1), karena menambahkan elemen di akhir ArrayList.
    public void tambahTransaksi(Transaksi transaksi) {
        riwayatTransaksi.add(transaksi);
    }

    // Mengembalikan seluruh riwayat transaksi.
    // Kompleksitas: O(1).
    public ArrayList<Transaksi> getRiwayatTransaksi() {
        return riwayatTransaksi;
    }

    // Menampilkan seluruh transaksi yang pernah dilakukan.
    // Kompleksitas: O(n), karena menampilkan seluruh isi ArrayList.
    public void tampilkanRiwayatTransaksi() {

        if (riwayatTransaksi.isEmpty()) {
            System.out.println("\nBelum ada transaksi.");
            return;
        }

        System.out.println("\n========== RIWAYAT TRANSAKSI ==========");

        for (Transaksi transaksi : riwayatTransaksi) {
            System.out.println(transaksi);
        }
    }

    // Menghitung jumlah transaksi yang tersimpan.
    // Kompleksitas: O(1).
    public int getJumlahTransaksi() {
        return riwayatTransaksi.size();
    }

}