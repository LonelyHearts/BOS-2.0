package cn.itcast.bos.controller;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.domain.common.ResponseResult;
import cn.itcast.bos.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/standard")
public class StandardController {
    @Autowired
    private StandardService standardService;

    @RequestMapping("/save")
    public ResponseResult save(Standard standard){
        try {
            standardService.save(standard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.FAIL();
        }
        return ResponseResult.SUCCESS();
    }

    @RequestMapping("/pageQuery")
    public Map pageQuery(int page,int rows){
        PageRequest pageRequest = PageRequest.of(page - 1, rows);
        Page<Standard> pageData = standardService.findPageData(pageRequest);

        Map<String,Object> result=new HashMap<>();
        result.put("total",pageData.getTotalElements());
        result.put("rows",pageData.getContent());
        return result;
    }

    @RequestMapping("/delete")
    public ResponseResult delete(String ids){
        try {
             String[] idArray=ids.split(",");
            standardService.delete(idArray);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.FAIL();
        }
        return ResponseResult.SUCCESS();
    }

    @RequestMapping("/findAll")
    public List<Standard> findAll(){
        List<Standard> standards = standardService.findAll();
        return standards;
    }


}
