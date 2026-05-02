package com.mobilemoney.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobilemonay.util.DBConnection;
import com.mobilemoney.model.Envoi;

public class EnvoiDAO {

    private static final String INSERT_ENVOI_QUERY =
        "INSERT INTO ENVOI (idEnv, numEnvoyeur, numRecepteur, montant, date, payer_frais_retrait, raison) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_ENVOI_QUERY =
        "SELECT e.*, f.frais_env " +
        "FROM ENVOI e " +
        "LEFT JOIN FRAIS_ENVOI f " +
        "ON e.montant BETWEEN f.montant1 AND f.montant2";

    private static final String SEARCH_ENVOI_BY_DATE =
        "SELECT e.*, f.frais_env " +
        "FROM ENVOI e " +
        "LEFT JOIN FRAIS_ENVOI f " +
        "ON e.montant BETWEEN f.montant1 AND f.montant2 " +
        "WHERE DATE(e.date) = ?";

    private static final String DELETE_ENVOI_QUERY =
        "DELETE FROM ENVOI WHERE idEnv = ?";


    public void insertEnvoi(Envoi env) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_ENVOI_QUERY)) {

            ps.setString(1, env.getIdEnv());
            ps.setString(2, env.getNumEnvoyeur());
            ps.setString(3, env.getNumRecepteur());
            ps.setInt(4, env.getMontant());
            ps.setTimestamp(5, Timestamp.valueOf(env.getDate()));
            ps.setBoolean(6, env.isPayerFraisRetrait());
            ps.setString(7, env.getRaison());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Envoi> findAllEnvoi() {
        List<Envoi> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_ENVOI_QUERY);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Envoi ev = new Envoi(
                        rs.getString("idEnv"),
                        rs.getString("numEnvoyeur"),
                        rs.getString("numRecepteur"),
                        rs.getInt("montant"),
                        rs.getTimestamp("date").toLocalDateTime(),
                        rs.getBoolean("payer_frais_retrait"),
                        rs.getString("raison")
                );

                ev.setFraisEnvoi(rs.getInt("frais_env"));

                list.add(ev);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public List<Envoi> searchByDate(String date) {
        List<Envoi> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SEARCH_ENVOI_BY_DATE)) {

            ps.setString(1, date);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Envoi ev = new Envoi(
                        rs.getString("idEnv"),
                        rs.getString("numEnvoyeur"),
                        rs.getString("numRecepteur"),
                        rs.getInt("montant"),
                        rs.getTimestamp("date").toLocalDateTime(),
                        rs.getBoolean("payer_frais_retrait"),
                        rs.getString("raison")
                );

                ev.setFraisEnvoi(rs.getInt("frais_env"));

                list.add(ev);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public void deleteEnvoi(String idEnv) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_ENVOI_QUERY)) {

            ps.setString(1, idEnv);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String getEmailByNumero(String numero) {

        String email = null;

        String sql = "SELECT email FROM client WHERE numero = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, numero);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                email = rs.getString("email");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return email;
    }
}