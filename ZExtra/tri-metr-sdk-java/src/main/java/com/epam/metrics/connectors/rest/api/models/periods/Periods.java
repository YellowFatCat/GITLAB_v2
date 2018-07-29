package com.epam.metrics.connectors.rest.api.models.periods;

import java.util.ArrayList;
import java.util.List;

public class Periods {
    List<Period> periods = new ArrayList<>();

    public List<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }
}