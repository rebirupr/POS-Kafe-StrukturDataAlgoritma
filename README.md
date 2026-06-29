# Seduh Kala — POS System
## Deskripsi Proyek
Seduh Kala adalah aplikasi kasir sederhana (Point of Sale/POS) berbasis **Command Line Interface (CLI)** yang dibuat untuk mensimulasikan proses transaksi di sebuah kafe. Program ini dapat mengelola antrean pelanggan, menampilkan katalog produk, mencatat pesanan beserta kustomisasinya, mengatur stok produk, mencetak struk, serta menyimpan riwayat transaksi untuk laporan penjualan.

Program dikembangkan menggunakan **Java** dengan memanfaatkan library bawaan seperti `java.util`, `java.io`, dan `java.time` tanpa menggunakan library tambahan.


---

## Anggota Kelompok

| Nama | NIM | Fokus Modul |
|---|---|---|
| Jovienda Nadia Judial Kharisa | L0125018 | Katalog Produk dan Kustomisasi |
| Rona Humaira | L0125030 | Antrean dan Transaksi Kasir |
| Ready Biru Prawira | L0125090 | Laporan dan Integrasi |

---

## Fitur Utama
- Mengelola antrean pelanggan menggunakan konsep **FIFO (Queue)**.
- Menampilkan dan mencari produk berdasarkan ID maupun nama.
- Mendukung kustomisasi pesanan seperti ukuran, tingkat gula, dan suhu minuman.
- Menambahkan produk ke keranjang serta membatalkan item terakhir menggunakan fitur **Undo (Stack)**.
- Menghitung total pembayaran secara otomatis dan mencetak struk transaksi.
- Menyimpan data transaksi ke dalam file CSV.
- Menampilkan laporan penjualan, total pendapatan, dan produk terlaris.

---

## Struktur Data yang Digunakan & Alasan Pemilihannya

| Struktur Data | Digunakan di | Fungsi | Alasan Pemilihan |
|---|---|---|---|
| **HashMap** | `ProductService` | Menyimpan katalog produk (key: ID produk) dan harga kustomisasi (key: nama opsi) | Pencarian produk berdasarkan ID menjadi **O(1)**, jauh lebih cepat dibanding `ArrayList` yang butuh **O(n)** untuk operasi serupa |
| **Queue** (`LinkedList`) | `QueueService` | Menyimpan antrean pelanggan | Sifat antrean kasir adalah **FIFO** (First In First Out) — pelanggan yang datang lebih dulu harus dilayani lebih dulu, sesuai prinsip kerja Queue |
| **Stack** | `CartService` | Menyimpan item dalam keranjang belanja | Fitur **Undo/Void** membutuhkan sifat **LIFO** (Last In First Out) — item yang paling baru dimasukkan adalah item yang dibatalkan lebih dulu |
| **ArrayList** | `TransactionService` | Menyimpan riwayat seluruh transaksi yang sudah selesai | Riwayat transaksi perlu diakses sesuai urutan kronologis dan diakses secara acak (by index), yang lebih sesuai dengan `ArrayList` dibanding `Queue`/`Stack` |
| **HashMap** (agregasi) | `ReportService` | Menghitung frekuensi penjualan tiap produk untuk menentukan produk terlaris | Memanfaatkan key-value pairing untuk menghitung jumlah kemunculan (counting) tiap nama produk dari seluruh riwayat transaksi |

---

## Struktur Folder

```
POS-Kafe-StrukturDataAlgoritma/
├── README.md
├── docs/
│   └── LembarPenilaianAnggota.md
└── src/
    ├── Main.java                 
    ├── data/
    │   ├── produk.csv             
    │   ├── transaksi.csv          
    │   └── detail_transaksi.csv  
    ├── model/
    │   ├── Produk.java           
    │   ├── Pelanggan.java         
    │   ├── ItemKeranjang.java    
    │   └── Transaksi.java       
    ├── service/
    │   ├── ProductService.java    
    │   ├── QueueService.java      
    │   ├── CartService.java     
    │   ├── TransactionService.java
    │   ├── ReportService.java    
    │   ├── ReceiptPrinter.java
    │   └── FileService.java       
    └── util/
        └── IdGenerator.java      
```

---

## Cara Menjalankan Program

### Prasyarat
- JDK 11 atau versi yang lebih baru telah terpasang

### Langkah Kompilasi & Eksekusi

1. Clone atau download repository ini, lalu masuk ke folder project:
   ```bash
   cd POS-Kafe-StrukturDataAlgoritma
   ```

2. Compile seluruh source code dari dalam folder `src`:
   ```bash
   cd src
   javac Main.java model/*.java service/*.java util/*.java
   ```

3. Jalankan program (tetap dari dalam folder `src`, karena path file CSV bersifat relatif terhadap lokasi ini):
   ```bash
   java Main
   ```

4. Ikuti menu yang tampil di terminal. Menu utama mencakup: tambah pelanggan ke antrean, layani pelanggan (mulai transaksi), lihat produk, cari produk, lihat riwayat transaksi, dan lihat laporan penjualan.

> **Penting:** program harus dijalankan dari dalam folder `src/`, bukan dari root folder project. Path file CSV (`src/data/produk.csv`, dst) ditulis relatif terhadap lokasi `Main.java` saat dieksekusi.

---

## Alur Penggunaan Singkat

1. Tambahkan pelanggan ke dalam antrean.
2. Layani pelanggan yang berada di antrean paling depan.
3. Pilih produk dan tentukan kustomisasi sesuai keinginan pelanggan.
4. Jika terjadi kesalahan, gunakan fitur **Undo** untuk membatalkan item terakhir.
5. Lakukan **Checkout** untuk menyelesaikan transaksi dan mencetak struk.
6. Data transaksi akan tersimpan ke file CSV dan dapat dilihat kembali pada menu laporan.


---

## Catatan Teknis & Keterbatasan yang Diketahui

- Stok produk akan berkurang ketika produk dimasukkan ke keranjang. Jika transaksi dibatalkan atau dilakukan Undo, stok akan dikembalikan secara otomatis.
- Nomor antrean dan ID transaksi akan dimulai kembali dari awal setiap kali program dijalankan ulang.
- Seluruh data transaksi tetap tersimpan pada file CSV sehingga dapat digunakan kembali untuk membuat laporan penjualan.

---

## 📖 Mata Kuliah

**Praktikum Struktur Data dan Algoritma**  
Program Studi Informatika  
Universitas Sebelas Maret
