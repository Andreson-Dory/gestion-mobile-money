package com.mobilemoney.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mobilemonay.util.DBConnection;
import com.mobilemoney.model.Client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ClientDAO {

	private static final String INSERT_CLIENT_QUERY = "INSERT INTO CLIENT (numtel, nom, sexe, age, mail) VALUES (?, ?, ?, ?, ?);";
	private static final String SELECT_ALL_CLIENT_QUERY = "SELECT * FROM CLIENT;";
	private static final String SELECT_CLIENT_BY_NUMTEL_QUERY = "SELECT * FROM CLIENT WHERE numtel = ?;";
	private static final String SEARCH_CLIENT_QUERY = "SELECT * FROM CLIENT WHERE numtel LIKE ? OR nom LIKE ? OR mail LIKE ?;";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE CLIENT SET nom= ?, sexe= ?, age= ?, solde= ?, mail= ? WHERE numtel= ?;";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM CLIENT WHERE numtel= ?;";
	
	
	public void insert (HttpServletRequest request, HttpServletResponse response, Client c) {
		try(Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(INSERT_CLIENT_QUERY))
		{
			ps.setString(1, c.getNumtel());
			ps.setString(2, c.getNom());
			ps.setString(3, c.getSexe());
			ps.setInt(4, c.getAge());
			ps.setString(5, c.getMail());
			
			ps.executeUpdate();		
			request.getSession().setAttribute("success", "Client ajouter avec succès !");
		} catch (SQLException e) {
	        request.getSession().setAttribute("error", "Erreur lors de l'ajout du client !");
			e.printStackTrace();
		};
	}
	
	public List<Client> findAll() {
		List<Client> list = new ArrayList<>();
		
		try (Connection conn = DBConnection.getConnection();
	             Statement st = conn.createStatement();
	             ResultSet rs = st.executeQuery(SELECT_ALL_CLIENT_QUERY)) {

	            while (rs.next()) {
	                Client e = new Client(
	                        rs.getString("numtel"),
	                        rs.getString("nom"),
	                        rs.getString("sexe"),
	                        rs.getInt("age"),
	                        rs.getInt("solde"),
	                        rs.getString("mail")
	                );
	                list.add(e);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	
	        return list;
	}
	
	public Client findByNumtel(String numtel) {
		try (Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(SELECT_CLIENT_BY_NUMTEL_QUERY))
		{
			ps.setString(1, numtel);
	        ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return new Client(
					rs.getString("numtel"),
					rs.getString("nom"),
					rs.getString("sexe"),
					rs.getInt("age"),
					rs.getInt("solde"),
					rs.getString("mail")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Client> searchClients(String value) {
		List<Client> list = new ArrayList<>();
		
		try (Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(SEARCH_CLIENT_QUERY))
		{
			String keyword = "%" + value + "%";
			
			ps.setString(1, keyword);
			ps.setString(2, keyword);
			ps.setString(3, keyword);
	        ResultSet rs = ps.executeQuery();
			
	        while (rs.next()) {
                Client e = new Client(
                        rs.getString("numtel"),
                        rs.getString("nom"),
                        rs.getString("sexe"),
                        rs.getInt("age"),
                        rs.getInt("solde"),
                        rs.getString("mail")
                );
                list.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void delete(HttpServletRequest request, HttpServletResponse response, String numtel) {
		
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_CLIENT_QUERY)) {

            ps.setString(1, numtel);
            ps.executeUpdate();
    		request.getSession().setAttribute("success", "Client mis à jour avec succès !");
        } catch (SQLException ex) {
	        request.getSession().setAttribute("error", "Erreur lors de la mise à jour du client !");
            ex.printStackTrace();
        }
	}
	
	public void update(HttpServletRequest request, HttpServletResponse response, Client c) {
		
		try (Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(UPDATE_CLIENT_QUERY)) 
		{
			ps.setString(1, c.getNom());
			ps.setString(2, c.getSexe());
			ps.setInt(3, c.getAge());
			ps.setInt(4, c.getSolde());
			ps.setString(5, c.getMail());
			ps.setString(6, c.getNumtel());
			
			ps.executeUpdate();
			request.getSession().setAttribute("success", "Client supprimé avec succès !");
		} catch (SQLException ex) {
	        request.getSession().setAttribute("error", "Erreur lors de la suppression du client !");
			ex.printStackTrace();
		}
	}
}
