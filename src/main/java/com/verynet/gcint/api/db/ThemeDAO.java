package com.verynet.gcint.api.db;

import com.verynet.gcint.api.model.DiscussionDate;
import com.verynet.gcint.api.model.Theme;

import java.util.List;

/**
 * Created by day on 19/02/2017.
 */
public interface ThemeDAO {
    public Theme saveThemes(Theme theme);

    public Theme getTheme(Integer id);

    public List<Theme> getAllThemes(Integer entityId);

    public boolean deleteTheme(Integer id);

    public DiscussionDate saveDiscussionDate(DiscussionDate discussionDate);

    public DiscussionDate getDiscussionDate(Integer id);

    public List<DiscussionDate> getAllDiscussionDate(Integer themeId);

    public boolean deleteDiscussionDate(Integer id);
}
