package com.boot.security.server;

import com.boot.security.server.dao.AppDao;
import com.boot.security.server.dao.SysLogsDao;
import com.boot.security.server.dto.AppDataPO;
import com.boot.security.server.dto.HighestDataDto;
import com.boot.security.server.dto.HighestDataPO;
import com.boot.security.server.service.AppService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SecurityApplicationTest {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Resource
	private SysLogsDao sysLogsDao;

	@Resource
	private AppDao appDao;

	@Resource
	private AppService appService;

	@Test
	public void test() {
//		Map<String,String> map=new HashMap();
//		map.put("k1","v1");
//		Vector<String> v = new Vector<>();
//		java.lang.String str1 = new java.lang.String("str1");
//		java.lang.String str2 = new java.lang.String("str2");
//		java.lang.String str3 = new java.lang.String("str3");
//		v.add(str1);
//		v.add(str2);
//		v.add(str3);
//		str1=null;
//		System.out.println(str1);
//		for (int i = 0; i < v.size(); i++) {
//			System.out.println(v.elementAt(i));
//		}
//		ArrayList<String> list = new ArrayList<>();
//		java.lang.String str1 = new java.lang.String("str1");
//		java.lang.String str2 = new java.lang.String("str2");
//		java.lang.String str3 = new java.lang.String("str3");
//		list.add(str1);
//		list.add(str2);
//		list.add(str3);
//		str1=null;
//		System.out.println(str1);
//		System.out.println(list);

//		List<AppDataPO> appDataPOS = appDao.selectAll();
//		System.out.println(appDataPOS.size());
//		System.out.println(appDao.count());
//		List<HighestDataPO> asc = appDao.desc();
//		System.out.println(asc.get(1));
		List<HighestDataDto> desc = appService.desc(0,10);
		System.out.println(desc.get(1));
//		AppDataPO s=appDao.desc(0,10).get(1);
//		System.out.println(s);
	}

}
