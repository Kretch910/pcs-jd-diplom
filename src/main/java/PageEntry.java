public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    @Override
    public int compareTo(PageEntry pageEntry) {
        return Long.compare(this.count, pageEntry.count);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("В файле ");
        sb.append(pdfName);
        sb.append("\n");
        sb.append("На странице № ");
        sb.append(page);
        sb.append("\n");
        sb.append("Встеречается: ");
        sb.append(count);
        sb.append(" раз");
        sb.append("\n");
        return sb.toString();
    }
}