package Console;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class RiwayatServis {
    private int id;
    private String jenisServis;
    private String keteranganServis;
    private Date tanggalMulai;
    private Date tanggalSelesai;
    private int kilometerAkhir;
    private float biaya;
    private int kendaraanId;

    public RiwayatServis(int id, String jenisServis, String keteranganServis, Date tanggalMulai, Date tanggalSelesai, int kilometerAkhir, float biaya, int kendaraanId) {
        this.id = id;
        this.jenisServis = jenisServis;
        this.keteranganServis = keteranganServis;
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
        this.kilometerAkhir = kilometerAkhir;
        this.biaya = biaya;
        this.kendaraanId = kendaraanId;
    }

    public int getId() {
        return id;
    }

    public void saveToDatabase() {
        String query = "INSERT INTO riwayat_servis (id, jenis_servis, keterangan_servis, tanggal_mulai, tanggal_selesai, kilometer_akhir, biaya, kendaraan_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setString(2, jenisServis);
            statement.setString(3, keteranganServis);
            statement.setDate(4, new java.sql.Date(tanggalMulai.getTime()));
            statement.setDate(5, new java.sql.Date(tanggalSelesai.getTime()));
            statement.setInt(6, kilometerAkhir);
            statement.setFloat(7, biaya);
            statement.setInt(8, kendaraanId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<RiwayatServis> getByKendaraanId(int kendaraanId) {
        ArrayList<RiwayatServis> riwayatServisList = new ArrayList<>();
        String query = "SELECT * FROM riwayat_servis WHERE kendaraan_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, kendaraanId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                RiwayatServis riwayatServis = new RiwayatServis(
                        resultSet.getInt("id"),
                        resultSet.getString("jenis_servis"),
                        resultSet.getString("keterangan_servis"),
                        resultSet.getDate("tanggal_mulai"),
                        resultSet.getDate("tanggal_selesai"),
                        resultSet.getInt("kilometer_akhir"),
                        resultSet.getFloat("biaya"),
                        resultSet.getInt("kendaraan_id")
                );
                riwayatServisList.add(riwayatServis);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return riwayatServisList;
    }

    @Override
    public String toString() {
        return "RiwayatServis{" +
                "id=" + id +
                ", jenisServis='" + jenisServis + '\'' +
                ", keteranganServis='" + keteranganServis + '\'' +
                ", tanggalMulai=" + tanggalMulai +
                ", tanggalSelesai=" + tanggalSelesai +
                ", kilometerAkhir=" + kilometerAkhir +
                ", biaya=" + biaya +
                ", kendaraanId=" + kendaraanId +
                '}';
    }
}