package lk.assignment.bankapp.core.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

    @Id
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;


    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    // Getters and Setters

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
