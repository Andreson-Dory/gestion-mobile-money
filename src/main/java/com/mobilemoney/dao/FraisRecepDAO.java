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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FraisRecepDAO {

	private static final String INSERT_FRAISRECEP_QUERY = "INSERT INTO FRAIS_RECEP (montant1, montant2, frais_rec) VALUES (?, ?, ?);";
	private static final String SELECT_ALL_FRAISRECEP_QUERY = "SELECT * FROM FRAIS_RECEP;";
	private static final String SELECT_FRAISRECEP_BY_MONTANT_QUERY = "SELECT * FROM FRAIS_RECEP WHERE ? BETWEEN montant1 AND montant2;";
	private static final String UPDATE_FRAISRECEP_QUERY = "UPDATE FRAIS_RECEP SET montant1= ?, montant2= ?, frais_rec= ? WHERE idRec= ?;";
	private static final String DELETE_FRAISRECEP_QUERY = "DELETE FROM FRAIS_RECEP WHERE idRec= ?;";
	
	public void insert(HttpServletRequest request, HttpServletResponse response, FraisRecep fr) {
		
		try(Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(INSERT_FRAISRECEP_QUERY))
			{	
				ps.setInt(1, fr.getMontant1());
				ps.setInt(2, fr.getMontant2());
				ps.setInt(3, fr.getFraisRec());
				
				ps.executeUpdate();		
		        request.getSession().setAttribute("success", "Frais réception ajouté avec succès !");
			} catch (SQLException e) {
		        request.getSession().setAttribute("error", "Erreur lors de l'ajout du frais de réception !");
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
	                        rs.getInt("idRec"),
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
		
	public FraisRecep findByMontant(int montant) {
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(SELECT_FRAISRECEP_BY_MONTANT_QUERY))
			{
				ps.setInt(1, montant);
		        ResultSet rs = ps.executeQuery();
				
				if (rs.next()) {
					return new FraisRecep(
							rs.getInt("idRec"),
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
	
	public void delete(HttpServletRequest request, HttpServletResponse response, int idRec) {
		
		try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(DELETE_FRAISRECEP_QUERY)) {

	            ps.setInt(1, idRec);
	            ps.executeUpdate();
	            request.getSession().setAttribute("success", "Frais réception mis à jour avec succès !");
	        } catch (SQLException ex) {
		        request.getSession().setAttribute("error", "Erreur lors de la mis à jour du frais de réception !");
	            ex.printStackTrace();
	        }
	}
	
	public void update(HttpServletRequest request, HttpServletResponse response, FraisRecep fr) {
		
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(UPDATE_FRAISRECEP_QUERY)) 
			{
				ps.setInt(1, fr.getMontant1());
				ps.setInt(2, fr.getMontant2());
				ps.setInt(3, fr.getFraisRec());
				ps.setInt(4, fr.getIdRec());
				
				ps.executeUpdate();
		        request.getSession().setAttribute("success", "Frais réception supprimé avec succès !");
			} catch (SQLException ex) {
		        request.getSession().setAttribute("error", "Erreur lors de la suppression du frais réception !");
				ex.printStackTrace();
			}
	}
}
