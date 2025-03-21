import java.util.List;
import java.util.ArrayList;

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

class MemoryManager {
    List<MemoryBlock> memory;

    public MemoryManager(int totalMemorySize) {
        memory = new ArrayList<>();
        memory.add(new MemoryBlock(0, totalMemorySize));
    }

    public boolean allocate(Process p, int sizeRequired) {
        for (MemoryBlock block : memory) {
            if (block.isFree && block.size >= sizeRequired) {
                block.isFree = false;
                p.memoryStartAddress = block.startAddress;
                System.out.println("Allocated Process " + p.pid + " to memory block at " + block.startAddress);
                return true;
            }
        }
        System.out.println("Failed to allocate memory for Process " + p.pid);
        return false;
    }

    public void free(Process p) {
        for (MemoryBlock block : memory) {
            if (block.startAddress == p.memoryStartAddress) {
                block.isFree = true;
                System.out.println("Freed memory block at " + block.startAddress + " for Process " + p.pid);
                break;
            }
        }
    }
}
