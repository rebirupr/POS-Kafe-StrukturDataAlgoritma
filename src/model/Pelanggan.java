package model;

public class Pelanggan { 
    // Variabel untuk menyimpan nomor antrean pelanggan.
    private String nomoAntrean;
    // Variabel untuk menyimpan nama pelanggan.
    private String nama;

    public Pelanggan(String nomorAntrean, String nama) {
        this.nomorAntrean = nomorAntrean;
        this.nama = nama;
    }

    public String getNomorAntrean() {
        return nomorAntrean;
    }

    public String getNama() {
        return nama;
    }
    
}