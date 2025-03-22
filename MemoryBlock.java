class MemoryBlock {
    int startAddress; // Starting address for the memory block
    int size; // Size of memory block in bytes
    boolean isFree; // Boolean flag that checks if the block is free (meaning true) or allocated (meaning false)

    // Public constructor that initializes the memory block with a given address and size
    public MemoryBlock(int startAddress, int size) {
        this.startAddress = startAddress;
        this.size = size;
        this.isFree = true; //Memory block is starting intially free
    }
}
