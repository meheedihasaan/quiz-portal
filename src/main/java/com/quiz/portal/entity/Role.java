package com.quiz.portal.entity;

import com.quiz.portal.constsant.EntityConstant;
import com.quiz.portal.model.audit.AuditModel;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = EntityConstant.ROLE)
public class Role extends AuditModel<String> {

    private String roleName;

    private String description;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();
}
