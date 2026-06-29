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

        // Memastikan file CSV diakhiri newline sebelum ditambahkan baris baru.
    // Soalnya FileWriter mode append cuma nempelin teks baru tepat di posisi byte terakhir file,
    // jadi kalau baris terakhir di file belum diakhiri newline (misal habis dibuka/disave editor tertentu),
    // baris transaksi baru bisa nempel jadi satu baris sama baris sebelumnya.
    private void pastikanDiakhiriNewline(String path) {

        try {

            java.io.File file = new java.io.File(path);

            if (!file.exists() || file.length() == 0) {
                return;
            }

            // Baca 1 byte terakhir aja buat dicek, gak perlu baca seluruh file.
            try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(file, "rw")) {

                long posisiTerakhir = raf.length() - 1;
                raf.seek(posisiTerakhir);
                int byteTerakhir = raf.read();

                if (byteTerakhir != '\n') {
                    raf.seek(raf.length());
                    raf.write(System.lineSeparator().getBytes());
                }

            }

        } catch (IOException e) {
            // Kalau gagal cek, biarin saja, paling jelek cuma baris nempel lagi.
        }

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
    // Urutan kolom disesuaikan dengan header file: ID Transaksi,Tanggal,Nomor Antrean,Total
    public void simpanTransaksi(Transaksi transaksi) {

        try {

            pastikanDiakhiriNewline("src/data/transaksi.csv");

            BufferedWriter writer = new BufferedWriter(
                    new FileWriter("src/data/transaksi.csv", true));

            writer.write(
                    transaksi.getIdTransaksi() + "," +
                    transaksi.getWaktuTransaksi() + "," +
                    transaksi.getNomorAntrean() + "," +
                    transaksi.getTotalHarga());

            writer.newLine();

            writer.close();

        } catch (IOException e) {

            System.out.println("Gagal menyimpan transaksi.");

        }

    }

    // Menyimpan detail transaksi ke detail_transaksi.csv.
    // Kompleksitas: O(n), n = jumlah item dalam transaksi.
    // Urutan kolom disesuaikan dengan header file: ID Transaksi,ID Produk,Nama Produk,Jumlah,Harga
    public void simpanDetailTransaksi(Transaksi transaksi) {

        try {

            pastikanDiakhiriNewline("src/data/transaksi.csv");

            BufferedWriter writer = new BufferedWriter(
                    new FileWriter("src/data/detail_transaksi.csv", true));

            for (ItemKeranjang item : transaksi.getDaftarItem()) {

                writer.write(
                        transaksi.getIdTransaksi() + "," +
                        item.getProduk().getId() + "," +
                        item.getProduk().getNama() + "," +
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