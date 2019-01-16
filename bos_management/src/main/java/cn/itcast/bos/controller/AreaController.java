package cn.itcast.bos.controller;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.common.PinYin4jUtils;
import cn.itcast.bos.domain.common.ResponseResult;
import cn.itcast.bos.service.AreaService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/area")
public class AreaController {
    @Autowired
    private AreaService areaService;

    @RequestMapping("/batchImport")
    public void batchImport(MultipartFile file) throws IOException {
        List<Area> areas = new ArrayList<>();
        //编写解析代码逻辑
        //基于.xls解析HSSF
        //加载Excel对象
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(file.getInputStream());
        //读取一个sheet
        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        //读取sheet中的每一行
        for (Row row : sheet) {
            //一个数据对应一个区域对象
            if (row.getRowNum() == 0) {
                //第一行跳过
                continue;
            }
            //跳过空行
            if (row.getCell(0) == null || StringUtils.isBlank(row.getCell(0).getStringCellValue())) {
                continue;
            }
            Area area = new Area();
            area.setId(row.getCell(0).getStringCellValue());
            area.setProvince(row.getCell(1).getStringCellValue());
            area.setCity(row.getCell(2).getStringCellValue());
            area.setDistrict(row.getCell(3).getStringCellValue());
            area.setPostcode(row.getCell(4).getStringCellValue());
            areas.add(area);

            // 基于pinyin4j生成城市编码和简码
            String province = area.getProvince();
            String city = area.getCity();
            String district = area.getDistrict();

            // 将最后一个字切掉
            province = province.substring(0, province.length() - 1);
            city = city.substring(0, city.length() - 1);
            district = district.substring(0, district.length() - 1);

            // 简码
            String[] headArea = PinYin4jUtils.getHeadByString(province + city + district);
            StringBuffer sBuffer = new StringBuffer();
            for (String headStr : headArea) {
                sBuffer.append(headStr);
            }

            String shortcode = sBuffer.toString();
            area.setShortcode(shortcode);

            // 城市编码
            String citycode = PinYin4jUtils.hanziToPinyin(city);
            area.setCitycode(citycode);
        }
        areaService.saveBatch(areas);

    }

    /*分页查询*/
    @RequestMapping("/pageQuery")
    public Map pageQuery(int page, int rows, Area area) {
        //封装pageRequest对象
        PageRequest pageRequest = PageRequest.of(page - 1, rows);
        //创建查询条件
        Specification<Area> specification = new Specification<Area>() {
            @Override
            public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                //简单单表查询
                if (StringUtils.isNotBlank(area.getProvince())) {
                    Predicate p1 = cb.like(root.get("province").as(String.class), "%" + area.getProvince() + "%");
                    list.add(p1);
                }

                if (StringUtils.isNotBlank(area.getCity())) {
                    Predicate p2 = cb.like(root.get("city").as(String.class), "%" + area.getCity() + "%");
                    list.add(p2);
                }

                if (StringUtils.isNotBlank(area.getDistrict())) {
                    Predicate p3 = cb.like(root.get("district").as(String.class), "%" + area.getDistrict() + "%");
                    list.add(p3);
                }

                //多表查询
               /* Join<Area, Standard> standardJoin = root.join("standard", JoinType.INNER);
                if (area.getStandard() != null && StringUtils.isNotBlank(area.getStandard().getName())) {
                    Predicate p4 = cb.like(standardJoin.get("name").as(String.class),
                            "%" + area.getStandard().getName() + "%");
                    list.add(p4);
                }*/
                return cb.and(list.toArray(new Predicate[0]));
            }
        };
        //调用业务层
        Page<Area> pageData = areaService.findPageData(specification, pageRequest);
        //将返回的page对象转换为datagrid需要的格式
        Map<String, Object> result = new HashMap<>();
        result.put("total", pageData.getTotalElements());
        result.put("rows", pageData.getContent());
        return result;
    }

    @RequestMapping("/save")
    public ResponseResult save(Area area) {
        try {
            areaService.save(area);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.FAIL();
        }
        return ResponseResult.SUCCESS();
    }

    @RequestMapping("/delete")
    public ResponseResult delete(String ids) {
        try {
            String[] idArray = ids.split(",");
            areaService.delete(idArray);
            return ResponseResult.FAIL();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseResult.SUCCESS();
    }

    @RequestMapping("/findAll")
    public List<Area> findAll(){
        List<Area> standards = areaService.findAll();
        return standards;
    }
}
