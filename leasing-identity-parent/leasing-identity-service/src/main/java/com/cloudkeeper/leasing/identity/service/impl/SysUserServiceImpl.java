package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.constant.AuthorizationConstants;
import com.cloudkeeper.leasing.base.enumeration.BooleanEnum;
import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.base.utils.TokenUtil;
import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.identity.domain.SysLoginNote;
import com.cloudkeeper.leasing.identity.domain.SysUser;
import com.cloudkeeper.leasing.identity.dto.sysuser.SysUserDTO;
import com.cloudkeeper.leasing.identity.repository.SysUserRepository;
import com.cloudkeeper.leasing.identity.service.SysDistrictService;
import com.cloudkeeper.leasing.identity.service.SysLoginNoteService;
import com.cloudkeeper.leasing.identity.service.SysUserService;
import liquibase.util.MD5Util;
import liquibase.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 系统用户 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

    /** 系统用户 repository */
    private final SysUserRepository sysUserRepository;

    /** redis 操作 */
    private final RedisTemplate<String, String> redisTemplate;

    /** 登录日志*/
    private final SysLoginNoteService sysLoginNoteService;

    /** 组织*/
    private final SysDistrictService sysDistrictService;

    @Override
    protected BaseRepository<SysUser> getBaseRepository() {
        return sysUserRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("userName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("password", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("districtId", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("portrait", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("phone", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Nonnull
    @Override
    public SysUser save(@Nonnull SysUser entity) {
        //新增时给密码赋值并加密
        if(StringUtils.isEmpty(entity.getId())){
            entity.setPassword(MD5Util.computeMD5("123456"));
        }else{
            Optional<SysUser> byId = sysUserRepository.findById(entity.getId());
            if (byId.isPresent()){
                SysUser sysUser = byId.get();
                //判断是否修改了密码（前台传的密码值是否和数据库相同，不同，说明修改了密码，进行加密）
                if(!sysUser.getPassword().equals(entity.getPassword())){
                    entity.setPassword(MD5Util.computeMD5(entity.getPassword()));
                }
            }
        }
        return super.save(entity);
    }

    @Override
    public Result<Map<String, Object>> login(SysUserDTO sysUserDTO) {
        Optional<SysUser> sysUserOptional = sysUserRepository.findByUserName(sysUserDTO.getUserName());
        if (!sysUserOptional.isPresent()) {
            return Result.of(Result.ResultCode.LOGIN_FAIL.getCode(), "用户名或密码错误！");
        }
        SysUser sysUser = sysUserOptional.get();
        if (!sysUser.getPassword().equals(MD5Util.computeMD5(sysUserDTO.getPassword()))) {
            saveLoginNote("密码错误",sysUser);
            return Result.of(Result.ResultCode.LOGIN_FAIL.getCode(), "用户名或密码错误！");
        }
        if (BooleanEnum.TRUE.ordinal() == sysUser.getIsDelete()) {
            saveLoginNote("用户名被禁用",sysUser);
            return Result.of(Result.ResultCode.LOGIN_FAIL.getCode(), "用户名已被禁用！");
        }
        String token = TokenUtil.of(sysUser.getId());
        redisTemplate.opsForValue().set(AuthorizationConstants.REDIS_TOKEN_KEY + sysUser.getId(), token, TokenUtil.TTL_MILLIS, TimeUnit.MILLISECONDS);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
       /* sysUser.setPassword("不告诉你");*/
        map.put("user", sysUser);
        saveLoginNote("登录成功",sysUser);
        updateLoginTime(sysUser);
        return Result.of("登录成功！", map);
    }

    /*
    * 保存登录日志
    * */
    private void saveLoginNote(String msg ,SysUser sysUser){
        SysLoginNote sysLoginNote = new SysLoginNote();
        sysLoginNote.setUserId(sysUser.getId());
        sysLoginNote.setUserName(sysUser.getUserName());
        sysLoginNote.setCreateTime(LocalDateTime.now());
        sysLoginNote.setRegion("web");
        sysLoginNote.setAction(msg);
        sysLoginNoteService.save(sysLoginNote);
    }

    /*
    * 更新用户最近登录时间
    * */
    private void updateLoginTime(SysUser sysUser){
        Optional<SysUser> byId = sysUserRepository.findById(sysUser.getId());
        if (byId.isPresent()){
            SysUser sysUser1 = byId.get();
            sysUser1.setLastTime(LocalDateTime.now());
            sysUserRepository.save(sysUser1);
        }

    }
}