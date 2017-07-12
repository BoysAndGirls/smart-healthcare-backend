package com.springboot.service.impl;


import com.springboot.domain.TpPersonal;
import com.springboot.mapper.PersonalMapper;
import com.springboot.service.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/7/12.
 */
@Service
public class PersonalServiceImpl implements PersonalService {
    @Autowired
    private PersonalMapper personalMapper;

    @Override
    public String login(String name, String password) {
        //通过用户名获取用户
        TpPersonal tpPersonal = personalMapper.selectByName(name);
        //若获取失败
        if (tpPersonal == null) {
            return "该个人用户不存在";
        }
        //获取成功后，将获取用户的密码和传入密码对比
        else if (!tpPersonal.getPassword().equals(password)) {
            return "密码错误";
        }else {
            return "登录成功";
        }
    }

    @Override
    public TpPersonal selectByName(String name) {
        return personalMapper.selectByName(name);
    }
}