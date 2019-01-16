package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.AreaRepository;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaRepository areaRepository;

    @Override
    public void saveBatch(List<Area> areas) {
        for (Area area : areas) {
            areaRepository.save(area);
        }
    }

    @Override
    public Page<Area> findPageData(Specification<Area> specification, Pageable pageable) {
        return areaRepository.findAll(specification,pageable);
    }

    @Override
    public void save(Area area) {
        areaRepository.save(area);
    }

    @Override
    public void delete(String[] ids) {
        for (String idStr : ids) {
            areaRepository.delete(idStr);
        }
    }

    @Override
    public List<Area> findAll() {
        return areaRepository.findAll();
    }
}
