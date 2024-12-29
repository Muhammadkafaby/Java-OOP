package Console;

public interface RekomendasiServis {
    void rekomendasiServis(Kendaraan kendaraan, int kilometerSekarang);

    void saveToDatabase();

    static RiwayatServis getFromDatabase(int id) {
        return null;
    }
}