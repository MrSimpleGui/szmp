package com.webdrp.entity.vo;

import com.webdrp.entity.Menu;
import lombok.Data;

import java.util.List;

@Data
public class MenuVo extends Menu {
    List<Menu> subMenuList;
}
