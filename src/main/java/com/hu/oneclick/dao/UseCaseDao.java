package com.hu.oneclick.dao;

import com.hu.oneclick.model.domain.dto.SearchResultDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用例DAO
 *
 * @author AI Assistant
 * @date 2024
 */
@Mapper
public interface UseCaseDao {

    /**
     * 根据项目ID和标题搜索用例 - 只返回id和title
     */
    List<SearchResultDto> searchByTitleAndProjectId(@Param("projectId") Long projectId, @Param("title") String title);
}
