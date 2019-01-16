package cn.itcast.bos.dao;

import cn.itcast.bos.domain.base.SubArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubAreaRepository extends JpaRepository<SubArea,String>,JpaSpecificationExecutor<SubArea> {
    @Query(value = "delete from Area where id=?1")
    @Modifying
    public void delete(String id);
}
