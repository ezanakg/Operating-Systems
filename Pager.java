import java.util.Objects;
import java.util.Queue;
import java.util.LinkedList;

class Page {
    int processId;
    int pageNumber;

    public Page(int processId, int pageNumber) {
        this.processId = processId;
        this.pageNumber = pageNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Page other) {
            return this.processId == other.processId && this.pageNumber == other.pageNumber;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(processId, pageNumber);
    }
}

class PageTable {
    Queue<Page> frames = new LinkedList<>();
    int capacity;

    public PageTable(int capacity) {
        this.capacity = capacity;
    }

    public void accessPage(Page page) {
        if (frames.contains(page)) {
            System.out.println("Page hit: " + page.processId + "-" + page.pageNumber);
        } else {
            if (frames.size() == capacity) {
                Page evicted = frames.poll();
                System.out.println("Page replaced (FIFO): " + evicted.processId + "-" + evicted.pageNumber);
            }
            frames.add(page);
            System.out.println("Page loaded: " + page.processId + "-" + page.pageNumber);
        }
    }
}