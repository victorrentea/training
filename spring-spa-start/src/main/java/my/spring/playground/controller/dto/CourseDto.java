package my.spring.playground.controller.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CourseDto {
	public Long id;
	public String name;
	public Long teacherId;
	public String teacherName;
	public String startDate;
	public String description;
}
