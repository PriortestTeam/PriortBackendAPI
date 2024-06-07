package com.hu.oneclick.controller;

import com.hu.oneclick.common.page.BaseController;
import com.hu.oneclick.model.base.Resp;
import com.hu.oneclick.model.domain.dto.VersionDto;
import com.hu.oneclick.model.domain.dto.VersionRequestDto;
import com.hu.oneclick.server.service.VersionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/release/versionManagement")
@Tag(name = "版本api")
public class VersionController extends BaseController {

    @Autowired
    VersionService versionService;

    @Operation(description = "增加")
    @PostMapping("releaseCreation")
    public Resp releaseCreation(@Valid @RequestBody VersionRequestDto releaseCreationDto) {
        versionService.releaseCreation(releaseCreationDto);
        return new Resp.Builder().ok();
    }

    @Operation(description = "修改")
    @PutMapping("releaseModification")
    public Resp releaseModification(@Valid @RequestBody VersionRequestDto releaseModification) {
        versionService.releaseModification(releaseModification);
        return new Resp.Builder().ok();
    }

    @Operation(description = "查询")
    @GetMapping("getVersion")
    public Resp<VersionDto> getVersion(@RequestParam Long id) {
        VersionDto versionDto = versionService.getVersion(id);
        return new Resp.Builder<VersionDto>().setData(versionDto).ok();
    }


    @Operation(description = "列表查询")
    @PostMapping("getVersionList")
    public Resp<List<VersionDto>> getVersionList(@RequestBody VersionRequestDto releaseModification) {
        List<VersionDto> versionDto = versionService.getVersionList(releaseModification);
        return new Resp.Builder().setData(versionDto).ok();
    }


}
