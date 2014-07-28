package br.unesp.repositorio.tools.metadatapatterner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.Quote;

import br.unesp.repositorio.base.xmlschema.metadatapatterner.ObjectFactory;
import br.unesp.repositorio.base.xmlschema.metadatapatterner.Pattern;
import br.unesp.repositorio.base.xmlschema.metadatapatterner.Patterns;
import br.unesp.repositorio.tools.metadatapatterner.tools.TextUtils;

import com.google.common.base.Joiner;

public class MetadataPatterner {
	private File inputCsv;
	private File outputCsv;
	private File mapXml;
	private String metadataName;


	public MetadataPatterner(File inputCsv, File outputCsv, File mapXml, String metadataName) {
		super();
		this.inputCsv = inputCsv;
		this.outputCsv = outputCsv;
		this.mapXml = mapXml;
		this.metadataName = metadataName;
	}

	@SuppressWarnings("unchecked")
	public void organizeItensOnCollections() throws JAXBException, IOException{
		Patterns patterns = loadPatternsMap();
		CSVParser csvParser = new CSVParser(new InputStreamReader(new FileInputStream(inputCsv),"UTF8"), CSVFormat.DEFAULT.withDelimiter(','));
		List<CSVRecord> records = csvParser.getRecords();
		CSVRecord headerRecord = records.get(0);
		String[] header = ((List<String>)IteratorUtils.toList(headerRecord.iterator())).toArray(new String[headerRecord.size()]);
		int metadataIndex = Arrays.asList(header).indexOf(this.metadataName);
		CSVPrinter csvPrinter = new CSVPrinter(new OutputStreamWriter(new FileOutputStream(outputCsv),"UTF8"), CSVFormat.DEFAULT.withDelimiter(',').withHeader(header).withRecordSeparator("\n").withQuotePolicy(Quote.MINIMAL));
		for(CSVRecord record : records){
			if(!record.equals(headerRecord)){
				List<String> values = new ArrayList<String>();
				String[] metadata_values = record.get(metadataIndex).split("\\|\\|");
				String patternvalue = "";
				for(String value : metadata_values){
					if(!value.trim().equals(""))
						patternvalue = findPatternValue(value.trim(), patterns);
						if(!patternvalue.isEmpty())
							values.add(patternvalue);
						else
							values.add(value);
				}
				List<String> newRecord = new ArrayList<String>(header.length);

				for(int i=0; i<header.length ; i++ ){
					if(!header[i].equals(this.metadataName)){
						newRecord.add(record.get(i));
					}else{
						newRecord.add(Joiner.on("||").join( values ));
					}
				}
				csvPrinter.printRecord(newRecord);
				csvPrinter.flush();
			}

		}
		csvPrinter.close();
		csvParser.close();

	}


	private Patterns loadPatternsMap() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		@SuppressWarnings("unchecked")
		JAXBElement<Patterns> element = (JAXBElement<Patterns>) unmarshaller.unmarshal(mapXml);
		return element.getValue();
	}

	private String findPatternValue(String examineCollumn, Patterns map){
		examineCollumn = TextUtils.removeExtraSpaces(TextUtils.removePuncts(TextUtils.removeAccents(examineCollumn).toLowerCase()));
		for(Pattern pattern: map.getPattern()){
			for(String pattern_rule: pattern.getRules().getMatch()){
				if(examineCollumn.contains(pattern_rule)){
					return pattern.getValue();
				}
			}
		}

		return "";
	}

}
