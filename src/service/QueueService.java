public class QueueService {
    private Queue<Pelanggan> antrean = new LinkedList<>();
    private int nomorBerikutnya = 1;
    public void tambahAntrean(String nama) {
        String nomorAntrean = String.format("A%03d", nomorBerikutnya);
        nomorBerikutnya++;
        Pelanggan pelanggan = new Pelanggan(nomorAntrean, nama);
        antrean.offer(pelanggan);
    }
    public Pelanggan layaniPelanggan() {
        return antrean.poll();
    }
    public Queue<Pelanggan> lihatAntrean() {
        return antrean;
    }
    public boolean isKosong() {
        return antrean.isEmpty();
    }
}