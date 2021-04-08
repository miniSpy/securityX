package com.boot.security.server.controller;

import com.boot.security.server.annotation.LogAnnotation;
import com.boot.security.server.dao.AppDao;
import com.boot.security.server.dto.HighestDataDto;
import com.boot.security.server.page.table.PageTableHandler;
import com.boot.security.server.page.table.PageTableRequest;
import com.boot.security.server.page.table.PageTableResponse;
import com.boot.security.server.service.AppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "apps")
@RestController
@RequestMapping("/apps")
public class AppController {

    @Autowired
    private AppService appService;




    @LogAnnotation
    @GetMapping("/desc")
    @ApiOperation(value = "收费app列表")
    public  PageTableResponse unfreelist(PageTableRequest request){

        return new PageTableHandler(new PageTableHandler.CountHandler() {
            @Override
            public int count(PageTableRequest request) {
                return 120;
            }
        }, new PageTableHandler.ListHandler() {

            //一般实现此接口(list)直接使用Dao层
            @Override
            public List<HighestDataDto> list(PageTableRequest request) {
                List<HighestDataDto> desc = appService.desc(request.getOffset(), request.getLimit());
                return desc;
            }
        }).handle(request);
    }
}
