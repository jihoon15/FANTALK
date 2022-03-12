package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String name;

    @Column(nullable = false)
    private String email;

    private String role; // ROLE_USER, ROLE_MANAGER

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Meeting> meetingList = new ArrayList<>();

    @Builder
    public User(String username, String name, String email, String role){
        this.username = username;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public User update(String username){
        this.username = username;
        return this;
    }
}
