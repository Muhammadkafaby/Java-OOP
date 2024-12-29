package Console;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Kendaraan {
    private int id;
    private String nomorRangka;
    private String nomorPlat;
    private int tahunPembuatan;
    private String merekKendaraan;
    private ArrayList<RiwayatServis> riwayatServisList;

    public Kendaraan(int id, String nomorRangka, String nomorPlat, int tahunPembuatan, String merekKendaraan) {
        this.id = id;
        this.nomorRangka = nomorRangka;
        this.nomorPlat = nomorPlat;
        this.tahunPembuatan = tahunPembuatan;
        this.merekKendaraan = merekKendaraan;
        this.riwayatServisList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNomorRangka() {
        return nomorRangka;
    }

    public String getNomorPlat() {
        return nomorPlat;
    }

    public int getTahunPembuatan() {
        return tahunPembuatan;
    }

    public String getMerekKendaraan() {
        return merekKendaraan;
    }

    public ArrayList<RiwayatServis> getRiwayatServisList() {
        return riwayatServisList;
    }

    public void addRiwayatServis(RiwayatServis servis) {
        riwayatServisList.add(servis);
        servis.saveToDatabase();
        System.out.println("Riwayat servis ditambahkan.");
    }

    public void deleteRiwayatServis(int servisId) {
        riwayatServisList.removeIf(servis -> servis.getId() == servisId);
        // Add code to delete servis from the database if needed
        System.out.println("Riwayat servis dihapus.");
    }

    public void displayRiwayatServis() {
        System.out.println("Riwayat Servis:");
        for (RiwayatServis servis : riwayatServisList) {
            System.out.println(servis);
        }
    }

    public void saveToDatabase() {
        String query = "INSERT INTO kendaraan (id, nomor_rangka, nomor_plat, tahun_pembuatan, merek_kendaraan) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setString(2, nomorRangka);
            statement.setString(3, nomorPlat);
            statement.setInt(4, tahunPembuatan);
            statement.setString(5, merekKendaraan);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateInDatabase() {
        String query = "UPDATE kendaraan SET nomor_rangka = ?, nomor_plat = ?, tahun_pembuatan = ?, merek_kendaraan = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nomorRangka);
            statement.setString(2, nomorPlat);
            statement.setInt(3, tahunPembuatan);
            statement.setString(4, merekKendaraan);
            statement.setInt(5, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFromDatabase(int id) {
        // Delete related rows in the mobil table
        String deleteMobilQuery = "DELETE FROM mobil WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteMobilQuery)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Delete related rows in the motor table
        String deleteMotorQuery = "DELETE FROM motor WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteMotorQuery)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Delete the row in the kendaraan table
        String deleteKendaraanQuery = "DELETE FROM kendaraan WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteKendaraanQuery)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Kendaraan getFromDatabase(int id) {
        String query = "SELECT * FROM kendaraan WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Kendaraan kendaraan = new Kendaraan(
                        resultSet.getInt("id"),
                        resultSet.getString("nomor_rangka"),
                        resultSet.getString("nomor_plat"),
                        resultSet.getInt("tahun_pembuatan"),
                        resultSet.getString("merek_kendaraan"));
                // Load riwayatServisList if needed
                return kendaraan;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}