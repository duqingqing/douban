package com.douban.book.dao.book.information.dao;

import com.douban.book.base.dao.GenericDao;
import com.douban.book.dao.book.information.domain.Information;
import com.douban.book.dao.book.type.domain.BookType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.awt.print.Book;

@Transactional
@Repository
public interface InformationDao  extends GenericDao<Information,Long> {

    @Query(value = "select information from Information  information where information.bookType=?1")
    public Information findByBookType(BookType bookType);

    @Modifying
    @Query("update Information information set information.mark = :mark where information.id = :id")
    public Integer updateInformationMark(@Param("mark")int mark, @Param("id") Long id);
}
