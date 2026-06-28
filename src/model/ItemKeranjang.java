package model;
import java.util.Map;
public class ItemKeranjang {
    private Produk produk;
    private int jumlah;
    private Map<String, String> kustomisasi;
    private double hargaFinal;
    public ItemKeranjang(Produk produk, int jumlah, Map<String, String> kustomisasi, double hargaFinal) {
        this.produk = produk;
        this.jumlah = jumlah;
        this.kustomisasi = kustomisasi;
        this.hargaFinal = hargaFinal;
    }
    public Produk getProduk() {
        return produk;
    }
    public int getJumlah() {
        return jumlah;
    }
    public Map<String, String> getKustomisasi() {
        return kustomisasi;
    }
    public double getHargaFinal() {
        return hargaFinal;
    }
} 