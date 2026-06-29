package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import model.Produk;

// HashMap katalog, harga kustomisasi, dan validasi stok
public class ProductService {

    private HashMap<String, Produk> katalog = new HashMap<>();
    private HashMap<String, Integer> hargaKustomisasi = new HashMap<>();

    /* baca data produk dari data atau produk.csv, dipanggil manual dari Main.java saat program dimulai,
    menggunakan clear() terlebih dahulu agar hasilnya konsisten meskipun method ini dipanggil lebih dari satu kali,
    format csv yaitu id,nama,hargaDasar,stok, baris pertama header jadi dilewatin */ 
    public void loadProduk() {
        katalog.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/data/produk.csv"))) {
            // lewati baris header
            reader.readLine(); 
            String baris;
            while ((baris = reader.readLine()) != null) {
                String[] data = baris.split(",");
                String id = data[0];
                String nama = data[1];
                int harga = Integer.parseInt(data[2]);
                int stok = Integer.parseInt(data[3]);
                katalog.put(id, new Produk(id, nama, harga, stok));
            }
        } catch (IOException | NumberFormatException e) {
            // IOException kalau filenya tidak ditemukan, NumberFormatException kalau isi csvnya rusak
            System.out.println("Gagal membaca data produk: " + e.getMessage());
        }
    }

    /*  harga tambahan tiap opsi kustomisasi yang ada, yang gratis tetep dimasukin nilainya 0, biar konsisten kalo dicari menggunakan getHargaKustomisasi()
     opsi ukuran cuma Regular dan Large, sesuai kesepakatan denggan Anggota kelompok */
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

    /*  Kompleksitasnya O(1) karena lookup langsung menggunakan key di HashMa jadi tidak perlu looping
    ini yang gunakan jika  sudah mengetahui ID produknya secara pasti, misal saat scan barcode*/
    public Produk cariProdukById(String id) {
        return katalog.get(id);
    }

    /*  kompleksitasnya O(n) karena harus looping semua produk untk dicocokkan dengan keyword,
    berbeda dengan cariProdukById yang kompleksitasnya O(1), karena  HashMap hanya cepat jika mencari menggunakan keynya langsung */
    public List<Produk> cariProdukByNama(String keyword) {
        List<Produk> hasil = new ArrayList<>();
        if (keyword == null) {
            // buat berjaga-jaga agar tidak NullPointerException jika ada yang memanggil dengan null
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

    //  Kompleksitasnya O(1) karena manggil cariProdukById yang sudah berkompleksitas O(1), kemudian memebandingkan angka, jumlah negatif/nol ditolak
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

    // dipanggil saat undo atau void untul mengembalikkan stok yang tadi sudah dikurangi
    public void tambahStok(String id, int jumlah) {
        if (jumlah <= 0) {
            return;
        }
        Produk p = cariProdukById(id);
        if (p != null) {
            p.setStok(p.getStok() + jumlah);
        }
    }

    // ambil harga tambahan dari 1 opsi kustomisasi, misal large berarti tambah 3000 kalau opsinya gak ketemu di HashMap maka dianggap  gratis (0) agar tidak null pointer
    public int getHargaKustomisasi(String opsi) {
        Integer harga = hargaKustomisasi.get(opsi);
        return harga != null ? harga : 0;
    }

    // untuk menampilin di menu lihat semua produk
    public Collection<Produk> getSemuaProduk() {
        return katalog.values();
    }

}