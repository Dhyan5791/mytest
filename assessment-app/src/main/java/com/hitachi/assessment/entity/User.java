package com.hitachi.assessment.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;
	
	@Setter
	@Column(name = "username", nullable = false, unique = true)
	private String username;
	
	@Setter
	@Column(name = "email", nullable = false, length = 100, unique = true)
	private String email;
	
	@Setter
	@Column(name = "password", nullable = false)
	private String password;
	
	@Setter
	@Column(name = "is_active", nullable = false)
	private boolean active;
	
	@CreationTimestamp
    @Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
    @Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	public User(String username, String email, String password, Boolean active) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.active = active;
    }
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	    name = "user_roles",
	    joinColumns = @JoinColumn(name = "user_id"),
	    inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	private Set<Transaction> customerTransactions = new HashSet<>();

	@OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
	private Set<Transaction> createdTransactions = new HashSet<>();

}
