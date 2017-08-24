package com.springboot.enums;


public enum ResultEnum {
    UNKONW_ERROR(-1,"未知错误"),
    NOT_LOGIN(1,"未登录"),
    PASSWORD_ERROR(2,"密码错误"),
    PASSWORDREPEAT_ERROR(2,"新密码与旧密码相同，请重新输入！"),
    DIFPASSWORD_ERROR(2,"两次输入的新密码不同，请重试！"),
    OLDPASSWORD_ERROR(2,"旧密码输入错误，请重试！"),
    NOT_ACTIVE_ERROR(3,"您的账户尚未激活。"),
    EXIST_ERROR(6,"用户已存在！"),
    NOT_EXIST_ERROR(6,"用户不存在！"),
    DEL_ERROR(5,"此条信息不存在！"),

    SUCCESS(200,"success"),
    DEL_SUCCESS(4,"删除信息成功！"),
    REGISTER_SUCCESS(4,"注册成功！"),
    LOGIN_SUCCESS(4,"登录成功! "),
    PASSRESET_SUCCESS(4,"密码修改成功！"),
    UPDATE_SUCCESS(4,"更新信息成功！"),


    ;
    private Integer code;

    private String msg;

    ResultEnum(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}