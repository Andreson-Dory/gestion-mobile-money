package com.mobilemoney.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mobilemonay.util.DBConnection;
import com.mobilemoney.model.Statistique;

public class StatistiqueDAO {
		
		public int getRecetteFraisEnvoi() {
			int total = 0;
			
			String sql="SELECT SUM(fe.FraisEnvoi) AS total " +
	                "FROM ENVOI ev, FRAIS_ENVOI fe " +
	                "WHERE ev.montant BETWEEN fe.montant1 AND fe.montant2";
			
	        try (Connection conn = DBConnection.getConnection();
	                PreparedStatement ps = conn.prepareStatement(sql);
	                ResultSet rs = ps.executeQuery()) {
	
	               if (rs.next()) {
	                   total = rs.getInt("total");
	               }
	
	           } catch (Exception e) {
	               e.printStackTrace();
	           }
	
	           return total;
	       }
		
		public int getNombreEnvois() {
			int nb = 0;
			
			String sql = "SELECT COUNT(*) AS total FROM ENVOI";
			
			try (Connection conn = DBConnection.getConnection();
					PreparedStatement ps = conn.prepareStatement(sql);
					ResultSet rs = ps.executeQuery()) {
				
				if (rs.next()) {
					nb = rs.getInt("total");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return nb;
		}
		
		public int getRecetteFraisRetrait() {
			int total = 0;

	        String sql = "SELECT SUM(fr.FraisRecep) AS total " +
	                     "FROM RETRAIT r, FRAIS_RECEP fr " +
	                     "WHERE r.montant BETWEEN fr.montant1 AND fr.montant2";

	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            if (rs.next()) {
	                total = rs.getInt("total");
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return total;
		}
		
		public int getNombreRetraits() {
	        int nb = 0;

	        String sql = "SELECT COUNT(*) AS total FROM RETRAIT";

	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            if (rs.next()) {
	                nb = rs.getInt("total");
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return nb;
	    }

		public int getRecetteTotale() {
	        return getRecetteFraisEnvoi() + getRecetteFraisRetrait();
	    }

		public Statistique getStatistiques() {

		    Statistique stat = new Statistique();

		    stat.setRecetteFraisEnvoi(getRecetteFraisEnvoi());
		    stat.setNombreEnvois(getNombreEnvois());
		    stat.setRecetteFraisRetrait(getRecetteFraisRetrait());
		    stat.setNombreRetraits(getNombreRetraits());

		    stat.setRecetteTotale(
		        stat.getRecetteFraisEnvoi() + stat.getRecetteFraisRetrait()
		    );

		    return stat;
		}
	}

