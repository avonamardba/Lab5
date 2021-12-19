package bigdata.labs.lab5;

public class TestResult {
    private final TestURL test;
    private final Long expectedResult;
    private final Boolean success;

    @JsonCreator
    public TestResult(@JsonProperty("expectedResult") String expectedResult,
                      @JsonProperty("actualResult") String actualResult,
                      @JsonProperty("success") Boolean success) {
        this.expectedResult = expectedResult;
        this.actualResult = actualResult;
        this.success = success;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public String getActualResult() {
        return actualResult;
    }

    public Boolean getSuccess() {
        return success;
    }
}
