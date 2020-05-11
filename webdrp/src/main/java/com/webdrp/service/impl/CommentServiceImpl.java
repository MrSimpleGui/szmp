package com.webdrp.service.impl;

import com.webdrp.entity.Comment;
import com.webdrp.entity.Friends;
import com.webdrp.entity.vo.CommentVo;
import com.webdrp.err.BusinessException;
import com.webdrp.mapper.CommentMapper;
import com.webdrp.mapper.FriendsMapper;
import com.webdrp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private FriendsMapper friendsMapper;


    /**
     * 插入评论
     * @param comment
     */
    @Override
    public void insert(Comment comment) {
        Friends temp = new Friends();
        temp.setId(comment.getFriendsId());
        Friends friends = friendsMapper.findById(temp);
        if(friends == null)
            throw new BusinessException("无该朋友圈");
        commentMapper.insert(comment);
    }


    /**
     * 删除单条评论
     * @param comment
     */
    @Override
    public void delete(Comment comment) {
        comment.beforeDelete();
        commentMapper.delete(comment);
    }

    /**
     * 根据朋友圈id删除以下所有评论
     * @param comment
     */
    @Override
    public void deleteCommentByFriendsId(Comment comment) {
        commentMapper.deleteCommentByFriendsId(comment);
    }

    /**
     * 加载某条朋友圈下的评论，暂时没用
     * @param comment
     * @return
     */
    @Override
    public List<CommentVo> loadCommentByFriendsId(Comment comment) {
        List<CommentVo> commentVos = commentMapper.loadCommentByFriendsId(comment);
        return commentVos;
    }
}
