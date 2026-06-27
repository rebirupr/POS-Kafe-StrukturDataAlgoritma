package model;
// Menyimpan data 1 produk kafe (id, nama, harga dasar sebelum kustomisasi, stok)
// Digunakan ProductService buat diisi ke HashMap katalog
public class Produk {

    private String id;
    private String nama;
    // pake int bukan double agar tidak ada masalah pembulatan desimal pas dijumlahkan
    private int hargaDasar; 
    private int stok;

    // constructor hanya menyerahkan validasi stok ke setStok(), agar aturan stok gaboleh negatif
    // hanya ada di satu tempat saja tidak boleh diduplikat di constructor
    public Produk(String id, String nama, int hargaDasar, int stok) {
        this.id = id;
        this.nama = nama;

        if (hargaDasar < 0) {
            this.hargaDasar = 0;
        } else {
            this.hargaDasar = hargaDasar;
        }

        setStok(stok);
    }

    // getter 
    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public int getHargaDasar() {
        return hargaDasar;
    }

    public int getStok() {
        return stok;
    }

    // validasi agar stok tidak bisa menjadi negatif dipanggil dari banyak modul yaitu, checkout, undo, restock
    public void setStok(int stok) {
        if (stok >= 0) {
            this.stok = stok;
        }
    }

    // untuk debug waktu testing sendiri atau jika digunakan untuk cetak struk
    @Override
    public String toString() {
        return nama + " (Rp" + hargaDasar + ") - Stok: " + stok;
    }

}