package victor.training.jpa.app.web;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import victor.training.jpa.app.domain.entity.CourseActivity;
import victor.training.jpa.app.domain.entity.LabActivity;
import victor.training.jpa.app.domain.entity.StudentsGroup;
import victor.training.jpa.app.domain.entity.StudentsYear;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.TeachingActivity;
import victor.training.jpa.app.facade.TheFacade;
import victor.training.jpa.app.facade.dto.OrarDto;
import victor.training.jpa.app.repo.StudentsYearRepo;

@RestController
@RequestMapping("/api/timetable")
public class TimeTableController {

	private final static Logger log = LoggerFactory.getLogger(TimeTableController.class);
	
	@Autowired
	private StudentsYearRepo yearRepo;
	@Autowired
	private TheFacade facade;

	@GetMapping("{yearId}")
	public OrarDto buildOrar(@PathVariable long yearId) {
		OrarDto dto = new OrarDto();
		StudentsYear year = yearRepo.getExactlyOne(yearId);
		List<StudentsGroup> yearGroups = year.getGroups();
		dto.yearCode = year.getCode();
		dto.groups = yearGroups.stream().map(StudentsGroup::getCode).collect(toList());

		Set<TeachingActivity> allActivities = facade.getAllActivities(yearId);
		log.debug("All activities {}", allActivities);
		List<DayOfWeek> workingDays = asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
		for (DayOfWeek day : workingDays) {
			TreeMap<Integer, List<TeachingActivity>> activitiesByHour =
					allActivities.stream()
						.filter(a -> a.getDay() == day)
						.collect(groupingBy(TeachingActivity::getStartHour, TreeMap::new, toList()));
			log.debug("For day {} activities by hour: {}", day, activitiesByHour);
			Map<String, List<OrarDto.CellDto>> lines = new TreeMap<>();
			for (int h = 8; h <= 20; h++) {
				List<OrarDto.CellDto> cells = new ArrayList<>();
				while (!activitiesByHour.isEmpty() && activitiesByHour.firstKey() == h) {
					List<TeachingActivity> activitiesOnRow = activitiesByHour.remove(activitiesByHour.firstKey());
					log.debug("Placing  activities at hour {} : {}", h, activitiesOnRow);
					if (activitiesOnRow.size() ==1 && activitiesOnRow.get(0)instanceof CourseActivity) {
						CourseActivity course = (CourseActivity) activitiesOnRow.get(0);
						OrarDto.CellDto cell = new OrarDto.CellDto();
						cell.rowspan = course.getDurationInHours();
						cell.colspan = dto.groups.size();
						cell.label = "Course: " + course.getSubject().getName() + " by " + course.getSubject().getHolderTeacher().getName() +
								" in " + course.getRoomId();
						cells.add(cell);
					} else {
						activitiesOnRow.sort((a1,a2)-> dto.groups.indexOf(((LabActivity)a1).getGroup().getCode()) - dto.groups.indexOf(((LabActivity)a2).getGroup().getCode()));
						int lastIndex = 0;
						for (TeachingActivity activity : activitiesOnRow) {
							LabActivity lab = (LabActivity) activity;
							int thisIndex = dto.groups.indexOf(lab.getGroup().getCode());
							OrarDto.CellDto cell = new OrarDto.CellDto();
							cell.rowspan = lab.getDurationInHours();
							cell.colskip = thisIndex - lastIndex;  
							cell.colspan = 1;
							cell.label = "Lab: " + lab.getSubject().getName() + " by " + lab.getTeachers().stream().map(Teacher::getName).collect(joining(",")) +
									" in " + lab.getRoomId();
							cells.add(cell);
							lastIndex = thisIndex + 1;
						}
					}
				}
				lines.put(String.format("%02d:00", h), cells);
			}
			dto.lines.put(day, lines);
		}
		return dto;
	}
}
