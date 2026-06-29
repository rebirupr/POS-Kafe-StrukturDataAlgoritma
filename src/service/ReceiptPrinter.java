package service;

import java.time.format.DateTimeFormatter;
import model.ItemKeranjang;
import model.Transaksi;

public class ReceiptPrinter {

    // Mencetak struk transaksi.
    // Kompleksitas: O(n), n = jumlah item dalam transaksi.
    public static void cetakStruk(Transaksi transaksi) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");

        // Lebar kolom "Nama Produk" dibuat dinamis, ikut nama produk paling panjang
        // di struk ini, supaya nama yang panjang (misal "Butterscotch Sea Salt")
        // gak nempel sama kolom "Harga Item" di sebelahnya.
        int lebarNama = "Nama Produk".length();
        for (ItemKeranjang item : transaksi.getDaftarItem()) {
            int panjangNama = item.getProduk().getNama().length();
            if (panjangNama > lebarNama) {
                lebarNama = panjangNama;
            }
        }
        lebarNama += 4; // kasih jarak lebih lega sebelum kolom berikutnya

        // Lebar garis pemisah tabel item ikut menyesuaikan lebarNama juga,
        // supaya garisnya tetap pas menutupi seluruh tabel.
        int lebarGarisTabel = lebarNama + 1 + 12 + 1 + 5 + 1 + 12;
        String garisTabel = "-".repeat(lebarGarisTabel);

        System.out.println();
        System.out.println("==============================================================");
        System.out.println("                        SEDUH KALA");
        System.out.println("             Jl. Slamet Riyadi No. 88, Surakarta");
        System.out.println("                  Telp. (0271) 765432");
        System.out.println("==============================================================");

        System.out.println("ID Transaksi : " + transaksi.getIdTransaksi());
        System.out.println("No. Antrean  : " + transaksi.getNomorAntrean());
        System.out.println("Atas Nama    : " + transaksi.getNamaPelanggan());
        System.out.println("Tanggal      : " + transaksi.getWaktuTransaksi().format(formatter));

        System.out.println(garisTabel);
        System.out.printf("%-" + lebarNama + "s %-12s %-5s %-12s%n",
                "Nama Produk",
                "Harga Item",
                "Qty",
                "Harga Total");
        System.out.println(garisTabel);

        int jumlahItem = 0;

        for (ItemKeranjang item : transaksi.getDaftarItem()) {

            int subtotal = item.getHargaFinal() * item.getJumlah();

            System.out.printf("%-18s Rp%-10d %-5d Rp%-10d%n",
                    item.getProduk().getNama(),
                    item.getHargaFinal(),
                    item.getJumlah(),
                    subtotal);

            System.out.println("("
                    + item.getKustomisasi().get("Suhu")
                    + " - "
                    + item.getKustomisasi().get("Gula")
                    + ")");

            System.out.println();

            jumlahItem += item.getJumlah();

        }

        System.out.println("--------------------------------------------------------------");
        System.out.println("Jumlah Item : " + jumlahItem);
        System.out.printf("%-40s Rp%d%n", "TOTAL BAYAR :", transaksi.getTotalHarga());

        System.out.println("==============================================================");
        System.out.println("              Terima kasih telah berkunjung.");
        System.out.println();
        System.out.println("         Seduh perlahan, nikmati setiap kala.");
        System.out.println();
        System.out.println("            Password WiFi : seduhkaladihati");
        System.out.println("==============================================================");
        System.out.println();

    }

}