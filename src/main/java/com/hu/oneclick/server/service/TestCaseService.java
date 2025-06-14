package com.hu.oneclick.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.oneclick.model.base.Resp;
import com.hu.oneclick.model.entity.Feature;
import com.hu.oneclick.model.entity.TestCase;
import com.hu.oneclick.model.entity.TestCycle;
import com.hu.oneclick.model.domain.dto.*;
import com.hu.oneclick.model.param.TestCaseParam;
import java.util.List;
import java.util.Map;

import com.hu.oneclick.model.domain.vo.IssueStatusVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author qingyang
 */
public interface TestCaseService extends IService<TestCase> {

  Resp<List<LeftJoinDto>> queryTitles(String projectId, String title);

  Resp<TestCase> queryById(Long id);

  Resp<List<TestCase>> queryList(TestCaseDto testCase);

  Resp<String> insert(TestCase testCase);

  Resp<String> update(TestCase testCase);

  Resp<String> delete(String id);

  Resp<Feature> queryTestNeedByFeatureId(String featureId);

  /**
   * excel导入测试用例
   *
   * @param file
   * @param param
   * @return
   */
  Resp<ImportTestCaseDto> importTestCase(MultipartFile file, String param);

  /**
   * 添加测试用例
   *
   * @param testCycleDto
   * @Param: [testCase]
   * @return: com.hu.oneclick.model.base.Resp<java.lang.String>
   * @Author: MaSiyi
   * @Date: 2021/12/1
   */
  Resp<String> addTestCase(TestCycleDto testCycleDto);

  /**
   * 更新action
   *
   * @Param: [testCaseId]
   * @return: com.hu.oneclick.model.base.Resp<java.lang.String>
   * @Author: MaSiyi
   * @Date: 2021/12/1
   */
  Resp<List<TestCase>> updateAction(List<String> testCaseId, String actionType, String testCycleId);

  List<TestCase> list(TestCaseParam param);

  TestCase save(TestCaseSaveDto dto);

  TestCase update(TestCaseSaveDto dto);

  TestCase info(Long id);

  void clone(List<Long> ids);

  List<TestCase> listExtend(TestCaseParam tmpParam);

  List<TestCaseBisDto> getTestCaseAllByCycleId(Long testCycleId);

  /**
   * 根据CaseId、projectId查找
   *
   * @param projectId
   * @param testCaseId
   * @return
   */
  TestCase getByIdAndProjectId(Long projectId, Long testCaseId);

  List<TestCase> testCaseSearch(Long projectId, String title);

    /**
     * 通用搜索方法 - 根据scope参数在不同表中搜索
     *
     * @param projectId 项目ID
     * @param title 标题关键字
     * @param scope 搜索范围 (feature/useCase/testCase/testCycle/runCase)
     * @return 搜索结果列表
     */
    List<SearchResultDto> testCaseSearch(Long projectId, String title, String scope);

    /**
     * 根据CaseId、projectId查找
     *
     * @param projectId
     * @param testCaseId
     * @return
     */


  Resp<Map> removeAndChild(Long id);

  TestCase queryByProjectIdAndExteranlId(Long projectId, String exteranlId);

    IssueStatusVo retrieveIssueStatusAsPerIssueId(Long projectId, Long issueId);

  TestCycle saveTestCycle(Long projectId, TestCycleSaveDto dto);

}
