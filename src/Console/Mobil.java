package Console;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mobil extends Kendaraan {
    private String warnaMobil;
    private String jenisTransmisi;

    public Mobil(int id, String nomorRangka, String nomorPlat, int tahunPembuatan, String merekKendaraan,
            String warnaMobil, String jenisTransmisi) {
        super(id, nomorRangka, nomorPlat, tahunPembuatan, merekKendaraan);
        this.warnaMobil = warnaMobil;
        this.jenisTransmisi = jenisTransmisi;
    }

    @Override
    public String toString() {
        return super.toString() + "Mobil{" +
                "warnaMobil='" + warnaMobil + '\'' +
                ", jenisTransmisi='" + jenisTransmisi + '\'' +
                "} ";
    }

    public String getJenis() {
        return "mobil";
    }

    public void saveToDatabase() {
        String query = "INSERT INTO mobil (id, nomor_rangka, nomor_plat, tahun_pembuatan, merek_kendaraan, warna_mobil, jenis_transmisi) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, getId());
            statement.setString(2, getNomorRangka());
            statement.setString(3, getNomorPlat());
            statement.setInt(4, getTahunPembuatan());
            statement.setString(5, getMerekKendaraan());
            statement.setString(6, warnaMobil);
            statement.setString(7, jenisTransmisi);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateInDatabase() {
        String query = "UPDATE mobil SET nomor_rangka = ?, nomor_plat = ?, tahun_pembuatan = ?, merek_kendaraan = ?, warna_mobil = ?, jenis_transmisi = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, getNomorRangka());
            statement.setString(2, getNomorPlat());
            statement.setInt(3, getTahunPembuatan());
            statement.setString(4, getMerekKendaraan());
            statement.setString(5, warnaMobil);
            statement.setString(6, jenisTransmisi);
            statement.setInt(7, getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Mobil getFromDatabase(int id) {
        String query = "SELECT * FROM mobil WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Mobil(
                        resultSet.getInt("id"),
                        resultSet.getString("nomor_rangka"),
                        resultSet.getString("nomor_plat"),
                        resultSet.getInt("tahun_pembuatan"),
                        resultSet.getString("merek_kendaraan"),
                        resultSet.getString("warna_mobil"),
                        resultSet.getString("jenis_transmisi"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}