package bigdata.labs.lab5;

public class TestResult {
    private final TestURL test;
    private final Long time;

    public TestResult(TestURL test, Long time) {
        this.test = test;
        this.time = time;
    }

    public TestURL getTest() {
        return test;
    }

    public Long getTime() {
        return time;
    }
}
