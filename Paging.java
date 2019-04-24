import java.util.Scanner;

public class Paging {
	int frames;
	int stringsize;
	int[] inputstring = new int[30];
	Scanner sc = new Scanner(System.in);
	int time[] = new int[5];
	int frame[] = new int[5];
	public Paging() {
		System.out.println("Enter frame size");
		frames = sc.nextInt();
		System.out.println("Enter stringsize");
		stringsize = sc.nextInt();
		System.out.println("Enter the string");
		int index = 0;
		for(int i=0 ; i<stringsize ; i++) {
			inputstring[i] = sc.nextInt();
		}
		for(int i=0 ; i<frames; i++)
			frame[i] = -1;
	}
	public void update_age() {
		for(int i=0 ; i<frames; i++) {
			time[i] += 1;
		}
	}
	public void show() {
		for(int i=0 ; i<frames ; i++)
			System.out.print("  "+frame[i] + "(" + time[i] + ")");
		System.out.println("s");
	}
	
	int find(int f) {
		//check if present
		for(int i=0 ; i<frames;i++)
			if(frame[i] == f) {
				//hit_ratio++;
				return i;
			}
		
		//check for empty space
		for(int i=0 ; i<frames;i++) {
			if(frame[i] == -1)
				return i;
		}
		
		//oldest return
		int max = Integer.MIN_VALUE;
		int index = 0;
		for(int i=0 ; i<frames ; i++) {
			if(max < time[i]) {
				max = time[i];
				index = i;
			}
		}
		return index;
	}
	public void FIFO() {
		clear();
		int index;
		float hit_ratio = 0;
		for(int i=0 ; i<stringsize ; i++) {
			index = find(inputstring[i]);
			if(frame[index] != inputstring[i]) {
				frame[index] = inputstring[i];
				time[index] = 0;
			}else {
				hit_ratio++;
			}
			update_age();
			System.out.print("char:" + inputstring[i] + " ");
			show();
		}
		
		System.out.println("hit ratio = " + hit_ratio/stringsize);
	}
	public void clear() {
		for(int i=0 ; i<frames ; i++) {
			frame[i] = -1;
			time[i] = 0;
		}
	}
	public void LRU() {
		clear();
		int index;
		float hit_ratio= 0;
		for(int i=0 ; i<stringsize ; i++) {
			index = find(inputstring[i]);
			if(frame[index] != inputstring[i]) {
				frame[index] = inputstring[i];
				time[index] = 0;
			}else {
				hit_ratio++;
				time[index] = 0;
			}
			update_age();
			System.out.print("char:" + inputstring[i] + " ");
			show();
		}
		
		System.out.println("hit ratio = " + hit_ratio/stringsize);
	}
	
	public void OPT() {
		clear();
		//use time[] for next use index;
		int hit_counter = 0;
		
		for(int i=0 ; i<stringsize ; i++) {
			//empty and present earlier
			boolean found = false;
			for(int j=0 ; j<frames ; j++) {
				if(frame[j] == inputstring[i]  ) {
					hit_counter++;
					found = true;
					break;
				}else if(frame[j] == -1) {
					frame[j] = inputstring[i];
					found = true;
					break;
				}
			}
			if(!found) {
				int index = -1;
				int next_val = Integer.MIN_VALUE;
				for(int j=0 ; j<frames ; j++) {
					boolean flag = false;
					for(int k= i+1 ; k<stringsize ; k++) {
						if(frame[j] == inputstring[k]) {
							System.out.println("value of k = " + k + "for char " + frame[j] + " , next_val = " + next_val);
							if(next_val < k) {
								System.out.println("inside if  block");
								next_val = k;
								index = j;
								
							}	
							flag = true;
							break;
						}
					}
					if(!flag) {
						index = j;
						break;
					}
				}
				if(index >= 0 && index < frames)
				frame[index] = inputstring[i];
				else
					System.out.println("exception");
				
			}
			
			System.out.print("char:" + inputstring[i] + " ");
			show();
		}
		
		System.out.println("No. of hits = " + hit_counter);
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Paging p = new Paging();
		//System.out.println("FIFO Replacement");
		//p.FIFO();
		
		//System.out.println("LRU Replacement");
		//p.LRU();
		
		System.out.println("OPT Replacement");
		p.OPT();
	}

}
