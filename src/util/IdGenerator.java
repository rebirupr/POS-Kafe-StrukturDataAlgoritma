package util;

// Class untuk membuat ID transaksi dan nomor antrean secara otomatis.
public class IdGenerator {

    // Counter untuk ID transaksi.
    private static int transaksiCounter = 1;

    // Counter untuk nomor antrean.
    private static int antreanCounter = 1;

    // Membuat ID transaksi, contoh: TRX001
    public static String generateIdTransaksi() {
        return String.format("TRX%03d", transaksiCounter++);
    }

    // Membuat nomor antrean, contoh: A001
    public static String generateNomorAntrean() {
        return String.format("A%03d", antreanCounter++);
    }

}