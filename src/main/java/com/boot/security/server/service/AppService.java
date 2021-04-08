package com.boot.security.server.service;

import com.boot.security.server.dto.AppDataPO;
import com.boot.security.server.dto.HighestDataDto;
import com.boot.security.server.dto.HighestDataPO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface AppService {
      List<AppDataPO> selectAll();

      List<HighestDataDto> desc(int Offset,int Limit);


}
