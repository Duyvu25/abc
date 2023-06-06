package com.example.abc.StudentService;

import com.example.abc.entity.Student;
import com.example.abc.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    Student saveStudent(Student student);
    Student getStudentById(Long id);
    Student updateStudent(Student student);
    void deleteStudentById(Long id);
    boolean isStudentExists(String email);

    Page<Student> findPaginated(int pageNo, int pageSize);

    List<Student> searchStudents(String keyword);

    List<Student> listAll(String keyword);

/*
    @Autowired
    private StudentRepository repo;

    public List<Student> listAll(String keyword) {
        if (keyword != null) {
            return repo.search(keyword);
        }
        return repo.findAll();
    }
*/

}