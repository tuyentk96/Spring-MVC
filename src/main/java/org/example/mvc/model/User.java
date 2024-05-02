package org.example.mvc.model;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username",unique = true,nullable = false, length = 100)
    private String username;

    @Column(name = "password",nullable = false, length = 100)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
