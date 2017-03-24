package com.xiongge.config;
import com.xiongge.CxfRuleConfig;
import com.retail.top.entity.TopPosServer;
import com.retail.top.manager.TopPosServerService;
import com.retail.top.utils.enums.StatusEnum;
import org.tempuri.IWCFService;

import javax.annotation.Resource;

public class IWCFServiceRuleConfig implements CxfRuleConfig<Integer>{

	@Resource
	private TopPosServerService topPosServerService;
	@Override
	public String url() {
		return "http://{0}:8000/GoldSoft.Mmis.WCF/GoldSoft.Mmis.WCF.WCFServices?wsdl";
	}

	@Override
	public Class<?> cxfInterface() {
		return IWCFService.class;
	}

	@Override
	public String decide(Integer param) {
		TopPosServer topPosServer = new TopPosServer();
		topPosServer.setStatus(StatusEnum.ENABLE.getCode());
		topPosServer.setStoreNo(String.valueOf(param));
		topPosServer = topPosServerService.selectWithCache(topPosServer);
		return topPosServer.getStoreServerIp();
	}

}
