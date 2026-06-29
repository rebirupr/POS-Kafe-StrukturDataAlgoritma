package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Stack;
import model.ItemKeranjang;
import model.Pelanggan;
import model.Transaksi;
import util.IdGenerator;

public class CartService {
    // Menyimpan seluruh item yang dipilih pelanggan.
    // Menggunakan Stack agar item terakhir yang ditambahkan akan menjadi item pertama yang dihapus.
    private Stack<ItemKeranjang> keranjang = new Stack<>();

    // Menambahkan satu item ke dalam keranjang.
    // Item akan diletakkan di bagian paling atas Stack sehingga dapat diambil kembali menggunakan fitur Undo.
    // Stok langsung dikurangi di sini (bukan nunggu checkout), supaya barang yang udah masuk keranjang
    // gak bisa "dijual dua kali" ke pelanggan lain selama transaksi ini masih berjalan.
    // Kompleksitas O(1) karena push() hanya menambahkan satu elemen ke bagian atas Stack.
    public void tambahItem(ItemKeranjang item, ProductService productService) {
        keranjang.push(item);
        productService.kurangiStok(item.getProduk().getId(), item.getJumlah());
    }

    // Menghapus item terakhir yang dimasukkan ke keranjang.
    // Digunakan untuk fitur Undo apabila pelanggan membatalkan item terakhir yang dipilih.
    // Karena stoknya udah dikurangi pas item ditambahkan, di sini stok itu dikembalikan lagi (restore).
    // Jika keranjang masih kosong, method berhenti.
    // Kompleksitas O(1) karena pop() hanya mengambil elemen paling atas Stack.
    public void undoItem(ProductService productService) {
        if (keranjang.isEmpty()) {
            System.out.println("Keranjang masih kososng.");
            return;
        }
        ItemKeranjang dihapus = keranjang.pop();
        productService.tambahStok(dihapus.getProduk().getId(), dihapus.getJumlah());
        System.out.println("Item \"" + dihapus.getProduk().getNama() + "\" berhasil dihapus dari keranjang, stok dikembalikan.");
    }

    // Menghitung subtotal seluruh item yang ada di dalam keranjang.
    // Setiap harga item dikaliakn jumlah pembelian, kemudian dijumlahkan untuk mendapatkan subtotal.
    // Kompleksitas O(n) karena harus mengunjungi seluruh item yang ada di dalam Stack.
    public int hitungSubtotal() {
        int subtotal = 0;
        for (ItemKeranjang item : keranjang) {
            subtotal += item.getHargaFinal() * item.getJumlah();
        }
        return subtotal;
    }

    // Menghitung total pembayaran.
    // Kompleksitas O(n) karena memanggil hitungSubtotal().
    public double hitungTotal() {
        return hitungSubtotal();
    }
    
    // Mengembalikan seluruh isi keranjang.
    // Digunakan oleh Main atau Class lain jika ingin menampilkan daftar item yanng dipilih pelanggan.
    // Kompleksitas O(1).
    public Stack<ItemKeranjang> getKeranjang() {
        return keranjang;
    }

    // Melakukan proses checkout.
    // Seluruh stokk produk akan dikurangi secara permanen, kemudian dibuat objek Transaksi dan disimpan ke TransactionService sebagai riwayat transaksi.
    // Setelah checkout selesai, keranjang dikosongkan agar siap digunakan oleh pelanggan berikutnya.
    // Kompleksitas O(n) karena harus memproses seluruh item yang ada di dalam keranjang.
    public void checkout(Pelanggan pelanggan, ProductService productservice, TransactionService transactionservice) {
        if (keranjang.isEmpty()) {
            System.out.println("Keranjang masih kosong.");
            return;
        }

        // Catatan: stok TIDAK dikurangi lagi di sini karena sudah dikurangi
        // sejak item dimasukkan ke keranjang lewat tambahItem(). Kalau dikurangi lagi di sini,
        // stoknya bakal kepotong dua kali untuk transaksi yang sama.

        // Mengubah isi Stack menjadi ArrayList karena class Transaksi menggunakan ArrayList.
        ArrayList<ItemKeranjang> daftarItem = new ArrayList<>(keranjang);

        // Membuat ID transaksi.
        String idTransaksi = IdGenerator.generateIdTransaksi();

        // Membuat objek Transaksi baru.
        Transaksi transaksi = new Transaksi(idTransaksi, pelanggan.getNomorAntrean(), pelanggan.getNama(), daftarItem, (int) hitungTotal(),LocalDateTime.now());

        // Menyimpan transaksi ke riwayat.
        transactionservice.tambahTransaksi(transaksi);

        // Cetak Struk
        ReceiptPrinter.cetakStruk(transaksi);

        // Keranjang dikosongkan karena transaksi sudah selesai.
        kosongkanKeranjang();
    }

    // Menghapus seluruh isi keranjang.
    // Dipanggil setelah checkout berhasil agar pelanggan berikutnya memulai keranjang yang kosong.
    // Kompleksitas O(1) karena clear() mengososngkan Stack.
    public void kosongkanKeranjang() {
        keranjang.clear();
    }
}