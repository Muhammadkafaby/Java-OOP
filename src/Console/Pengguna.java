package Console;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Pengguna {
    private int id;
    private String namaPengguna;
    private String noTelepon;
    private String password;
    private String username;
    private ArrayList<Kendaraan> kendaraanList;

    public Pengguna(int id, String namaPengguna, String noTelepon, String password, String username) {
        this.id = id;
        this.namaPengguna = namaPengguna;
        this.noTelepon = noTelepon;
        this.password = password;
        this.username = username;
        this.kendaraanList = new ArrayList<>();
    }

    public static Pengguna getFromDatabaseByUsernameAndPassword(String username, String password) {
        String query = "SELECT * FROM pengguna WHERE username = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Pengguna(
                        resultSet.getInt("id"),
                        resultSet.getString("nama_pengguna"),
                        resultSet.getString("no_telepon"),
                        resultSet.getString("password"),
                        resultSet.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Pengguna getFromDatabaseById(int id) {
        String query = "SELECT * FROM pengguna WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Pengguna(
                        resultSet.getInt("id"),
                        resultSet.getString("nama_pengguna"),
                        resultSet.getString("no_telepon"),
                        resultSet.getString("password"),
                        resultSet.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateInDatabase() {
        String query = "UPDATE pengguna SET nama_pengguna = ?, no_telepon = ?, password = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, namaPengguna);
            statement.setString(2, noTelepon);
            statement.setString(3, password);
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Pengguna{" +
                "id=" + id +
                ", namaPengguna='" + namaPengguna + '\'' +
                ", noTelepon='" + noTelepon + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}