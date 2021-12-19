package bigdata.labs.lab4;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TestResult {
    private final String actualResult;
    private final String expectedResult;
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
