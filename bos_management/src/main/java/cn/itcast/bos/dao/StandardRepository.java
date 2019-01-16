package cn.itcast.bos.dao;

import cn.itcast.bos.domain.base.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface StandardRepository extends JpaRepository<Standard,Integer> {
    @Query(value = "delete  from Standard where id=?1")
    @Modifying
    public void delete(Integer id);
}
