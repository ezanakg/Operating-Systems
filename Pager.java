import java.util.Objects;
import java.util.Queue;
import java.util.LinkedList;

// Class representing a page belonging to a process
class Page {
    int processId;     // ID of the process to which the page belongs
    int pageNumber;    // The page number within the process

    // Constructor to initialize a page with process ID and page number
    public Page(int processId, int pageNumber) {
        this.processId = processId;
        this.pageNumber = pageNumber;
    }

    // Override equals to compare two pages based on processId and pageNumber
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Page other) {
            return this.processId == other.processId && this.pageNumber == other.pageNumber;
        }
        return false;
    }

    // Override hashCode to ensure that equal pages have the same hash
    @Override
    public int hashCode() {
        return Objects.hash(processId, pageNumber);
    }
}

// Class representing a page table that uses FIFO (First-In, First-Out) page replacement
class PageTable {
    Queue<Page> frames = new LinkedList<>();  // Queue to store pages in memory (FIFO order)
    int capacity;  // Maximum number of pages the memory can hold

    // Constructor to set the capacity of the page table
    public PageTable(int capacity) {
        this.capacity = capacity;
    }

    // Method to access a page in memory
    public void accessPage(Page page) {
        // Check if the page is already in memory
        if (frames.contains(page)) {
            System.out.println("Page hit: " + page.processId + "-" + page.pageNumber);
        } else {
            // If memory is full, remove the oldest page (FIFO replacement)
            if (frames.size() == capacity) {
                Page evicted = frames.poll(); // Remove the oldest page
                System.out.println("Page replaced (FIFO): " + evicted.processId + "-" + evicted.pageNumber);
            }
            // Add the new page to memory
            frames.add(page);
            System.out.println("Page loaded: " + page.processId + "-" + page.pageNumber);
        }
    }
}
