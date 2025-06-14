package com.hu.oneclick.server.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.oneclick.common.constant.OneConstant;
import com.hu.oneclick.common.enums.SysConstantEnum;
import com.hu.oneclick.common.exception.BizException;
import com.hu.oneclick.common.security.service.JwtUserServiceImpl;
import com.hu.oneclick.common.util.DateUtil;
import com.hu.oneclick.dao.*;
import com.hu.oneclick.model.base.Resp;
import com.hu.oneclick.model.base.Result;
import com.hu.oneclick.model.domain.dto.*;
import com.hu.oneclick.model.entity.Feature;
import com.hu.oneclick.model.entity.TestCase;
import com.hu.oneclick.model.entity.View;
import com.hu.oneclick.relation.service.RelationService;
import com.hu.oneclick.server.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author qingyang
 */
@Service
@Slf4j
public class TestCaseServiceImpl extends ServiceImpl<TestCaseDao, TestCase> implements
    TestCaseService {

    @Resource
    private ModifyRecordsService modifyRecordsService;
    @Resource
    private JwtUserServiceImpl jwtUserService;
    @Resource
    private QueryFilterService queryFilterService;
    @Resource
    private FeatureDao featureDao;
    @Resource
    private SysCustomFieldService sysCustomFieldService;
    @Resource
    private TestCaseStepDao testCaseStepDao;
    @Resource
    private MailService mailService;
    @Resource
    private TestCycleJoinTestCaseDao testCycleJoinTestCaseDao;
    @Resource
    private CustomFieldsService customFieldsService;
    @Resource
    private TestCaseDao testCaseDao;
    @Resource
    private TestCaseStepService testCaseStepService;
    @Resource
    private UseCaseDao useCaseDao;
    @Resource
    private TestCycleDao testCycleDao;
    @Resource
    private TestCycleJoinTestCaseDao testCycleJoinTestCaseDao;

    @Resource
    TestCycleTcDao testCycleTcDao;

    @Resource
    private RelationService relationService;

    @Resource
    private IssueService issueService;

    @Resource
    TestCycleService testCycleService;

    @Override
    public Resp<List<LeftJoinDto>> queryTitles(String projectId, String title) {
        List<TestCase> list = this.lambdaQuery()
            .eq(TestCase::getProjectId, projectId)
            .eq(TestCase::getCreateUserId, jwtUserService.getMasterId())
            .like(StrUtil.isNotBlank(title), TestCase::getTitle, title)
            .list();
        if (CollUtil.isEmpty(list)) {
            return new Resp.Builder<List<LeftJoinDto>>().ok();
        }
        List<LeftJoinDto> leftJoinDtoList = list.stream().map(testCase -> {
            LeftJoinDto leftJoinDto = new LeftJoinDto();
            leftJoinDto.setId(testCase.getId());
            leftJoinDto.setTitle(testCase.getTitle());
            return leftJoinDto;
        }).collect(Collectors.toList());
        return new Resp.Builder<List<LeftJoinDto>>().setData(leftJoinDtoList).ok();
    }

    @Override
    public List<SearchResultDto> testCaseSearch(Long projectId, String title, String scope) {
        if (projectId == null || StrUtil.isBlank(scope)) {
            return new ArrayList<>();
        }

        try {
            switch (scope.toLowerCase()) {
                case "feature":
                    return featureDao.searchByTitleAndProjectId(projectId, title);
                case "usecase":
                    return useCaseDao.searchByTitleAndProjectId(projectId, title);
                case "testcase":
                    return testCaseDao.searchByTitleAndProjectId(projectId, title);
                case "testcycle":
                    return testCycleDao.searchByTitleAndProjectId(projectId, title);
                case "runcase":
                    return testCycleJoinTestCaseDao.searchByTitleAndProjectId(projectId, title);
                default:
                    log.warn("未知的搜索范围: {}", scope);
                    return new ArrayList<>();
            }
        } catch (Exception e) {
            log.error("搜索失败，scope: {}, projectId: {}, title: {}, 错误: {}", scope, projectId, title, e.getMessage(), e);
            return new ArrayList<>();
        }
    }


    /**
     * update testcase custom
     *
     * @Param: [id]
     * @return: com.hu.oneclick.model.base.Resp<com.hu.oneclick.model.entity.TestCase>
     * @Author: MaSiyi
     * @Date: 2021/12/28
     */
    @Override
    public Resp<TestCase> queryById(Long id) {
        TestCase testCase = this.getById(id);
//        testCase.setCustomFieldDatas(customFieldDataService.testCaseRenderingCustom(id.toString()));
        return new Resp.Builder<TestCase>().setData(testCase).ok();
    }

    @Override
    public Resp<List<TestCase>> queryList(TestCaseDto testCase) {
        try {
            String masterId = jwtUserService.getMasterId();
            testCase.setCreateUserId(Long.valueOf(masterId));
            testCase.setFilter(
                queryFilterService.mysqlFilterProcess(testCase.getViewTreeDto(), masterId));
            List<TestCase> select = baseMapper.queryList(testCase);
            return new Resp.Builder<List<TestCase>>().setData(select).total(select).ok();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Resp<String> insert(TestCase testCase) {
        try {
            // 验证参数
            // 验证是否存在
            // verifyIsExist(testCase.getTitle(), testCase.getProjectId(), null);
            // verifyIsExistExternaID(testCase.getExternaId(), testCase.getFeature(), null);
            // testCase.setUserId(jwtUserService.getMasterId());
            // testCase.setAuthorName(jwtUserService.getUserLoginInfo().getSysUser().getUserName());
            // 判断创建时间是否传入，如未传入自动生成
            if (null == testCase.getCreateTime()) {
                Date date = new Date();
                testCase.setCreateTime(date);
                testCase.setUpdateTime(date);
            }
            return Result.addResult(baseMapper.insert(testCase));
        } catch (BizException e) {
            log.error("class: TestCaseServiceImpl#insert,error []" + e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Resp.Builder<String>().buildResult(e.getCode(), e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Resp<String> update(TestCase testCase) {
        try {
            // 验证是否存在
            // verifyIsExist(testCase.getTitle(), testCase.getProjectId(), testCase.getId());
            // verifyIsExistExternaID(testCase.getExternaId(), testCase.getFeature(), testCase.getId());
            // testCase.setUserId(jwtUserService.getMasterId());
            // 新增修改字段记录
            modifyRecord(testCase);
            return Result.updateResult(baseMapper.updateByPrimaryKeySelective(testCase));
        } catch (BizException e) {
            log.error("class: TestCaseServiceImpl#update,error []" + e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Resp.Builder<String>().buildResult(e.getCode(), e.getMessage());
        }
    }

    /**
     * 修改字段，进行记录
     *
     * @param testCase
     */
    private void modifyRecord(TestCase testCase) {

    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Resp<String> delete(String id) {
        return null;
    }

    @Override
    public Resp<Feature> queryTestNeedByFeatureId(String featureId) {
        String masterId = jwtUserService.getMasterId();
        Feature featureDto = featureDao.queryById(featureId, masterId);
        return new Resp.Builder<Feature>().setData(featureDto).ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Resp<ImportTestCaseDto> importTestCase(MultipartFile multipartFile, String param) {
        // try {
        //    //1.取出文件并验证文件；
        //    //原始文件名称
        //    String originalFilename = multipartFile.getOriginalFilename();
        //    //解析到文件后缀，判断是否合法
        //    int lastIndexOf = originalFilename.lastIndexOf(".");
        //    String suffix = null;
        //    if (lastIndexOf == -1 || (suffix = originalFilename.substring(lastIndexOf + 1)).isEmpty()) {
        //        //文件后缀不能为空
        //        throw new BizException(SysConstantEnum.UPLOAD_FILE_FAILED.getCode(), "文件后缀不能为空！");
        //    }
        //    //支持.et, .xlsx, .xls, .csv格式
        //    Set<String> allowSuffix = new HashSet<>(Arrays.asList("et", "xlsx", "xls", "csv"));
        //    if (!allowSuffix.contains(suffix.toLowerCase())) {
        //        throw new BizException(SysConstantEnum.UPLOAD_FILE_FAILED.getCode(), "非法的文件，不允许的文件类型:" + suffix);
        //    }
        //    //解析excel文件
        //    JSONObject jsonObject = JSONObject.parseObject(param);
        //    //是否忽略第一行表头 1是 0否
        //    Integer ifIgnorFirstRow = jsonObject.getInteger("ifIgnorFirstRow");
        //    //构建导入测试模板获取列对应的cell下标
        //    JSONObject cellIndexObject = buildCellIndexByTemplateTestCase(jsonObject);
        //    List<String> allowPriority = Arrays.asList("高", "中", "低");
        //    List<String> allowBrowser = Arrays.asList("Google Chrome", "Fire Fox", "IE");
        //    List<String> allowPlatform = Arrays.asList("window", "mac");
        //    List<String> statusPlatform = Arrays.asList("Ready", "Draft");
        //    List<String> moudleMergeValues = sysCustomFieldService.getSysCustomField("moudle").getData().getMergeValues();
        //    List<String> versionsMergeValues = sysCustomFieldService.getSysCustomField("versions").getData().getMergeValues();
        //    List<String> testCategoryMergeValues = sysCustomFieldService.getSysCustomField("testCategory").getData().getMergeValues();
        //    List<String> testTypeMergeValues = sysCustomFieldService.getSysCustomField("testType").getData().getMergeValues();
        //    List<String> testEnvMergeValues = sysCustomFieldService.getSysCustomField("testEnv").getData().getMergeValues();
        //    List<String> testDeviceMergeValues = sysCustomFieldService.getSysCustomField("testDevice").getData().getMergeValues();
        //    List<String> testMethodMergeValues = sysCustomFieldService.getSysCustomField("testMethod").getData().getMergeValues();
        //    Date now = new Date();
        //    Map<SysConstantEnum, Map<String, String>> errorTipsMap = new HashMap<>();
        //    int successCount = 0;
        //    int errorCount = 0;
        //    int updateCount = 0;
        //    //判断文件后缀，根据不同后缀操作数据
        //    JSONArray rowValueArray = buildRowValueArray(suffix, multipartFile.getInputStream(),
        //            cellIndexObject, ifIgnorFirstRow);
        //    List<TestCase> testCases = new ArrayList<>();
        //    Map<String, List<TestCaseStep>> testCaseStepsMap = new HashMap<>();
        //    for (Object o : rowValueArray) {
        //        JSONObject rowValue = (JSONObject) o;
        //        TestCase testCase = new TestCase();
        //        //处理Feature 故事
        //        if (rowValue.containsKey("featureCol")) {
        //            JSONObject featureCol = rowValue.getJSONObject("featureCol");
        //            String title = featureCol.getString("value");
        //            if (StringUtils.isBlank(title)) {
        //                //记录错误，故事标题不能为空
        //                buildErrorTips(errorTipsMap, SysConstantEnum.IMPORT_TESTCASE_ERROR_REQUIRED
        //                        , featureCol, null);
        //            }
        //            Feature feature = queryFeatureByTitle(title);
        //            if (null == feature) {
        //                //记录错误，未查询到此项目的标题
        //                buildErrorTips(errorTipsMap, SysConstantEnum.IMPORT_TESTCASE_ERROR_NOFEATURE
        //                        , featureCol, null);
        //            } else {
        //                testCase.setProjectId(Long.valueOf(feature.getProjectId()));
        //                testCase.setFeature(feature.getId());
        //            }
        //        }
        //        //处理文本字段
        //        //title
        //        setValue(rowValue.getJSONObject("testTitleCol"), testCase
        //                , errorTipsMap, "title", true);
        //        //Pre-condition 测试条件
        //        setValue(rowValue.getJSONObject("preConditionCol"), testCase
        //                , errorTipsMap, "preCondition", false);
        //        //description 描述
        //        setValue(rowValue.getJSONObject("descriptionCol"), testCase
        //                , errorTipsMap, "description", false);
        //        //ExternalID
        //        setValue(rowValue.getJSONObject("externalIdCol"), testCase
        //                , errorTipsMap, "externaId", false);
        //        //Comments 备注
        //        setValue(rowValue.getJSONObject("commentsCol"), testCase
        //                , errorTipsMap, "comments", false);
        //        //处理固定字典类型
        //        // Priority 优先级
        //        setSelectValue(rowValue.getJSONObject("priorityCol"), allowPriority, testCase
        //                , errorTipsMap, "priority", true);
        //        //Browser 浏览器
        //        setSelectValue(rowValue.getJSONObject("browserCol"), allowBrowser, testCase
        //                , errorTipsMap, "browser", false);
        //        //Platform 平台
        //        setSelectValue(rowValue.getJSONObject("platformCol"), allowPlatform, testCase
        //                , errorTipsMap, "platform", false);
        //        //status 状态
        //        setSelectValue(rowValue.getJSONObject("statusCol"), statusPlatform, testCase
        //                , errorTipsMap, "status", false);
        //        //处理动态字典类型
        //        //Module 模块
        //        setSelectValue(rowValue.getJSONObject("moduleCol"), moudleMergeValues, testCase
        //                , errorTipsMap, "module", true);
        //        //DeviceType 测试设备
        //        setSelectValue(rowValue.getJSONObject("deviceTypeCol"), testDeviceMergeValues, testCase
        //                , errorTipsMap, "testDevice", false);
        //        //Env 测试环境
        //        setSelectValue(rowValue.getJSONObject("envCol"), testEnvMergeValues, testCase
        //                , errorTipsMap, "env", true);
        //        //Version
        //        setSelectValue(rowValue.getJSONObject("versionCol"), versionsMergeValues, testCase
        //                , errorTipsMap, "version", true);
        //        //CaseCategory  测试分类
        //        setSelectValue(rowValue.getJSONObject("caseCategoryCol"), testCategoryMergeValues, testCase
        //                , errorTipsMap, "caseCategory", true);
        //        //CaseType 测试类型
        //        setSelectValue(rowValue.getJSONObject("caseTypeCol"), testTypeMergeValues, testCase
        //                , errorTipsMap, "testType", true);
        //        //Automation 测试方法
        //        setSelectValue(rowValue.getJSONObject("automationCol"), testMethodMergeValues, testCase
        //                , errorTipsMap, "testMethod", true);
        //        ///判断是否新增或者更新，根据故事ID+ExternalID查询测试用例，如果存在则进行更新；
        //
        //        //判断ExternalI是否存在，进行判断下一步是否更新
        //        if (jsonObject.containsKey("ifUpdateCase")) {
        //            JSONObject externalIdCol = rowValue.getJSONObject("externalIdCol");
        //            String externalId = externalIdCol.getString("value");
        //            TestCase queryFeaturExternalIDTestCase = new TestCase();
        //            queryFeaturExternalIDTestCase.setFeature(testCase.getFeature());
        //            queryFeaturExternalIDTestCase.setExternalLinkId(externalId);
        //            queryFeaturExternalIDTestCase.setId(null);
        //            //TestCase featurExternalIDTestCase = this.baseMapper.selectOne(queryFeaturExternalIDTestCase);
        //            //if (null != featurExternalIDTestCase) {
        //            //    Boolean ifUpdateCase = jsonObject.getBooleanValue("ifUpdateCase");
        //            //    if (ifUpdateCase) {           //进行更新
        //            //        //将已存在ID打上标识，后续判断新增或插入
        //            //        testCase.setId("UPDATE" + featurExternalIDTestCase.getId());
        //            //    } else {  //如果存在，并且更新标识为否，提示用户此故事下，已经存在此ExternalID，无法进行插入
        //            //        buildErrorTips(errorTipsMap, SysConstantEnum.IMPORT_TESTCASE_ERROR_EXIST_FEATURE_EXTERNALID
        //            //                , externalIdCol, null);
        //            //    }
        //            //}
        //        }
        //
        //        //处理 Step
        //        List<TestCaseStep> testCaseSteps = new ArrayList<>();
        //        if (cellIndexObject.containsKey("stepCol")) {
        //            String setpValue = getCellValue(errorTipsMap,
        //                    rowValue.getJSONObject("stepCol"), true);
        //
        //            //测试数据
        //            String cellTestDataValue = getCellValue(errorTipsMap,
        //                    rowValue.getJSONObject("stepTestDataCol"), true);
        //
        //            //Expected Result 预期结果
        //            String cellExpectedResultValue = getCellValue(errorTipsMap,
        //                    rowValue.getJSONObject("stepExpectResultCol"), true);
        //            Boolean ifSplitTestStep = jsonObject.getBoolean("ifSplitTestStep");
        //            //是否分隔
        //            if (ifSplitTestStep) {
        //                String splitTestStep = jsonObject.getString("splitTestStep");
        //                String[] steps = setpValue.split(splitTestStep);
        //                String[] testDatas = cellTestDataValue.split(splitTestStep);
        //                String[] expectedResults = cellExpectedResultValue.split(splitTestStep);
        //
        //                for (int i1 = 0; i1 < steps.length; i1++) {
        //                    int stepsLength = steps.length;
        //                    TestCaseStep testCaseStep = new TestCaseStep();
        //                    testCaseStep.setCreateTime(now);
        //                    testCaseStep.setStatus(0);
        //                    testCaseStep.setStep(steps[i1]);
        //                    //如果测试数据的长度与测试名称一致 则分开存储
        //                    if (stepsLength == testDatas.length) {
        //                        testCaseStep.setTestData(testDatas[i1]);
        //                    } else {          //如果不相等则每个步骤都插入一致的测试数据
        //                        testCaseStep.setTestData(cellTestDataValue);
        //                    }
        //                    //预期结果的长度与测试名称一致 则分开存储
        //                    if (stepsLength == expectedResults.length) {
        //                        testCaseStep.setExpectedResult(expectedResults[i1]);
        //                    } else {          //如果不相等则每个步骤都插入一致的测试数据
        //                        testCaseStep.setExpectedResult(cellExpectedResultValue);
        //                    }
        //                    testCaseSteps.add(testCaseStep);
        //                }
        //            } else {
        //                TestCaseStep testCaseStep = new TestCaseStep();
        //                testCaseStep.setTestData(setpValue);
        //                testCaseStep.setTestData(cellTestDataValue);
        //                testCaseStep.setExpectedResult(cellExpectedResultValue);
        //                testCaseStep.setStatus(0);
        //                testCaseStep.setCreateTime(now);
        //                testCaseSteps.add(testCaseStep);
        //            }
        //        } else {
        //            buildErrorTips(errorTipsMap, SysConstantEnum.IMPORT_TESTCASE_ERROR_REQUIRED
        //                    , rowValue.getJSONObject("stepCol"), null);
        //        }
        //        testCases.add(testCase);
        //        //testCaseStepsMap.put(testCase.getId().replace("UPDATE", ""), testCaseSteps);
        //    }
        //
        //    //判断是否异常,如出现异常，则全部进行操作db
        //    if (errorTipsMap.isEmpty()) {
        //        //判断是否新增或者更新，根据故事ID+ExternalID查询测试用例，如果存在则进行更新；
        //        for (TestCase testCase : testCases) {
        //            Resp<String> insertOrUpdate = null;
        //            //如ID为空则进行新增
        //            //if (!testCase.getId().startsWith("UPDATE")) {
        //            //    testCase.setCreateTime(now);
        //            //    testCase.setUpdateTime(now);
        //            //    insertOrUpdate = this.insert(testCase);
        //            //    successCount++;
        //            //} else {          //如ID不为空更新
        //            //    testCase.setId(testCase.getId().replace("UPDATE", ""));
        //            //    insertOrUpdate = this.update(testCase);
        //            //    //删除测试用例步骤重新插入
        //            //    TestCaseStep delTestCase = new TestCaseStep();
        //            //    delTestCase.setTestCaseId(testCase.getId());
        //            //    delTestCase.setId(null);
        //            //    this.testCaseStepDao.delete(delTestCase);
        //            //    updateCount++;
        //            //}
        //            List<TestCaseStep> testCaseSteps = testCaseStepsMap.get(testCase.getId());
        //            if (insertOrUpdate.getCode().equals("200")) {
        //                for (TestCaseStep testCaseStep : testCaseSteps) {
        //                    //testCaseStep.setTestCaseId(testCase.getId());
        //                    testCaseStepDao.insert(testCaseStep);
        //                }
        //            } else {
        //                throw new BizException(insertOrUpdate.getCode(), insertOrUpdate.getMsg());
        //            }
        //        }
        //        //判断是否创建视图
        //        Boolean ifCreateView = jsonObject.getBooleanValue("ifCreateView");
        //        if (ifCreateView) {
        //            createViewImportTestCase(now);
        //        }
        //    } else {
        //        errorCount = rowValueArray.size();
        //    }
        //    //判断是否发送email
        //    Boolean ifSendEmail = jsonObject.getBooleanValue("ifSendEmail");
        //    if (ifSendEmail) {
        //        sendEmailImportTestCase(successCount, updateCount, errorCount);
        //    }
        //    return new Resp.Builder<ImportTestCaseDto>().setData(buildImportTestCaseDto(errorTipsMap, successCount, updateCount, errorCount)).ok();
        //} catch (Exception e) {
        //    log.error("class: TestCaseServiceImpl#importTestCase,error []" + e.getMessage());
        //    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        //    return new Resp.Builder<ImportTestCaseDto>().buildResult(SysConstantEnum.SYSTEM_BUSY.getCode(), e.getMessage());
        //}
        return new Resp.Builder<ImportTestCaseDto>().buildResult(SysConstantEnum.SYSTEM_BUSY.getCode(),
            "导入测试用例失败");
    }

    /**
     * 导入测试用例发送emial
     *
     * @param successCount
     * @param updateCount
     * @param errorCount
     */
    private void sendEmailImportTestCase(int successCount, int updateCount, int errorCount) {
        // 获取当前登录人的邮箱
        String email = jwtUserService.getUserLoginInfo().getSysUser().getEmail();
        // 判断是否子用户，根据分割标识
        if (email.indexOf(OneConstant.COMMON.SUB_USER_SEPARATOR) > 0) {
            email = email.split(OneConstant.COMMON.SUB_USER_SEPARATOR)[1];
        }
        MailDto mailDto = new MailDto();
        mailDto.setToEmail(email);
        mailDto.setTitle(OneConstant.EMAIL.TITLE_IMPORTTESTCASE);
        mailDto.setTemplateHtmlName(OneConstant.EMAIL.TEMPLATEHTMLNAME_IMPORTTESTCASE);
        Map<String, Object> attachmentMap = new HashMap<>();
        attachmentMap.put("importDateTime", DateUtil.format(new Date(), "yyyy年MM月dd日 HH:mm:ss"));
        attachmentMap.put("successCount", successCount);
        attachmentMap.put("errorCount", errorCount);
        attachmentMap.put("updateCount", updateCount);
        mailDto.setAttachment(attachmentMap);
        mailService.sendTemplateMail(mailDto);
    }

    /**
     * 导入测试创建视图
     *
     * @param now 导入时间
     */
    private void createViewImportTestCase(Date now) {
        String nowString = DateUtil.format(now, "yyyy-MM-dd HH:mm:ss");
        JSONObject filterJson = new JSONObject();
        filterJson.put("andOr", "and");
        filterJson.put("beginDate", nowString);
        filterJson.put("endDate", nowString);
        filterJson.put("fieldName", "createTime");
        filterJson.put("intVal", "");
        filterJson.put("sourceVal", "");
        filterJson.put("textVal", "");
        filterJson.put("type", "fDateTime");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(filterJson);
        View view = new View();
        view.setFilter(jsonArray.toJSONString());
        view.setScopeName("TestCase");
        view.setIsPrivate(0);
        view.setTitle("创建时间：" + nowString + "-" + nowString);
//        viewService.addView(view);
    }

    /**
     * 根据文件封装col数据
     *
     * @param suffix          文件后缀
     * @param inputStream     文件流
     * @param cellIndexObject 列的下标JSON
     * @param ifIgnorFirstRow 是否忽略第一行
     * @return
     */
    public JSONArray buildRowValueArray(String suffix, InputStream inputStream,
                                        JSONObject cellIndexObject,
                                        Integer ifIgnorFirstRow) throws IOException {
        JSONArray rowValueArray = new JSONArray();
        if (suffix.equals("csv")) {
            // 编码格式要是用GBK
            InputStreamReader is = new InputStreamReader(inputStream, "GBK");
            BufferedReader reader = new BufferedReader(is);
            CSVParser parser = CSVFormat.DEFAULT.parse(reader);
            int rownum = 0;

            // 读取文件每行内容
            for (CSVRecord record : parser.getRecords()) {
                //  跳过表头
                if (1 == ifIgnorFirstRow && rownum == 0) {
                    rownum++;
                    continue;
                }
                JSONObject rowValue = new JSONObject();
                // 封装数据
                for (String colKey : cellIndexObject.keySet()) {
                    JSONObject colValue = new JSONObject();
                    Integer colIndex = cellIndexObject.getInteger(colKey);
                    String value = record.get(colIndex);
                    colValue.put("value", value);
                    colValue.put("rownum", rownum);
                    colValue.put("colIndex", colIndex);
                    colValue.put("colLetter", getColLetterByIndex(colIndex));
                    rowValue.put(colKey, colValue);
                    // colValueArray.add(colKey);
                }
                rowValueArray.add(rowValue);
                rownum++;
            }
        } else {
            Workbook workbook = null;
            if (suffix.equals("xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            } else {
                workbook = new HSSFWorkbook(inputStream);
            }
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();

            for (int rownum = 0; rownum <= lastRowNum; rownum++) {
                //  跳过表头
                if (1 == ifIgnorFirstRow && rownum == 0) {
                    continue;
                }
                Row row = sheet.getRow(rownum);
                if (row == null) {
                    continue;
                }
                JSONObject rowValue = new JSONObject();
                // 封装数据
                for (String colKey : cellIndexObject.keySet()) {
                    JSONObject colValue = new JSONObject();
                    Integer colIndex = cellIndexObject.getInteger(colKey);
                    Cell cell = row.getCell(colIndex);
                    String value = "";
                    if (cell != null) {
                        if (cell.getCellType() == CellType.STRING) {
                            value = cell.getStringCellValue();
                        } else if (cell.getCellType() == CellType.NUMERIC) {
                            value = String.valueOf(cell.getNumericCellValue());
                        } else if (cell.getCellType() == CellType.BOOLEAN) {
                            value = String.valueOf(cell.getBooleanCellValue());
                        }
                    }
                    colValue.put("value", value);
                    colValue.put("rownum", rownum);
                    colValue.put("colIndex", colIndex);
                    colValue.put("colLetter", getColLetterByIndex(colIndex));
                    rowValue.put(colKey, colValue);
                }
                rowValueArray.add(rowValue);
            }
            workbook.close();
        }
        return rowValueArray;
    }

    /**
     * 根据列索引获取列字母
     */
    private String getColLetterByIndex(int colIndex) {
        StringBuilder result = new StringBuilder();
        while (colIndex >= 0) {
            result.insert(0, (char) ('A' + colIndex % 26));
            colIndex = colIndex / 26 - 1;
        }
        return result.toString();
    }

    /**
     * 构建导入错误提示
     */
    private void buildErrorTips(Map<SysConstantEnum, Map<String, String>> errorTipsMap,
                                SysConstantEnum errorEnum, JSONObject cellValue, String additionalInfo) {
        // 错误处理逻辑
    }

    /**
     * 设置字段值
     */
    private void setValue(JSONObject cellValue, TestCase testCase,
                          Map<SysConstantEnum, Map<String, String>> errorTipsMap, String fieldName, boolean required) {
        // 字段设置逻辑
    }

    /**
     * 设置选择字段值
     */
    private void setSelectValue(JSONObject cellValue, List<String> allowValues, TestCase testCase,
                                Map<SysConstantEnum, Map<String, String>> errorTipsMap, String fieldName, boolean required) {
        // 选择字段设置逻辑
    }

    /**
     * 获取单元格值
     */
    private String getCellValue(Map<SysConstantEnum, Map<String, String>> errorTipsMap,
                                JSONObject cellValue, boolean required) {
        if (cellValue != null && cellValue.containsKey("value")) {
            return cellValue.getString("value");
        }
        return "";
    }

    /**
     * 构建单元格索引对象
     */
    private JSONObject buildCellIndexByTemplateTestCase(JSONObject jsonObject) {
        JSONObject cellIndexObject = new JSONObject();
        // 根据模板构建列索引映射
        return cellIndexObject;
    }

    /**
     * 根据标题查询Feature
     */
    private Feature queryFeatureByTitle(String title) {
        // 查询Feature逻辑
        return null;
    }

    /**
     * 构建导入结果DTO
     */
    private ImportTestCaseDto buildImportTestCaseDto(Map<SysConstantEnum, Map<String, String>> errorTipsMap,
                                                     int successCount, int updateCount, int errorCount) {
        ImportTestCaseDto dto = new ImportTestCaseDto();
        dto.setSuccessCount(successCount);
        dto.setUpdateCount(updateCount);
        dto.setErrorCount(errorCount);
        return dto;
    }

    /**
     * 获取中文字段名
     */
    private String getCnField(String fieldName) {
        // 字段名映射逻辑
        return fieldName;
    }
}
