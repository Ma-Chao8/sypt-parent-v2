package com.tianma315.core.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class CellUtil {
    public static Boolean isExcel(String fileName){
        Boolean flag;
        String[] nameArray =  fileName.split("\\.");
        Integer index =nameArray.length - 1;
        String name  = nameArray[index];
        if ("xls".equals(name)){
            flag = true;
        }else if("xlsx".equals(name)){
            flag = true;
        }else if("xlsm".equals(name)){
            flag = true;
        }else{
            flag = false;
        }
        return flag;
    }

    public static Boolean isCellEmpty(Cell cell){
        if (cell == null){
            return true;
        }else{
            cell.setCellType(CellType.STRING);
            if (cell.getStringCellValue()==null||cell.getStringCellValue()==""){
                return true;
            }else{
                return false;
            }
        }

    }
}
