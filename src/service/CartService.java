package service;

import java.util.Stack;
import model.ItemKeranjang;
import model.Pelanggan;
import java.time.LocalDateTime;
import java.util.ArrayList;
import model.Transaksi;

public class CartService {
    // Menyimpan seluruh item yang dipilih pelanggan.
    // Menggunakan Stack agar item terakhir yang ditambahkan akan menjadi item pertama yang dihapus.
    private Stack<ItemKeranjang> keranjang = new Stack<>();

    // Menambahkan satu item ke dalam keranjang.
    // Item akan diletakkan di bagian paling atas Stack sehingga dapat diambil kembali menggunakan fitur Undo.
    public void tambahItem(ItemKeranjang item) {
        keranjang.push(item);
    }

    // Menghapus item terakhir yang dimasukkan ke keranjang.
    // Digunakan untuk fitur Undo apabila pelanggan membatalkan item terakhir yang dipilih.
    // Jika keranjang masih kosong, method berhenti.
    public void undoItem() {
        if (keranjang.isEmpty()) {
            System.out.println("Keranjang masih kososng.");
            return;
        }
        keranjang.pop();
        System.out.println("Item berhasil dihapus dari keranjang.");
    }

    // Menghitung subtotal seluruh item yang ada di dalam keranjang.
    // Setiap harga item dikaliakn jumlah pembelian, kemudian dijumlahkan untuk mendapatkan subtotal.
    public double hitungSubtotal() {
        double subtotal = 0;
        for (ItemKeranjang item : keranjang) {
            subtotal += item.getHargaFinal() * item.getJumlah();
        }
        return subtotal;
    }

    // Menghitung total pembayaran.
    public double hitungTotal() {
        return hitungSubtotal();
    }
    
    // Mengembalikan seluruh isi keranjang.
    // Digunakan oleh Main atau Class lain jika ingin menampilkan daftar item yanng dipilih pelanggan.
    public Stack<ItemKeranjang> getKeranjang() {
        return keranjang;
    }

    // Melakukan proses checkout.
    // Seluruh stokk produk akan dikurangi secara permanen, kemudian dibuat objek Transaksi dan disimpan ke TransactionService sebagai riwayat transaksi.
    // Setelah checkout selesai, keranjang dikosongkan agar siap digunakan oleh pelanggan berikutnya.
    public void checkout(Pelanggan pelanggan, ProductService productservice, TransactionService transactionservice) {
        if (keranjang.isEmpty()) {
            System.out.println("Keranjang masih kosong.");
            return;
        }

        // Mengurangi stok setiap produk yang dibeli.
        for (ItemKeranjang item : keranjang) {
            productservice.kurangiStok(item.getProduk().getId(), item.getJumlah());
        }

        // Mengubah isi Stack menjadi ArrayList karena class Transaksi menggunakan ArrayList.
        ArrayList<ItemKeranjang> daftarItem = new ArrayList<>(keranjang);

        // Membuat ID transaksi.
        String idTransaksi = "TRX" + (transactionService.getJumlahTransaksi() + 1);

        // Membuat objek Transaksi baru.
        Transaksi transaksi = new Transaksi(idTransaksi, pelanggan.getNomorAntrean, daftarItem, (int) hitungTotal(),LocalDateTime.now());

        // Menyimpan transaksi ke riwayat.
        transactionService.tambahTransaksi(transaksi);

        System.out.println();
        System.out.println("===============================");
        System.out.println("        KAFE SEDUH KALA        ");
        System.out.println("===============================");
        System.out.println("ID Transaksi: " + idTransaksi);
        System.out.println("Nomor Antrean: " + pelanggan.getNomorAntrean());
        System.out.println("Pelanggan: " + pelanggan.getNama());
        System.out.println("-------------------------------");

        for (ItemKeranjang item : keranjang) {
            System.out.println(item.getProduk().getNama() + " x" + item.getJumlah() + " = Rp " + (item.getHargaFinal() * item.getJumlah()));
        }

        System.out.println("-------------------------------");
        System.out.println("TOTAL: Rp " + (int) hitungTotal());
        System.out.println("================================");
        System.out.println("Terima kasih telah berbelanja.");

        // Keranjang dikosongkan karena transaksi sudah selesai.
        kosongkanKeranjang();
    }

    // Menghapus seluruh isi keranjang.
    // Dipanggil setelah checkout berhasil agar pelanggan berikutnya memulai keranjang yang kosong.
    public void kosongkanKeranjang() {
        keranjang.clear();
    }
}