package com.verynet.gcint.api.services;

import com.verynet.gcint.api.db.ReunionDAO;
import com.verynet.gcint.api.model.Reunion;

import java.util.List;

/**
 * Created by day on 03/10/2016.
 */
public interface ReunionService {

    public void setReunionDAO(ReunionDAO dao);

    public Reunion saveReunion(Reunion reunion);

    public Reunion getReunion(Integer id);

    public List<Reunion> getAllReunions(Integer entityId);

    public boolean deleteReunion(Integer id);
}
