package com.example.abc.StudentController;

import com.example.abc.StudentService.StudentService;
import com.example.abc.entity.Student;
import com.example.abc.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;
    //search bar 2
    @RequestMapping("/search-result/{pageNo}")
    public String Student(Model model, @Param("keyword") String keyword) {
        List<Student> listStudents = studentService.listAll(keyword);
        model.addAttribute("listStudent", listStudents);
        model.addAttribute("keyword", keyword);

        return "students";
    }

    ///search bar 2
    @GetMapping("/students")
    public String listStudent(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(required = false, defaultValue = "") String keyword,
            Model model
    ) {
        int pageSize = 5;

        Page<Student> page;

        Pageable paging = PageRequest.of(pageNo-1,pageSize);

        List<Student> listStudents;// = page.getContent();

        if (keyword == "") {
            page = studentRepository.findAll(paging);
        } else {
            page = studentService.search(keyword,paging);
            model.addAttribute("keyword",keyword);
        }

        listStudents = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("keyword", keyword);
//        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("students", listStudents);
        return "students";
    }

    @GetMapping("/students-search")
    public String getAll (
            Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "7") int size
    )
    {
        try {
            List<Student> students;
            Pageable paging = PageRequest.of(page-1,size);
            Page<Student> pageStu;
            if(keyword == null){
                pageStu = (Page<Student>) studentRepository.findAll(paging);
            }else{
                pageStu = studentRepository.search(keyword,paging);
                model.addAttribute("keyword",keyword);
            }
            students = pageStu.getContent();
            model.addAttribute("students",students);
            model.addAttribute("currentPage",pageStu.getNumber()+1);
            model.addAttribute("totalItems",pageStu.getTotalElements());
            model.addAttribute("totalPages",pageStu.getTotalPages());
            model.addAttribute("pageSize",size);
        }catch (Exception e){
            model.addAttribute("message",e.getMessage());
        }
        return "students";
    }

    @GetMapping("/students/new")
    public String createStudentForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "create_student";
    }





    @PostMapping("/students")
    public String saveStudent(
            @Valid Student student,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) throws InterruptedException {
        if (result.hasErrors()) {
            return "create_student";
        }

        if (studentService.isStudentExists(student.getEmail())) {
            // Thông tin đã tồn tại, xử lý lỗi tương ứng
            model.addAttribute("errorMessage", "Thông tin đã tồn tại.");
            redirectAttributes.addFlashAttribute("message", "thông tin tồn tại");
            return "create_student";
        }

        studentService.saveStudent(student);
        model.addAttribute("successMessage", "Thêm học sinh thành công.");
        redirectAttributes.addFlashAttribute("message", "Create student successfully");
        return "redirect:/students";

        // search

    }
    //
//        studentService.saveStudent(student);
//        model.addAttribute("student",studentService.saveStudent(student));
//        //thêm học sinh thành công thì báo
//        model.addAttribute("successMessage", "Student create successful");
//
//        redirectAttributes.addFlashAttribute("successMessage", "Create student successfully");
//        return "redirect:/students";
//    }

    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        return "edit_student";
    }

    @PostMapping("/students/edit/{id}")
    public String updateStudent(@PathVariable Long id,
                                @ModelAttribute("student") Student student,
                                Model model, RedirectAttributes redirectAttributes) {

        Student excitingStudent = studentService.getStudentById(id);
        excitingStudent.setId(id);
        excitingStudent.setName(student.getName());
        excitingStudent.setEmail(student.getEmail());

        studentService.updateStudent(excitingStudent);
        redirectAttributes.addFlashAttribute("message", "update student successfully");
        return "redirect:/students";
    }

    @GetMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.deleteStudentById(id);
        redirectAttributes.addFlashAttribute("message", "Delete student successfully");

        return "redirect:/students";
    }

    // public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    //        @RequestMapping("/error")
//        public String handleError() {
//            // Xử lý lỗi và trả về trang lỗi tương ứng
//            return "error";
//        }
//
//    }


    //    @GetMapping("/page/{pageNo}")
//    public String findPaginated(@PathVariable(value = "pageNo") Integer pageNo,
//                                Model model) {
//        int pageSize = 5;
//
//        Page<Student> page = studentService.findPaginated(pageNo, pageSize);
//        List<Student> listStudent = page.getContent();
//
//        model.addAttribute("currentPage", pageNo);
//        model.addAttribute("totalItems", page.getTotalElements());
//
//        int totalPages = page.getTotalPages() != null ?
//                page.getTotalPages() : 0;
//        model.addAttribute("totalPages", totalPages);
//
//        model.addAttribute("listStudent", listStudent);
//        return "students";
//    }
    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") Integer pageNo,
//                                @RequestParam("sortField") String sortField,
//                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page<Student> page = studentService.findPaginated(pageNo, pageSize);
        List<Student> listStudents = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

//        model.addAttribute("sortField", sortField);
//        model.addAttribute("sortDir", sortDir);
//        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listStudents", listStudents);
        return "students";
    }
}