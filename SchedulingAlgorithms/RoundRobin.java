import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;


public class RoundRobin {
	private PriorityQueue<Process> processes = new PriorityQueue<Process>(new comparator());
	private Scanner sc = new Scanner(System.in);
	
	public void accept(){
		int add_next = 1;
		
		while(add_next == 1){
			Process p = new Process();
			System.out.println("Enter Process id");
			String process_id = sc.nextLine();
			p.setProcess_id(process_id);
			
			System.out.println("Enter Arrival time");
			int Arrival_time = sc.nextInt();
			p.setArrival_time(Arrival_time);
			

			System.out.println("Enter Burst time");
			int Burst_time = sc.nextInt();
			p.setBurst_time(Burst_time);
		
			processes.add(p);
			
			System.out.println("Do you wish to add another process?");
			add_next = sc.nextInt();
			sc = new Scanner(System.in);
		}
		
	}
	
	public void disp(){
		for (Process p: processes){
			System.out.println("ID: " + p.getProcess_id() + "\t| \tArrival Time:" + p.getArrival_time() + "\t| \tBurst Time:" + p.getBurst_time());
		}
			
			System.out.println("");
			 
	}
	
	public void schedule(){
		int interval_size= 4;
		int completed = 0;
		int interval = 1;
		
		int waiting_time = 0;
		Queue<Process> curprocesses = new LinkedList<Process>();
		Process p = processes.poll();
		
		while(completed< processes.size()){
		
			try{
				System.out.println("arrival=" + p.Arrival_time + "interval_size*interval" + interval_size*interval  );
			while(!processes.isEmpty() && p.Arrival_time < interval_size*interval){
				curprocesses.add(p);
				p = processes.poll();
			}
			}
			catch(Exception e){
				System.out.println("exception");
			}
			Process curprocess = curprocesses.remove();
			
			if(curprocess.Burst_time - interval_size < 0){
				completed++;
				
			}else{
				curprocess.Burst_time -= interval_size;
				curprocess.Waiting_time += waiting_time;
				waiting_time += interval_size;
				curprocesses.add(curprocess);
			}
			System.out.println("iteration: " + interval+" process: " + curprocess.process_id );
			interval++;
		}
	
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RoundRobin r = new RoundRobin();
		r.accept();
		r.schedule();

	}

}
