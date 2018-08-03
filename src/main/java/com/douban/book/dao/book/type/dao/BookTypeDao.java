package com.douban.book.dao.book.type.dao;

import com.douban.book.base.dao.GenericDao;
import com.douban.book.dao.book.type.domain.BookType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface BookTypeDao extends GenericDao<BookType,Long> {

    @Query(value = "select booktype from BookType booktype where booktype.type = ?1")
    public BookType  findByType(String type);
    @Modifying
    @Query("update BookType booktype set booktype.mark = :mark where booktype.id = :id")
    public Integer updateBookTypeUrlMark(@Param("mark")int mark,@Param("id") Long id);
    @Query(value = "select booktype from BookType booktype where booktype.id = ?1 ")
    public BookType getByBookTypeById(Long id);
}
