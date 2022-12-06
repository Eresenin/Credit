package com.lpnu.vasyliev.credit.controller.menu.command;

import java.util.ArrayList;
import java.util.List;

public class DefaultCommandLists {
    public static List<MenuItem> MAIN_MENU_COMMANDS;

    static {
        MAIN_MENU_COMMANDS = new ArrayList<>();
        MAIN_MENU_COMMANDS.add(new TakeALoan());
        MAIN_MENU_COMMANDS.add(new ExtendCreditLine());
    }

}
