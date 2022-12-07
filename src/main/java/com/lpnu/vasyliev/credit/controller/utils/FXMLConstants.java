package com.lpnu.vasyliev.credit.controller.utils;

import java.util.HashMap;
import java.util.Map;

public class FXMLConstants {
    public static Map<String, String> FXML_FILES_MAP;

    static {
        FXML_FILES_MAP=new HashMap<>();
        FXML_FILES_MAP.put("Take a loan", "take-a-loan.fxml");
        FXML_FILES_MAP.put("Pay a loan", "pay-a-loan.fxml");
        FXML_FILES_MAP.put("Extend credit line", "extend-credit-line.fxml");
        FXML_FILES_MAP.put("Update loans", "update-loans.fxml");
    }

}
