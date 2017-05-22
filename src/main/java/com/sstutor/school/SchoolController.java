package com.sstutor.school;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sstutor.common.DBConnection;
import com.sstutor.exception.ExceptionDetail;
import com.sstutor.exception.GenericException;
import com.sstutor.model.Classes;
import com.sstutor.model.Dailydiary;
import com.sstutor.model.PeriodTypes;
import com.sstutor.model.SchoolTeacherRole;
import com.sstutor.model.Subject;
import com.sstutor.model.Teacher;
import com.sstutor.model.Classes1;
import com.sstutor.model.Student_name;



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
public class SchoolController {

		
	@CrossOrigin()
	@RequestMapping(value = "/schools/{school_id}/classes", method = RequestMethod.GET)
	public ResponseEntity<List<Classes>> getAllClassesForSchool(@PathVariable("school_id") Long id) {

		Connection c = null;
		ResultSet res;
		ArrayList<Classes> classes = new ArrayList<Classes>();

		try {
			c = DBConnection.getDBConnection();
			String sql = "select distinct on (standard)standard, id, section, academic_year from school_class_year "
					+ "where school_id = ? order by  standard asc, section asc, academic_year desc;";

			
/*			String sql = "select id, standard, section, academic_year from school_class_year "
					+ "where school_id = ? order by academic_year desc, standard asc, section asc;";
*/
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, id);

			res = pstmt.executeQuery();

			while (res.next()) {
				Classes cs = new Classes();
				cs.setId(res.getLong("id"));
				cs.setAcademic_year(res.getInt("academic_year"));
				cs.setSection(res.getString("section"));
				cs.setStandard(res.getString("standard"));
				classes.add(cs);
			}
			pstmt.close();
			res.close();
			c.close();

			return new ResponseEntity<List<Classes>>(classes, HttpStatus.OK);

		} catch (Exception e) {
			throw new GenericException("Getting classes for school failed:" + e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/schools/{school_id}/classes", method = RequestMethod.POST)
	public String createClassForSchool(@PathVariable("school_id") Long id, @RequestBody Classes cl) {

		try {

			if (cl == null) {
				throw new Exception("No request body");
			}

			String standard = cl.getStandard();
			String section = cl.getSection();
			int acad_year = cl.getAcademic_year();

			String op = "{ }";

			System.out.println("Hello there starting create class:" + standard + section + acad_year);

			Connection c = null;

			c = DBConnection.getDBConnection();

			String sql = "insert into school_class_year (school_id, standard, section, academic_year) "
					+ " values (?,?,?,?)";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, id);
			pstmt.setString(2, standard);
			pstmt.setString(3, section);
			pstmt.setInt(4, acad_year);

			pstmt.execute();

			pstmt.close();
			c.close();
			return op;

		} catch (Exception e) {
			throw new GenericException("Create class for school failed:" + e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/schools/{school_id}/{class_id}/subjects", method = RequestMethod.GET)
	public ResponseEntity<List<Subject>> getAllSubjectsForSchool(@PathVariable Long school_id,@PathVariable String class_id) {
		Connection c = null;
		ResultSet res;
		ArrayList<Subject> subjects = new ArrayList<Subject>();

		try {
			c = DBConnection.getDBConnection();

			String sql = "select id, subject from school_subject where school_id=? and class_id=?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, school_id);
			pstmt.setString(2, class_id);

			res = pstmt.executeQuery();

			while (res.next()) {
				Subject s = new Subject();
				//s.setSchool_subject_id(Long.parseLong(res.getString("id")));
				s.setSchool_subject_id(res.getLong("id"));
				s.setSubject_name(res.getString("subject"));
				subjects.add(s);
			}
			pstmt.close();
			res.close();
			c.close();

			return new ResponseEntity<List<Subject>>(subjects, HttpStatus.OK);
		} catch (Exception e) {
			throw new GenericException("Get all subjects for school failed:" + e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/schools/{school_id}/teachers", method = RequestMethod.GET)
	public ResponseEntity<List<Teacher>> getAllTeachersForSchool(@PathVariable("school_id") Long id) {
		Connection c = null;
		ResultSet res;
		ArrayList<Teacher> teachers = new ArrayList<Teacher>();

		try {
			c = DBConnection.getDBConnection();

			String sql = "select t.id as tid, t.user_id, u.name as name from teacher t, users u where u.id = t.user_id and t.school_id=?";

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
		} catch (Exception e) {
			throw new GenericException("Getting teachers for school failed:" + e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/schools/{school_id}/teacherroles", method = RequestMethod.GET)
	public ResponseEntity<List<SchoolTeacherRole>> getAllTeachersRolesForSchool(@PathVariable("school_id") Long id) {
		Connection c = null;
		ResultSet res;
		ArrayList<SchoolTeacherRole> roles = new ArrayList<SchoolTeacherRole>();

		try {
			c = DBConnection.getDBConnection();

			String sql = "select id, school_id, role_name, role_desc from school_teacher_roles where school_id=?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, id);

			res = pstmt.executeQuery();

			while (res.next()) {
				SchoolTeacherRole r = new SchoolTeacherRole();
				r.setRole_desc(res.getString("role_desc"));
				r.setRole_id(res.getLong("id"));
				r.setRole_name(res.getString("role_name"));
				roles.add(r);
			}
			pstmt.close();
			res.close();
			c.close();

			return new ResponseEntity<List<SchoolTeacherRole>>(roles, HttpStatus.OK);
		} catch (Exception e) {
			throw new GenericException("Getting teacher roles for school failed:" + e.getMessage());
		}
	}

	@CrossOrigin()
	@RequestMapping(value = "/schools/{school_id}/periodtypes", method = RequestMethod.GET)
	public ResponseEntity<List<PeriodTypes>> getPeriodTypesForSchool(@PathVariable("school_id") Long id) {
		Connection c = null;
		ResultSet res;
		ArrayList<PeriodTypes> types = new ArrayList<PeriodTypes>();

		try {
			c = DBConnection.getDBConnection();

			String sql = "select type, comments, value from school_period_types where school_id=?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, id);

			res = pstmt.executeQuery();

			while (res.next()) {
				PeriodTypes p = new PeriodTypes();
				p.setComments(res.getString("comments"));
				p.setType(res.getString("type"));
				p.setValue(res.getString("value"));
				types.add(p);
			}
			pstmt.close();
			res.close();
			c.close();

			return new ResponseEntity<List<PeriodTypes>>(types, HttpStatus.OK);
		} catch (Exception e) {
			throw new GenericException("Getting period types for school failed:" + e.getMessage());
		}
	}
	
	
	@CrossOrigin()
	@RequestMapping(value = "/schools/{school_id}/{standard}/section", method = RequestMethod.GET)
	public ResponseEntity<List<Classes1>> getAllClassesForSchool(@PathVariable Long school_id, @PathVariable String standard) {

		Connection c = null;
		ResultSet res;
		ArrayList<Classes1> classes = new ArrayList<Classes1>();

		try {
			c = DBConnection.getDBConnection();
			String sql = "select id, standard, section, academic_year from school_class_year "
					+ "where school_id = ? and standard=? order by  section asc, academic_year desc;";

			
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, school_id);
			pstmt.setString(2, standard);

			res = pstmt.executeQuery();

			while (res.next()) {
				Classes1 cs = new Classes1();
				cs.setId(res.getLong("id"));
				cs.setAcademic_year(res.getInt("academic_year"));
				cs.setSection(res.getString("section"));
				cs.setStandard(res.getString("standard"));
				classes.add(cs);
			}
			pstmt.close();
			res.close();
			c.close();

			return new ResponseEntity<List<Classes1>>(classes, HttpStatus.OK);

		} catch (Exception e) {
			throw new GenericException("Getting classes for school failed:" + e.getMessage());
		}
	}
	
	@CrossOrigin()
	@RequestMapping(value = "/classes/{school_id}/{standard}/{section}/student", method = RequestMethod.GET)
	public ResponseEntity<List<Student_name>> getAttendanceForPeriod(@PathVariable Long school_id, @PathVariable String standard, @PathVariable String section) {
		Connection c = null;
		ResultSet res;
		ArrayList<Student_name> atts = new ArrayList<Student_name>();

		try {

			c = DBConnection.getDBConnection();

			String sql = "select student_id, student_name from master_db where school_id = ? and standard=? and section= ?";

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, school_id);
			pstmt.setString(2, standard);
			pstmt.setString(3, section);


			res = pstmt.executeQuery();
			while (res.next()) {
				Student_name a = new Student_name();
				a.setstudent_id(res.getLong("student_id"));
				a.setstudent_name(res.getString("student_name"));
				atts.add(a);
			}

			pstmt.close();
			c.close();
			return new ResponseEntity<List<Student_name>>(atts, HttpStatus.OK);

		} catch (Exception e) {
			throw new GenericException("Get students for period failed:" + e.getMessage());
		}
	}


	@CrossOrigin()
	@RequestMapping(value = "/schools/dummy", method = RequestMethod.POST)
	public ResponseEntity<Classes> getDummy(@RequestBody Classes cl) {
		System.out.println("Hello there dummy");
		if (cl != null)
			cl.setAcademic_year(cl.getAcademic_year() + 10);

		return new ResponseEntity<Classes>(cl, HttpStatus.OK);
	}

	@ExceptionHandler(GenericException.class)
	public ResponseEntity<ExceptionDetail> myError(HttpServletRequest request, Exception exception) {
		System.out.println("SchoolController Exception:" + exception.getLocalizedMessage());
		ExceptionDetail error = new ExceptionDetail();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exception.getLocalizedMessage());
		error.setUrl(request.getRequestURL().toString());
		return new ResponseEntity<ExceptionDetail>(error, HttpStatus.BAD_REQUEST);
	}

}
