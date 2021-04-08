package com.boot.security.server.service.impl;

import com.boot.security.server.dao.AppDao;
import com.boot.security.server.dto.AppDataPO;
import com.boot.security.server.dto.HighestDataDto;
import com.boot.security.server.dto.HighestDataPO;
import com.boot.security.server.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class AppServiceImpl implements AppService {
    @Autowired
    private AppDao appDao;

    /**
     * 仅供测试使用 不显示到前端
     * @return
     */
    @Override
    public List<AppDataPO> selectAll() {
       return appDao.selectAll();

    }

    @Override
    public List<HighestDataDto> desc(int Offset,int Limit) {
        List<HighestDataPO> desc = appDao.desc(Offset,Limit);
        ArrayList<HighestDataDto> highestDataDtos = new ArrayList<>();
        for (HighestDataPO po: desc) {
            HighestDataDto highestDataDto = new HighestDataDto();
//            System.out.println(po.getSize_bytes());
            highestDataDto.setId(po.getId());
            highestDataDto.setAid(po.getAid());
            highestDataDto.setPrice(po.getPrice());
            highestDataDto.setSizeBytes(po.getSizeBytes());
            highestDataDto.setTrackName(po.getTrackName());
//            highestDataDto.setSize_bytes(po.getSize_bytes());
//            highestDataDto.setTrack_name(po.getTrack_name());
            highestDataDto.setDescription(po.getDescription());
            highestDataDtos.add(highestDataDto);
        }
        return highestDataDtos;

    }
}
