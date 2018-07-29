package com.epam.metrics.connectors.rest.jira.helpers;


import com.epam.metrics.connectors.rest.jira.models.issues.Issue;
import com.epam.metrics.connectors.rest.jira.models.issues.IssueChange;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by Sergei_Zheleznov on 24.08.2017.
 */
public class ChangeLogHelper {

    /**
     * Gets value of specific field as it was requested at specific date and time in the past.
     * Value is retrieved from changelog or issue fields (in case it was not changed during issue lifetime).
     * Note that issue fields collection usually contains different to changelog value format - this requires aditional delegate parameter.
     *
     * @param issue            JIRA issue
     * @param fieldName        Defines name of desired field
     * @param date             Defines date for field value calculation
     * @param parseFieldObject Defines name of desired field
     * @return Field value as it was requested of reuested date
     */
    public static String getFieldValueAsOfDate(Issue issue, String fieldName, DateTime date, Function<Map, String> parseFieldObject) {
        if (parseFieldObject == null) throw new IllegalArgumentException("Parse field object function can not be null");

        String fieldValueFromChangeLog = null;

        StringBuilder fieldValueFromChangeLogSb = new StringBuilder();
        if (tryGetFieldValueFromChangeLogAsOfDate(issue, fieldName, date, fieldValueFromChangeLogSb)) {
            return fieldValueFromChangeLogSb.toString();
        } else {
            if (issue.getCreatedDate().isAfter(date)) {
                // if requested date is before issue's creation date, return nothing
                return null;
            }

            if (issue.getFields().containsKey(fieldName)) {
                return parseFieldObject.apply((Map) issue.getFields().get(fieldName));
            } else {
                throw new RuntimeException("Field " + fieldName + " is not found in issue " + issue.getKey());
            }
        }
    }

    /**
     * Gets value of specific field as it was requested at specific date and time in the past.
     * Value is retrieved from changelog (fields property of issue is ignored)
     * Note that changelog may not have information of requested field
     *
     * @param issue        JIRA issue
     * @param fieldName    Defines name of desired field
     * @param date         Defines date for field value calculation
     * @param fieldValueSb Resulting field value (new StringBuilder should be passed)
     * @return Value indicating whether field value was succesfully retrived from changelog
     */
    public static boolean tryGetFieldValueFromChangeLogAsOfDate(Issue issue, String fieldName, DateTime date, StringBuilder fieldValueSb) {
        if (issue == null) throw new IllegalArgumentException("issue parameter is null");
        if (StringUtils.isEmpty(fieldName)) throw new IllegalArgumentException("fieldName parameter is null or empty");
        if (issue.getCreatedDate().isAfter(date)) {
            return false;
        }
        IssueChange issueChange = getAnyNearbyChangeToDesiredDate(issue, fieldName, date);

        if (issueChange != null) {
            IssueChange.ChangeField changedField = issueChange.getChangeFields().stream().filter(s -> fieldName.equals(s.getName())).findFirst().get();

            if (issueChange.getChangeDate().isAfter(date)) {
                fieldValueSb.append(changedField.getPreviousValue());
            } else {
                fieldValueSb.append(changedField.getCurrentValue());
            }
        }

        return issueChange != null;
    }


    protected static IssueChange getAnyNearbyChangeToDesiredDate(Issue issue, String fieldName, DateTime date) {
        // find latest change log entry with the given field occured before the desired date (or equals the desired date)
        IssueChange issueChange = issue.getChangeLog().stream()
                .filter(s -> s.getChangeFields().stream().anyMatch(t -> t.getName().equals(fieldName)))
                .sorted(Comparator.comparing(IssueChange::getChangeDate).reversed())
                .filter(s -> !s.getChangeDate().isAfter(date))
                .findFirst().orElse(null);

        if (issueChange == null) {
            // find first change log entry with the given field occured after the desired date
            issueChange = issue.getChangeLog().stream()
                    .filter(s -> s.getChangeFields().stream().anyMatch(t -> t.getName().equals(fieldName)))
                    .sorted(Comparator.comparing(IssueChange::getChangeDate))
                    .filter(s -> s.getChangeDate().isAfter(date))
                    .findFirst().orElse(null);
        }

        return issueChange;
    }
}
