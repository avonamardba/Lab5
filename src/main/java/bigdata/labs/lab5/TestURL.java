package bigdata.labs.lab5;

public class TestURL implements Comparable<TestURL> {
    private final String url;
    private final Integer count;

    public TestURL(String url, Integer count) {
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
        if (this.url.compareTo(testURL.url) != 0) {
            return this.url.compareTo(testURL.url);
        }
        return this.count.compareTo(testURL.count);
    }
}