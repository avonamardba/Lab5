package bigdata.labs.lab5;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TestMessage {
    private final String testName;
    private String actualResult;
    private final String expectedResult;
    private final Object[] params;
    private TestPackage Parent;

    @JsonCreator
    public TestMessage(@JsonProperty("testName") String testName,
                       @JsonProperty("expectedResult") String expectedResult,
                       @JsonProperty("params") Object[] params) {
        this.testName = testName;
        this.expectedResult = expectedResult;
        this.params = params;
    }

    public void setActualResult(String actualResult) {
        this.actualResult = actualResult;
    }

    public void setParent(TestPackage parent) {
        this.Parent = parent;
    }

    public String getTestName() {
        return this.testName;
    }

    public String getActualResult() {
        return this.actualResult;
    }

    public String getExpectedResult() {
        return this.expectedResult;
    }

    public Object[] getParams() {
        return this.params;
    }

    public TestPackage getParent() {
        return this.Parent;
    }

}
