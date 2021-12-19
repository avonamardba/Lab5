package bigdata.labs.lab5;

public class TestURL implements Comparable<TestURL>{
    private final String url;
    private final int count;

    public TestURL(String url, int count) {
        this.url = url;
        this.count = count;
    }

    public String getUrl() {
        return url;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(TestURL testURL) {
        return 0;
    }
}
