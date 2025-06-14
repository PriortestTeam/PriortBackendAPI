package com.hu.oneclick.controller;

import com.github.pagehelper.PageInfo;
import com.hu.oneclick.common.page.BaseController;
import com.hu.oneclick.model.base.Resp;
import com.hu.oneclick.model.domain.dto.SearchResultDto;
import com.hu.oneclick.model.entity.TestCase;
import com.hu.oneclick.model.domain.dto.TestCaseSaveDto;
import com.hu.oneclick.model.param.TestCaseParam;
import com.hu.oneclick.server.service.TestCaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("testCase")
@Api(tags = "测试用例")
@Slf4j
public class TestCaseController extends BaseController {

    @Resource
    private TestCaseService testCaseService;

    @ApiOperation("列表")
    @PostMapping("/list")
    public Resp<PageInfo<TestCase>> list(@RequestBody @Validated TestCaseParam param) {
        startPage();
        List<TestCase> testCaseList = testCaseService.list(param);
        return new Resp.Builder<PageInfo<TestCase>>().setData(PageInfo.of(testCaseList)).ok();
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Resp<?> save(@RequestBody @Validated TestCaseSaveDto dto) {
        try {
            TestCase testCase = testCaseService.save(dto);
            return new Resp.Builder<TestCase>().setData(testCase).ok();
        } catch (Exception e) {
            log.error("新增测试用例失败，原因：" + e.getMessage(), e);
            return new Resp.Builder<TestCase>().fail();
        }
    }

    @ApiOperation("修改")
    @PutMapping("/update")
    public Resp<?> update(@RequestBody @Validated TestCaseSaveDto dto) {
        try {
            TestCase testCase = testCaseService.update(dto);
            return new Resp.Builder<TestCase>().setData(testCase).ok();
        } catch (Exception e) {
            log.error("修改测试用例失败，原因：" + e.getMessage(), e);
            return new Resp.Builder<TestCase>().fail();
        }
    }

    @ApiOperation("详情")
    @GetMapping("/info/{id}")
    public Resp<TestCase> info(@PathVariable Long id) {
        TestCase testCase = testCaseService.info(id);
        return new Resp.Builder<TestCase>().setData(testCase).ok();
    }



    @ApiOperation("克隆")
    @PostMapping("/clone")
    public Resp<?> clone(@RequestBody @Validated Long[] ids) {
        try {
            testCaseService.clone(Arrays.asList(ids));
            return new Resp.Builder<>().ok();
        } catch (Exception e) {
            log.error("克隆测试用例失败，原因：" + e.getMessage(), e);
            return new Resp.Builder<>().fail();
        }
    }

    @ApiOperation("测试用例输入框回显")
    @GetMapping("/testCaseSearch")
    public Resp<List<SearchResultDto>> testCaseSearch(
        @RequestParam Long projectId,
        @RequestParam String title,
        @RequestParam String scope) {
        List<SearchResultDto> resultList = testCaseService.testCaseSearch(projectId, title, scope);
        return new Resp.Builder<List<SearchResultDto>>().setData(resultList).ok();
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete/{id}")
    public Resp<?> delete(@PathVariable Long id) {
       return testCaseService.removeAndChild(id);
    }

}
