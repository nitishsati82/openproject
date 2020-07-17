package com.example.profile.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.example.profile.dbconfig.DbConfiguration;


public class TestIt {

	@Value("${app.message}")
	private String msg;
	
	@Autowired
	DbConfiguration dbconfig;
	
	@Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }
	
	@Bean
    public CommandLineRunner schedulingRunner(TaskExecutor executor) {
        return new CommandLineRunner() {
            public void run(String... args) throws Exception {
            	System.out.println("Running..."+msg);
            	System.out.println(dbconfig.getUrl());
                //executor.execute(new MainOpeation(jdbcTemplate,moveDir));
            }
        };
    }
}
