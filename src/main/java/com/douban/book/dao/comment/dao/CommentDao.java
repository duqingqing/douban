package com.douban.book.dao.comment.dao;

import com.douban.book.base.dao.GenericDao;
import com.douban.book.dao.book.information.domain.Information;
import com.douban.book.dao.book.url.domain.BookUrl;
import com.douban.book.dao.comment.domain.Comment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface CommentDao extends GenericDao<Comment,Long> {
    @Modifying
    @Query("update Comment comment set comment.mark = :mark where comment.id = :id")
    public Integer updateCommentMark(@Param("mark") int mark, @Param("id") Long id);

    @Query(value = "select book_url_id from book_comment order by id desc LIMIT 1",nativeQuery = true)
    public Long findLastOne();

    @Query(value="select count(*) from book_comment b where b.book_url_id = ?1",nativeQuery = true)
    public int getCommentsNumber(Long id);

    @Modifying
    @Query(value="delete from book_comment  where book_url_id = ?1",nativeQuery = true)
    public void deleteCommentsByBookUrl(Long id);
}


