import java.io.BufferedReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class FCFS {
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
			 
			/*
		Process p1 = processes.remove();
		System.out.println(p1.getProcess_id());
		Process p2 = processes.remove();
		comparator c = new comparator();
		System.out.println(c.compare(p1, p2));
		
		*/
	}
	
	public static void main(String args[]){
		
		FCFS f = new FCFS();
		f.accept();
		f.disp();
		f.schedule(); 
	}

	private void schedule() {
		// TODO Auto-generated method stub
		float tot = 0;
		float wt = 0;
		int n = 0;
		int completion_time = -1;
		
		System.out.println("ID  | ARVL|BRST|CMPT|WAIT|TURN");
		while (!processes.isEmpty()) { 
			//System.out.println(processes.poll().getArrival_time());
			
			Process p = processes.poll();
			if(completion_time == -1){
				completion_time = p.getBurst_time() + p.getArrival_time();
				p.setCompletion_time(completion_time);
			}
			else{
				if(p.Arrival_time < completion_time)
					completion_time += p.Burst_time;
					
				else 
						completion_time = (p.getBurst_time() + p.getArrival_time());
				p.setCompletion_time(completion_time);
			}
			//p.setWaiting_time(p.getCompletion_time() - p.getArrival_time());
			System.out.println(p.toString());
			tot += p.getTurn_around();
			wt += p.getWaiting_time();
			n++;
		}
		
		System.out.println("Total turn around time = " + tot + " \t Total waiting time = " + wt + "total processes = " + n);
		System.out.println("average turn around = " + (tot/n) + "\t average waiting time = " + (wt/n));
	}
}
