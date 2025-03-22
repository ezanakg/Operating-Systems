import java.util.List;
import java.util.ArrayList;

/**
 *  This code represents a block of memory in a memory management system 
 */

class MemoryBlock {
    int startAddress;
    int size;
    boolean isFree;

    public MemoryBlock(int startAddress, int size) {
        this.startAddress = startAddress;
        this.size = size;
        this.isFree = true;
    }
}

// This class is used for management of memory allocation and deallocation for the processes.
class MemoryManager {
    List<MemoryBlock> memory;

    public MemoryManager(int totalMemorySize) {
        memory = new ArrayList<>();
        memory.add(new MemoryBlock(0, totalMemorySize));
    }
        /**
         * This function allocates memory for a process when a suitable free block is available
         */
            
        */
    public boolean allocate(Process p, int sizeRequired) {
        for (MemoryBlock block : memory) {
            if (block.isFree && block.size >= sizeRequired) {
                block.isFree = false; // This marks block as allocated
                p.memoryStartAddress = block.startAddress;
                System.out.println("Allocated Process " + p.pid + " to memory block at " + block.startAddress);
                return true;
            }
        }
        System.out.println("Failed to allocate memory for Process " + p.pid); // Prints when the allocation fails
        return false; 
    }
    // Frees memory block as freed
    public void free(Process p) {
        for (MemoryBlock block : memory) {
            if (block.startAddress == p.memoryStartAddress) {
                block.isFree = true; // Marks the block as free
                System.out.println("Freed memory block at " + block.startAddress + " for Process " + p.pid);
                break;
            }
        }
    }
}
