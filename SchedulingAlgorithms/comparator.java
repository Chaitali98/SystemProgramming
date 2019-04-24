import java.util.Comparator;

public class comparator implements Comparator<Process> { 
    public int compare(Process p1, Process p2) 
    { 
        return p1.getArrival_time() - p2.getArrival_time();
    } 
} 