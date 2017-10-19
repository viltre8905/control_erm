package com.verynet.gcint.api.db;

import com.verynet.gcint.api.model.Deficiency;

import java.util.List;

/**
 * Created by day on 02/10/2016.
 */
public interface DeficiencyDAO {
    public Deficiency saveDeficiency(Deficiency deficiency);

    public Deficiency getDeficiency(Integer id);

    public List<Deficiency> getAllDeficiencies();

    public boolean deleteDeficiency(Integer id);
}
