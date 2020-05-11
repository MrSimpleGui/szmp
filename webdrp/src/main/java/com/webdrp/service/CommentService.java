package com.webdrp.service;

import com.webdrp.entity.Comment;
import com.webdrp.entity.vo.CommentVo;

import java.util.List;

public interface CommentService {

     void insert(Comment comment);

     void delete(Comment comment);

     void deleteCommentByFriendsId(Comment comment);

     List<CommentVo> loadCommentByFriendsId(Comment comment);
}
