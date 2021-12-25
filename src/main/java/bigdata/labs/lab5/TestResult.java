package bigdata.labs.lab5;

import java.util.Optional;

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

    public Optional<TestResult> get() {
        if (this.getTime() != null) {
            return Optional.of(this);
        }
        return Optional.empty();
    }
}
