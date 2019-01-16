package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.StandardRepository;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StandardServiceImpl implements StandardService {
    @Autowired
    private StandardRepository standardRepository;

    @Override
    public void save(Standard standard) {
        standardRepository.save(standard);
    }

    @Override
    public Page<Standard> findPageData(Pageable pageable) {
        return standardRepository.findAll(pageable);
    }

    @Override
    public void delete(String[] ids) {
        for (String idStr : ids) {
            Integer id = Integer.parseInt(idStr);
           standardRepository.delete(id);
        }
    }

    @Override
    public List<Standard> findAll() {
        return standardRepository.findAll();
    }
}
