package com.hu.oneclick.controller;

import com.hu.oneclick.model.base.Resp;
import com.hu.oneclick.model.domain.ProjectSignOff;
import com.hu.oneclick.model.domain.dto.CustomFieldPossBileDto;
import com.hu.oneclick.model.domain.dto.LeftJoinDto;
import com.hu.oneclick.model.domain.dto.SignOffDto;
import com.hu.oneclick.server.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author 王富贵
 * @date 2021/9/16 - 21:13
 */
@RestController
@RequestMapping("signOff")
@Tag(name = "验收")
public class SignOffController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private TestCaseService testCaseService;
    @Autowired
    private TestCycleService testCycleService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private SysCustomFieldService sysCustomFieldService;
    @Autowired
    private ProjectSignOffService signOffService;
    @Autowired
    UserProjectService userProjectService;
    @Autowired
    CustomFieldsService customFieldsService;


    @GetMapping("/getProjectEnv")
    public Resp<List<CustomFieldPossBileDto>> getProjectEnv() {
        return customFieldsService.getPossBile("env");
    }


    @GetMapping("/getProjectVersion")
    public Resp<List<CustomFieldPossBileDto>> getProjectVersion()  {
        return customFieldsService.getPossBile("version");
    }


    @GetMapping("/getIssue")
    public Resp<List<CustomFieldPossBileDto>> getIssue() {
        return customFieldsService.getPossBile("issueStatus");
    }

    @GetMapping("/getTestCycleDetail")
    public Resp<List<Map<String, String>>> getTestCycleVersion(@RequestParam String projectId, @RequestParam String env, @RequestParam String version) {
        return testCycleService.getTestCycleVersion(projectId, env, version);
    }

    @PostMapping("/generate")
    @Operation(description = "生成pdf文档")
    public Resp<String> generate(@RequestBody SignOffDto signOffDto) {
        return projectService.generate(signOffDto);
    }

    @PostMapping("/upload")
    @Operation(description = "文件上传")
    public Resp<String> upload(@RequestBody MultipartFile file) {
        return projectService.upload(file);
    }

    @GetMapping("/delete")
    @Operation(description = "文件删除")
    public Resp<String> delete(@RequestParam String fileId) {
        return attachmentService.deleteAttachmentById(fileId);
    }


    @GetMapping("/getPdf")
    @Operation(description = "返回当前项目下产生的PDF列表")
    public  Resp< List<ProjectSignOff>> getPdf() {
        return signOffService.getPdf();
    }



    @GetMapping("/getUserAttachmentSign")
    @Operation(description = "访问用户签名文件路径")
    public Resp<List<Map<String, Object>>> getUserAttachmentSign() {
        return attachmentService.getUserAttachment();
    }


    @GetMapping("getProjectListByUser")
    @Operation(description = "获取当前用户下title列表")
    public Resp<List<LeftJoinDto>> getProjectListByUser()
    {
        return userProjectService.getUserByProject();
    }
}
