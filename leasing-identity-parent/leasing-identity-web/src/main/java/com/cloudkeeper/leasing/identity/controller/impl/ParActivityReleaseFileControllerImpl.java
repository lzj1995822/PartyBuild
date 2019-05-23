package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.ParActivityReleaseFileController;
import com.cloudkeeper.leasing.identity.domain.ParActivityReleaseFile;
import com.cloudkeeper.leasing.identity.dto.paractivityreleasefile.ParActivityReleaseFileDTO;
import com.cloudkeeper.leasing.identity.dto.paractivityreleasefile.ParActivityReleaseFileSearchable;
import com.cloudkeeper.leasing.identity.service.ParActivityReleaseFileService;
import com.cloudkeeper.leasing.identity.vo.ParActivityReleaseFileVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * 发布任务上传文件 controller
 * @author lxw
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParActivityReleaseFileControllerImpl implements ParActivityReleaseFileController {

    /** 发布任务上传文件 service */
    private final ParActivityReleaseFileService parActivityReleaseFileService;

    @Override
    public Result<ParActivityReleaseFileVO> findOne(@ApiParam(value = "发布任务上传文件id", required = true) @PathVariable String id) {
        Optional<ParActivityReleaseFile> parActivityReleaseFileOptional = parActivityReleaseFileService.findOptionalById(id);
        return parActivityReleaseFileOptional.map(parActivityReleaseFile -> Result.of(parActivityReleaseFile.convert(ParActivityReleaseFileVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<ParActivityReleaseFileVO> add(@ApiParam(value = "发布任务上传文件 DTO", required = true) @RequestBody @Validated ParActivityReleaseFileDTO parActivityReleaseFileDTO) {
        ParActivityReleaseFile parActivityReleaseFile = parActivityReleaseFileService.save(parActivityReleaseFileDTO.convert(ParActivityReleaseFile.class));
        return Result.ofAddSuccess(parActivityReleaseFile.convert(ParActivityReleaseFileVO.class));
    }

    @Override
    public Result<ParActivityReleaseFileVO> update(@ApiParam(value = "发布任务上传文件id", required = true) @PathVariable String id,
        @ApiParam(value = "发布任务上传文件 DTO", required = true) @RequestBody @Validated ParActivityReleaseFileDTO parActivityReleaseFileDTO) {
        Optional<ParActivityReleaseFile> parActivityReleaseFileOptional = parActivityReleaseFileService.findOptionalById(id);
        if (!parActivityReleaseFileOptional.isPresent()) {
            return Result.ofLost();
        }
        ParActivityReleaseFile parActivityReleaseFile = parActivityReleaseFileOptional.get();
        BeanUtils.copyProperties(parActivityReleaseFileDTO, parActivityReleaseFile);
        parActivityReleaseFile = parActivityReleaseFileService.save(parActivityReleaseFile);
        return Result.ofUpdateSuccess(parActivityReleaseFile.convert(ParActivityReleaseFileVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "发布任务上传文件id", required = true) @PathVariable String id) {
        parActivityReleaseFileService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<ParActivityReleaseFileVO>> list(@ApiParam(value = "发布任务上传文件查询条件", required = true) @RequestBody ParActivityReleaseFileSearchable parActivityReleaseFileSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<ParActivityReleaseFile> parActivityReleaseFileList = parActivityReleaseFileService.findAll(parActivityReleaseFileSearchable, sort);
        List<ParActivityReleaseFileVO> parActivityReleaseFileVOList = ParActivityReleaseFile.convert(parActivityReleaseFileList, ParActivityReleaseFileVO.class);
        return Result.of(parActivityReleaseFileVOList);
    }

    @Override
    public Result<Page<ParActivityReleaseFileVO>> page(@ApiParam(value = "发布任务上传文件查询条件", required = true) @RequestBody ParActivityReleaseFileSearchable parActivityReleaseFileSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<ParActivityReleaseFile> parActivityReleaseFilePage = parActivityReleaseFileService.findAll(parActivityReleaseFileSearchable, pageable);
        Page<ParActivityReleaseFileVO> parActivityReleaseFileVOPage = ParActivityReleaseFile.convert(parActivityReleaseFilePage, ParActivityReleaseFileVO.class);
        return Result.of(parActivityReleaseFileVOPage);
    }

}