package org.sms.schoolmanagementsystem.repository;

import org.sms.schoolmanagementsystem.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
}
