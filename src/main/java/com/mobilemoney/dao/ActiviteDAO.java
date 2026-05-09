package com.mobilemoney.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mobilemonay.util.DBConnection;
import com.mobilemoney.model.Activite;

public class ActiviteDAO {

    public List<Activite> getActivitesRecentes() {

        List<Activite> list = new ArrayList<>();

        String sql = 
                "SELECT 'ENVOI' AS type, c.nom AS client, e.montant, e.date AS dateOp " +
                "FROM ENVOI e " +
                "JOIN CLIENT c ON e.numRecepteur = c.numtel " +
                
                "UNION ALL " +
                
                "SELECT 'RETRAIT' AS type, c.nom AS client, r.montant, r.daterecep AS dateOp " +
                "FROM RETRAIT r " +
                "JOIN CLIENT c ON r.numtel = c.numtel " +
                
                "ORDER BY dateOp DESC " +
                "LIMIT 8";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Activite act = new Activite(
                        rs.getString("type"),
                        rs.getString("client"),
                        rs.getInt("montant"),
                        rs.getString("dateOp")
                );
                list.add(act);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}