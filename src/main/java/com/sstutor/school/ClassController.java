package com.sstutor.school;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.sstutor.common.DBConnection;
import com.sstutor.exception.ExceptionDetail;
import com.sstutor.exception.GenericException;
import com.sstutor.model.Attendance;
import com.sstutor.model.ClassReferenceTime;
import com.sstutor.model.Classes;
import com.sstutor.model.MediaCapture;
import com.sstutor.model.Notification;
import com.sstutor.model.Quiz;
import com.sstutor.model.Quizanswer;
import com.sstutor.model.Restricted_Media_Access;
import com.sstutor.model.SchoolTeacherRole;
import com.sstutor.model.StorageMedia;
import com.sstutor.model.Student;
import com.sstutor.model.Subject;
import com.sstutor.model.Teacher;
import com.sstutor.model.TimeTable;
import com.sstutor.model.Usages;
import com.sstutor.model.Result;
import com.sstutor.model.page_visit;
import com.sstutor.model.Login;
import com.sstutor.model.Master_db;
import com.sstutor.model.Dailydiary;
import com.sstutor.model.Exam_timetable;
import com.sstutor.model.ParentTeacherMeeting;
import com.sstutor.model.Dailydiary1;
import com.sstutor.model.Absent_list;
import com.sstutor.school.ClassController;
import com.sstutor.model.TimeTable1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClassController {

	
	@CrossOrigin()
	@RequestMapping(value = "/classes/{class_id}/subjects", method = RequestMethod.GET)
	public ResponseEntity<List<Subject>> getAllSubjectsForClass(@PathVariable("class_id") Long id) {

		Connection c = null;
		ResultSet res;
		ArrayList<Subject> subjects = new ArrayList<Subject>();

		try {
			c = DBConnection.getDBConnection();

			String sql = "select school_subject_id, subject from class_subject cs, school_subject ss "
						+ "where cs.school_subject_id = ss.id and cs.class_id = ?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, id);

			res = pstmt.executeQuery();

		while (res.next()) {
			Subject s = new Subject();
			s.setSchool_subject_id(res.getLong("school_subject_id"));
			s.setSubject_name(res.getString("subject"));
			subjects.add(s);
			}

			pstmt.close();
			c.close();
		return new ResponseEntity<List<Subject>>(subjects, HttpStatus.OK);

			}
		
		catch (Exception e) {
		throw new GenericException("Getting subjects failed:" + e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/classes/{class_id}/subjects", method = RequestMethod.POST)
	public String addSubjectForClass(@PathVariable("class_id") Long id,	@RequestBody Subject subject) {

		try {
			
		if (subject == null) {
		throw new Exception("req. body missing");
			}

			String op = "{ }";
			Long schoolSubjectID = subject.getSchool_subject_id();

			Connection c = null;

			c = DBConnection.getDBConnection(); 
			
			String sql = "insert into class_subject (class_id, school_subject_id) values (?,?)";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, id);
			pstmt.setLong(2, schoolSubjectID);

			pstmt.execute();
			pstmt.close();
			c.close();
		return op;

		}
		catch (Exception e) {
		throw new GenericException("Insert subject failed:"	+ e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/classes/{class_id}/subjects/{subject_id}", method = RequestMethod.DELETE)
	public String removeSubjectFromClass(@PathVariable Long class_id, @PathVariable Long subject_id) {

		String op = "{ }";
		Connection c = null;

		try {
			c = DBConnection.getDBConnection();

			String sql = "delete from class_subject where class_id = ? and school_subject_id = ?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, class_id);
			pstmt.setLong(2, subject_id);

			pstmt.execute();
			pstmt.close();
			c.close();

		} 
		catch (Exception e) {
		throw new GenericException("Delete subject failed:"	+ e.getMessage());
		}
		
		return op;
	}

	@CrossOrigin()
	@RequestMapping(value = "/classes/{class_id}/teachers/{teacher-id}/roles", method = RequestMethod.POST)
	public String addTeacherToRole(@PathVariable Long class_id,	@PathVariable Long teacher_id, @RequestBody SchoolTeacherRole role) {

		try {
			if (role == null) {
				throw new Exception("Request body missing");
			}

			String op = "{ }";
			Long roleID = role.getRole_id();

			Connection c = null;

			c = DBConnection.getDBConnection();

			String sql = "insert into teacher_class_role (teacher_id, class_id, school_role_id) values (?,?,?)";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, teacher_id);
			pstmt.setLong(2, class_id);
			pstmt.setLong(3, roleID);

			pstmt.execute();
			pstmt.close();
			c.close();
			return op;

		} 
		catch (Exception e) {
		throw new GenericException("Add teacher to role failed:" + e.getMessage());
		
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/classes/{class_id}/subjects/{subject_id}/teachers", method = RequestMethod.GET)
	public ResponseEntity<List<Teacher>> getTeachersForSubjectClass(@PathVariable Long class_id, @PathVariable Long subject_id) {
		
		Connection c = null;
		ResultSet res;
		ArrayList<Teacher> teachers = new ArrayList<Teacher>();

		try {
			c = DBConnection.getDBConnection();

			String sql = "select t.id as id, u.name as name from teacher_class_subject tcs, teacher t,  users u where tcs.teacher_id = t.id and t.user_id = u.id and tcs.class_id =? and tcs.subject_id = ?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, class_id);
			pstmt.setLong(2, subject_id);

			res = pstmt.executeQuery();
		while (res.next()) {
			Teacher t = new Teacher();
			t.setTeacher_id(res.getLong("id"));
			t.setName(res.getString("name"));
			teachers.add(t);
			}

			pstmt.close();
			c.close();
		return new ResponseEntity<List<Teacher>>(teachers, HttpStatus.OK);

		}
		catch (Exception e) {
		throw new GenericException("Getting teachers for subject failed:"+ e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/classes/{class_id}/subjects/{subject_id}/teachers", method = RequestMethod.POST)
	public String addTeacherForSubject(@PathVariable Long class_id,	@PathVariable Long subject_id, @RequestBody Teacher teacher) {

		try {
			if (teacher == null) {
			throw new Exception("req. body missing");
			}

			String op = "{ }";
			Long teacherID = teacher.getTeacher_id();

			Connection c = null;

			c = DBConnection.getDBConnection();

			String sql = "insert into teacher_class_subject (teacher_id, class_id, subject_id) values (?,?,?)";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, teacherID);
			pstmt.setLong(2, class_id);
			pstmt.setLong(3, subject_id);

			pstmt.execute();
			pstmt.close();
			c.close();
		return op;

		}
		catch (Exception e) {
		throw new GenericException("Adding teacher for subject failed:"	+ e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/classes/{class_id}/subjects/{subject_id}/teachers/{teacher_id}", method = RequestMethod.DELETE)
	public String removeTeacherFromSubjectClass(@PathVariable Long class_id, @PathVariable Long subject_id, @PathVariable Long teacher_id) {

		String op = "{ }";
		Connection c = null;

		try {
			c = DBConnection.getDBConnection();

			String sql = "delete from teacher_class_subject where class_id = ? and subject_id = ? and teacher_id = ?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, class_id);
			pstmt.setLong(2, subject_id);
			pstmt.setLong(3, teacher_id);

			pstmt.execute();
			pstmt.close();
			c.close();
			return op;

		} 
		catch (Exception e) {
		throw new GenericException(
					"Deleting teacher from subject for class failed:" + e.getMessage());
		}

	}

	@CrossOrigin()
	@RequestMapping(value = "/classes/{class_id}/referencetimes", method = RequestMethod.GET)
	public ResponseEntity<List<ClassReferenceTime>> getReferenceTime(@PathVariable Long class_id) {
		Connection c = null;
		ResultSet res;
		ArrayList<ClassReferenceTime> times = new ArrayList<ClassReferenceTime>();

		try {
			c = DBConnection.getDBConnection();

			String sql = "select id, start_time, end_time, period_type, attendance_required from class_reference_time where class_id = ? order by start_time asc";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, class_id);

			res = pstmt.executeQuery();
		while (res.next()) {
				ClassReferenceTime t = new ClassReferenceTime();
				t.setAttendance_required(res.getBoolean("attendance_required"));
				t.setEnd_time(res.getString("end_time"));
				t.setId(res.getLong("id"));
				t.setPeriod_type(res.getString("period_type"));
				t.setStart_time(res.getString("start_time"));
				times.add(t);
			}

			pstmt.close();
			c.close();
		return new ResponseEntity<List<ClassReferenceTime>>(times,
					HttpStatus.OK);

		} catch (Exception e) {
			throw new GenericException("Get reference times failed:"
					+ e.getMessage());
		}

	}

	@CrossOrigin()
	@RequestMapping(value = "/classes/{class_id}/referencetimes", method = RequestMethod.POST)
	public String addRefTimeForClass(@PathVariable Long class_id,
			@RequestBody ClassReferenceTime crt) {

		try {
			if (crt == null) {
			throw new Exception("No req. body");
			}

			String op = "{ }";

			Connection c = null;

			c = DBConnection.getDBConnection();

			String sql = "insert into class_reference_time (class_id, start_time, end_time, period_type, attendance_required) values (?,?,?,?,?)";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, class_id);
			pstmt.setString(2, crt.getStart_time());
			pstmt.setString(3, crt.getEnd_time());
			pstmt.setString(4, crt.getPeriod_type());
			pstmt.setBoolean(5, crt.getAttendance_required());

			pstmt.execute();
			pstmt.close();
			c.close();
			return op;

		} 
		catch (Exception e) {
		throw new GenericException("Adding ref time for class failed:" + e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/classes/{class_id}/referencetimes/{ref_id}", method = RequestMethod.DELETE)
	public String removeReferenceTime(@PathVariable Long class_id,	@PathVariable Long ref_id) {
		String op = "{ }";
		Connection c = null;

		try {
			c = DBConnection.getDBConnection();

			String sql = "delete from class_reference_time where id = ?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, ref_id);

			pstmt.execute();
			pstmt.close();
			c.close();
		return op;

		}
		catch (Exception e) {
		throw new GenericException("Remove ref time for class failed:" + e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/classes/{class_id}/{section}/{day}/{tt_date}/timetable", method = RequestMethod.GET)
	public ResponseEntity<List<TimeTable>> getTimeTable(@PathVariable Long class_id, 
														@PathVariable String section,
														@PathVariable String day, 
														@PathVariable String tt_date) {
		Connection c = null;
		ResultSet res;
		ArrayList<TimeTable> tts = new ArrayList<TimeTable>();

		try {

			c = DBConnection.getDBConnection();

			String sql = "select id, start_time, end_time, period, attendance_required, tt_date, subject, teacher_id, teacher_name , day from class_time_table where class_id =? and section=? and (day=? or tt_date=?) and active= 'Y' order by period, tt_date asc";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, class_id);
			pstmt.setString(2, section);
			pstmt.setString(3, day);
			pstmt.setString(4, tt_date);

			res = pstmt.executeQuery();
		while (res.next()) {
				TimeTable t = new TimeTable();
				t.setattendance_required(res.getBoolean("attendance_required"));
				t.setend_time(res.getString("end_time"));
				t.setid(res.getLong("id"));
				t.setperiod(res.getString("period"));
				t.setstart_time(res.getString("start_time"));
				t.settt_date(res.getString("tt_date"));
				t.setsubject(res.getString("subject"));
				t.setteacher_id(res.getLong("teacher_id"));
				t.setteacher_name(res.getString("teacher_name"));
				t.setday(res.getString("day"));

				tts.add(t);
			}

			pstmt.close();
			c.close();
		return new ResponseEntity<List<TimeTable>>(tts, HttpStatus.OK);

		} 
		catch (Exception e) {
		throw new GenericException("Get time table for class failed:" + e.getMessage());
		}
	}
	
	@CrossOrigin()
	@RequestMapping(value = "/classes/{class_id}/{section}/{tt_date}/timetable", method = RequestMethod.POST)
	public String addtimetable(@PathVariable Long class_id, @PathVariable String section,
							   @PathVariable String tt_date, @RequestBody TimeTable[] tt) {
			String op = "{ }";

		try {
		
			String[] start_time = new String[tt.length];
			String[] end_time = new String[tt.length];
			String[] period = new String[tt.length];
			Boolean[] attendance_required = new Boolean[tt.length];
			String[] subject = new String[tt.length];
			String[] teacher_name = new String[tt.length];
			Long[] teacher_id = new Long[tt.length];
			String[] active = new String[tt.length];
			Long[] school_id = new Long[tt.length];
			Long[] cls = new Long[tt.length];
			String[] sec = new String[tt.length];
			String[] date = new String[tt.length];
			
		for (int i = 0; i < tt.length; i++) {
			
			start_time[i] = tt[i].getstart_time();
			end_time[i] = tt[i].getend_time();
			period[i] = tt[i].getperiod();
			attendance_required[i] = tt[i].getattendance_required();
			subject[i] = tt[i].getsubject();
			teacher_name[i] = tt[i].getteacher_name();
			teacher_id[i] = tt[i].getteacher_id();
			active[i] = tt[i].getactive();
			school_id[i] = tt[i].getschool_id();
			cls[i] = tt[i].getclass_id();
			sec[i] = tt[i].getsection();
			date[i] = tt[i].gettt_date();
		
			}
			
			Connection c = null;
			c = DBConnection.getDBConnection();

			String sql = "insert into class_time_table(class_id, start_time, end_time, period, attendance_required, tt_date, subject, teacher_name, section, active, school_id, teacher_id)" +  "select * from unnest( array[?], array[?], array[?], array[?], array[?], array[?], array[?], array[?],array[?], array[?], array[?], array[?])";

			PreparedStatement pstmt = c.prepareStatement(sql);		

			Array clss = c.createArrayOf("bigint", cls);
			Array start = c.createArrayOf("text", start_time);
			Array end = c.createArrayOf("text", end_time);
			Array per = c.createArrayOf("text", period);
			Array attendance = c.createArrayOf("boolean", attendance_required);
			Array tdate = c.createArrayOf("text",date);
			Array sub = c.createArrayOf("text", subject);
			Array name = c.createArrayOf("text", teacher_name);
			Array sect = c.createArrayOf("text", sec);
			Array act = c.createArrayOf("text", active);
			Array school = c.createArrayOf("bigint", school_id);
			Array id = c.createArrayOf("bigint", teacher_id);

			pstmt.setArray(1, clss);
			pstmt.setArray(2, start);
			pstmt.setArray(3, end);
			pstmt.setArray(4, per);
			pstmt.setArray(5, attendance);
			pstmt.setArray(6, tdate);
			pstmt.setArray(7, sub);
			pstmt.setArray(8, name);
			pstmt.setArray(9, sect);
			pstmt.setArray(10, act);		
			pstmt.setArray(11, school);
			pstmt.setArray(12, id);

			pstmt.execute();
			pstmt.close();
			c.close();
		return op;
			
		}
		catch (Exception e) {
		throw new GenericException("Adding timetable failed:" + e.getMessage());
		}
	}

	
	@CrossOrigin()
	@RequestMapping(value = "/classes/{class_id}/{section}/{day}", method = RequestMethod.POST)
	public String timetablenotification(@PathVariable Long class_id, @PathVariable String section,
										@PathVariable String day, @RequestBody TimeTable1[] t) {
			String op = "{ }";

		try {
		
			String[] start_time = new String[t.length];
			String[] end_time = new String[t.length];
			String[] period = new String[t.length];
			Boolean[] attendance_required = new Boolean[t.length];
			String[] subject = new String[t.length];
			String[] teacher_name = new String[t.length];
			Long[] teacher_id = new Long[t.length];
			String[] active = new String[t.length];
			Long[] school_id = new Long[t.length];
			Long[] cls = new Long[t.length];
			String[] sec = new String[t.length];
			String[] day1 = new String[t.length];
			
		for (int i = 0; i < t.length; i++) {
			
			start_time[i] = t[i].getstart_time();
			end_time[i] = t[i].getend_time();
			period[i] = t[i].getperiod();
			attendance_required[i] = t[i].getattendance_required();
			subject[i] = t[i].getsubject();
			teacher_name[i] = t[i].getteacher_name();
			teacher_id[i] = t[i].getteacher_id();
			active[i] = t[i].getactive();
			school_id[i] = t[i].getschool_id();
			cls[i] = t[i].getclass_id();
			sec[i] = t[i].getsection();
			day1[i] = t[i].getday();
			}
			
			Connection c = null;
			c = DBConnection.getDBConnection();

			String sql = "insert into class_time_table(class_id, start_time, end_time, period, attendance_required, subject, teacher_name, section, day, active, school_id, teacher_id)" +  "select * from unnest( array[?], array[?], array[?], array[?], array[?], array[?], array[?], array[?],array[?], array[?], array[?], array[?])";

			PreparedStatement pstmt = c.prepareStatement(sql);		

			Array clss = c.createArrayOf("bigint", cls);
			Array start = c.createArrayOf("text", start_time);
			Array end = c.createArrayOf("text", end_time);
			Array per = c.createArrayOf("text", period);
			Array attendance = c.createArrayOf("boolean", attendance_required);
			Array sub = c.createArrayOf("text", subject);
			Array name = c.createArrayOf("text", teacher_name);
			Array sect = c.createArrayOf("text", sec);
			Array da = c.createArrayOf("text", day1);
			Array act = c.createArrayOf("text", active);
			Array school = c.createArrayOf("bigint", school_id);
			Array id = c.createArrayOf("bigint", teacher_id);

			pstmt.setArray(1, clss);
			pstmt.setArray(2, start);
			pstmt.setArray(3, end);
			pstmt.setArray(4, per);
			pstmt.setArray(5, attendance);
			pstmt.setArray(6, sub);
			pstmt.setArray(7, name);
			pstmt.setArray(8, sect);
			pstmt.setArray(9, da);
			pstmt.setArray(10, act);		
			pstmt.setArray(11, school);
			pstmt.setArray(12, id);

			pstmt.execute();
			pstmt.close();
			c.close();
		return op;
			
		}
		catch (Exception e) {
		throw new GenericException("Adding timetable notification failed:" + e.getMessage());
		}
	}


	@CrossOrigin()
	@RequestMapping(value = "/classes/{school_id}/{section}/{class_id}/{day}/timetable", method = RequestMethod.PUT)
	public String updateTimeTable(@PathVariable Long school_id, @PathVariable Long class_id, 
								  @PathVariable String section, @PathVariable String day, 
								  @RequestBody TimeTable nn) {

		String op = "{ }";

		Connection c = null;

		try {

			c = DBConnection.getDBConnection();

			String sql = "update class_time_table set active='N' where school_id = ? and class_id=? and section=? and day=?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, school_id);
			pstmt.setLong(2, class_id);
			pstmt.setString(3, section);
			pstmt.setString(4, day);

			pstmt.execute();
			pstmt.close();
			c.close();
			return op;

		} catch (Exception e) {
			throw new GenericException("Update timetable for class failed:" + e.getMessage());
		}
	}


	@CrossOrigin()
	@RequestMapping(value = "/classes/{school_id}/{class_id}/{section}/{student_id}/studentattendance", method = RequestMethod.GET)
	public ResponseEntity<List<Absent_list>> getstudent(@PathVariable Long school_id, @PathVariable Long class_id,
														@PathVariable String section, @PathVariable Long student_id) {
			Connection c = null;
			ResultSet res;
			ArrayList<Absent_list> atts = new ArrayList<Absent_list>();

		try {

			c = DBConnection.getDBConnection();

			String sql = "select attendance_check, period, modified_by, modified_timestamp, student_id, student_name, date from student_attendance where school_id=? and class_id= ? and section= ? and student_id= ?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, school_id);
			pstmt.setLong(2, class_id);
			pstmt.setString(3, section);
			pstmt.setLong(4, student_id);

			res = pstmt.executeQuery();
		while (res.next()) {
				Absent_list a = new Absent_list();
				a.setattendance_check(res.getBoolean("attendance_check"));
				a.setmodified_by(res.getLong("modified_by"));
				a.setmodified_timestamp(res.getString("modified_timestamp"));
				a.setstudent_id(res.getLong("student_id"));
				a.setperiod(res.getString("period"));
				a.setstudent_name(res.getString("student_name"));
				a.setdate(res.getString("date"));

				atts.add(a);
			}

			pstmt.close();
			c.close();
		return new ResponseEntity<List<Absent_list>>(atts, HttpStatus.OK);

		}
		catch (Exception e) {
		throw new GenericException("Get absent for period failed:"
					+ e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/classes/{school_id}/{class_id}/{section}/{date}/attendance", method = RequestMethod.GET)
	public ResponseEntity<List<Attendance>> getAttendanceForPeriod(@PathVariable Long school_id, 
																   @PathVariable Long class_id,
																   @PathVariable String section, 
																   @PathVariable String date) {
		Connection c = null;
		ResultSet res;
		ArrayList<Attendance> atts = new ArrayList<Attendance>();

		try {

			c = DBConnection.getDBConnection();
			String sql = "select distinct period from student_attendance where school_id=? and class_id=? and section=? and date=? ORDER BY period";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, school_id);
			pstmt.setLong(2, class_id);
			pstmt.setString(3, section);
			pstmt.setString(4, date);

			res = pstmt.executeQuery();
		while (res.next()) {
				Attendance a = new Attendance();
				a.setperiod(res.getString("period"));
				atts.add(a);
			}

			pstmt.close();
			c.close();
		return new ResponseEntity<List<Attendance>>(atts, HttpStatus.OK);

		}
		catch (Exception e) {
		throw new GenericException("Get attendance for period failed:" + e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "classes/{school_id}/attendance", method = RequestMethod.POST)
	public String addattendance(@PathVariable Long school_id,
								@RequestBody Attendance[] a) {
			String op = "{ }";
			Connection c = null;
		try {

			Long[] student_id = new Long[a.length];
			String[] student_name = new String[a.length];
			Boolean[] attendance_check = new Boolean[a.length];
			Long[] modified_by = new Long[a.length];
			String[] date = new String[a.length];
			String[] period = new String[a.length];
			Long[] tt_id = new Long[a.length];
			Long[] class_id = new Long[a.length];
			String[] section = new String[a.length];

		for (int i = 0; i < a.length; i++) {
				student_id[i] = a[i].getstudent_id();
				student_name[i] = a[i].getstudent_name();
				attendance_check[i] = a[i].getattendance_check();
				modified_by[i] = a[i].getmodified_by();
				date[i] = a[i].getdate();
				period[i] = a[i].getperiod();
				tt_id[i] = a[i].gettt_id();
				class_id[i] = a[i].getclass_id();
				section[i] = a[i].getsection();

			}

			c = DBConnection.getDBConnection();
			
			String sql = "insert into student_attendance (student_id, student_name, attendance_check, modified_by, date, period, tt_id, class_id, section)"
					+ "select * from unnest(array[?],array[?],array[?],array[?],array[?],array[?],array[?],array[?],array[?]) ";
			PreparedStatement pstm = c.prepareStatement(sql);

			Array std_id = c.createArrayOf("bigint", student_id);
			Array std_name = c.createArrayOf("text", student_name);
			Array att = c.createArrayOf("boolean", attendance_check);
			Array modi_by = c.createArrayOf("bigint", modified_by);
			Array dat = c.createArrayOf("text", date);
			Array per = c.createArrayOf("text", period);
			Array t_id = c.createArrayOf("bigint", tt_id);
			Array cls_id = c.createArrayOf("bigint", class_id);
			Array sec = c.createArrayOf("text", section);

			pstm.setArray(1, std_id);
			pstm.setArray(2, std_name);
			pstm.setArray(3, att);
			pstm.setArray(4, modi_by);
			pstm.setArray(5, dat);
			pstm.setArray(6, per);
			pstm.setArray(7, t_id);
			pstm.setArray(8, cls_id);
			pstm.setArray(9, sec);

			pstm.execute();
			pstm.close();
			c.close();
		return op;
		}

		catch (Exception e) {
		throw new GenericException(" Insert attendance failed:"	+ e.getMessage());
		}
	}
	
	@CrossOrigin()
	@RequestMapping(value = "/classes/{class_id}/timetable/{tt_id}/attendance", method = RequestMethod.PUT)
	public String storeAttendanceForPeriod(@PathVariable Long class_id,
											@PathVariable Long tt_id,
											@RequestBody Attendance[] p_attendances) {
			String op = "{ }";
			Connection c = null;
		
		try {

			Long[] student_ids = new Long[p_attendances.length];
			Boolean[] atts = new Boolean[p_attendances.length];
			String[] student_name = new String[p_attendances.length];
			
		for (int i = 0; i < p_attendances.length; i++) {
				student_ids[i] = p_attendances[i].getstudent_id();
				atts[i] = p_attendances[i].getattendance_check();
				student_name[i] = p_attendances[i].getstudent_name();
				}

			c = DBConnection.getDBConnection();

			String sql = "select upsert_attendance2(?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, tt_id);
			Array pst = c.createArrayOf("BIGINT", student_ids);
			pstmt.setArray(2, pst);
			Array patt = c.createArrayOf("BOOLEAN", atts);
			pstmt.setArray(3, patt);
			pstmt.setLong(4, p_attendances[0].getmodified_by());
			pstmt.setString(5, p_attendances[0].getperiod());
			Array std = c.createArrayOf("TEXT", student_name);
			pstmt.setArray(6, std);
			pstmt.setString(7,p_attendances[0].getsection());
			pstmt.setLong(8, p_attendances[0].getschool_id());
			pstmt.setString(9,p_attendances[0].getdate());
			pstmt.setLong(10,p_attendances[0].getclass_id());

			pstmt.execute();
			pstmt.close();
			c.close();
		return op;

		} 
		catch (Exception e) {
		throw new GenericException("Storing attendance for period failed:" + e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/classes/{class_id}/students", method = RequestMethod.GET)
	public ResponseEntity<List<Student>> getStudentsForClass(@PathVariable Long class_id) {
			Connection c = null;
			ResultSet res;
			ArrayList<Student> students = new ArrayList<Student>();

		try {

			c = DBConnection.getDBConnection();

			String sql = "select sc.id as id, u.name as name, sc.user_id as user_id from student_class sc, users u where sc.class_id = ? and sc.user_id = u.id order by u.name asc";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, class_id);

			res = pstmt.executeQuery();
		while (res.next()) {
				Student s = new Student();
				s.setId(res.getLong("id"));
				s.setName(res.getString("name"));
				s.setUser_id(res.getLong("user_id"));
				students.add(s);
			}

			pstmt.close();
			c.close();
			return new ResponseEntity<List<Student>>(students, HttpStatus.OK);

		} catch (Exception e) {
			throw new GenericException("Getting students for class failed:"
					+ e.getMessage());
		}
	}
	
	@CrossOrigin()
	@RequestMapping(value = "/classes/{class_id}/teachers", method = RequestMethod.GET)
	public ResponseEntity<List<Teacher>> getAllTeachersForClass(@PathVariable("class_id") Long id) {
			Connection c = null;
			ResultSet res;
			ArrayList<Teacher> teachers = new ArrayList<Teacher>();

		try {
			c = DBConnection.getDBConnection();

			String sql = "select t.id as tid, u.name as name from teacher t, users u,  where u.id = t.user_id and t.school_id=?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, id);

			res = pstmt.executeQuery();

		while (res.next()) {
				Teacher t = new Teacher();
				t.setTeacher_id(res.getLong("tid"));
				t.setName(res.getString("name"));
				teachers.add(t);
			}
			pstmt.close();
			res.close();
			c.close();

		return new ResponseEntity<List<Teacher>>(teachers, HttpStatus.OK);
		}
		catch (Exception e) {
			System.out.println("Hello there:" + e.getClass().getName() + ": " + e.getMessage());
		return new ResponseEntity<List<Teacher>>(teachers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/usage_stats/{user_id}", method = RequestMethod.GET)
	public ResponseEntity<List<page_visit>> getpage_visit(@PathVariable("user_id") Long id) {
			Connection c = null;
			ResultSet res;
			ArrayList<page_visit> pagevisit = new ArrayList<page_visit>();
		try {
			c = DBConnection.getDBConnection();
			String sql = "select  user_id, page_visit, timestamp from usage_stats where user_id = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, id);

			res = pstmt.executeQuery();

		while (res.next()) {
			page_visit p = new page_visit();
			p.setuser_id(res.getLong("user_id"));
			p.setpage_visit(res.getString("page_visit"));
			p.settimestamp(res.getTimestamp("timestamp"));
			pagevisit.add(p);
			}
			pstmt.close();
			c.close();
		return new ResponseEntity<List<page_visit>>(pagevisit,	HttpStatus.OK);
		} 
		catch (Exception e) {
		throw new GenericException("Getting pagevisit failed:" + e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/usage_stats/{user_id}", method = RequestMethod.POST)
	public String addpage_visit(@PathVariable("user_id") Long id,
								@RequestBody page_visit pv) {

		try {
		if (pv == null) {
		throw new Exception("req. body missing");
			}
			String op = "{ }";
			Long user_id = pv.getuser_id();
			String page_visit = pv.getpage_visit();

			Connection c = null;

			c = DBConnection.getDBConnection();
			String sql = "insert into usage_stats ( user_id, page_visit) values (?,?)";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, user_id);
			pstmt.setString(2, page_visit); 
			pstmt.execute();
			pstmt.close();
			c.close();
			return op;

		} catch (Exception e) {
			throw new GenericException("Insert pv failed:" + e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/media_captured/{school_id}", method = RequestMethod.GET)
	public ResponseEntity<List<MediaCapture>> getUrl(@PathVariable("school_id") Long id) {

			Connection c = null;
			ResultSet res;
			ArrayList<MediaCapture> tit = new ArrayList<MediaCapture>();
			
		try {
			
			c = DBConnection.getDBConnection();
			String sql = "select school_id, url, title, restricted_access, created_by, name from media_captured cross join users where school_id = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, id);

			res = pstmt.executeQuery();

		while (res.next()) {
				MediaCapture t = new MediaCapture();
				t.setschool_id(res.getLong("school_id"));
				t.seturl(res.getString("url"));
				t.settitle(res.getString("title"));
				t.setrestricted_access(res.getBoolean("restricted_access"));
				t.setcreated_by(res.getString("created_by"));
				t.setname(res.getString("name"));
				tit.add(t);

			}
			pstmt.close();
			c.close();
		return new ResponseEntity<List<MediaCapture>>(tit, HttpStatus.OK);
		}
		catch (Exception e) {
		throw new GenericException("Getting mediacapture failed:" + e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/media_captured/{school_id}", method = RequestMethod.POST)
	public String addmediacapture(@PathVariable("school_id") Long school_id,
								  @RequestBody MediaCapture t) {

		try {
		if (t == null) {
		throw new Exception("req. body missing");
			}
			String op = "{ }";
			Long schoolid = t.getschool_id();
			Long id = t.getid();
			String url = t.geturl();
			String title = t.gettitle();
			Boolean restricted_access = t.getrestricted_access();
			String created_by = t.getcreated_by();
			Connection c = null;

			c = DBConnection.getDBConnection();

			String sql = "insert into media_captured ( school_id, id, created_by, url, title, restricted_access) values (?,?,?,?,?,?)";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, school_id);
			pstmt.setLong(2, id);
			pstmt.setString(3, created_by);
			pstmt.setString(4, url);
			pstmt.setString(5, title);
			pstmt.setBoolean(6, restricted_access);
			pstmt.execute();
			pstmt.close();
			c.close();
			return op;

		} catch (Exception e) {
			throw new GenericException("Insert mediacapture failed:"
					+ e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/restricted_media_access/{media_id}", method = RequestMethod.GET)
	public ResponseEntity<List<Restricted_Media_Access>> getuser_id(@PathVariable("media_id") Long id) {
			Connection c = null;
			ResultSet res;
			ArrayList<Restricted_Media_Access> user = new ArrayList<Restricted_Media_Access>();
		
			try {
				
				c = DBConnection.getDBConnection();

				String sql = "select media_id, user_id, url, restricted_access from restricted_media_access right join media_captured on media_id =?";
				PreparedStatement pstmt = c.prepareStatement(sql);
				pstmt.setLong(1, id);

				res = pstmt.executeQuery();
			while (res.next()) {
				Restricted_Media_Access ui = new Restricted_Media_Access();
				ui.setmedia_id(res.getLong("media_id"));
				ui.setuser_id(res.getLong("user_id"));
				ui.seturl(res.getString("url"));
				ui.setrestricted_access(res.getBoolean("restricted_access"));
				user.add(ui);
			}

				pstmt.close();
				c.close();
			return new ResponseEntity<List<Restricted_Media_Access>>(user,
					HttpStatus.OK);

			} 
			catch (Exception e) {
			throw new GenericException("Getting user_id failed:" + e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/restricted_media_access/{media_id}", method = RequestMethod.POST)
	public String adduser_idForrestricted_media_access(	@PathVariable Long media_id, 
														@RequestBody Restricted_Media_Access um) {

		try {
		if (um == null) {
		throw new Exception("req. body missing");
			}

			String op = "{ }";
			Long user_id = um.getuser_id();
			Connection c = null;
			c = DBConnection.getDBConnection();
			System.out.println("user_id:" + um.getuser_id());
			System.out.println("media_id: " + um.getmedia_id());

			String sql = "insert into restricted_media_access (user_id, media_id) values (?,?)";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, user_id);
			pstmt.setLong(2, media_id);
			pstmt.execute();
			pstmt.close();
			c.close();
		return op;

		}
		catch (Exception e) {
		throw new GenericException("Insert user_id failed:"	+ e.getMessage());
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/media_capture/{school_id}/{user_id}", method = RequestMethod.GET)
	public ResponseEntity<List<Restricted_Media_Access>> geturl(@PathVariable Long school_id, 
																@PathVariable Long user_id) {
		Connection c = null;
		ResultSet rst;
		ArrayList<Restricted_Media_Access> u = new ArrayList<Restricted_Media_Access>();
		
		try {
			c = DBConnection.getDBConnection();
			String sql = "Select url from media_captured where school_id =? and user_id = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, school_id);
			pstmt.setLong(2, user_id);

			rst = pstmt.executeQuery();

		while (rst.next()) {
				Restricted_Media_Access u1 = new Restricted_Media_Access();
				u1.seturl(rst.getString("url"));
				u.add(u1);
			}
			pstmt.close();
			c.close();
		return new ResponseEntity<List<Restricted_Media_Access>>(u,
					HttpStatus.OK);
		}
		catch (Exception e) {
		throw new GenericException("Getting url failed:" + e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/notifications/{school_id}/{to_date}", method = RequestMethod.GET)
	public ResponseEntity<List<Notification>> getNotificationsForSchool(@PathVariable Long school_id,
																		@PathVariable String to_date) {
			Connection c = null;
			ResultSet res;
			ArrayList<Notification> nArr = new ArrayList<Notification>();

		try {
			c = DBConnection.getDBConnection();

			String sql = "select n.id, from_date ,to_date, title, message, created_by, weekday1, weekday2, weekday3, weekday4, weekday5, weekday6, weekday7 from notifications n, users u where u.name= n.created_by and school_id = ? and to_date>= ?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, school_id);
			pstmt.setString(2, to_date);

			res = pstmt.executeQuery();

		while (res.next()) {
				Notification n = new Notification();
				n.setid(res.getLong("id"));
				n.setfrom_date(res.getString("from_date"));
				n.setto_date(res.getString("to_date"));
				n.settitle(res.getString("title"));
				n.setmessage(res.getString("message"));
				n.setcreated_by(res.getString("created_by"));
				n.setweekday1(res.getString("weekday1"));
				n.setweekday2(res.getString("weekday2"));
				n.setweekday3(res.getString("weekday3"));
				n.setweekday4(res.getString("weekday4"));
				n.setweekday5(res.getString("weekday5"));
				n.setweekday6(res.getString("weekday6"));
				n.setweekday7(res.getString("weekday7"));

				nArr.add(n);
			}
			pstmt.close();
			res.close();
			c.close();

		return new ResponseEntity<List<Notification>>(nArr, HttpStatus.OK);
		}
		catch (Exception e) {
		throw new GenericException("Getting notifications for school failed:" + e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/notifications/{school_id}", method = RequestMethod.POST)
	public String addNotificationToSchool(@PathVariable("school_id") Long id,
										  @RequestBody Notification notification) {

		try {
		if (notification == null) {
		throw new Exception("req. quiz missing");
			}
			String op = "{ }";
			String from_date = notification.getfrom_date();
			String to_date = notification.getto_date();
			String title = notification.gettitle();
			String message = notification.getmessage();
			String created_by = notification.getcreated_by();
			Long school_id = notification.getschool_id();
			String weekday1 = notification.getweekday1();
			String weekday2 = notification.getweekday2();
			String weekday3 = notification.getweekday3();
			String weekday4 = notification.getweekday4();
			String weekday5 = notification.getweekday5();
			String weekday6 = notification.getweekday6();
			String weekday7 = notification.getweekday7();

			Connection c = null;
			c = DBConnection.getDBConnection();
			String sql = "insert into notifications (from_date, to_date, title, message, school_id, created_by, weekday1, weekday2, weekday3, weekday4, weekday5, weekday6, weekday7)	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstm = c.prepareStatement(sql);
			pstm.setString(1, from_date);
			pstm.setString(2, to_date);
			pstm.setString(3, title);
			pstm.setString(4, message);
			pstm.setLong(5, school_id);
			pstm.setString(6, created_by);
			pstm.setString(7, weekday1);
			pstm.setString(8, weekday2);
			pstm.setString(9, weekday3);
			pstm.setString(10, weekday4);
			pstm.setString(11, weekday5);
			pstm.setString(12, weekday6);
			pstm.setString(13, weekday7);

			pstm.execute();
			pstm.close();
			c.close();
		return op;
		} 
		catch (Exception e) {
		throw new GenericException(" Insert notification failed:" + e.getMessage());

		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/notifications/{id}", method = RequestMethod.PUT)
	public String updatenotification(@PathVariable Long id,
									 @RequestBody Notification nn) {

		String op = "{ }";

		Connection c = null;

		try {

			c = DBConnection.getDBConnection();

			String sql = "update notifications set from_date =? ,to_date =?, title=?, message=?, created_by=?, weekday1=?, weekday2=?, weekday3=?, weekday4 =?, weekday5= ?, weekday6 =?, weekday7 =? where id = ?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, nn.getfrom_date());
			pstmt.setString(2, nn.getto_date());
			pstmt.setString(3, nn.gettitle());
			pstmt.setString(4, nn.getmessage());
			pstmt.setString(5, nn.getcreated_by());
			pstmt.setString(6, nn.getweekday1());
			pstmt.setString(7, nn.getweekday2());
			pstmt.setString(8, nn.getweekday3());
			pstmt.setString(9, nn.getweekday4());
			pstmt.setString(10, nn.getweekday5());
			pstmt.setString(11, nn.getweekday6());
			pstmt.setString(12, nn.getweekday7());
			pstmt.setLong(13, id);

			pstmt.execute();
			pstmt.close();
			c.close();
		return op;

		}
		catch (Exception e) {
		throw new GenericException("Update notifications for class failed:"	+ e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/notifications/{id}", method = RequestMethod.DELETE)
	public String removenotificationFromClass(@PathVariable Long id) {

			String op = "{ }";
			Connection c = null;

		try {
			c = DBConnection.getDBConnection();

			String sql = "delete from notifications where id = ?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, id);

			pstmt.execute();
			pstmt.close();
			c.close();

		} 
		catch (Exception e) {
		throw new GenericException("Delete notification failed:"+ e.getMessage());
		}
		return op;

	}

	
	@CrossOrigin()
	@RequestMapping(value = "/quiz/{classes}/{subject}", method = RequestMethod.GET)
	public ResponseEntity<List<Quiz>> getquizquestions(	@PathVariable Long classes,
														@PathVariable String subject) {
			Connection c = null;
			ResultSet rst;
			ArrayList<Quiz> q = new ArrayList<Quiz>();
			
		try {
			
			c = DBConnection.getDBConnection();
			
			String sql = "select questions,option1,option2,option3,option4, answer from quiz where classes =? and subject=?";
			
			PreparedStatement pstm = c.prepareStatement(sql);
			pstm.setLong(1, classes);
			pstm.setString(2, subject);
			rst = pstm.executeQuery();
			
		while (rst.next()) {
			Quiz q1 = new Quiz();
			q1.setquestions(rst.getString("questions"));
			q1.setoption1(rst.getString("option1"));
			q1.setoption2(rst.getString("option2"));
			q1.setoption3(rst.getString("option3"));
			q1.setoption4(rst.getString("option4"));
			q1.setanswer(rst.getString("answer"));
			q.add(q1);
			}
			pstm.close();
			c.close();
		return new ResponseEntity<List<Quiz>>(q, HttpStatus.OK);
		}
		catch (Exception e) {
		throw new GenericException("Getting questions failed:" + e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "quiz/{classes}/{subject}", method = RequestMethod.POST)
	public String addQuiz(@PathVariable Long classes,
						  @PathVariable String subject,
						  @RequestBody Quiz q) {
		try {
		if (q == null) {
		throw new Exception("req. quiz missing");
		
		}
			String op = "{ }";
			String questions = q.getquestions();
			String option1 = q.getoption1();
			String option2 = q.getoption2();
			String option3 = q.getoption3();
			String option4 = q.getoption4();
			String answer = q.getanswer();
			Long school_id = q.getschool_id();
			
			Connection c = null;
			c = DBConnection.getDBConnection();
			String sql = "insert into quiz(classes, subject, questions, option1, option2, option3, option4, answer, school_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement pstm = c.prepareStatement(sql);
			pstm.setLong(1, classes);
			pstm.setString(2, subject);
			pstm.setString(3, questions);
			pstm.setString(4, option1);
			pstm.setString(5, option2);
			pstm.setString(6, option3);
			pstm.setString(7, option4);
			pstm.setString(8, answer);
			pstm.setLong(9, school_id);
			pstm.execute();
			pstm.close();
			c.close();
		return op;
		}
		catch (Exception e) {
		throw new GenericException(" Insert quiz failed:" + e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/login/{phone_number}", method = RequestMethod.GET)
	public ResponseEntity<Login> getlogindetails(@PathVariable Long phone_number) {
			Connection c = null;
			ResultSet rst;
			Login log = new Login();
			
		try {
			
			c = DBConnection.getDBConnection();
			
			String sql = "select login.first_name, login.last_name,login.login_id, password, master_db.student_name,master_db.standard,master_db.section,master_db.email,master_db.phone_number from login left join master_db on login.login_id= master_db.login_id where login.phone_number=?";
			
			PreparedStatement pstm = c.prepareStatement(sql);
			pstm.setLong(1, phone_number);
			rst = pstm.executeQuery();
		while (rst.next()) {
				log.setfirst_name(rst.getString("first_name"));
				log.setlast_name(rst.getString("last_name"));
				log.setpassword(rst.getString("password"));
				log.setstudent_name(rst.getString("student_name"));
				log.setlogin_id(rst.getString("login_id"));
				log.setsection(rst.getString("section"));
				log.setstandard(rst.getString("standard"));
				log.setemail(rst.getString("email"));
				log.setphone_number(rst.getLong("phone_number"));
			}
			pstm.close();
			c.close();
		return new ResponseEntity<Login>(log, HttpStatus.OK);
		}
		catch (Exception e) {
		throw new GenericException("gettiong login details failed:"	+ e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/login/{phone_number}", method = RequestMethod.POST)
	public String addlogindetails(@PathVariable Long phone_number,
								  @RequestBody Login log) {
		try {
		if (log == null) {
		throw new Exception("req login missing");
			}
		
			String op = "{ }";
			String first_name = log.getfirst_name();
			String last_name = log.getlast_name();
			String password = log.getpassword();
			String login_id = log.getlogin_id();
			String email = log.getemail();
			String forget_password = log.getforget_password();
			
			Connection c = null;
			c = DBConnection.getDBConnection();
			
			String sql = "insert into login (first_name,last_name,login_id,password,phone_number,email,forget_password) values(?,?,?,?,?,?,?)";
			
			PreparedStatement pstm = c.prepareStatement(sql);
			pstm.setString(1, first_name);
			pstm.setString(2, last_name);
			pstm.setString(3, login_id);
			pstm.setString(4, password);
			pstm.setLong(5, phone_number);
			pstm.setString(6, email);
			pstm.setString(7, forget_password);
			pstm.execute();
			pstm.close();
			c.close();
		return op;
		}
		catch (Exception e) {
		throw new GenericException(" Insert login failed:" + e.getMessage());

		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/login/{phone_number}", method = RequestMethod.PUT)
	public String updatelogindetails(@PathVariable Long phone_number,
									 @RequestBody Login log) {

		try {
			String op = "{ }";

			Connection c = null;

			c = DBConnection.getDBConnection();
			
			String sql = "update login set first_name= ? and last_name=? and login_id= ? and email=? and where phone_number=?";
			
			PreparedStatement pstm = c.prepareStatement(sql);
			
			pstm.setString(1, log.getfirst_name());
			pstm.setString(2, log.getlast_name());
			pstm.setString(3, log.getlogin_id());
			pstm.setString(4, log.getemail());
			pstm.setLong(5, phone_number);

			pstm.execute();
			pstm.close();
			c.close();
		return op;
		}
		catch (Exception e) {
		throw new GenericException(" Update login failed:" + e.getMessage());

		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/master_db/{student_id}", method = RequestMethod.GET)
	public ResponseEntity<List<Master_db>> getmaster_dbdetails(	@PathVariable Long student_id) {
			Connection c = null;
			ResultSet rst;
			ArrayList<Master_db> mas = new ArrayList<Master_db>();
		
		try {
			
			c = DBConnection.getDBConnection();
			
			String sql = "select student_name, login_id, standard, section, email, phone_number from master_db where student_id=?";
			
			PreparedStatement pstm = c.prepareStatement(sql);
			pstm.setLong(1, student_id);
			rst = pstm.executeQuery();
		while (rst.next()) {
				Master_db mas1 = new Master_db();
				mas1.setstudent_name(rst.getString("student_name"));
				mas1.setstudent_id(rst.getString("login_id"));
				mas1.setsection(rst.getString("section"));
				mas1.setstandard(rst.getString("standard"));
				mas1.setemail(rst.getString("email"));
				mas1.setphone_number(rst.getLong("phone_number"));
				mas.add(mas1);
			}
			pstm.close();
			c.close();
		return new ResponseEntity<List<Master_db>>(mas, HttpStatus.OK);
		}
		catch (Exception e) {
		throw new GenericException("getting student details failed:"+ e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/master_db/{phone_number}/phonenumber", method = RequestMethod.GET)
	public ResponseEntity<List<Master_db>> getphonedetails(	@PathVariable Long phone_number) {
			Connection c = null;
			ResultSet rst;
			ArrayList<Master_db> ma = new ArrayList<Master_db>();
			
		try {
			
			c = DBConnection.getDBConnection();
			
			String sql = "select student_name, login_id, standard, section, email, roll, phone_number, student_id, school_id from master_db where phone_number=?";
			
			PreparedStatement pstm = c.prepareStatement(sql);
			pstm.setLong(1, phone_number);
			rst = pstm.executeQuery();
		while (rst.next()) {
				Master_db ma1 = new Master_db();
				ma1.setstudent_name(rst.getString("student_name"));
				ma1.setstudent_id(rst.getString("login_id"));
				ma1.setsection(rst.getString("section"));
				ma1.setstandard(rst.getString("standard"));
				ma1.setemail(rst.getString("email"));
				ma1.setroll(rst.getLong("roll"));
				ma1.setphone_number(rst.getLong("phone_number"));
				ma1.setstudent_id(rst.getLong("student_id"));
				ma1.setschool_id(rst.getLong("school_id"));

				ma.add(ma1);
			}
			pstm.close();
			c.close();
		return new ResponseEntity<List<Master_db>>(ma, HttpStatus.OK);
		}
		catch (Exception e) {
		throw new GenericException("getting student details failed:"+ e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/master_db/{phone_number}", method = RequestMethod.POST)
	public String addmaster_dbdetails(@PathVariable Long phone_number,
									  @RequestBody Master_db mas) {
		try {
		if (mas == null) {
		throw new Exception("req master_db missing");
			}

			String op = "{ }";
			String student_name = mas.getstudent_name();
			String standard = mas.getstandard();
			String section = mas.getsection();
			String login_id = mas.getlogin_id();
			String email = mas.getemail();
			Long roll = mas.getroll();

			Connection c = null;
			c = DBConnection.getDBConnection();
			
			String sql = "insert into master_db (student_name, login_id, standard, section, email, phone_number, roll ) values(?,?,?,?,?,?,?)";
			
			PreparedStatement pstm = c.prepareStatement(sql);
			pstm.setString(1, student_name);
			pstm.setString(2, login_id);
			pstm.setString(3, standard);
			pstm.setString(4, section);
			pstm.setString(5, email);
			pstm.setLong(6, phone_number);
			pstm.setLong(7, roll);

			pstm.execute();
			pstm.close();
			c.close();
		return op;
		}
		catch (Exception e) {
		throw new GenericException(" Insert master_db failed:"	+ e.getMessage());

		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/master_db/{phone_number}", method = RequestMethod.PUT)
	public String updatephonenumber(@PathVariable Long phone_number,
									@RequestBody Master_db master) {
		String op = "{ }";

		Connection c = null;

		try {

			c = DBConnection.getDBConnection();

			String sql = "update master_db set student_name= ? and login_id=? and standard= ? and section=? and email=? and roll=? where phone_number = ? ";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, master.getstudent_name());
			pstmt.setString(2, master.getlogin_id());
			pstmt.setString(3, master.getstandard());
			pstmt.setString(4, master.getsection());
			pstmt.setString(5, master.getemail());
			pstmt.setLong(6, master.getroll());
			pstmt.setLong(7, phone_number);

			pstmt.execute();
			pstmt.close();
			c.close();
			return op;

		} 
		catch (Exception e) {
		throw new GenericException("Update phone_number for class failed:"	+ e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/class_diary1/{school_id}/{class_id}/{section}/{activity}/{end_date}", method = RequestMethod.GET)
	public ResponseEntity<List<Dailydiary1>> getForSchool(@PathVariable Long school_id,
														  @PathVariable Long class_id,
														  @PathVariable String section,
														  @PathVariable String activity,
														  @PathVariable String end_date) {
			Connection c = null;
			ResultSet res;
			ArrayList<Dailydiary1> nArr = new ArrayList<Dailydiary1>();
		try {
			c = DBConnection.getDBConnection();
			
			String sql = "select section, end_date, title, message, subject, created_by, school_id, activity, class_id, id from class_diary1 where school_id=? and class_id=? and section=? and activity=? and end_date>=?";
			
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, school_id);
			pstmt.setLong(2, class_id);
			pstmt.setString(3, section);
			pstmt.setString(4, activity);
			pstmt.setString(5, end_date);

			res = pstmt.executeQuery();
		while (res.next()) {
		
			Dailydiary1 n = new Dailydiary1();
			n.setsection(res.getString("section"));
			n.setend_date(res.getString("end_date"));
			n.settitle(res.getString("title"));
			n.setmessage(res.getString("message"));
			n.setsubject(res.getString("subject"));
			n.setcreated_by(res.getString("created_by"));
			n.setschool_id(res.getLong("school_id"));
			n.setactivity(res.getString("activity"));
			n.setclass_id(res.getLong("class_id"));
			n.setid(res.getLong("id"));

			nArr.add(n);
			}
			pstmt.close();
			res.close();
			c.close();
		return new ResponseEntity<List<Dailydiary1>>(nArr, HttpStatus.OK);
		}
		catch (Exception e) {
		throw new GenericException(	"Getting notifications for school failed:" + e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/class_diary1/{school_id}/{class_id}", method = RequestMethod.GET)
	public ResponseEntity<List<Dailydiary>> getForSchool(@PathVariable Long school_id,
														 @PathVariable Long class_id) {
			Connection c = null;
			ResultSet res;
			ArrayList<Dailydiary> nArr = new ArrayList<Dailydiary>();
		
			try {
			
				c = DBConnection.getDBConnection();
			
				String sql = "select distinct section from class_diary1 where school_id=? and class_id=?";
		
				PreparedStatement pstmt = c.prepareStatement(sql);
				pstmt.setLong(1, school_id);
				pstmt.setLong(2, class_id);
				res = pstmt.executeQuery();
			while (res.next()) {
				Dailydiary n = new Dailydiary();
				n.setsection(res.getString("section"));
				nArr.add(n);
				}
				pstmt.close();
				res.close();
				c.close();
			return new ResponseEntity<List<Dailydiary>>(nArr, HttpStatus.OK);
			} 
			catch (Exception e) {
			throw new GenericException("Getting notifications for school failed:" + e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "class_diary1/{class_id}/{section}", method = RequestMethod.POST)
	public String addclassdiaryToSchool(@PathVariable Long class_id,
										@PathVariable String section, 
										@RequestBody Dailydiary diary) {

		try {
		if (diary == null) {
		throw new Exception("req. body missing");
			}

			String op = "{ }";
			String end_date = diary.getend_date();
			String title = diary.gettitle();
			String message = diary.getmessage();
			String subject = diary.getsubject();
			String activity = diary.getactivity();
			Long school_id = diary.getschool_id();

			Connection c = null;
			c = DBConnection.getDBConnection();
			String sql = "insert into class_diary1 (end_date, title, message, subject, activity, class_id, section, school_id) values (?,?,?,?,?,?,?,?)";

			PreparedStatement pstmt = c.prepareStatement(sql);

			pstmt.setString(1, end_date);
			pstmt.setString(2, title);
			pstmt.setString(3, message);
			pstmt.setString(4, subject);
			pstmt.setString(5, activity);
			pstmt.setLong(6, class_id);
			pstmt.setString(7, section);
			pstmt.setLong(8, school_id);

			pstmt.execute();
			pstmt.close();
			c.close();
		return op;

		}
		catch (Exception e) {
		throw new GenericException("Adding dailydiary failed:"	+ e.getMessage());
		}

	}

	@CrossOrigin()
	@RequestMapping(value = "/class_diary1/{id}", method = RequestMethod.PUT)
	public String updateclassdiaryToSchool(@PathVariable Long id,
											@RequestBody Dailydiary1 diary) {
			String op = "{ }";
			Connection c = null;

		try {

			c = DBConnection.getDBConnection();

			String sql = "update class_diary1 set  end_date = ?, title = ?, message = ?, subject = ?, activity= ?, created_by =?, school_id =?, class_id =?, section =? where id = ?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, diary.getend_date());
			pstmt.setString(2, diary.gettitle());
			pstmt.setString(3, diary.getmessage());
			pstmt.setString(4, diary.getsubject());
			pstmt.setString(5, diary.getactivity());
			pstmt.setString(6, diary.getcreated_by());
			pstmt.setLong(7, diary.getschool_id());
			pstmt.setLong(8, diary.getclass_id());
			pstmt.setString(9, diary.getsection());
			pstmt.setLong(10, id);

			pstmt.execute();
			pstmt.close();
			c.close();
		return op;

		}
		catch (Exception e) {
		throw new GenericException("Update dailydiary for class failed:"+ e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/class_diary1/{id}/delete", method = RequestMethod.DELETE)
	public String removeDiaryFromClass(@PathVariable Long id) {

			String op = "{ }";
			Connection c = null;

		try {
			c = DBConnection.getDBConnection();

			String sql = "delete from class_diary1 where id = ?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, id);

			pstmt.execute();
			pstmt.close();
			c.close();

		} 
		catch (Exception e) {
		throw new GenericException("Delete dailydiary failed:" + e.getMessage());
		}
		return op;

	}

	@CrossOrigin()
	@RequestMapping(value = "/exam_timetable/{school_id}/{class_id}/{date}", method = RequestMethod.GET)
	public ResponseEntity<List<Exam_timetable>> gettimetableforexam(@PathVariable Long school_id,
																	@PathVariable Long class_id,
																	@PathVariable String date) {
			Connection c = null;
			ResultSet rst;
			ArrayList<Exam_timetable> et = new ArrayList<Exam_timetable>();
			
		try {
			c = DBConnection.getDBConnection();
			
			String sql = "select school_id, class_id, date, from_time, end_time, exam_type, subject, id, syllabus from exam_timetable where school_id=? and class_id= ? and date>=?";
			
			PreparedStatement pstm = c.prepareStatement(sql);
			pstm.setLong(1, school_id);
			pstm.setLong(2, class_id);
			pstm.setString(3, date);

			rst = pstm.executeQuery();
		while (rst.next()) {
			Exam_timetable et1 = new Exam_timetable();
			et1.setschool_id(rst.getLong("school_id"));
			et1.setclass_id(rst.getLong("class_id"));
			et1.setdate(rst.getString("date"));
			et1.setfrom_time(rst.getString("from_time"));
			et1.setend_time(rst.getString("end_time"));
			et1.setexam_type(rst.getString("exam_type"));
			et1.setsubject(rst.getString("subject"));
			et1.setid(rst.getLong("id"));
			et1.setsyllabus(rst.getString("syllabus"));
			et.add(et1);
			}
			pstm.close();
			c.close();
		return new ResponseEntity<List<Exam_timetable>>(et, HttpStatus.OK);
		}
		catch (Exception e) {
		throw new GenericException("getting exam timetable details failed:"	+ e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/exam_timetable/{school_id}/{class_id}", method = RequestMethod.POST)
	public String addexam_timetable(@PathVariable Long school_id,
									@PathVariable Long class_id,
									@RequestBody Exam_timetable et) {
		try {
			if (et == null) {
				throw new Exception("req exam_timetable missing");
			}
			String op = "{ }";
			String date = et.getdate();
			String from_time = et.getfrom_time();
			String end_time = et.getend_time();
			String exam_type = et.getexam_type();
			String subject = et.getsubject();
			String syllabus = et.getsyllabus();

			Connection c = null;
			c = DBConnection.getDBConnection();

			String sql = "insert into exam_timetable (date, from_time, end_time, exam_type, subject, school_id, class_id, syllabus ) values(?,?,?,?,?,?,?,?)";
			PreparedStatement pstm = c.prepareStatement(sql);
			pstm.setString(1, date);
			pstm.setString(2, from_time);
			pstm.setString(3, end_time);
			pstm.setString(4, exam_type);
			pstm.setString(5, subject);
			pstm.setLong(6, school_id);
			pstm.setLong(7, class_id);
			pstm.setString(8, syllabus);

			pstm.execute();
			pstm.close();
			c.close();
		return op;
		}
		catch (Exception e) {
		throw new GenericException(" Insert exam_timetable failed:"	+ e.getMessage());

		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/exam_timetable/{id}", method = RequestMethod.PUT)
	public String updateexam_timetable(@PathVariable Long id,
									   @RequestBody Exam_timetable et) {

			String op = "{ }";
			Connection c = null;

		try {

			c = DBConnection.getDBConnection();

			String sql = "update exam_timetable set date =?, from_time =?, end_time=?, exam_type =?, subject =?, syllabus=? where id = ?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, et.getdate());
			pstmt.setString(2, et.getfrom_time());
			pstmt.setString(3, et.getend_time());
			pstmt.setString(4, et.getexam_type());
			pstmt.setString(5, et.getsubject());
			pstmt.setString(6, et.getsyllabus());
			pstmt.setLong(7, id);

			pstmt.execute();
			pstmt.close();
			c.close();
		return op;

		}
		catch (Exception e) {
		throw new GenericException("Update exam_timetable for class failed:" + e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/exam_timetable/{id}", method = RequestMethod.DELETE)
	public String removeexamFromClass(@PathVariable Long id) {

			String op = "{ }";
			Connection c = null;

		try {
			c = DBConnection.getDBConnection();

			String sql = "delete from exam_timetable where id = ?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, id);

			pstmt.execute();
			pstmt.close();
			c.close();

		} 
		catch (Exception e) {
		throw new GenericException("Delete exam timetable failed:"+ e.getMessage());
		}
		return op;

	}

	@CrossOrigin()
	@RequestMapping(value = "/result/{roll_no}/{exam_type}", method = RequestMethod.GET)
	public ResponseEntity<List<Result>> getResults(@PathVariable String roll_no,
												   @PathVariable String exam_type) {
			Connection c = null;
			ResultSet res;
			ArrayList<Result> rs = new ArrayList<Result>();

		try {

			c = DBConnection.getDBConnection();

			String sql = "select exam_type, section, standard, roll_no,subject, mark, out_of_mark from result where roll_no = ? and exam_type=?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, roll_no);
			pstmt.setString(2, exam_type);

			res = pstmt.executeQuery();
		while (res.next()) {
			Result r = new Result();
			r.setexam_type(res.getString("exam_type"));
			r.setsubject(res.getString("subject"));
			r.setstandard(res.getString("standard"));
			r.setsection(res.getString("section"));
			r.setroll_no(res.getString("roll_no"));
			r.setmark(res.getLong("mark"));
			r.setout_of_mark(res.getLong("out_of_mark"));
			rs.add(r);
			}

			pstmt.close();
			c.close();
		return new ResponseEntity<List<Result>>(rs, HttpStatus.OK);

		}
		catch (Exception e) {
		throw new GenericException("Get time table for class failed:"+ e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/result/{roll_no}", method = RequestMethod.POST)
	public String addTimeTableBlankEntry(@PathVariable String roll_no,
										 @RequestBody Result rr) {

		try {
			if (rr == null) {
				throw new Exception("req. body missing");
			}
			
			String op = "{ }";
			
			String exam_type = rr.getexam_type();
			String subject = rr.getsubject();
			String standard = rr.getstandard();
			String section = rr.getsection();
			Long mark = rr.getmark();
			Long out_of_mark = rr.getout_of_mark();

			Connection c = null;

			c = DBConnection.getDBConnection();

			String sql = "insert into result(exam_type, subject, standard, section, roll_no, mark, out_of_mark) VALUES (?, ?, ?, ?, ?, ?,?)";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, exam_type);
			pstmt.setString(2, standard);
			pstmt.setString(3, section);
			pstmt.setString(4, roll_no);
			pstmt.setString(5, subject);
			pstmt.setLong(6, mark);
			pstmt.setLong(7, out_of_mark);

			pstmt.execute();
			pstmt.close();
			c.close();
		return op;

		}
		catch (Exception e) {
		throw new GenericException("Post Result for class failed:"	+ e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/parent_teacher_meeting/{roll_no}", method = RequestMethod.GET)
	public ResponseEntity<ParentTeacherMeeting> getdetails(	@PathVariable String roll_no) {
		
			Connection c = null;
			ResultSet rst;
			ParentTeacherMeeting log = new ParentTeacherMeeting();
		
		try {
			c = DBConnection.getDBConnection();
			
			String sql = "select school_id, class_id, section, roll_no, teacher_message, student_message from parent_teacher_meeting where roll_no = ?";
			
			PreparedStatement pstm = c.prepareStatement(sql);
			pstm.setString(1, roll_no);
			rst = pstm.executeQuery();
		while (rst.next()) {
			log.setschool_id(rst.getLong("school_id"));
			log.setclass_id(rst.getLong("class_id"));
			log.setsection(rst.getString("section"));
			log.setteacher_message(rst.getString("teacher_message"));
			log.setstudent_message(rst.getString("student_message"));
			log.setroll_no(rst.getString("roll_no"));
		}
			pstm.close();
			c.close();
		return new ResponseEntity<ParentTeacherMeeting>(log, HttpStatus.OK);
		}
		catch (Exception e) {
		throw new GenericException("gettiong meeting details failed:"+ e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/parent_teacher_meeting/{school_id}/{class_id}/{section}", method = RequestMethod.POST)
	public String adddetails(@PathVariable Long school_id, 
							 @PathVariable Long class_id, 
							 @PathVariable String section, 
							 @RequestBody ParentTeacherMeeting log) {
		
		try {
			if (log == null) {
				throw new Exception("req login missing");
			}
			String op = "{ }";
			String teacher_message = log.getteacher_message();
			String student_message = log.getstudent_message();
			String roll_no = log.getroll_no();

			Connection c = null;
			c = DBConnection.getDBConnection();
			
			String sql = "insert into parent_teacher_meeting (school_id, class_id, section, roll_no, teacher_message, student_message) values(?, ?, ?, ?, ?, ?)";
			
			PreparedStatement pstm = c.prepareStatement(sql);
			pstm.setLong(1, school_id);
			pstm.setLong(2, class_id);
			pstm.setString(3, section);
			pstm.setString(4, roll_no);
			pstm.setString(5, teacher_message);
			pstm.setString(6, student_message);
			pstm.execute();
			pstm.close();
			c.close();
		return op;
		}
		catch (Exception e) {
		throw new GenericException(" Insert file failed:" + e.getMessage());

		}
	}

	@ExceptionHandler(GenericException.class)
	public ResponseEntity<ExceptionDetail> myError(HttpServletRequest request,Exception exception) {
		System.out.println("ClassController Exception:"	+ exception.getLocalizedMessage());
		ExceptionDetail error = new ExceptionDetail();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exception.getLocalizedMessage());
		error.setUrl(request.getRequestURL().toString());
		return new ResponseEntity<ExceptionDetail>(error,
				HttpStatus.BAD_REQUEST);
	}

}
