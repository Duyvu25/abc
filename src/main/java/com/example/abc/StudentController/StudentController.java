package com.example.abc.StudentController;

import com.example.abc.StudentService.StudentService;
import com.example.abc.entity.Student;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping
public class StudentController {
    @Autowired
    private StudentService studentService;

    //search bar 2
    @RequestMapping("/")
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
            Model model) {
        int pageSize = 5;

        Page<Student> page = studentService.findPaginated(pageNo, pageSize);
        List<Student> listStudents = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("students", listStudents);

//        model.addAttribute("students", studentService.getAllStudents());
//        model.addAttribute("currentPage", 1);
//        model.addAttribute("pageNumber", 1);
//        model.addAttribute("totalPages", 1);
        // thêm hs thnahf cng thì báo
        // model.addAttribute("successMessage", "Student create successful");
        return "students";
    }

    // sreach bar

//    public String getStudentList(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
//        List<Student> students;
//
//        if (keyword != null && !keyword.isEmpty()) {
//            // Nếu có từ khóa, thực hiện tìm kiếm danh sách học sinh dựa trên từ khóa
//            students = studentService.searchStudents(keyword);
//        } else {
//            // Ngược lại, lấy toàn bộ danh sách học sinh
//            students = studentService.getAllStudents();
//        }
//
//        model.addAttribute("students", students);
//        return "students";
//    }
/// search bar

    @GetMapping("/students/new")
    public String createStudentForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "create_student";
    }





    @PostMapping("/students")
    public String saveStudent(@Valid Student student, BindingResult result, Model model, RedirectAttributes redirectAttributes) throws InterruptedException {
        if (result.hasErrors()) {
            return "create_student";
        }
        //
        if (studentService.isStudentExists(student.getEmail())) {
            // Thông tin đã tồn tại, xử lý lỗi tương ứng
            model.addAttribute("errorMessage", "Thông tin đã tồn tại.");
            redirectAttributes.addFlashAttribute("errorMessage", "thông tin tồn tại");
            return "create_student";
        }

        studentService.saveStudent(student);
        model.addAttribute("successMessage", "Thêm học sinh thành công.");
        redirectAttributes.addFlashAttribute("successMessage", "Create student successfully");
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

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id,
                                @ModelAttribute("student") Student student,
                                Model model, RedirectAttributes redirectAttributes) {

        Student excitingStudent = studentService.getStudentById(id);
        excitingStudent.setId(id);
        excitingStudent.setName(student.getName());
        excitingStudent.setEmail(student.getEmail());

        studentService.updateStudent(excitingStudent);
        redirectAttributes.addFlashAttribute("successMessage", "update student successfully");
        return "redirect:/students";
    }

    @GetMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.deleteStudentById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Delete student successfully");

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