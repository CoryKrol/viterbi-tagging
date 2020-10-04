package me.coryt.viterbi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class ViterbiApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(ViterbiApplication.class, args);
	}
	
	@Override
	public void run(String... args) {
		if (args.length != 2) {
			log.error("Invalid number of arguments");
			displayHelp();
			return;
		}

//		if (Integer.parseInt(args[1]) == 0) {
//			ApplicationConstants.LAPLACE_SMOOTHING = false;
//		}
//
//		modelTrainer.trainModel(args[0]);
//		log.info("MODEL TRAINED: ");
//		List<List<BiGram>> testData = modelTrainer.loadTestData(TextProcessingUtil.tokenizeCorpus(ResourceReader.readFileToString("test.txt")));
//		log.info("TEST DATA LOADED: ");
//
//		log.info("DISPLAY RESULTS: ");
//		modelTrainer.printResults(testData);
	}
	
	public void displayHelp() {
		log.info("Program accepts an absolute file path and a 0/1 to enable/disable Laplace smoothing");
	}
	
}
