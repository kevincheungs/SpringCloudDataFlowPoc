/*
 * Copyright 2016-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.task.timestamp;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;

/**
 * Spring Boot Application that has tasks enabled.
 */
@EnableTask
@SpringBootApplication
@EnableConfigurationProperties({ TimestampTaskProperties.class })
public class TaskApplication {

	private static final Log logger = LogFactory.getLog(TaskApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
	}

	@Bean
	public TimestampTask timeStampTask() {
		return new TimestampTask();
	}

	/**
	 * A commandline runner that prints a timestamp.
	 */
	public static class TimestampTask implements CommandLineRunner {

		@Autowired
		private TimestampTaskProperties config;

		@Override
		public void run(String... strings) throws Exception {
			DateFormat dateFormat = new SimpleDateFormat(this.config.getFormat());
			String formattedDate = dateFormat.format(new Date());
			logger.info(formattedDate);

			List<String> listOfArgs = Arrays.stream(strings).toList();
			logger.info("Arguments = " + listOfArgs);


			Path file = Paths.get("/Users/kevincheung/results.txt");

			if(formattedDate.equals("2022")) {
				throw new RuntimeException("manually throw error");
			} else {
				List<String> listOfDate = List.of(formattedDate);
				Files.write(file, listOfDate, StandardCharsets.UTF_8);
			}
		}

	}

}
