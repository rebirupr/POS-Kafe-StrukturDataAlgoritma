package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import model.Produk;

public class ProductService {
    // Menyimpan seluruh produk dengan key yang berupa ID
    private HashMap<String, Produk> katalog = new HashMap<>();
    //Menyimpan biaya tambahan untuk setiap opsi kustomisasi
    private HashMap<String, Integer> hargaKustomisasi = new HashMap<>();

    // isi data produk awal, dipanggil manual dari Main.java pas program mulai
    // pake clear() dulu agar hasilnya konsisten meskipun method ini dipanggil lebih dari satu kali
    public void loadProduk() {
        katalog.clear();
        katalog.put("P001", new Produk("P001", "Americano", 20000, 20));
        katalog.put("P002", new Produk("P002", "Caramel Latte", 28000, 15));
        katalog.put("P003", new Produk("P003", "Cappuccino", 28000, 13));
        katalog.put("P004", new Produk("P004", "Matcha Latte", 32000, 20));
        katalog.put("P005", new Produk("P005", "Espresso", 18000, 10));
        katalog.put("P006", new Produk("P006", "Hazelnut Latte", 30000, 15));
        katalog.put("P007", new Produk("P007", "Caramel Macchiato", 30000, 18));
        katalog.put("P008", new Produk("P008", "Butterscotch Sea Salt", 28000, 15));
    }
    
    /* harga tambahan tiap opsi kustomisasi (ukuran, gula, suhu) yang gratis tetep dimasukin nilainya 0, 
    agar konsisten jika dicari menggunakan getHargaKustomisasi()*/
    public void loadHargaKustomisasi() {
        hargaKustomisasi.clear();
        hargaKustomisasi.put("Regular", 0);
        hargaKustomisasi.put("Large", 3000);
        hargaKustomisasi.put("Normal", 0);
        hargaKustomisasi.put("Less Sugar", 0);
        hargaKustomisasi.put("No Sugar", 0);
        hargaKustomisasi.put("Hot", 0);
        hargaKustomisasi.put("Ice", 0);
    }

    // Kompleksitasnya O(1) karena lookup langsung menggunakan key di HashMap tidak perlu looping
    // digunakan jika sudah tau ID produknya pasti misalkan saat scan barcode
    public Produk cariProdukById(String id) {
        return katalog.get(id);
    }

    /*  Kompleksitasnya O(n) karena harus looping semua produk untuk mencocokkan dengan keyword berbeda dengan
     cariProdukById yang kompleksitasnya O(1), karena HashMap cepat jika mencari pakai keynya langsung */
    public List<Produk> cariProdukByNama(String keyword) {
        List<Produk> hasil = new ArrayList<>();
        if (keyword == null) {
            // untuk berjaga-jaga agar tidak  NullPointerException jika ada yang memanggil dengan null
            return hasil; 
        }
        // di luar loop agar tidak dipanggil berulang setiap iterasi
        String keywordLower = keyword.toLowerCase(); 
        for (Produk p : katalog.values()) {
            if (p.getNama().toLowerCase().contains(keywordLower)) {
                hasil.add(p);
            }
        }
        return hasil;
    }

    // Kompleksitasnya O(1) karena  memanggil cariProdukById yang sudah O(1), kemudian  tinggal membandingkan angka, dan jumlah negatif ataupun nol ditolak
    public boolean stokCukup(String id, int jumlah) {
        if (jumlah <= 0) {
            return false;
        }
        Produk p = cariProdukById(id);
        if (p == null) {
            return false;
        }
        return p.getStok() >= jumlah;
    }

    // dipanggil saat checkout untuk mengurangi stok permanen, jumlah negatif atau nol ditolak 
    public void kurangiStok(String id, int jumlah) {
        if (jumlah <= 0) {
            return;
        }
        Produk p = cariProdukById(id);
        if (p != null) {
            p.setStok(p.getStok() - jumlah);
        }
    }

    // Dipanggil saat undo atau void, untuk memngembalikkan  stok yang sempat dikurangkan
    public void tambahStok(String id, int jumlah) {
        if (jumlah <= 0) {
            return;
        }
        Produk p = cariProdukById(id);
        if (p != null) {
            p.setStok(p.getStok() + jumlah);
        }
    }

    // ambil harga tambahan dari 1 opsi kustomisasi, misal Large = 3000, jika opsinya tidak ditemukan di HashMap maka dianggap   gratis (0), agar gak null pointer
    public int getHargaKustomisasi(String opsi) {
        Integer harga = hargaKustomisasi.get(opsi);
        return harga != null ? harga : 0;
    }

    // Untuk menampilkan di menu lihat semua produk
    public Collection<Produk> getSemuaProduk() {
        return katalog.values();
    }

}