package com.example.batch1.season2.team4.EMS.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="season2_batch1_team4_EMS_login")
public class Credentials {
    
    @Id
    @Column(name="associate_id")
    String associateId;
    @Column(name="password")
    String password;
    @Column(name="role")
    String role;
    
    public Credentials() {
        
    }

    public Credentials(String associate_id, String password, String role) {
        super();
        this.associateId = associate_id;
        this.password = password;
        this.role = role;
    }

    public String getAssociate_id() {
        return associateId;
    }

    public void setAssociate_id(String associate_id) {
        this.associateId = associate_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

	@Override
	public String toString() {
		return "Credentials [associateId=" + associateId + ", password=" + password + ", role=" + role + "]";
	}
    
    
}
