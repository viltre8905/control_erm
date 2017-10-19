package com.verynet.gcint.api.db.hibernate;

import com.verynet.gcint.api.db.ThemeDAO;
import com.verynet.gcint.api.model.DiscussionDate;
import com.verynet.gcint.api.model.Theme;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by day on 19/02/2017.
 */
public class HibernateThemeDAO extends HibernateGeneralDAO implements ThemeDAO {
    public HibernateThemeDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Theme saveThemes(Theme theme) {
        currentSession().saveOrUpdate(theme);
        return theme;
    }

    @Override
    public Theme getTheme(Integer id) {
        return (Theme) currentSession().get(Theme.class, id);
    }

    @Override
    public List<Theme> getAllThemes(Integer entityId) {
        return currentSession().createCriteria(Theme.class).createAlias("entity", "e")
                .add(Restrictions.eq("e.id", entityId)).list();
    }

    @Override
    public boolean deleteTheme(Integer id) {
        Theme theme = getTheme(id);
        if (theme != null) {
            currentSession().delete(theme);
            return true;
        }
        return false;
    }

    @Override
    public DiscussionDate saveDiscussionDate(DiscussionDate discussionDate) {
        currentSession().saveOrUpdate(discussionDate);
        return discussionDate;
    }

    @Override
    public DiscussionDate getDiscussionDate(Integer id) {
        return (DiscussionDate) currentSession().get(DiscussionDate.class, id);
    }

    @Override
    public List<DiscussionDate> getAllDiscussionDate(Integer themeId) {
        return currentSession().createCriteria(DiscussionDate.class, "dd")
                .createAlias("dd.theme", "th").add(Restrictions.eq("th.id", themeId)).list();
    }

    @Override
    public boolean deleteDiscussionDate(Integer id) {
        DiscussionDate discussionDate = getDiscussionDate(id);
        if (discussionDate != null) {
            currentSession().delete(discussionDate);
            return true;
        }
        return false;
    }
}
