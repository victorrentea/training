package victor.training.java8.voxxed.jobs;

public class JobResults {

	public enum Status {
		FAILED, SUCCESS
	}
	
	private Status status;
	private String errorMessage;
	
	private JobResults() {
	}
	
	public static JobResults error(Exception e) {
		JobResults results = new JobResults();
		results.errorMessage = e.getMessage();
		results.status = Status.FAILED;
		return results;
	}
	
	public static JobResults success() {
		JobResults results = new JobResults();
		results.status = Status.SUCCESS;
		return results;
	}

	@Override
	public String toString() {
		return "JobResults:"+status+":" + errorMessage;
	}
}
