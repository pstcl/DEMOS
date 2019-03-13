package com.example.mailtest.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import com.example.mailtest.Repositories.MailListRepo;
import com.example.mailtest.entity.MailList;

@Configuration
public class ExcelFileToDatabaseJobConfig {
 
	@Autowired
	MailListRepo repository;

	@Autowired 
	 MailService mailservice;
	
	public void scheduleFixedRateTask() {

		File fileToRead=new File("C:/Users/pc/Desktop/Book1.xlsx");
		List<MailList> list;
		
		

		list= readExcel(fileToRead);
  
		repository.saveAll(list);
		
		mailservice.scheduleFixedRateTask();
		
	}


	
	
	private Map<String,int[]> getColumnIndices(Sheet datatypeSheet, File fileToRead,Workbook wb)
	{
		Map<String,int[]> map=new HashMap<>();
		for (Row row : datatypeSheet) {

			for (Cell cell : row) {

				if (cell.getCellType() == CellType.STRING) {
					if (cell.getRichStringCellValue().getString().trim().equals("Name")) 
					{
						int[] array={cell.getColumnIndex(),cell.getRowIndex()};
						map.put(cell.getRichStringCellValue().getString().trim(), array);

					}

					if (cell.getRichStringCellValue().getString().trim().equals("Email")) 
					{

						int[] array={cell.getColumnIndex(),cell.getRowIndex()};
						map.put(cell.getRichStringCellValue().getString().trim(), array);
					}

				}
			}
		}
		return map;

	}


	public List<MailList> readExcel(File fileToRead) {
		List<MailList> list=new ArrayList();
		FileInputStream inputStream=null;
		try {
			inputStream = new FileInputStream(fileToRead);
			Workbook workbook = new XSSFWorkbook(inputStream);
			//workbook.getSettings().setRegion(CountryCode.INDIA); 
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Map<String,int[]> columnIndices= getColumnIndices(datatypeSheet,fileToRead,workbook);

			if(columnIndices!=null&&!columnIndices.isEmpty())
			{

				for (Row row : datatypeSheet) {
					if(row.getRowNum()!=columnIndices.get("Name")[1]
							&&row.getCell(columnIndices.get("Name")[0])!= null 
							&& row.getCell(columnIndices.get("Name")[0]).getCellType() != CellType.BLANK)
					{
						String email=row.getCell(columnIndices.get("Email")[0]).getStringCellValue();
						if(repository.existsById(email)==false) {
						
						MailList entity=new MailList();
						
						
						entity.setName(row.getCell(columnIndices.get("Name")[0]).getStringCellValue());
						entity.setEmailAddress(email);
						entity.setFlag(false);
						list.add(entity);
						System.out.println(entity);
						}
					}

				}
			}

			inputStream.close();
			return list;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;


	}



}