package com.example.abc.repository;

import com.example.abc.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);


    // search bar repo
    @Query("SELECT s FROM Student s WHERE s.name LIKE %:keyword%"
            + " OR s.id LIKE %:keyword%"
            + " OR s.email LIKE %:keyword%")
    public List<Student> search(@Param("keyword") String keyword);

    /// search bả repo
}


