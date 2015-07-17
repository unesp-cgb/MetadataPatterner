package br.unesp.repositorio.tools.metadatapatterner;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import br.unesp.repositorio.tools.metadatapatterner.gui.GUI;

public class MetadataPatternerMain {

	private static Options options;

	public static void main(String[] args) throws ParseException {

		options = new Options();

		options.addOption("h", "help", false, "print this message.");
		options.addOption("c", "collumn", true, "collumn to verify.");
		options.addOption("i", "input-file", true, "defines input csv file.");
		options.addOption("o", "output-file", true, "defines output csv file.");
		options.addOption("m", "map-file", true, "defines xml map file.");


		CommandLineParser parser = new GnuParser();
		CommandLine cmd = parser.parse( options, args);

		String collumn = "";
		String input = "";
		String output = "";
		String map = "";

		//If no command def
		if(cmd.getOptions().length==0){
			new GUI().setVisible(true);
		}else{
			try{
				if(cmd.hasOption("h")){
					showHelp();
				}else{
					if(!cmd.hasOption("i")||!cmd.hasOption("o")||!cmd.hasOption("m")||!cmd.hasOption("c")){
						System.err.println("Error: Input file, Output file, Column and Map file are required!");
						showHelp();
					}else{
						input = cmd.getOptionValue("i");
						output = cmd.getOptionValue("o");
						map = cmd.getOptionValue("m");
						collumn = cmd.getOptionValue("c");
						System.out.println("Info: Checking, this process may take a while.");
						MetadataPatterner i2c = new MetadataPatterner(new File(input), new File(output), new File(map), collumn);
						i2c.organizeItensOnCollections();
						System.out.println("Info: Ok");
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				showHelp();
			}

		}

	}

	private static void showHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("MetadataPatterner", options);

	}

}
