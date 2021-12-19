package bigdata.labs.lab5;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class RequestAnswers {
    private String packageId;
    private ArrayList<TestResult> results;

    @JsonCreator
    public RequestAnswers(@JsonProperty("packageId") String packageId,
                          @JsonProperty("tests") ArrayList<TestResult> results) {
        this.packageId = packageId;
        this.results = results;
    }

    public ArrayList<TestResult> getResults() {
        return results;
    }

    public String getPackageId() {
        return packageId;
    }
}
