package snod.com.cn.iot.device.a1lK0waMzT5;

import snod.com.cn.iot.device.common.Ibasic;

public class device_two implements Ibasic{

	@Override
	public void update(String s) {
		// TODO Auto-generated method stub
		System.out.println(s);
	}

	@Override
	public void online(String str) {
		// TODO Auto-generated method stub
		System.out.println(str);
	}

	@Override
	public void offline(String str) {
		// TODO Auto-generated method stub
		System.out.println(str);
	}

}
