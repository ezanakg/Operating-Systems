// Represents a process in a scheduling or memory management system
public class Process {
    int pid;  // Process ID
    int arrivalTime;  // Time the process arrives
    int burstTime;  // Time required for execution
    int priority;  // Process priority level
    int waitingTime = 0;  // Time spent waiting in the queue
    int turnaroundTime = 0;  // Total time from arrival to completion
    int memoryStartAddress = -1;  // Assigned memory address (-1 if not assigned)

    // Constructor to initialize a process
    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}
