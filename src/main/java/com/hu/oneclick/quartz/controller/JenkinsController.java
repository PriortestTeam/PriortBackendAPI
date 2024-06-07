package com.hu.oneclick.quartz.controller;

import cn.hutool.core.map.MapUtil;
import com.hu.oneclick.model.base.Resp;
import com.hu.oneclick.quartz.JenkinsManager;
import com.hu.oneclick.quartz.domain.JenkinsBuildDto;
import com.hu.oneclick.quartz.domain.JenkinsOperateDto;
import com.hu.oneclick.quartz.domain.JenkinsSaveDto;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/jenkins")
@Tag(name = "API - jenkins")
@Slf4j
public class JenkinsController {

    @Resource
    private JenkinsManager jenkinsManager;


    @Operation(description = "添加Job")
    @PostMapping(value = "/addJob")
    public Resp<?> addJob(@RequestBody @Validated JenkinsSaveDto dto) {
        try {
            jenkinsManager.createJob(dto.getJobName(), dto.getXml());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new Resp.Builder<>().fail();
        }
        return new Resp.Builder<>().ok();
    }

    @Operation(description = "更新Job")
    @PutMapping(value = "/updateJob")
    public Resp<?> updateJob(@RequestBody @Validated JenkinsSaveDto dto) {
        try {
            jenkinsManager.updateJob(dto.getJobName(), dto.getXml());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new Resp.Builder<>().fail();
        }
        return new Resp.Builder<>().ok();
    }

    @Operation(description = "Job详情")
    @GetMapping(value = "/jobInfo")
    public Resp<?> jobInfo(@RequestParam(value = "jobName") String jobName) {
        try {
            JobWithDetails job = jenkinsManager.getJob(jobName);
            return new Resp.Builder<>().setData(job).ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new Resp.Builder<>().fail();
        }
    }

    @Operation(description = "获取Job列表")
    @GetMapping(value = "/jobList")
    public Resp<?> jobList() {
        try {
            Map<String, Job> jobs = jenkinsManager.getJobList();
            return new Resp.Builder<>().setData(jobs).ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new Resp.Builder<>().fail();
        }
    }

    @Operation(description = "构建Job")
    @PutMapping("/buildJob")
    public Resp<?> buildJob(@RequestBody @Validated JenkinsBuildDto dto) {
        try {
            if (MapUtil.isEmpty(dto.getParam())) {
                jenkinsManager.buildJob(dto.getJobName());
            } else {
                jenkinsManager.buildParamJob(dto.getJobName(), dto.getParam());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new Resp.Builder<>().fail();
        }
        return new Resp.Builder<>().ok();
    }

    @Operation(description = "停止Job")
    @PutMapping("/stopJob")
    public Resp<?> stopJob(@RequestBody @Validated JenkinsOperateDto dto) {
        try {
            jenkinsManager.stopJob(dto.getJobName());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new Resp.Builder<>().fail();
        }
        return new Resp.Builder<>().ok();
    }

    @Operation(description = "删除任务")
    @DeleteMapping("/deleteJob")
    public Resp<?> deleteJob(@RequestBody @Validated JenkinsOperateDto dto) {
        try {
            jenkinsManager.deleteJob(dto.getJobName());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new Resp.Builder<>().fail();
        }
        return new Resp.Builder<>().ok();
    }

}
