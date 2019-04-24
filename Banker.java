import java.util.Scanner;


public class Banker {
	int resources_no;
	int processes;
	
	int max[][];
	int available[];
	int need[][];
	int allocated[][];
	
	Scanner sc = new Scanner(System.in);
	public Banker(){
		System.out.println("Enter no. of resources in environment");
		resources_no = sc.nextInt();
		System.out.println("Enter instances of these rescources at the begining:");
		
		available = new int[resources_no];
		for(int i=0 ; i<resources_no ; i++){
			available[i] = sc.nextInt();
		}
		
		System.out.println("Enter no. of processes in environment");
		processes = sc.nextInt();
		System.out.println("Enter maximum instances of these rescources needed by these processes at the begining:");
		max = new int[processes][resources_no];
		allocated = new int[processes][resources_no];
		need = new int[processes][resources_no];
		
		for(int j=0 ; j<processes ; j++){
			System.out.println("Enter max for process " + j);
			for(int i=0 ; i<resources_no ; i++){
				max[j][i] = sc.nextInt();
			}
		}
		
		System.out.println("Enter allocated instances of rescources needed by these processes at the begining:");
		for(int j=0 ; j<processes ; j++){
			System.out.println("Enter allocated for process " + j);
			for(int i=0 ; i<resources_no ; i++){
				allocated[j][i] = sc.nextInt();
				available[i] -= allocated[j][i];
			}
		}
		
		for(int j=0 ; j<processes ; j++){
			for(int i=0 ; i<resources_no ; i++){
				need[j][i] = max[j][i] - allocated[j][i];
			}
		}
		
		
		
		//printing all matrices
		System.out.println("Max matrix");
		for(int[] i : max){
			for(int j : i){
				System.out.print("\t" + j);
			}
			System.out.println("");
		}
		
		System.out.println("Allocated matrix");
		for(int[] i : allocated){
			for(int j : i){
				System.out.print("\t" + j);
			}
			System.out.println("");
		}
		
		System.out.println("Need matrix");
		for(int[] i : need){
			for(int j : i){
				System.out.print("\t" + j);
			}
			System.out.println("");
		}
		System.out.println("available matrix");
		for(int i: available)
			System.out.print("\t" +i);
		System.out.println("");
		
	}
	public void clear(int[] mat){
		for(int i=0 ; i<mat.length ; i++)
			mat[i] = 0;
	}
	public void process_request(){
		int[] request = new int[resources_no];
		int process_id;
		boolean next = false;
		boolean safe = isSafe(new int[resources_no], 0);
		if(!safe)
			{
				System.out.println("System is initially is in unsecure state...cannot procede forward");
				return;
			}
		
		do{
			clear(request);
			System.out.println("Enter process id of requesting process:");
			process_id = sc.nextInt();
			System.out.println("Enter request array:");
			for(int i=0 ; i<resources_no ; i++)
				request[i]= sc.nextInt();
			
			System.out.println("available matrix before function call is:");
			show(available);
			System.out.println("");
			if(allocate(request, process_id) && !isSafe(request, process_id))
			{
				System.out.println("This request takes the system in uncertain stage. Hence cannot execute this request");
				deallocate(request, process_id);
			}else{
				//System.out.println("Request accepted:\nAllocated matrix is");
				
				for(int[] i : allocated){
					for(int j : i){
						System.out.print("\t" + j);
					}
					System.out.println("");
				}
			}
			
			System.out.println("is there another request?(true/false)");
			next = sc.nextBoolean();
		}while(next);
		
	}
	private boolean allocate(int[] request , int process){
		for(int i=0 ; i<resources_no ; i++){
			if(need[process][i] < request[i] || request[i] > available[i]){
				System.out.println("process asking for more rescources than needed");
				return false;
			}
		}
		for(int i=0 ; i<resources_no ; i++){
			if(request[i] > available[i]){
				System.out.println("process asking for more rescources than available");
				return false;
			}
		}
		
		
		for(int i=0 ; i<resources_no ; i++){
			allocated[process][i] += request[i];
			need[process][i] -= request[i];
			available[i] -= request[i];
		}
		
		return true;
	}
	
	private void deallocate(int[] request , int process){
		
		for(int i=0 ; i<resources_no ; i++){
			allocated[process][i] -= request[i];
			need[process][i] += request[i];
			available[i] += request[i];
		}
	}
	private boolean isSafe(int[] request , int process){
		int completed = 0;
		boolean comp[] = new boolean[processes];
		int passes = 0;
		int j=0;
		int work[]= new int[resources_no];
		for(int i = 0; i<available.length ; i++){
			work[i] = available[i];
		}
		System.out.println("available matrix was:");
		for(int i : available)
			System.out.print("\t" + i);
		
		boolean scheduled = false;
		
		while(completed < processes){
			if(comp[j] == false){
				boolean flag = true;
				for(int i=0 ; i<resources_no ; i++){
					if(need[j][i] > work[i])
						flag = false;
				}
				if(flag){
					System.out.println("work matrix was:");
					for(int i : work)
						System.out.print("\t" + i);
					System.out.println("\n");
					System.out.println("\tprocess " + j + " scheduled");
					scheduled = true;
					completed++;
					comp[j] = true;
					for(int i=0 ; i<resources_no ; i++){
						work[i] += allocated[j][i];
					}
					System.out.println("work matrix");
					show(work);
				}
			}
			j++;
			if(j==processes){
				if(!scheduled)
					return false;
				j =0;
				passes++;
				scheduled = false;
			}
		}
		
		return true;
	}
	
	private void show(int[] work){
		for(int i=0 ; i<work.length ; i++){
			System.out.print("\t " + work[i]);
		}
		System.out.println("");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Banker b = new Banker();
		b.process_request();

	}

}
