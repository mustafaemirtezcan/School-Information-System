package org.sms.schoolmanagementsystem.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sms.schoolmanagementsystem.user.User;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Student {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    private String birthDate;
    private String email;
    private String phoneNumber;
    private String studentNumber;
    private Double gpa;
    private int achievedCredits;
    private String password;

    @ManyToOne
    @JsonBackReference
    private School school;
    @OneToOne
    @JoinColumn(name = "address_id",foreignKey = @ForeignKey(name = "fk_student_address"))
    private Address address;

    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @JsonManagedReference
    private Set<Course> courses;


}
