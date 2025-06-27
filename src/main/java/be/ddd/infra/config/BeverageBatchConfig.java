package be.ddd.infra.config;

import be.ddd.application.batch.CafeBeverageBatchService;
import be.ddd.application.batch.dto.LambdaBeverageDto;
import be.ddd.domain.entity.crawling.CafeBeverage;
import be.ddd.domain.repo.CafeBeverageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BeverageBatchConfig {

    private final CafeBeverageBatchService cafeBeverageBatchService;
    private final CafeBeverageRepository repository;

    @Bean
    public ItemReader<LambdaBeverageDto> beverageReader() {
        List<LambdaBeverageDto> list = cafeBeverageBatchService.fetchAll();
        return new ListItemReader<>(list != null ? list : List.of());
    }

    @Bean
    public ItemProcessor<LambdaBeverageDto, CafeBeverage> beverageProcessor() {
        return cafeBeverageBatchService::toEntity;
    }

    @Bean // TODO 배치 Insert 구현
    public ItemWriter<CafeBeverage> beverageWriter() {
        return repository::saveAll;
    }

    @Bean
    public Step loadBeveragesStep(
            JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("loadBeverages", jobRepository)
                .<LambdaBeverageDto, CafeBeverage>chunk(10, transactionManager)
                .reader(beverageReader())
                .processor(beverageProcessor())
                .writer(beverageWriter())
                .build();
    }

    @Bean
    public Job beverageJob(JobRepository jobRepository, Step loadBeveragesStep) {
        return new JobBuilder("beverageJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(loadBeveragesStep)
                .build();
    }
}
