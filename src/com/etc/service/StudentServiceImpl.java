package com.etc.service;

import java.util.List;

import com.etc.dao.StudentDao;
import com.etc.dao.impl.StudentDaoImpl;
import com.etc.entity.Student;

public class StudentServiceImpl implements StudentService{
	
	private StudentDao dao = new StudentDaoImpl();

	@Override
	public String addStudent(Student student) {
		if(student.getName() == null){
			return "ѧ�����Ʋ���Ϊ��";
		}
		if(dao.addStudent(student) == 1){
			return "��ӳɹ���";
		}
		return "���ʧ�ܣ�";
	}

	@Override
	public String delStudent(int studentId) {
		if(studentId == 0){
			return "ѧ��ID����Ϊ��";
		}
		if(dao.delStudent(studentId) == 1){
			return "ѧ��ɾ���ɹ�";
		}
		return "ѧ��ɾ��ʧ��";
	}

	@Override
	public String updStudent(Student student) {
		if(student.getName() == null){
			return "ѧ�����Ʋ���Ϊ��";
		}
		if(dao.updStudent(student) == 1){
			return "�޸ĳɹ���";
		}
		return "�޸�ʧ�ܣ�";
	}

	@Override
	public Student getStudentById(int studentId) {
		return dao.getStudentById(studentId);
	}

	@Override
	public List<Student> findAllStudent() {
		return dao.findAllStudent();
	}

}
