package com.verynet.gcint.api.services.impl;

import com.verynet.gcint.api.db.ThemeDAO;
import com.verynet.gcint.api.model.DiscussionDate;
import com.verynet.gcint.api.model.Theme;
import com.verynet.gcint.api.services.ThemeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by day on 19/02/2017.
 */
@Transactional
public class ThemeServiceImpl implements ThemeService {
    private ThemeDAO dao;

    @Override
    public void setThemeDAO(ThemeDAO dao) {
        this.dao = dao;
    }

    @Override
    public Theme saveTheme(Theme theme) {
        return dao.saveThemes(theme);
    }

    @Override
    @Transactional(readOnly = true)
    public Theme getTheme(Integer id) {
        return dao.getTheme(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Theme> getAllThemes(Integer entityId) {
        return dao.getAllThemes(entityId);
    }

    @Override
    public boolean deleteTheme(Integer id) {
        return dao.deleteTheme(id);
    }

    @Override
    public DiscussionDate saveDiscussionDate(DiscussionDate discussionDate) {
        return dao.saveDiscussionDate(discussionDate);
    }

    @Override
    public DiscussionDate getDiscussionDate(Integer id) {
        return dao.getDiscussionDate(id);
    }

    @Override
    public List<DiscussionDate> getAllDiscussionDate(Integer themeId) {
        return dao.getAllDiscussionDate(themeId);
    }

    @Override
    public boolean deleteDiscussionDate(Integer id) {
        return dao.deleteDiscussionDate(id);
    }
}
