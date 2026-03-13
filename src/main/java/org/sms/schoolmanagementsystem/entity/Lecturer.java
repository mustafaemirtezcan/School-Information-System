package org.sms.schoolmanagementsystem.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.sms.schoolmanagementsystem.user.User;

import java.util.Set;

@Getter
@Setter
@Entity
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    private String lecturerNumber;
    private String phoneNumber;
    private String email;
    private String branch;
    private String password;

    @ManyToOne
    @JsonBackReference
    private School school;
    @OneToOne
    @JoinColumn(name = "address_id",foreignKey = @ForeignKey(name = "fk_lecturer_address"))
    private Address address;

    @ManyToMany
    @JoinTable(
        name = "lecturer_course",
        joinColumns = @JoinColumn(name = "lecturer_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @JsonManagedReference
    private Set<Course> courses;
}
