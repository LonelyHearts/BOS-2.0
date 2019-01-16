package cn.itcast.bos.dao;

import cn.itcast.bos.domain.base.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AreaRepository extends JpaRepository<Area,String>,JpaSpecificationExecutor<Area> {
    @Query(value = "delete from Area where id=?1")
    @Modifying
    public void delete(String id);
}
