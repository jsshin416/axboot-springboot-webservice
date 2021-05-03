package edu.axboot.domain.eduwebtest.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public interface EduWebTestBookRepository extends JpaRepository<EduWebTestBook, Long> {


}
