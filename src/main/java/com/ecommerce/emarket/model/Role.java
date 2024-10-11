package com.ecommerce.emarket.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Name in the db
    @Column(name = "role_id")
    private Integer roleId;

    // An Enum type in AppRole is persisted the db as integer by default. But wee
    // need to store it as string
    @Enumerated(EnumType.STRING)
    @ToString.Exclude
    @Column(name = "role_name", length = 20)
    private AppRole roleName;

    @Override
    public String toString() {
        return "Role [roleId=" + roleId + ", roleName=" + roleName + "]";
    }

}
