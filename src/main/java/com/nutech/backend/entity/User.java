package com.nutech.backend.entity;

import com.nutech.backend.entity.audit.BaseTimeEntity;
import com.nutech.backend.entity.enumType.SocialCode;
import com.nutech.backend.entity.enumType.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialCode socialCode;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder
    private User(String email, String password, String name, SocialCode socialCode){
        this.email = email;
        this.password = password;
        this.name = name;
        this.socialCode = socialCode;
        this.role = UserRole.USER;
    }

    public static User of(String email, String password, String name) {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .socialCode(SocialCode.NORMAL)
                .build();
    }
}
