package com.mydream.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_EncryptUtil {
	
	EncryptUtil encryptUtil = new EncryptUtil();
	
	@Test
	public void getLoginPassword() {
		String str = "123456";
		String md5Str = encryptUtil.stringToMD5(str);
		System.out.println("md5==="+md5Str);
		String sst = encryptUtil.stringToBASE64(md5Str);
		System.out.println("base64Str====="+sst);
		String base64toStr = encryptUtil.base64ToString(sst);
		System.out.println("=-=-="+base64toStr);
	}

}
