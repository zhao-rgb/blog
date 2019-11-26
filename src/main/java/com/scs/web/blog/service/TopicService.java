package com.scs.web.blog.service;


import com.scs.web.blog.util.Result;

/**
 * @author zhao
 * @className TopicService
 * @Description TODO
 * @Date 2019/11/17
 * @Version 1.0
 **/
public interface TopicService {
    /**
     * 获取热门专题
     * @param id
     * @return
     */
    Result getHotTopics();

    /**
     * 分页查询专题
     * @param currentPage
     * @param pageCount
     * @return
     */
    Result getPageTopics(int currentPage,int pageCount);


    /**
     * 根据id获取专题详情
     * @param id
     * @return
     */
    Result getTopic(long id);

    /**
     * 根据名称或描述模糊搜索专题
     *
     * @param keywords
     * @return
     */
    Result selectByKeywords(String keywords);


    /**
     * 分页查询专题信息
     * @param currentPage
     * @param count
     * @return
     */
    Result selectByPage(int currentPage,int count);

}
