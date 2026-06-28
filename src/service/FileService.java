package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import model.ItemKeranjang;
import model.Transaksi;

// Class untuk membaca dan menulis file CSV.
public class FileService {

    // Constructor
    public FileService() {

    }

    // Membaca data produk dari file CSV.
    // Kompleksitas: O(n), karena membaca seluruh isi file.
    public void bacaProduk() {

        try {

            BufferedReader reader = new BufferedReader(
                    new FileReader("src/data/produk.csv"));

            String baris;

            // Lewati header
            reader.readLine();

            while ((baris = reader.readLine()) != null) {

                // Nanti akan diproses oleh ProductService.
                // Saat ini cukup dibaca agar file dapat diakses.
                String[] data = baris.split(",");

            }

            reader.close();

        } catch (IOException e) {

            System.out.println("Gagal membaca data produk.");

        }

    }

    // Menyimpan transaksi ke transaksi.csv.
    // Kompleksitas: O(1)
    public void simpanTransaksi(Transaksi transaksi) {

        try {

            BufferedWriter writer = new BufferedWriter(
                    new FileWriter("src/data/transaksi.csv", true));

            writer.write(
                    transaksi.getIdTransaksi() + "," +
                    transaksi.getNomorAntrean() + "," +
                    transaksi.getTotalHarga() + "," +
                    transaksi.getWaktuTransaksi());

            writer.newLine();

            writer.close();

        } catch (IOException e) {

            System.out.println("Gagal menyimpan transaksi.");

        }

    }

    // Menyimpan detail transaksi ke detail_transaksi.csv.
    // Kompleksitas: O(n), n = jumlah item dalam transaksi.
    public void simpanDetailTransaksi(Transaksi transaksi) {

        try {

            BufferedWriter writer = new BufferedWriter(
                    new FileWriter("src/data/detail_transaksi.csv", true));

            for (ItemKeranjang item : transaksi.getDaftarItem()) {

                writer.write(
                        transaksi.getIdTransaksi() + "," +
                        item.getNamaProduk() + "," +
                        item.getJumlah() + "," +
                        item.getHargaFinal());

                writer.newLine();

            }

            writer.close();

        } catch (IOException e) {

            System.out.println("Gagal menyimpan detail transaksi.");

        }

    }

    // Membaca seluruh riwayat transaksi.
    // Kompleksitas: O(n)
    public void bacaRiwayatTransaksi() {

        try {

            BufferedReader reader = new BufferedReader(
                    new FileReader("src/data/transaksi.csv"));

            String baris;

            reader.readLine();

            while ((baris = reader.readLine()) != null) {

                System.out.println(baris);

            }

            reader.close();

        } catch (IOException e) {

            System.out.println("Gagal membaca riwayat transaksi.");

        }

    }

}