package com.webdrp.config.wechat;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Button;
import com.foxinmy.weixin4j.mp.WeixinProxy;
import com.foxinmy.weixin4j.mp.model.Tag;
import com.foxinmy.weixin4j.type.ButtonType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化创建菜单
 * Created by Macx on 2017/8/16.
 */
@Component
public class WechatMenuConfig {

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 统一api
     */
//    @Autowired
//    @Qualifier("wechatService")
//    private WeixinProxy wechatService;

    public static Tag findTagByName(String name, WeixinProxy wechatService) throws WeixinException {
        Tag g = null;
        List<Tag> tags = wechatService.listTags();
        for (Tag group: tags) {
            if (group.getName().equals(name))
                g = group;
        }
        if (g == null){
            g = wechatService.createTag(name);
        }
        return g;
    }

    /**
     * 更新目录
     */
    public void updateMenu() {
        // 菜单
//        List<Button> buttons = new ArrayList<>();
//        // 左1
//        Button left = new Button("首页");
//        left.setType(ButtonType.view);
//        left.setContent("http://yuanye.xiaoye0724.cn/wechat/v1/index");
//        buttons.add(left);
//        try {
//            logger.debug("创建默认菜单成功",  wechatService.createMenu(buttons).toString());
//        } catch (WeixinException e) {
//            logger.error("创建菜单失败");
//            e.printStackTrace();
//        }
    }
}
