package com.epam.metrics.connectors.rest.jira.helpers;

import com.epam.metrics.connectors.rest.jira.models.issues.Issue;
import com.epam.metrics.connectors.rest.jira.models.issues.IssueChange;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.function.Function;

import static com.epam.metrics.connectors.rest.jira.helpers.ChangeLogHelper.getAnyNearbyChangeToDesiredDate;
import static com.epam.metrics.connectors.rest.jira.helpers.ChangeLogHelper.getFieldValueAsOfDate;

@RunWith(SpringRunner.class)
public class ChangeLogHelperTest {

    private final static IssueChange issueChange1 = new IssueChange();
    private final static IssueChange issueChange2 = new IssueChange();
    private final static IssueChange issueChange3 = new IssueChange();

    static {
        issueChange1.setChangeDate(DateTime.parse("2017-01-02"));
        issueChange1.addChangeField("field1", "prev11", "cur11");
        issueChange1.addChangeField("field2", "prev12", "cur12");
        issueChange1.addChangeField("field3", "prev13", "cur13");

        issueChange2.setChangeDate(DateTime.parse("2017-01-03"));
        issueChange2.addChangeField("field1", "prev21", "cur21");
        issueChange2.addChangeField("field2", "prev22", "cur22");
        issueChange2.addChangeField("field3", "prev23", "cur23");

        issueChange3.setChangeDate(DateTime.parse("2017-01-04"));
        issueChange3.addChangeField("field1", "prev31", "cur31");
        issueChange3.addChangeField("field2", "prev32", "cur32");
        issueChange3.addChangeField("field3", "prev33", "cur33");
    }

    private Map getFieldMap(String name) {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        return map;
    }

    private Issue getTestIssue() {
        Issue issue = new Issue();

        issue.setCreatedDate(DateTime.parse("2017-01-01"));

        issue.getFields().put("field1", getFieldMap("FIELD_1 NAME"));
        issue.getFields().put("field2", getFieldMap("FIELD_2 NAME"));
        issue.getFields().put("field3", getFieldMap("FIELD_3 NAME"));
        issue.getFields().put("field4", getFieldMap("HIDDEN FIELD_4 NAME"));

        List<IssueChange> issueChangeList = new ArrayList<>();
        issueChangeList.addAll(Arrays.asList(issueChange1, issueChange2, issueChange3));
        issue.setChangeLog(issueChangeList);

        return issue;
    }

    @Test
    public void testGetAnyNearbyChangeToDesiredDate() {
        Issue issue = getTestIssue();
        Assert.assertEquals(issueChange1, getAnyNearbyChangeToDesiredDate(issue, "field1", DateTime.parse("2017-01-01"))); //after
        Assert.assertEquals(issueChange1, getAnyNearbyChangeToDesiredDate(issue, "field1", DateTime.parse("2017-01-02"))); //before or eq
        Assert.assertEquals(issueChange2, getAnyNearbyChangeToDesiredDate(issue, "field2", DateTime.parse("2017-01-03"))); //before or eq
        Assert.assertEquals(issueChange2, getAnyNearbyChangeToDesiredDate(issue, "field3", DateTime.parse("2017-01-03T17:51:44Z"))); //before or eq
        Assert.assertEquals(null, getAnyNearbyChangeToDesiredDate(issue, "field4", DateTime.parse("2017-01-03T17:51:44Z"))); //field not found
    }

    @Test
    public void testGetFieldValueAsOfDate() {
        Issue issue = getTestIssue();

        Assert.assertEquals(
                "prev11",
                getFieldValueAsOfDate(issue, "field1", DateTime.parse("2017-01-01"), new Function<Map, String>() {
                    @Override
                    public String apply(Map map) {
                        return (String) map.get("name");
                    }
                })
        );

        Assert.assertEquals(
                "cur11",
                getFieldValueAsOfDate(issue, "field1", DateTime.parse("2017-01-02"), new Function<Map, String>() {
                    @Override
                    public String apply(Map map) {
                        return (String) map.get("name");
                    }
                })
        );

        Assert.assertEquals(
                "cur22",
                getFieldValueAsOfDate(issue, "field2", DateTime.parse("2017-01-03"), new Function<Map, String>() {
                    @Override
                    public String apply(Map map) {
                        return (String) map.get("name");
                    }
                })
        );

        // field4 has no change dates, but have somehing in fields map (extracted via function)
        Assert.assertEquals(
                "HIDDEN FIELD_4 NAME",
                getFieldValueAsOfDate(issue, "field4", DateTime.parse("2017-01-03T17:51:44Z"), new Function<Map, String>() {
                    @Override
                    public String apply(Map map) {
                        return (String) map.get("name");
                    }
                })
        );
    }
}
