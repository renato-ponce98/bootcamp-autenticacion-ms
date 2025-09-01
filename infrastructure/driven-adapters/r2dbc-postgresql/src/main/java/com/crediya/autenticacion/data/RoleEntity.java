package com.crediya.autenticacion.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("roles")
public class RoleEntity {

    @Id
    private Long id;
    private String name;
    private String description;
}
