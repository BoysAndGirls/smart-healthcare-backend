package com.springboot.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by liuyongg on 17/7/2017.
 */
@Data
public class PersonalResetPass {


    @NotEmpty(message = "真实姓名不能为空！")
    @NotNull(message = "真实姓名不能为空！")
    private String realName;

    @Pattern(regexp = "^1[34578]\\d{9}$", message = "手机号码格式不正确！")
    @NotEmpty(message = "手机号码不能为空！")
    @NotNull(message = "手机号码不能为空！")
    private String tel;

    @NotEmpty(message = "邮箱地址不能为空！")
    @NotNull(message = "邮箱地址不能为空！")
    @Email(message = "邮箱地址不符合规范！")
    private String email;

    @NotEmpty(message = "新密码不能为空！")
    @NotNull(message = "新密码不能为空！")
    @Size(min = 6, max = 16, message = "新密码长度必须在6到16之间！")
    private String newPassword;
}