package com.mobilemoney.service;

import java.util.List;

import com.mobilemoney.dao.ActiviteDAO;
import com.mobilemoney.dao.StatistiqueDAO;
import com.mobilemoney.model.Activite;
import com.mobilemoney.model.Statistique;

public class dashboardService {

    private StatistiqueDAO statDAO = new StatistiqueDAO();
    private ActiviteDAO actDAO = new ActiviteDAO();

    // Récupérer les statistiques
    public Statistique getStatistiques() {
        return statDAO.getStatistiques();
    }

    // Récupérer les activités récentes
    public List<Activite> getActivitesRecentes() {
        return actDAO.getActivitesRecentes();
    }
}