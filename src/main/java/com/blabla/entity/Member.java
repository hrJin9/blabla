package com.blabla.entity;

import com.blabla.exception.LoginUnAuthorizedException;
import com.blabla.util.PasswordEncryptor;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET deleted = true WHERE member_id = ?")
@Where(clause = "deleted = false")
@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String loginId;

    private String email;

    private String encryptedPassword;

    private String nickName;

    private String phone;

    private Boolean deleted = Boolean.FALSE;

    @Builder
    public Member(String loginId, String email, String encryptedPassword, String nickName, String phone) {
        this.loginId = loginId;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.nickName = nickName;
        this.phone = phone;
    }

    public Member(Long id, String loginId, String email, String encryptedPassword, String nickName, String phone, Boolean deleted) {
        this.id = id;
        this.loginId = loginId;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.nickName = nickName;
        this.phone = phone;
        this.deleted = deleted;
    }

    public static Member of(String loginId, String email, String password, String nickName, String phone) {
        return new Member(
                loginId,
                email,
                PasswordEncryptor.encrypt(password),
                nickName,
                phone);
    }

    public void checkPassword(String password) {
        String hashedPassword = PasswordEncryptor.encrypt(password);
        if (!this.encryptedPassword.equals(hashedPassword)) {
            throw new LoginUnAuthorizedException("비밀번호가 일치하지 않습니다.");
        }
    }
}
