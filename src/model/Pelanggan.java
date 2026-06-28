package model;

// Menyimpan data pelanggan yang sedang berada di dalam antrean.
public class Pelanggan { 
    // Variabel untuk menyimpan nomor antrean pelanggan.
    private String nomoAntrean;
    // Variabel untuk menyimpan nama pelanggan.
    private String nama;

    // Constructor untuk membuat objek Pelanggan baru.
    // Nomor antrean dan nama langsung disimpan ke dalam atribut object.
    public Pelanggan(String nomorAntrean, String nama) {
        this.nomorAntrean = nomorAntrean;
        this.nama = nama;
    }

    // Mengembalikan nomor antrean pelanggan.
    // Dipanggil saat ingin mengetahui pelanggan mana yang sedang atau akan dilayani.
    public String getNomorAntrean() {
        return nomorAntrean;
    }

    // Mengembalikan nama pelanggan.
    // Digunakan saat mencetak struk atau menampilkan informasi pelanggan.
    public String getNama() {
        return nama;
    }
    
}