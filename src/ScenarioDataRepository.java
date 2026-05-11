public class ScenarioDataRepository {
    public static Scenario createDefaultScenario() {
        Scenario activeScenario = new Scenario("Scenario C - Team Alpha");

        Dimension usability = new Dimension("Usability", 25);
        usability.addMetric(new Metric("SUS score", 50, "Higher", 0, 100, "points", 89));
        usability.addMetric(new Metric("Onboarding time", 50, "Lower", 0, 60, "min", 5));

        Dimension performance = new Dimension("Perf. Efficiency", 20);
        performance.addMetric(new Metric("Video start time", 50, "Lower", 0, 15, "sec", 3));
        performance.addMetric(new Metric("Concurrent exams", 50, "Higher", 0, 600, "users", 450));

        Dimension reliability = new Dimension("Reliability", 20);
        reliability.addMetric(new Metric("Uptime", 50, "Higher", 95, 100, "%", 99));
        reliability.addMetric(new Metric("MTTR", 50, "Lower", 0, 120, "min", 30));

        activeScenario.addDimension(usability);
        activeScenario.addDimension(performance);
        activeScenario.addDimension(reliability);

        return activeScenario;
    }
}