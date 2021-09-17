package com.moshhsa.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="permissions")
public class Permission extends BaseEntity
{

    @Column(nullable=false, unique=true)
    private String name;
    @Column(length=1024)
    private String description;
    @ManyToMany(mappedBy="permissions")
    private List<Role> roles;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public List<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }
}