package com.springboot.controller;

import com.springboot.domain.Result;
import com.springboot.domain.TpEnterpriseProject;
import com.springboot.domain.TpPersonInfo;
import com.springboot.domain.TpServiceProvider;
import com.springboot.dto.CheckMail;
import com.springboot.dto.Password;
import com.springboot.dto.ServiceProviderResetPass;
import com.springboot.enums.ResultEnum;
import com.springboot.mapper.EnterpriseMapper;
import com.springboot.mapper.PersonalMapper;
import com.springboot.service.ServiceProviderService;
import com.springboot.tools.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
@Slf4j
@RequestMapping(value = "/translate")
@RestController
public class ServiceProviderController {

    private ServiceProviderService serviceProviderService;
    private PersonalMapper personalMapper;
    private EnterpriseMapper enterpriseMapper;

    @Autowired
    public ServiceProviderController(ServiceProviderService serviceProviderService) {
        this.serviceProviderService = serviceProviderService;
        this.personalMapper = personalMapper;
        this.enterpriseMapper = enterpriseMapper;
    }

    //根据amount的值 查询供应商最新发布的几条“个人信息”
    @ResponseBody
    @RequestMapping(value = "/serviceProvider/person_latest")
    public Result<List<TpPersonInfo>> selectPersonInfoLatestAmount(@Param("amount") Integer amount) {
        return serviceProviderService.selectPersonInfoLatestAmount(amount);
    }

    //根据amount的值查询供应商最新发布的几条“企业信息”
    @ResponseBody
    @RequestMapping(value = "/serviceProvider/enterprise_latest")
    public Result<List<TpEnterpriseProject>> selectEnterpriseInfoLatestAmount(@Param("amount") Integer amount) {
        return serviceProviderService.selectEnterpriseInfoLatestAmount(amount);
    }

    //根据UUID查询供应商个人信息
    @ResponseBody
    @GetMapping(value = "/serviceProvider/selecterviceProvider")
    public Result<TpServiceProvider> selectServiceProvider(HttpSession session) {
        return serviceProviderService.selectServiceProviderByName(session);
    }

    //完善修改供应商信息
    @ResponseBody
    @RequestMapping(value = "/serviceProvider/modifyServiceProvider", method = RequestMethod.POST)
    public Result updateServiceProviderByName(@Valid @RequestBody TpServiceProvider tpServiceProvider, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        return serviceProviderService.updateServiceProviderByName(tpServiceProvider, session);
    }

    //供应商发布个人信息
    @ResponseBody
    @PostMapping(value = "/serviceProvider/newPersonInfo")
    public Result newServiceProviderPersonInfo(@Valid @RequestBody TpPersonInfo tpPersonInfo, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        return serviceProviderService.newServiceProviderPersonInfo(tpPersonInfo, session);
    }

    //供应商发布企业信息
    @ResponseBody
    @PostMapping(value = "/serviceProvider/newEnterpriseInfo")
    public Result newEnterpriseInfo(@Valid @RequestBody TpEnterpriseProject tpEnterpriseProject, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        return serviceProviderService.newServiceProviderEnterpriseInfo(tpEnterpriseProject, session);
    }

    //修改供应商发布的个人信息
    @ResponseBody
    @PostMapping(value = "/serviceProvider/modifyPersonInfo")
    public Result updateServiceProviderInfoById(@Valid @RequestBody TpPersonInfo tpPersonInfo, BindingResult bindingResult, HttpSession session) {
        try {
            String name = session.getAttribute("serviceProviderName").toString();
            String uuid = session.getAttribute("serviceProviderUuid").toString();
            tpPersonInfo.setName(name);
            tpPersonInfo.setUuid(uuid);
        } catch (NullPointerException e) {
            log.info("供应商修改发布的个人信息时，未登录 " + e.toString());
            return ResultUtil.error(ResultEnum.NOT_LOGIN);
        }
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        tpPersonInfo.setRegisterTime(new Date());
        personalMapper.updateInfoById(tpPersonInfo);
        log.info("供应商:" + session.getAttribute("serviceProviderUuid").toString() + "修改个人发布信息成功！");
        return ResultUtil.success(ResultEnum.UPDATE_SUCCESS);
    }

    //修改供应商发布的企业信息
    @ResponseBody
    @PostMapping(value = "/serviceProvider/modifyEnterpriseInfo")
    public Result updateServiceProviderEnterprseInfoById(@Valid @RequestBody TpEnterpriseProject tpEnterpriseProject, BindingResult bindingResult, HttpSession session) {
        try {
            String name = session.getAttribute("serviceProviderName").toString();
            String uuid = session.getAttribute("serviceProviderUuid").toString();
            tpEnterpriseProject.setName(name);
            tpEnterpriseProject.setUuid(uuid);
        } catch (NullPointerException e) {
            log.info("修改供应商发布的企业信息时，未登录 " + e.toString());
            return ResultUtil.error(ResultEnum.NOT_LOGIN);
        }
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return ResultUtil.error(error.getDefaultMessage());
            }
        }
        tpEnterpriseProject.setRegisterTime(new Date());
        enterpriseMapper.updateEnterpriseProjectById(tpEnterpriseProject);
        log.info("供应商:" + session.getAttribute("serviceProviderUuid").toString() + "修改企业发布信息成功！");
        return ResultUtil.success(ResultEnum.UPDATE_SUCCESS);
    }















    //供应商密码修改，需登录后，传入name.password,newpassword,retypePassword
    @PostMapping(value = "/serviceProvider/modifyPass")
    public String modifyPass(@Valid @RequestBody Password password, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        String result = serviceProviderService.updateServiceProviderPass(password, session);
        return result;
    }

    //供应商重置密码（真实姓名、邮箱、电话）,需传入name,tel,email,newPassword
    @PostMapping(value = "/serviceProvider/resetPass")
    public String resetPass(@Valid @RequestBody ServiceProviderResetPass serviceProviderResetPass, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        String result = serviceProviderService.resetServiceProviderPass(serviceProviderResetPass);
        return result;
    }


    //发送激活账户邮件
    @RequestMapping(value = "/serviceProvider/sendMail")
    public void sendMail(@RequestBody CheckMail checkMail) throws Exception {
        serviceProviderService.sendMail(checkMail);
    }

    //点击邮件链接激活账户
    @RequestMapping(value = "/serviceProvider/emailCheck")
    public String emailCheck(CheckMail checkMail) {
        return serviceProviderService.emailCheck(checkMail);
    }
}
