package com.hu.oneclick.controller;

import com.hu.oneclick.model.base.Resp;
import com.hu.oneclick.model.domain.SystemConfig;
import com.hu.oneclick.server.service.SystemConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author MaSiyi
 * @version 1.0.0 2021/10/11
 * @since JDK 1.8.0
 */
@Tag(name = "系统配置")
@RestController
@RequestMapping("systemConfig")
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    @PostMapping("/insert")
    @Operation(description = "增")
    public Resp<String> insert(@RequestBody SystemConfig systemConfig) {
        return systemConfigService.insert(systemConfig);
    }

    @PostMapping("/update")
    @Operation(description = "改")
    public Resp<String> update(@RequestBody SystemConfig systemConfig) {
        return systemConfigService.update(systemConfig);
    }

    @PostMapping("/getData")
    @Operation(description = "查")
    public Resp<String> getData(@RequestParam String key) {
        String data = systemConfigService.getData(key);
        return new Resp.Builder<String>().setData(data).ok();
    }

    @DeleteMapping("/delete")
    @Operation(description = "删")
    public Resp<String> delete(@RequestParam String key) {
        String data = systemConfigService.delete(key);
        return new Resp.Builder<String>().setData(data).ok();
    }


    @PostMapping("/getDataUi")
    @Operation(description = "查ui")
    public Resp<SystemConfig> getDataUi(@RequestParam String key) {
        SystemConfig data = systemConfigService.getDataUI(key);
        return new Resp.Builder<SystemConfig>().setData(data).ok();
    }

    @GetMapping("getAllUi")
    @Operation(description = "查所有ui")
    public Resp<List<SystemConfig>> getAllUi() {
        List<SystemConfig> data = systemConfigService.getAllUi();
        return new Resp.Builder<List<SystemConfig>>().setData(data).ok();
    }

}
