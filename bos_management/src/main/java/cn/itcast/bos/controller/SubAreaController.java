package cn.itcast.bos.controller;

import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.domain.common.ResponseResult;
import cn.itcast.bos.service.SubAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subArea")
public class SubAreaController {
    @Autowired
    private SubAreaService subAreaService;
    @RequestMapping("/save")
    public ResponseResult save(SubArea subArea){
        try {
            subAreaService.save(subArea);
            return ResponseResult.FAIL();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseResult.SUCCESS();
    }

    @RequestMapping("/pageQuery")
    public Map pageQuery(int page, int rows){
        PageRequest pageRequest = PageRequest.of(page - 1, rows);
        Page<SubArea> pageData = subAreaService.findPageData(pageRequest);

        Map<String,Object> result=new HashMap<>();
        result.put("total",pageData.getTotalElements());
        result.put("rows",pageData.getContent());
        return result;
    }


}
