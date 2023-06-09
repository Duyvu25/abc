package com.example.abc.StudentServiceImpl;

import com.example.abc.StudentService.StudentService;
import com.example.abc.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.abc.repository.StudentRepository;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    @Override
    public Student saveStudent(Student student){
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).get();
    }

    @Override

    public Student updateStudent(Student student) {

        return studentRepository.save(student);
    }

    @Override
    public void deleteStudentById(Long id) {
    studentRepository.deleteById(id);
    }

    @Override
    public boolean isStudentExists(String email) {
        return studentRepository.existsByEmail(email);
    }
    @Override
    public Page<Student> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        return this.studentRepository.findAll(pageable);
    }

    @Override
    public Page<Student> searchStudents(int pageNo, String keyword) {
        return null;
    }
    @Override
    public List<Student> listAll(String keyword) {
        return null;
    }

    public Page<Student> search(String keyword, Pageable pageable) {
        return this.studentRepository.search(keyword, pageable);
    }

}

