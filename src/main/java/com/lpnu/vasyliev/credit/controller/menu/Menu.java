package com.lpnu.vasyliev.credit.controller.menu;

import com.lpnu.vasyliev.credit.controller.menu.command.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    private static final Logger logger = LoggerFactory.getLogger(Menu.class);
    private Map<String, MenuItem> menuItemMap;

    public Menu(List<MenuItem> menuItems) {
        menuItemMap = new HashMap<>();

        for(MenuItem menuItem: menuItems)
            menuItemMap.put(menuItem.toString(), menuItem);
    }


    public void run(String command, String targetLoanId){
        logger.info("entered", this);
        System.out.println(targetLoanId);
        menuItemMap.get(command).execute(targetLoanId);
    }
}
