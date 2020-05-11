package com.webdrp.mapper;


import com.webdrp.entity.Comment;
import com.webdrp.entity.vo.CommentVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CommentMapper {

    void insert(Comment commoent);


    void delete(Comment comment);

    void deleteCommentByFriendsId(Comment comment);

    List<CommentVo> loadCommentByFriendsId(Comment comment);
}
