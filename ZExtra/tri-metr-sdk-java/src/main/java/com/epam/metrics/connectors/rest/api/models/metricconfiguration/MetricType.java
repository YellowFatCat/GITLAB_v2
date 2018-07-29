package com.epam.metrics.connectors.rest.api.models.metricconfiguration;

/**
 * Defines type of mentric.
 */
public enum MetricType
{
    /// <summary>
    /// Identifies Velocity metric.
    /// </summary>
    Velocity,

    /// <summary>
    /// Identifies Spent vs Estimate metric.
    /// </summary>
    SpentVsEstimate,

    /// <summary>
    /// Identifies Delivered vs Planned metric.
    /// </summary>
    DeliveredVsPlanned,

    /// <summary>
    /// Identifies Defect Containment metric.
    /// </summary>
    DefectContainment,

    /// <summary>
    /// Identifies Average Time Spent metric.
    /// </summary>
    AverageTimeSpentOnWorkItem,

    /// <summary>
    /// Identifies Test Automation Coverage metric.
    /// </summary>
    TestAutomationCoverage,

    /// <summary>
    /// Identifies Seniority Index metric.
    /// </summary>
    SeniorityIndex,

    /// <summary>
    /// Identifies Percent of Bugs of First Level Priority metric.
    /// </summary>
    PercentOfBugsOfFirstLevelPriority,

    /// <summary>
    /// Identifies Percent of Bugs of Second Level Priority metric.
    /// </summary>
    PercentOfBugsOfSecondLevelPriority,

    /// <summary>
    /// Identifies pass Ratio metric.
    /// </summary>
    PassRatio,

    /// <summary>
    /// Identifies unit test coverage metric
    /// </summary>
    UnitTestCoverage,

    /// <summary>
    /// Identifies Delivered vs Planned cumulative metric.
    /// </summary>
    DeliveredVsPlannedCumulative,

    /// <summary>
    /// Identifies Delivered vs Planned in items.
    /// </summary>
    DeliveredVsPlannedInItems,

    /// <summary>
    /// Identifies Delivered vs Planned in epics cumulative metric.
    /// </summary>
    DeliveredVsPlannedInItemsCumulative,

    /// <summary>
    /// Identifies Spent vs Estimate cumulative metric.
    /// </summary>
    SpentVsEstimateCumulative,

    /// <summary>
    /// Identifies Original Estimate vs Zero Level Estimate metric.
    /// </summary>SpentVsEstimateCumulative,
    OriginalEstimateVsZeroLevelEstimate,

    /// <summary>
    /// Identifies Original Estimate vs Zero Level Estimate cumulative metric.
    /// </summary>
    OriginalEstimateVsZeroLevelEstimateCumulative,

    /// <summary>
    /// Identifies Hydration Index metric.
    /// </summary>
    HydrationIndex,

    /// <summary>
    /// Identifies Hydration index for next sprint metric.
    /// </summary>
    HydrationPercentNextSprint,

    /// <summary>
    /// Identifies Hydration index for next + 1 sprint metric.
    /// </summary>
    HydrationPercentNextSecondSprint,

    /// <summary>
    /// Identifies Hydration index for next + 2 sprint metric.
    /// </summary>
    HydrationPercentNextThirdSprint,

    /// <summary>
    /// Identifies Percent of Bugs of First Level Priority cumulative metric.
    /// </summary>
    PercentOfBugsOfFirstLevelPriorityCumulative,

    /// <summary>
    /// Identifies Percent of Bugs of Second Level Priority cumulative metric.
    /// </summary>
    PercentOfBugsOfSecondLevelPriorityCumulative,

    /// <summary>
    /// Identifies Average Velocity metric.
    /// </summary>
    AverageVelocity,

    /// <summary>
    /// Identifies Velocity in items (epics,stories,etc.) metric.
    /// </summary>
    VelocityInItems,

    /// <summary>
    /// Identifies Average Velocity in items (epics,stories,etc.) metric.
    /// </summary>
    AverageVelocityInItems,

    /// <summary>
    /// Identifies Total Release scope metric.
    /// </summary>
    TotalReleaseScope,

    /// <summary>
    /// Identifies Total Release scope in items (epics,stories,etc.) metric.
    /// </summary>
    TotalReleaseScopeInItems,

    /// <summary>
    /// Identifies delivered unscheduled metric.
    /// </summary>
    DeliveredUnscheduledCumulative,

    /// <summary>
    /// Identifies delivered scheduled metric.
    /// </summary>
    DeliveredScheduledCumulative,

    /// <summary>
    /// Identifies delivered unscheduled in items metric.
    /// </summary>
    DeliveredUnscheduledInItemsCumulative,

    /// <summary>
    /// Identifies delivered scheduled in items metric.
    /// </summary>
    DeliveredScheduledInItemsCumulative,

    /// <summary>
    /// Identifies planned cumulative metric.
    /// </summary>
    PlannedCumulative,

    /// <summary>
    /// Identifies planned in items cumulative metric.
    /// </summary>
    PlannedInItemsCumulative
}
