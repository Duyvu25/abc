package com.example.abc.StudentService;

import com.example.abc.entity.Student;
import com.example.abc.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    Student saveStudent(Student student);
    Student getStudentById(Long id);
    Student updateStudent(Student student);
    void deleteStudentById(Long id);
    boolean isStudentExists(String email);

    Page<Student> findPaginated(int pageNo, int pageSize);

    Page<Student> searchStudents(int pageNo,String keyword);

    List<Student> listAll(String keyword);

}