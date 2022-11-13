package com.lambdateam.mycar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/** SEGUNDO PASSO PARA IMPLANTAR O SPRING SECURITY -> Fazer os JoinTable e implementar os Override de User Details **/

@Entity
@Table(name = "User")
public class UserModel implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 60, columnDefinition = "VARCHAR(60)", nullable = false)
    private String username;

    @Column(name = "email", length = 60, columnDefinition = "VARCHAR(60)", nullable = false)
    private String email;

    @Column(name = "password", columnDefinition = "VARCHAR(255)", nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Users_role", uniqueConstraints = @UniqueConstraint( columnNames = {"user_id", "role_id"}, name = "unique_role_user"),
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", table = "User", unique = false,
            foreignKey = @ForeignKey(name = "user_fk", value = ConstraintMode.CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", table = "Role", unique = false, updatable = false,
                    foreignKey = @ForeignKey(name = "role_fk", value = ConstraintMode.CONSTRAINT)))
    private List<Role> roles;

    public UserModel() {

    }

    public UserModel(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        username = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String emailString) {
        email = emailString;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwordString) {
        password = passwordString;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        UserModel other = (UserModel) obj;
        if(id == null) {
            if(other.id != null)
                return false;
        } else if(!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

//    @JsonIgnore
//    @Override
//    public String getPassword() {
//        return this.password;
//    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}