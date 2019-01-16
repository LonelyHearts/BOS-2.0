package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.SubArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubAreaService {
    public void save(SubArea subArea);

    public Page<SubArea> findPageData(Pageable pageable);


}
