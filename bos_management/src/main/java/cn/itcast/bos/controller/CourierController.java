package cn.itcast.bos.controller;


import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.domain.common.ResponseResult;
import cn.itcast.bos.service.CourierService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/courier")
public class CourierController {
    @Autowired
    private CourierService courierService;

    @RequestMapping("/save")
    public ResponseResult save(Courier courier){
        try {
            courierService.save(courier);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.FAIL();
        }
        return ResponseResult.SUCCESS();
    }

    /*简单查询*/
    @RequestMapping("/pageQuery2")
    public Map pageQuery2(int page,int rows){
        PageRequest pageRequest = PageRequest.of(page - 1, rows);
        Page<Courier> pageData = courierService.findPageData(pageRequest);

        Map<String,Object> result=new HashMap<>();
        result.put("total",pageData.getTotalElements());
        result.put("rows",pageData.getContent());
        return result;
    }


    /*分页查询*/
    @RequestMapping("/pageQuery")
    public Map pageQuery(int page,int rows,Courier courier){
        //封装pageRequest对象
        PageRequest pageRequest = PageRequest.of(page - 1, rows);
        //创建查询条件
        Specification<Courier> specification=new Specification<Courier>() {
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list=new ArrayList<>();
                //简单单表查询
                if(StringUtils.isNotBlank(courier.getCourierNum())){
                    Predicate p1=cb.equal(root.get("courierNum").as(String.class),courier.getCourierNum());
                    list.add(p1);
                }

                if(StringUtils.isNotBlank(courier.getCompany())){
                    Predicate p2=cb.like(root.get("company").as(String.class),"%"+courier.getCompany()+"%");
                    list.add(p2);
                }

                if(StringUtils.isNotBlank(courier.getType())){
                    Predicate p3=cb.equal(root.get("type").as(String.class),courier.getType());
                    list.add(p3);
                }

                //多表查询
                Join<Courier,Standard> standardJoin=root.join("standard",JoinType.INNER);
                if(courier.getStandard()!=null&&StringUtils.isNotBlank(courier.getStandard().getName())){
                    Predicate p4=cb.like(standardJoin.get("name").as(String.class),
                            "%"+courier.getStandard().getName()+"%");
                    list.add(p4);
                }
                return cb.and(list.toArray(new Predicate[0]));
            }
        };
        //调用业务层
        Page<Courier> pageData = courierService.findPageData(specification, pageRequest);
        //将返回的page对象转换为datagrid需要的格式
        Map<String,Object> result=new HashMap<>();
        result.put("total",pageData.getTotalElements());
        result.put("rows",pageData.getContent());
        return result;
    }

    @RequestMapping("/delBatch")
    public ResponseResult delBatch(String ids){
        try {
            String[] idArray = ids.split(",");
            courierService.delBatch(idArray);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.FAIL();
        }
        return ResponseResult.SUCCESS();
    }

    @RequestMapping("/restore")
    public ResponseResult restore(String ids){
        try {
            String[] idArray = ids.split(",");
            courierService.restore(idArray);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.FAIL();
        }
        return ResponseResult.SUCCESS();
    }

}
