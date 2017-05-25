package victor.training.jpa.entity;

public class EmployeeExportVO {

	private Integer employeeId;
	private String employeeName;
	private String siteName;
	
	public EmployeeExportVO(Integer employeeId, String employeeName, String siteName) {
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.siteName = siteName;
	}
	public final Integer getEmployeeId() {
		return employeeId;
	}
	public final void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public final String getEmployeeName() {
		return employeeName;
	}
	public final void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public final String getSiteName() {
		return siteName;
	}
	public final void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	
}
