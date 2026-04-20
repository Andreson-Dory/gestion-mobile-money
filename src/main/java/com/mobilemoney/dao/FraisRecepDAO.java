package com.mobilemoney.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mobilemonay.util.DBConnection;
import com.mobilemoney.model.FraisRecep;

public class FraisRecepDAO {

	private static final String INSERT_FRAISRECEP_QUERY = "INSERT INTO FRAIS_RECEP (idRec, montant1, montant2, frais_rec) VALUEs (?, ?, ?, ?);";
	private static final String SELECT_ALL_FRAISRECEP_QUERY = "SELECT * FROM FRAIS_RECEP;";
	private static final String SELECT_FRAISRECEP_BY_IDREC_QUERY = "SELECT * FROM FRAIS_RECEP WHERE idRec= ?;";
	private static final String UPDATE_FRAISRECEP_QUERY = "UPDATE FRAIS_RECEP SET montant1= ?, montant2= ?, frais_rec= ? WHERE idRec= ?;";
	private static final String DELETE_FRAISRECEP_QUERY = "DELETE FROM FRAIS_RECEP WHERE idRec= ?;";
	
	public void insert(FraisRecep fr) {
		
		try(Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(INSERT_FRAISRECEP_QUERY))
			{	
				ps.setString(1, fr.getIdRec());
				ps.setInt(2, fr.getMontant1());
				ps.setInt(3, fr.getMontant2());
				ps.setInt(4, fr.getFraisRec());
				
				ps.executeUpdate();			
			} catch (SQLException e) {
				e.printStackTrace();
			};
	}
	
	public List<FraisRecep> findAll() {
		List<FraisRecep> list = new ArrayList<>();
		
		try (Connection conn = DBConnection.getConnection();
	             Statement st = conn.createStatement();
	             ResultSet rs = st.executeQuery(SELECT_ALL_FRAISRECEP_QUERY)) {

	            while (rs.next()) {
	            	FraisRecep fe = new FraisRecep(
	                        rs.getString("idRec"),
	                        rs.getInt("montant1"),
	                        rs.getInt("montant2"),
	                        rs.getInt("frais_rec")
	                );
	                list.add(fe);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return list;
	}
	
	public FraisRecep findByIdEnv(String idRec) {
		
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(SELECT_FRAISRECEP_BY_IDREC_QUERY))
			{
				ps.setString(1, idRec);
		        ResultSet rs = ps.executeQuery();
				

				if (rs.next()) {
					return new FraisRecep(
							rs.getString("idRec"),
	                        rs.getInt("montant1"),
	                        rs.getInt("montant2"),
	                        rs.getInt("frais_rec")
					);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	}
	
	public void delete(String idRec) {
		
		try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(DELETE_FRAISRECEP_QUERY)) {

	            ps.setString(1, idRec);
	            ps.executeUpdate();

	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	}
	
	public void update(FraisRecep fr) {
		
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(UPDATE_FRAISRECEP_QUERY)) 
			{
				ps.setInt(1, fr.getMontant1());
				ps.setInt(2, fr.getMontant2());
				ps.setInt(3, fr.getFraisRec());
				ps.setString(4, fr.getIdRec());
				
				ps.executeUpdate();
			
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
	}
}
