package model;
import java.util.Map;

// Menyimpan satu item yang ada di dalam keranjang belanja.
// Setiap object berisi produk yang dipilih pelanggan, jumlah pembelian, pilihan kustomisasi, dan harga akhirnya.
public class ItemKeranjang {

    // Menyimpan data produk yang dipilih pelanggan.
    private Produk produk;

    // Menyimpan jumlah produk yang dibeli.
    private int jumlah;

    // Menyimpan seluruh pilihan kustomisasi produk.
    // Menggunakan Map agar setiap kategori (ukuran, gula, suhu) daapt dipasangkan dengan pilihan yang dipilih pelanggan.
    private Map<String, String> kustomisasi;

    // Menyimpan harga akhir satu produk setelah ditambah biaya dari seluruh pilihan kustomisasi.
    private double hargaFinal;

    // Constructor digunakan saat pelanggan menambahkan produk ke dalam keranjang.
    // Seluruh dara yang diterima langsung disimpan ke atribut object agar dapat digunakan saat menghitung total maupun mencetak struk.
    public ItemKeranjang(Produk produk, int jumlah, Map<String, String> kustomisasi, double hargaFinal) {
        this.produk = produk;
        this.jumlah = jumlah;
        this.kustomisasi = kustomisasi;
        this.hargaFinal = hargaFinal;
    }

    // Mengembalikan data produk yang tersimpan pada item keranjnag.
    // Digunakan saat menghitung total, mengurangi stok, maupun mencetak nama produk pada struk.
    public Produk getProduk() {
        return produk;
    }

    // Mengembalikan jumlah produk yang dibeli pelanggan.
    public int getJumlah() {
        return jumlah;
    }

    // Mengembalikan seluruh pilihan kustomisasi produk.
    // Data ini digunakan saat mencetak struk agar pelanggan mengetahui pilihan yang dipilih.
    public Map<String, String> getKustomisasi() {
        return kustomisasi;
    }

    // Mengembalikan harga akhir produk yang sudah memperhitungkan seluruh biaya kustomisasi.
    public double getHargaFinal() {
        return hargaFinal;
    }
} 