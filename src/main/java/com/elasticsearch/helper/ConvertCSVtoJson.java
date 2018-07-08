package com.elasticsearch.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class ConvertCSVtoJson {

	public static void main(String args[]) {
		// read the csv file and build a json string
		File csvFile = new File("resources/f_5500_2017_latest.csv");
		File outputJsonFile = new File("resources/bulk_upload_2.json");
		CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
		CsvMapper csvMapper = new CsvMapper();
		List<Object> readAll;
		int numDocsToIndex = 1000;
		FileWriter fw = null;
		try {
			readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(csvFile).readAll();
			fw = new FileWriter(outputJsonFile);
			ObjectMapper mapper = new ObjectMapper();
			int count = 0;
			for (Object obj : readAll) {
				if (count == numDocsToIndex) {
					break;
				}
				++count;
				fw.write("{ \"index\" : { \"_index\": \"plansponsors\", \"_type\" : \"plansponsor\", \"_id\" : \""
						+ count + "\" } }\n");
				fw.write(mapper.writeValueAsString(obj));
				fw.write("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
