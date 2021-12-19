package bigdata.labs.lab5;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class TestPackage {
    private final String packageId;
    private final String functionName;
    private final String jsScript;
    private final ArrayList<TestMessage> tests;

    public TestPackage(@JsonProperty("packageId") String packageId,
                       @JsonProperty("functionName") String functionName,
                       @JsonProperty("jsScript") String jsScript,
                       @JsonProperty("tests") ArrayList<TestMessage> tests) {
        this.packageId = packageId;
        this.functionName = functionName;
        this.jsScript = jsScript;
        this.tests = tests;
    }

    public String getPackageId() {
        return this.packageId;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public String getJsScript() {
        return this.jsScript;
    }

    public ArrayList<TestMessage> getTests() {
        return this.tests;
    }
}
