
public class Process {
		String process_id;
		int Arrival_time;
		int Burst_time;
		int Completion_time;
		int Waiting_time;
		int Turn_around;
		int priority;
		
		public int getPriority() {
			return priority;
		}
		public void setPriority(int priority) {
			this.priority = priority;
		}
		public String getProcess_id() {
			return process_id;
		}
		public void setProcess_id(String process_id) {
			this.process_id = process_id;
		}
		public int getArrival_time() {
			return Arrival_time;
		}
		public void setArrival_time(int arrival_time) {
			Arrival_time = arrival_time;
		}
		public int getBurst_time() {
			return Burst_time;
		}
		public void setBurst_time(int burst_time) {
			Burst_time = burst_time;
		}
		public int getCompletion_time() {
			return Completion_time;
		}
		public void setCompletion_time(int completion_time) {
			Completion_time = completion_time;
			Turn_around = Completion_time - Arrival_time;
			Waiting_time = Turn_around - Burst_time;
		}
		public int getWaiting_time() {
			return Waiting_time;
		}
		public void setWaiting_time(int waiting_time) {
			Waiting_time = waiting_time;
		}
		public int getTurn_around() {
			return Turn_around;
		}
		public void setTurn_around(int turn_around) {
			Turn_around = turn_around;
		}
		
		
		public String toString(){
			String process_id = String.format("%" + 2 + "s", getProcess_id());
			String process_burst = String.format("%" + 5 + "s", getBurst_time());
			String process_arrival = String.format("%" + 5 + "s", getArrival_time());
			String process_completion = String.format("%" + 5 + "s", getCompletion_time());
			String process_turn = String.format("%" + 5 + "s", getTurn_around());
			String process_wait = String.format("%" + 5 + "s", getWaiting_time());

			return(process_id + process_arrival + process_burst + process_completion + process_wait + process_turn);
		}
}
