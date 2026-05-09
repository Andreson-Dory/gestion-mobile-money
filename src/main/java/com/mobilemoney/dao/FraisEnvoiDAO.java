package com.mobilemoney.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mobilemonay.util.DBConnection;
import com.mobilemoney.model.FraisEnvoi;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FraisEnvoiDAO {

	private static final String INSERT_FRAISENVOI_QUERY = "INSERT INTO FRAIS_ENVOI (montant1, montant2, frais_env) VALUES (?, ?, ?);";
	private static final String SELECT_ALL_FRAISENVOI_QUERY = "SELECT * FROM FRAIS_ENVOI;";
	private static final String SELECT_FRAISENV_BY_IDENV_QUERY = "SELECT * FROM FRAIS_ENVOI WHERE idEnv= ?;";
	private static final String SELECT_FRAISENV_BY_MONTANT_QUERY = "SELECT * FROM FRAIS_ENVOI WHERE ? BETWEEN montant1 AND montant2;";
	private static final String UPDATE_FRAISENV_QUERY = "UPDATE FRAIS_ENVOI SET montant1= ?, montant2= ?, frais_env= ? WHERE idEnv= ?;";
	private static final String DELETE_FRAISENV_QUERY = "DELETE FROM FRAIS_ENVOI WHERE idEnv= ?;";
	
	public void insert(HttpServletRequest request, HttpServletResponse response, FraisEnvoi fe) {
		
		try(Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(INSERT_FRAISENVOI_QUERY))
			{	
				ps.setInt(1, fe.getMontant1());
				ps.setInt(2, fe.getMontant2());
				ps.setInt(3, fe.getFraisEnv());
				
				ps.executeUpdate();
		        request.getSession().setAttribute("success", "Frais envoi ajouté avec succès !");
			} catch (SQLException e) {
		        request.getSession().setAttribute("error", "Erreur lors de l'ajout du frais envoi !");
				e.printStackTrace();
			};
	}
	
	public List<FraisEnvoi> findAll() {
		List<FraisEnvoi> list = new ArrayList<>();
		
		try (Connection conn = DBConnection.getConnection();
	             Statement st = conn.createStatement();
	             ResultSet rs = st.executeQuery(SELECT_ALL_FRAISENVOI_QUERY)) {

	            while (rs.next()) {
	            	FraisEnvoi fe = new FraisEnvoi(
	                        rs.getInt("idEnv"),
	                        rs.getInt("montant1"),
	                        rs.getInt("montant2"),
	                        rs.getInt("frais_env")
	                );
	                list.add(fe);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return list;
	}
	
	public FraisEnvoi findByIdEnv(String idEnv) {
		
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(SELECT_FRAISENV_BY_IDENV_QUERY))
			{
				ps.setString(1, idEnv);
		        ResultSet rs = ps.executeQuery();
				

				if (rs.next()) {
					return new FraisEnvoi(
							rs.getInt("idEnv"),
	                        rs.getInt("montant1"),
	                        rs.getInt("montant2"),
	                        rs.getInt("frais_env")
					);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	}
	
	public FraisEnvoi findByMontant(int montant) {
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(SELECT_FRAISENV_BY_MONTANT_QUERY))
			{
				ps.setInt(1, montant);
		        ResultSet rs = ps.executeQuery();
				
				if (rs.next()) {
					return new FraisEnvoi(
							rs.getInt("idEnv"),
	                        rs.getInt("montant1"),
	                        rs.getInt("montant2"),
	                        rs.getInt("frais_env")
					);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	}
	
	public void delete(HttpServletRequest request, HttpServletResponse response, int idEnv) {
		
		try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(DELETE_FRAISENV_QUERY)) {

	            ps.setInt(1, idEnv);
	            ps.executeUpdate();
	            request.getSession().setAttribute("success", "Frais envoi mis à jour avec succès !");
	        } catch (SQLException ex) {
		        request.getSession().setAttribute("error", "Erreur lors de la suppression du frais envoi !");
	            ex.printStackTrace();
	        }
	}
	
	public void update(HttpServletRequest request, HttpServletResponse response, FraisEnvoi fe) {
		
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(UPDATE_FRAISENV_QUERY)) 
			{
				ps.setInt(1, fe.getMontant1());
				ps.setInt(2, fe.getMontant2());
				ps.setInt(3, fe.getFraisEnv());
				ps.setInt(4, fe.getIdEnv());
				
				ps.executeUpdate();
		        request.getSession().setAttribute("success", "Frais envoi supprimé avec succès !");
			} catch (SQLException ex) {
		        request.getSession().setAttribute("error", "Erreur lors de la mise à jour du frais envoi !");
				ex.printStackTrace();
			}
	}
}
