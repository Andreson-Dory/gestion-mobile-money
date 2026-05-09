package com.mobilemoney.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mobilemonay.util.DBConnection;
import com.mobilemoney.model.Retrait;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RetraitDAO {
	
	private static final String INSERT_RETRAIT_QUERY = "INSERT INTO RETRAIT (numtel, montant) VALUES (?, ?);";
	private static final String SELECT_ALL_RETRAIT_QUERY = "SELECT \n"
			+ "    r.idrecep AS idrecep,\n"
			+ "    r.numtel AS numtel,\n"
			+ "    r.montant AS montant,\n"
			+ "    r.daterecep AS daterecep,\n"
			+ "    c.nom AS nom\n"
			+ "FROM RETRAIT AS r\n"
			+ "JOIN CLIENT AS c \n"
			+ "    ON c.numtel = r.numtel\n"
			+ "WHERE r.status = 'VALIDE'";
	private static final String SELECT_RETRAIT_BY_IDRECEP_QUERY = "SELECT \n"
			+ "r.idrecep AS idrecep,\n"
			+ "r.numtel AS numtel,\n"
			+ "r.montant AS montant,\n"
			+ "r.daterecep AS daterecep,\n"
			+ "c.nom AS nom\n"
			+ "FROM RETRAIT AS r\n"
			+ "JOIN CLIENT AS c \n"
			+ "		ON c.numtel = r.numtel\n"
			+ "WHERE r.status = 'VALIDE' AND idrecep = ?;";
	private static final String SELECT_RETRAIT_BY_DATE_QUERY = "SELECT \n"
			+ "		r.idrecep AS idrecep,\n"
			+ "		r.numtel AS numtel,\n"
			+ "		r.montant AS montant,\n"
			+ "		r.daterecep AS daterecep,\n"
			+ "		c.nom AS nom\n"
			+ "	FROM RETRAIT AS r\n"
			+ "	JOIN CLIENT AS c \n"
			+ "		ON c.numtel = r.numtel\n"
			+ "	WHERE r.status = 'VALIDE' AND DATE(r.daterecep) = ?;";
	private static final String DELETE_RETRAIT_QUERY = "UPDATE RETRAIT set status = 'ANNULE' WHERE idrecep= ?;";
	
	public void insert (HttpServletRequest request, HttpServletResponse response, Retrait r) {
		try(Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(INSERT_RETRAIT_QUERY))
		{
			ps.setString(1, r.getNumtel());
			ps.setInt(2, r.getMontant());
			
			ps.executeUpdate();		
	        request.getSession().setAttribute("success", "Retrait effectué avec succès !");
		} catch (SQLException e) {
	        request.getSession().setAttribute("error", "Erreur lors du retrait !");
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
	                        rs.getTimestamp("daterecep").toLocalDateTime(),
	                        rs.getString("nom")
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
	
	public List<Retrait> searchRetrait(LocalDateTime date) {
		List<Retrait> list = new ArrayList<>();
		
		try (Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(SELECT_RETRAIT_BY_DATE_QUERY))
		{
			ps.setTimestamp(1, Timestamp.valueOf(date));
	        ResultSet rs = ps.executeQuery();
			
	        while (rs.next()) {
            	Retrait e = new Retrait(
                        rs.getString("idrecep"),
                        rs.getString("numtel"),
                        rs.getInt("montant"),
                        rs.getTimestamp("daterecep").toLocalDateTime(),
                        rs.getString("nom")
                );
                list.add(e);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void delete(HttpServletRequest request, HttpServletResponse response, String idrecep) {
		
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_RETRAIT_QUERY)) {

            ps.setString(1, idrecep);
            ps.executeUpdate();
            request.getSession().setAttribute("success", "Retrait supprimé avec succès !");
        } catch (SQLException ex) {
	        request.getSession().setAttribute("error", "Erreur lors de la suppression du retrait !");
            ex.printStackTrace();
        }
	}
}
