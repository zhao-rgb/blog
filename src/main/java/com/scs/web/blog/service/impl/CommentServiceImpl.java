package com.scs.web.blog.service.impl;

import com.scs.web.blog.dao.ArticleDao;
import com.scs.web.blog.dao.CommentDao;
import com.scs.web.blog.domain.dto.CommentDto;
import com.scs.web.blog.domain.vo.CommentVo;
import com.scs.web.blog.entity.Article;
import com.scs.web.blog.entity.Comment;
import com.scs.web.blog.factory.DaoFactory;
import com.scs.web.blog.service.CommentService;
import com.scs.web.blog.util.Message;
import com.scs.web.blog.util.Result;
import com.scs.web.blog.util.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhao
 * @className CommentServiceImpl
 * @Description TODO
 * @Date 2019/12/5
 * @Version 1.0
 **/
public class CommentServiceImpl implements CommentService {
    private CommentDao commentDao = DaoFactory.getCommentDaoInstance();
    private ArticleDao articleDao = DaoFactory.getArticleDaoInstance();
    private static Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    @Override
    public Map<String, Object> newcomment(CommentDto commentDto) {
        Map<String ,Object> map = new HashMap<>();
        int i = 0;
        int a = 0;
        try{
            i = commentDao.insert(commentDto);
            a = commentDao.updatecommnet(commentDto.getArticleId());
        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("i的值为：" + i);
        System.out.println("a的值为：" + a);
        if(i == 1){
            map.put("msg", Message.REGISTER_SUCCESS);
            map.put("data",commentDto);
            logger.info("评论成功");
        }else {
            map.put("msg",Message.REGISTER_DEFEATED);
        }

        return map;
    }

    @Override
    public List<Comment> listComment() {
        List<Comment> comments = null;
        try {
            comments=commentDao.selectAll();
        } catch (SQLException e) {
            System.err.println("查询所有学生异常");
        }
        return comments;
    }

    @Override
    public Result getComment(long articleId,int currentPage,int count) {
        List<CommentVo> commentVoList = new ArrayList<>();
        try {
            commentVoList = commentDao.getComment(articleId,currentPage,count);
        } catch (SQLException e) {
            logger.error("异常");
        }
        if(commentVoList !=null){
            return Result.success(commentVoList);
        }else {
            return Result.failure(ResultCode.RESULT_CODE_DATA_NONE);
        }

    }

    @Override
    public Result deleteComment(long id ,long articleId) {
        int n =0;
        try {
            n = commentDao.delete(id);
            Article article = articleDao.getArticle(articleId);
            article.setComments(article.getComments()-1);
            articleDao.updatee(article);
        } catch (SQLException e) {
            logger.error("删除出现异常");
        }
        if(n !=0){
            return  Result.success(n);
        }else {
            return Result.failure(ResultCode.RESULT_CODE_DATA_NONE);
        }
    }
}
