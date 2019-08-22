package victor.training.jpa.app.facade;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import victor.training.jpa.app.domain.entity.ContactChannel;
import victor.training.jpa.app.domain.entity.CourseActivity;
import victor.training.jpa.app.domain.entity.LabActivity;
import victor.training.jpa.app.domain.entity.StudentsGroup;
import victor.training.jpa.app.domain.entity.StudentsYear;
import victor.training.jpa.app.domain.entity.Subject;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.TeacherDetails;
import victor.training.jpa.app.domain.entity.TeachingActivity;
import victor.training.jpa.app.facade.dto.ContactChannelDto;
import victor.training.jpa.app.facade.dto.StudentsGroupDto;
import victor.training.jpa.app.facade.dto.SubjectDto;
import victor.training.jpa.app.facade.dto.SubjectWithActivitiesDto;
import victor.training.jpa.app.facade.dto.TeacherDetailsDto;
import victor.training.jpa.app.facade.dto.TimeSlotDto;
import victor.training.jpa.app.facade.dto.YearWithGroupsDto;
import victor.training.jpa.app.util.MyUtil;

@Service
@Transactional
public class TheFacade {
	private final static Logger log = LoggerFactory.getLogger(TheFacade.class);

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private AnotherService anotherService;
	
	@Autowired
	private NonTransactedService nonTransactedService;
	
	
	// 1. persist. when IDs are assigned?
	// 2. link existing entity from DB. check != null
	// 3. getReference
	public Long createSubject(SubjectDto subjectDto) {
		Subject subject = new Subject();
		subject.setName(subjectDto.name);
		subject.setHolderTeacher(em.find(Teacher.class, subjectDto.holderTeacherId));
		log.debug("ID before persist: " + subject.getId());
		em.persist(subject);
		return subject.getId();
	}
	
	// Object references a transient object.  Cascade or .persist ?
	public Long createTeacher(TeacherDetailsDto teacherDto) {
		Teacher teacher = new Teacher();
		teacher.setName(teacherDto.name);
		teacher.setGrade(teacherDto.grade);
		TeacherDetails details = new TeacherDetails();
		details.setCv(teacherDto.cv);
		teacher.setDetails(details);
		em.persist(teacher);
		return teacher.getId();
	}
	
	// 1. auto flush at Tx end 
	// 2. EntityManager = 1st level cache (see logged SQLs). return the Subject from checkPermissions and compare them with ==
	// 3. If not Transaction -> no persist
	// 4. lock (PERSSIMISTIC_WRITE for SELECT_FOR_UPDATE
	public void updateSubject(SubjectDto subjectDto) {
		anotherService.checkPermissionsOnSubject(subjectDto.id);
		Subject subject = em.find(Subject.class, subjectDto.id);
		em.lock(subject, LockModeType.PESSIMISTIC_WRITE); // SELECT FOR UPDATE
		subject.setName(subjectDto.name);
		subject.setHolderTeacher(em.find(Teacher.class, subjectDto.holderTeacherId));
	}
	
	// Remember to set OWNER side (not mappedBy side) of a relation!
	public long addLab(long subjectId, TimeSlotDto timeSlotDto) {
		Subject subject = em.find(Subject.class, subjectId);
		LabActivity lab = new LabActivity();
		lab.setDay(timeSlotDto.day);
		lab.setDurationInHours(timeSlotDto.durationInHours);
		lab.setStartHour(timeSlotDto.startHour);
		lab.setRoomId(timeSlotDto.roomId);
		subject.getActivities().add(lab);
		lab.setSubject(subject);
		em.persist(lab);
		return lab.getId();
	}
	
	public void deleteLab(long labId) {
		LabActivity lab = em.find(LabActivity.class, labId);
		em.remove(lab);
	}
	
	// Remember to update OWNER side (not mappedBy side) of a relation!
	public void assignTeacherToLab(long teacherId, long labId) {
		LabActivity lab = em.find(LabActivity.class, labId);
		Teacher teacher = em.find(Teacher.class, teacherId);
		teacher.getActivities().add(lab);
		lab.getTeachers().add(teacher); // SOLUTION
		// results in an INSERT into the many-to-many table
	}
	
	// Remember to update OWNER side (not mappedBy side) of a relation!
	public void removeTeacherFromLab(long teacherId, long labId) {
		LabActivity lab = em.find(LabActivity.class, labId);
		Teacher teacher = em.find(Teacher.class, teacherId);
		teacher.getActivities().remove(lab);
		lab.getTeachers().remove(teacher); 
		// results in a DELETE from the many-to-many table
	}
	
	// 1. auto Rollback Tx on exception
	// 2. on exception thrown by an invoked method (anotherService.throwException())
	// 3. by ANY invoked method ? try with a) static method call b) method in non-transacted bean
	public void updateSubjectFailing(SubjectDto subjectDto) {
		anotherService.checkPermissionsOnSubject(subjectDto.id);
		Subject subject = em.find(Subject.class, subjectDto.id);
		subject.setName(subjectDto.name);
		subject.setHolderTeacher(em.find(Teacher.class, subjectDto.holderTeacherId));
		em.flush();
//		throw new RuntimeException("Thrown on purpose");
		try{ 
			anotherService.throwException(); 
			nonTransactedService.throwException();
			MyUtil.staticMethodThrowingException();
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
	}
	
	// Trying to do something in a dead transaction
	public void updateSubjectFailingLogged(SubjectDto subjectDto) {
		anotherService.checkPermissionsOnSubject(subjectDto.id);
		Subject subject = em.find(Subject.class, subjectDto.id);
		subject.setName(subjectDto.name);
		subject.setHolderTeacher(em.find(Teacher.class, subjectDto.holderTeacherId));
		try{ 
			anotherService.throwException(); // My Tx dies before returning from this call
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			// I am walking with a zombie Transaction
			anotherService.persistErrorLog("Problem happened: " + e.getMessage());
		}
	}
	
	// Lazy Load collections
	// 1) EAGER
	// 2) FETCH JOIN 
	public SubjectWithActivitiesDto getSubjectWithActivities(Long subjectId) {
		Subject subject = em.find(Subject.class, subjectId);
		log.debug("Got Subject from Database");
		return new SubjectWithActivitiesDto(subject);
	}
	
	// Force the Lazy load of the @..ToOne link
	public TeacherDetailsDto getTeacher(Long teacherId) {
		Teacher teacher = em.find(Teacher.class, teacherId);
		log.debug("Got Teacher from JPA");
		return new TeacherDetailsDto(teacher);
	}

	// =============== MERGE start ===============

	public void updateYearWithGroups_manuallyEditingChildren(long yearId, YearWithGroupsDto dto) {
		StudentsYear year = em.find(StudentsYear.class, yearId);
		year.setCode(dto.code);
		
		// remove children no longer there
		Set<Long> preservedIds = dto.groups.stream().map(StudentsGroupDto::getId).collect(toSet());
		Set<StudentsGroup> toRemove = new HashSet<>();
		for (StudentsGroup oldGroup : year.getGroups()) {
			if (!preservedIds.contains(oldGroup.getId())) {
				toRemove.add(oldGroup);
			}
		}
		year.getGroups().removeAll(toRemove);

		// add and update
		for (StudentsGroupDto groupDto : dto.groups) {
			if (groupDto.id == null) {
				StudentsGroup newGroup = new StudentsGroup();
				newGroup.setCode(groupDto.code);
				newGroup.setEmails(groupDto.emails);
				em.persist(newGroup);
				year.getGroups().add(newGroup);
				newGroup.setYear(year);
			} else {
				StudentsGroup oldGroup = year.getGroups().stream().filter(g -> g.getId().equals(groupDto.id)).findFirst().get();
				oldGroup.setCode(groupDto.code);
			}
		}
	}
	
	public void updateYearWithGroups_cascadingMerge(long yearId, YearWithGroupsDto yearDto) {
		// to work, year should cascade to group
		StudentsYear year = new StudentsYear();
		year.setCode(yearDto.code);
		year.setId(yearDto.id);
		
		for (StudentsGroupDto groupDto : yearDto.groups) {
			StudentsGroup group = new StudentsGroup();
			group.setId(groupDto.id);
			group.setCode(groupDto.code);
			group.setEmails(groupDto.emails);
			group.setYear(year);
			year.getGroups().add(group);
		}
		StudentsYear yearFoundBefore = em.find(StudentsYear.class, yearId);
		yearFoundBefore.setCode("x");
		StudentsYear returnedByMerge = em.merge(year);
		
		log.debug("First instance was attached? {}; Or another one was loaded from db, updated and returned? {}"
				,em.contains(year)
				,em.contains(returnedByMerge));
		log.debug("The entity already attached before merge is == the entity returned by merge? {}", returnedByMerge == yearFoundBefore );
		log.debug("The newly created entity is == the entity returned by merge? {}", returnedByMerge == year );
	}

	// =========================== gata merge =======================
	
	public List<ContactChannelDto> getTeacherChannels(long teacherId) {
		Teacher teacher = em.find(Teacher.class, teacherId);
		log.debug("Teacher got from DB");
		return teacher.getChannels().stream().map(ContactChannelDto::new).collect(toList());
	}
	
	public void setTeacherChannels(long teacherId, List<ContactChannelDto> channelDtos) {
		List<ContactChannel> channels = new ArrayList<>();
		for (ContactChannelDto dto : channelDtos) {
			channels.add(new ContactChannel(dto.type, dto.value));
		}
		Teacher teacher = em.find(Teacher.class, teacherId);
		teacher.setChannels(channels);
	}

	public Set<TeachingActivity> getAllActivities(long yearId) {
		StudentsYear year = em.find(StudentsYear.class, yearId);
		Set<TeachingActivity> set = new HashSet<>();
		for (CourseActivity course : year.getCourses()) {
			set.add(course);
			for (StudentsGroup group: year.getGroups()) {
				set.addAll(group.getLabs());
			}
		}
		return set;
	}
	

}
