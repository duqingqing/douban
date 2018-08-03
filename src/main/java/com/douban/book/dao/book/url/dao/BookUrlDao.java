package com.douban.book.dao.book.url.dao;

import com.douban.book.base.dao.GenericDao;
import com.douban.book.dao.book.type.domain.BookType;
import com.douban.book.dao.book.url.domain.BookUrl;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface BookUrlDao extends GenericDao{

    @Query(value = "select bookUrl from BookUrl bookUrl where bookUrl.bookType=?1")
    public List<BookUrl> findByType(BookType bookType);

    @Modifying
    @Query("update BookUrl bookUrl set bookUrl.mark = :mark where bookUrl.id = :id")
    public Integer updateBookUrlMark(@Param("mark")int mark, @Param("id") Long id);
}
