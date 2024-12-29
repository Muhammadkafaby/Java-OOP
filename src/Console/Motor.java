package Console;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Motor extends Kendaraan {
    private String warnaMotor;
    private String jenisMotor;

    public Motor(int id, String nomorRangka, String nomorPlat, int tahunPembuatan, String merekKendaraan,
            String warnaMotor, String jenisMotor) {
        super(id, nomorRangka, nomorPlat, tahunPembuatan, merekKendaraan);
        this.warnaMotor = warnaMotor;
        this.jenisMotor = jenisMotor;
    }

    @Override
    public String toString() {
        return super.toString() + "Motor{" +
                "warnaMotor='" + warnaMotor + '\'' +
                ", jenisMotor='" + jenisMotor + '\'' +
                "} ";
    }

    public String getJenis() {
        return "motor";
    }

    public void saveToDatabase() {
        String query = "INSERT INTO motor (id, nomor_rangka, nomor_plat, tahun_pembuatan, merek_kendaraan, warna_motor, jenis_motor) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, getId());
            statement.setString(2, getNomorRangka());
            statement.setString(3, getNomorPlat());
            statement.setInt(4, getTahunPembuatan());
            statement.setString(5, getMerekKendaraan());
            statement.setString(6, warnaMotor);
            statement.setString(7, jenisMotor);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateInDatabase() {
        String query = "UPDATE motor SET nomor_rangka = ?, nomor_plat = ?, tahun_pembuatan = ?, merek_kendaraan = ?, warna_motor = ?, jenis_motor = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, getNomorRangka());
            statement.setString(2, getNomorPlat());
            statement.setInt(3, getTahunPembuatan());
            statement.setString(4, getMerekKendaraan());
            statement.setString(5, warnaMotor);
            statement.setString(6, jenisMotor);
            statement.setInt(7, getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Motor getFromDatabase(int id) {
        String query = "SELECT * FROM motor WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Motor(
                        resultSet.getInt("id"),
                        resultSet.getString("nomor_rangka"),
                        resultSet.getString("nomor_plat"),
                        resultSet.getInt("tahun_pembuatan"),
                        resultSet.getString("merek_kendaraan"),
                        resultSet.getString("warna_motor"),
                        resultSet.getString("jenis_motor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}