package com.mobilemoney.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobilemonay.util.DBConnection;
import com.mobilemoney.model.Retrait;

public class RetraitDAO {
	
	private static final String INSERT_RETRAIT_QUERY = "INSERT INTO RETRAIT (idrecep, numtel, montant, daterecep) VALUES (?, ?, ?, ?);";
	private static final String SELECT_ALL_RETRAIT_QUERY = "SELECT * FROM RETRAIT;";
	private static final String SELECT_RETRAIT_BY_IDRECEP_QUERY = "SELECT * FROM RETRAIT WHERE idrecep = ?;";
	private static final String DELETE_RETRAIT_QUERY = "DELETE FROM RETRAIT WHERE idrecep= ?;";
	
	public void insert (Retrait r) {
		try(Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(INSERT_RETRAIT_QUERY))
		{
			ps.setString(1, r.getIdrecep());
			ps.setString(2, r.getNumtel());
			ps.setInt(3, r.getMontant());
			ps.setTimestamp(4, Timestamp.valueOf(r.getDaterecep()));
			
			ps.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		};
	}
	
	public List<Retrait> findAll() {
		List<Retrait> list = new ArrayList<>();
		
		try (Connection conn = DBConnection.getConnection();
	             Statement st = conn.createStatement();
	             ResultSet rs = st.executeQuery(SELECT_ALL_RETRAIT_QUERY)) {

	            while (rs.next()) {
	            	Retrait e = new Retrait(
	                        rs.getString("idrecep"),
	                        rs.getString("numtel"),
	                        rs.getInt("montant"),
	                        rs.getTimestamp("daterecep").toLocalDateTime()
	                );
	                list.add(e);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return list;
	}
	
	public Retrait findByNumtel(String idrecep) {
		try (Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(SELECT_RETRAIT_BY_IDRECEP_QUERY))
		{
			ps.setString(1, idrecep);
	        ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return new Retrait(
						rs.getString("idrecep"),
                        rs.getString("numtel"),
                        rs.getInt("montant"),
                        rs.getTimestamp("daterecep").toLocalDateTime()
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void delete(String idrecep) {
		
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_RETRAIT_QUERY)) {

            ps.setString(1, idrecep);
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
	}
}
