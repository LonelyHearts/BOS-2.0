package cn.itcast.bos.test;

import cn.itcast.bos.domain.common.PinYinDuoYinUtils;
import org.junit.Test;

public class PinYin4JTest {

	@Test
	public void test(){
		/*//初始化多音字库
		PinYinDuoYinUtils.initPinyin("pinyin.txt");
		*/
		//
		String chinese = "长城银行";
		
		String pinyin = PinYinDuoYinUtils.convertChineseToPinyin(chinese,false);
		
		
		System.out.println(pinyin);
		
		
		
		
	}
}
