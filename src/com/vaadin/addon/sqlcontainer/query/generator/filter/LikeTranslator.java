package com.vaadin.addon.sqlcontainer.query.generator.filter;

import com.vaadin.addon.sqlcontainer.filters.Like;
import com.vaadin.addon.sqlcontainer.query.generator.StatementHelper;
import com.vaadin.data.Container.Filter;

public class LikeTranslator implements FilterTranslator {

    public boolean translatesFilter(Filter filter) {
        return filter instanceof Like;
    }

    public String getWhereStringForFilter(Filter filter, StatementHelper sh) {
        Like like = (Like) filter;
        if (like.isCaseSensitive()) {
            sh.addParameterValue(like.getValue());
            return QueryBuilder.quote(like.getPropertyId())
                    + " LIKE ?";
        } else {
            sh.addParameterValue(like.getValue().toUpperCase());
            return "UPPER("
                    + QueryBuilder.quote(like.getPropertyId())
                    + ") LIKE ?";
        }
    }

}
