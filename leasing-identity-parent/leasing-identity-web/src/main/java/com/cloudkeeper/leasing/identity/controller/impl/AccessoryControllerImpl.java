package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.annotation.Authorization;
import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.AccessoryController;
import com.cloudkeeper.leasing.identity.domain.Accessory;
import com.cloudkeeper.leasing.identity.domain.ParActivityPicture;
import com.cloudkeeper.leasing.identity.domain.ParPictureInfro;
import com.cloudkeeper.leasing.identity.dto.accessory.AccessoryDTO;
import com.cloudkeeper.leasing.identity.dto.accessory.AccessorySearchable;
import com.cloudkeeper.leasing.identity.service.AccessoryService;
import com.cloudkeeper.leasing.identity.service.ParActivityPictureService;
import com.cloudkeeper.leasing.identity.service.ParPictureInfroService;
import com.cloudkeeper.leasing.identity.service.impl.BASE64DecodedMultipartFile;
import com.cloudkeeper.leasing.identity.vo.AccessoryVO;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 系统附件 controller
 * @author asher
 */
@RestController
public class AccessoryControllerImpl implements AccessoryController {

    /** 系统附件 service */
    @Autowired
    private AccessoryService accessoryService;

    @Autowired
    private ParPictureInfroService parPictureInfroService;

    @Autowired
    private ParActivityPictureService parActivityPictureService;

    @Override
    public Result<AccessoryVO> findOne(@ApiParam(value = "系统附件id", required = true) @PathVariable String id) {
        Optional<Accessory> accessoryOptional = accessoryService.findOptionalById(id);
        return accessoryOptional.map(accessory -> Result.of(accessory.convert(AccessoryVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<AccessoryVO> add(@ApiParam(value = "系统附件 DTO", required = true) AccessoryDTO accessoryDTO, @RequestParam("file") MultipartFile file) throws IOException {
        Accessory accessory = accessoryService.save(accessoryDTO.convert(Accessory.class), file);
        return Result.ofAddSuccess(accessory.convert(AccessoryVO.class));
    }

    @Override
    public Result<AccessoryVO> update(@ApiParam(value = "系统附件id", required = true) @PathVariable String id,
                                      @ApiParam(value = "系统附件 DTO", required = true) @RequestBody @Validated AccessoryDTO accessoryDTO) {
        Optional<Accessory> accessoryOptional = accessoryService.findOptionalById(id);
        if (!accessoryOptional.isPresent()) {
            return Result.ofLost();
        }
        Accessory accessory = accessoryOptional.get();
        BeanUtils.copyProperties(accessoryDTO, accessory);
        accessory = accessoryService.save(accessory);
        return Result.ofUpdateSuccess(accessory.convert(AccessoryVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "系统附件id", required = true) @PathVariable String id) {
        accessoryService.deleteAndFile(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<AccessoryVO>> list(@ApiParam(value = "系统附件查询条件", required = true) @RequestBody AccessorySearchable accessorySearchable,
                                          @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<Accessory> accessoryList = accessoryService.findAll(accessorySearchable, sort);
        List<AccessoryVO> accessoryVOList = Accessory.convert(accessoryList, AccessoryVO.class);
        return Result.of(accessoryVOList);
    }

    @Override
    public Result<Page<AccessoryVO>> page(@ApiParam(value = "系统附件查询条件", required = true) @RequestBody AccessorySearchable accessorySearchable,
                                          @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<Accessory> accessoryPage = accessoryService.findAll(accessorySearchable, pageable);
        Page<AccessoryVO> accessoryVOPage = Accessory.convert(accessoryPage, AccessoryVO.class);
        return Result.of(accessoryVOPage);
    }


    @Override
    public void download(@PathVariable String id, HttpServletResponse response) throws IOException {
        Accessory accessory = accessoryService.findById(id);
        accessoryService.download(accessory, response);
    }

    @Override
    public Result<List<AccessoryVO>> addList(@ApiParam(value = "系统附件 DTO", required = true) List<AccessoryDTO> accessoryDTOs, @RequestParam("files") MultipartFile[] files) throws IOException {
        List<AccessoryVO> accessoryVOS = new ArrayList<>();
        for (int i = 0; i < accessoryDTOs.size(); i++){
            Accessory accessory = accessoryService.save(accessoryDTOs.get(i).convert(Accessory.class), files[i]);
            accessoryVOS.add(accessory.convert(AccessoryVO.class));
        }
        return Result.ofAddSuccess(accessoryVOS);
    }

    @Override
    public Result<List<AccessoryVO>> singleBatch( @RequestParam("files") MultipartFile[] files) throws IOException {
        Date batchStartTime = new Date();
        List<AccessoryVO> accessoryVOS = new ArrayList<>();
        for (int i = 0; i < files.length; i++){
            Accessory accessory = accessoryService.save(new Accessory(), files[i]);
            accessoryVOS.add(accessory.convert(AccessoryVO.class));
        }
        return Result.ofAddSuccess(accessoryVOS);
    }

    @Override
    @Authorization(required = true)
    public void getFromRedis(@PathVariable String uuid, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String base64 = accessoryService.getFromRedis(uuid);

        InputStream in = null;
        MultipartFile multipartFile = null;
        try {
            /*
                判断图片数据是否还在Redis数据库中，如果不在则已经上传到图片服务器上，
                查找对应的图片存储记录获取图片的存储地址，再将该图片返回给前台
            */
            if (StringUtils.isEmpty(base64)) {
                ParPictureInfro byRedisUuid = parPictureInfroService.findByRedisUuid(uuid);
                ParActivityPicture byRedisUuid1 = parActivityPictureService.findByRedisUuid(uuid);
                if (byRedisUuid != null) {
                    in = getImageStream(byRedisUuid.getImageURL());
                }
                else if (byRedisUuid1 != null) {
                    in = getImageStream(byRedisUuid1.getImageUrl());
                } else {
                    httpServletResponse.setStatus(HttpStatus.NOT_FOUND.value());
                    return;
                }
            } else {
                multipartFile = BASE64DecodedMultipartFile.base64ToMultipart(base64);
                httpServletResponse.setContentType(multipartFile.getContentType());
                httpServletResponse.setHeader("Content-Disposition", "inline; filename=" + uuid);
                in = multipartFile.getInputStream();
            }

            BufferedInputStream br = new BufferedInputStream(in);
            byte[] bytes = new byte[1024];
            int len = 0;
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            while ((len = br.read(bytes)) > 0) {
                outputStream.write(bytes, 0, len);
            }
            br.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //根据网络上的图片地址获取输入流
    private InputStream getImageStream(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                return inputStream;
            }
        } catch (IOException e) {
            System.out.println("获取网络图片出现异常，图片路径为：" + url);
            e.printStackTrace();
        }
        return null;
    }

}
