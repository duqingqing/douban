package com.douban.book.dao.ip.dao;

import com.douban.book.base.dao.GenericDao;
import com.douban.book.dao.ip.domain.Ip;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface IpDao extends GenericDao<Ip,Long> {

    @Modifying
    @Query("update Ip ip set ip.mark = :mark where ip.id = :id")
    public Integer updateIpMark(@Param("mark")int mark, @Param("id") Long id);

}
