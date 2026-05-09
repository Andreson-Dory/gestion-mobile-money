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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EnvoiDAO {

    private static final String INSERT_ENVOI_QUERY =
        "INSERT INTO ENVOI (numEnvoyeur, numRecepteur, montant, date, payer_frais_retrait, raison) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_ENVOI_QUERY =
        "SELECT * FROM ENVOI";

    private static final String SEARCH_ENVOI_BY_DATE =
        "SELECT * FROM ENVOI WHERE DATE(e.date) = ?";

    private static final String SELECT_CLIENT_MONTHLY_ENVOI_QUERY = 
    		"SELECT * FROM ENVOI WHERE (numEnvoyeur = ? OR numRecepteur = ?) AND DATE_FORMAT(date, '%Y-%m') = ?";
    
    private static final String DELETE_ENVOI_QUERY =
        "DELETE FROM ENVOI WHERE idEnv = ?";


    public void insertEnvoi(HttpServletRequest request, HttpServletResponse response, Envoi env) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_ENVOI_QUERY)) {

            ps.setString(1, env.getNumEnvoyeur());
            ps.setString(2, env.getNumRecepteur());
            ps.setInt(3, env.getMontant());
            ps.setTimestamp(4, Timestamp.valueOf(env.getDate()));
            ps.setBoolean(5, env.isPayerFraisRetrait());
            ps.setString(6, env.getRaison());

            ps.executeUpdate();
            request.getSession().setAttribute("success", "Transfert effectué avec succès !");
        } catch (SQLException e) {
	        request.getSession().setAttribute("error", "Erreur lors du transfert !");
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
                        rs.getInt("idEnv"),
                        rs.getString("numEnvoyeur"),
                        rs.getString("numRecepteur"),
                        rs.getInt("montant"),
                        rs.getTimestamp("date").toLocalDateTime(),
                        rs.getBoolean("payer_frais_retrait"),
                        rs.getString("raison")
                );

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
                        rs.getInt("idEnv"),
                        rs.getString("numEnvoyeur"),
                        rs.getString("numRecepteur"),
                        rs.getInt("montant"),
                        rs.getTimestamp("date").toLocalDateTime(),
                        rs.getBoolean("payer_frais_retrait"),
                        rs.getString("raison")
                );

                list.add(ev);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Envoi> findClientMonthlyEnvoi(String numtel, String date ) {
        List<Envoi> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_CLIENT_MONTHLY_ENVOI_QUERY)) {

            ps.setString(1, numtel);
            ps.setString(2, numtel);
            ps.setString(3, date);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Envoi ev = new Envoi(
                        rs.getInt("idEnv"),
                        rs.getString("numEnvoyeur"),
                        rs.getString("numRecepteur"),
                        rs.getInt("montant"),
                        rs.getTimestamp("date").toLocalDateTime(),
                        rs.getBoolean("payer_frais_retrait"),
                        rs.getString("raison")
                );

                list.add(ev);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void deleteEnvoi(HttpServletRequest request, HttpServletResponse response, String idEnv) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_ENVOI_QUERY)) {

            ps.setString(1, idEnv);
            ps.executeUpdate();
            request.getSession().setAttribute("success", "Transfert supprimé avec succès !");
        } catch (SQLException e) {
	        request.getSession().setAttribute("error", "Erreur lors de la suppression du transfert !");
            e.printStackTrace();
        }
    }
    
}