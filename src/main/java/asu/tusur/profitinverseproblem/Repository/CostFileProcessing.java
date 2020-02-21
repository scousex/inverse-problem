package asu.tusur.profitinverseproblem.Repository;

import asu.tusur.profitinverseproblem.Model.Product;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CostFileProcessing {

    private static final int NAME_COLUMN_NUMBER = 0;
    private static final int DESCRIPTION_COLUMN_NUMBER = 1;
    private static final int COST_COLUMN_NUMBER = 2;
    private static final int PRICE_COLUMN_NUMBER = 3;
    private static final int PRODUCT_ROW = 5;
    private static final int DATE_ROW = 2;
    private static final int DATE_COLUMN_NUMBER = 0;

    public List<Product> getProducts(String filePath) throws Exception {

        System.out.println("Начинаем обработку файла "+filePath);
        String path = "C:/Web/temp/"+filePath;
        List<Product> productList = new ArrayList<Product>();
        File fileStream = new File(path);
        POIFSFileSystem fileSystem = new POIFSFileSystem(fileStream);
        HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
        HSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.rowIterator();
        while(rows.hasNext()){
            HSSFRow row = (HSSFRow) rows.next();
            if(row.getRowNum()>PRODUCT_ROW-1)  {
                HSSFCell nameCell = row.getCell(NAME_COLUMN_NUMBER);
                HSSFCell costCell = row.getCell(COST_COLUMN_NUMBER);
                HSSFCell priceCell = row.getCell(PRICE_COLUMN_NUMBER);
                HSSFCell descripionCell = row.getCell(DESCRIPTION_COLUMN_NUMBER);

                if (nameCell != null && costCell != null && priceCell != null) {
                    Product product = new Product(nameCell.getStringCellValue(),
                            costCell.getNumericCellValue(),
                            priceCell.getNumericCellValue());
                    if (descripionCell != null) product.setProductDescription(descripionCell.getStringCellValue());
                    productList.add(product);
                }
            }
        }
        workbook.close();
        return productList;
    }
}
