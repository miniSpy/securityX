package com.boot.security.server.dao;

import com.boot.security.server.dto.AppDataPO;
import com.boot.security.server.dto.HighestDataPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AppDao {
    @Select("select  * from appdata2")
    public List<AppDataPO> selectAll();

    @Select("select count(*) from  appdata2")
    public int count();

//    @Select(" select appdata2.*,applestore_description.description from appdata2\n" +
//            "        inner  join applestore_description\n" +
//            "        on appdata2.Aid=applestore_description.Aid\n" +
//            "        where appdata2.price>0\n" +
//            "        order by price desc limit 100;")
    public List<HighestDataPO> desc(int Offset,int Limit);




}
