package service;

import java.util.LinkedList;
import java.util.Queue;
import model.Pelanggan;

// Mengelola antrean pelanggan menggunakan struktur data Queue agar pelangggan yang datang lebih dulu akan dilayani lebih dulu.
public class QueueService {

    // Menyimmpan seluruh pelanggan yang sedang mengantre.
    private Queue<Pelanggan> antrean = new LinkedList<>();

    // Digunakan untuk membuat nomor antrean secara otomatis.
    // Nilai awal dimuali dari 1sehingga pelanggan pertama mendapat A001.
    private int nomorBerikutnya = 1;

    // Menambahkan pelanggan baru ke dalam antrean.
    // Nomor antrean dibuat secara otomatis agar tidak ada nomor yang sama, kemudian pelaggan dimasukkan ke bagian paling belakang Queue.
    public void tambahAntrean(String nama) {
        String nomorAntrean = String.format("A%03d", nomorBerikutnya);
        nomorBerikutnya++;
        Pelanggan pelanggan = new Pelanggan(nomorAntrean, nama);
        antrean.offer(pelanggan);
    }

    // Memngambil pelanggan yang berada di urutan paling depan antrean.
    // Setelah pelanggan diambil, otomatis pelanggan tersebut keluar dari antrean sehingga pelanggan berikutnya menjadi paling depan.
    public Pelanggan layaniPelanggan() {
        // Menggunakan poll() agar mengembalikan null jika antrean kosong.
        return antrean.poll();
    }

    // Mengembalikan seluruh isi antrean.
    // Digunakan jika ingin menampilkan daftar pelanggan tanpa mengahapus data yang ada di dalam Queue.
    public Queue<Pelanggan> lihatAntrean() {
        return antrean;
    }

    // Mengecek apakah antrean kosong atau tidak.
    // Biasanya dipanggil sebelum melayani pelanggan agar tidak mengambil data dari Queue yang kososng.
    public boolean isKosong() {
        return antrean.isEmpty();
    }
}