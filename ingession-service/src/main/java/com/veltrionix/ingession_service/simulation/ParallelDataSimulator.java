package com.veltrionix.ingession_service.simulation;

import com.veltrionix.ingession_service.dto.EnergyUsageDto;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
public class ParallelDataSimulator implements CommandLineRunner {

    @Value("${simulation.parallel-threads}")
    private int parallelThreads;

    @Value("${simulation.requests-per-interval}")
    private int requestsPerInterval;

    @Value("${simulation.endpoint}")
    private String ingestionEndpoint;

    private final ExecutorService executorService;
    private final Random random;
    private final RestTemplate restTemplate;

    public ParallelDataSimulator(){
        this.executorService = Executors.newCachedThreadPool();
        this.random = new Random();
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Parallel Data Simulator Started .......");
        ((ThreadPoolExecutor)executorService).setCorePoolSize(parallelThreads);
    }

    @Scheduled(fixedRateString = "${simulation.interval-ms}")
    public void sendMockData(){
        int batchSize = requestsPerInterval / parallelThreads;
        int remainders = requestsPerInterval % parallelThreads;

        for(int i = 0; i < parallelThreads; i++){
            int requestForThreads = batchSize * (i < remainders ? 1 : 0);
            executorService.submit(() -> {
                for(int j = 0; j < requestsPerInterval; j++){
                    EnergyUsageDto dto = EnergyUsageDto.builder()
                                                       .deviceId(random.nextLong(1, 6))
                                                       .energyConsumed(Math.round(random.nextDouble(0.0, 10.0) * 1000) / 100.0)
                                                       .timestamp(LocalDateTime.now()
                                                                               .atZone(ZoneId.systemDefault()).toInstant()).build();
                    try{
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);

                        HttpEntity<EnergyUsageDto> request = new HttpEntity<>(dto, headers);
                        restTemplate.postForEntity(ingestionEndpoint, request, Void.class);

                        log.info("Send mock data: {}", dto);
                    } catch (Exception e) {
                        log.error("Failed to send data: {}", e.getMessage());
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }


    @PreDestroy
    public void shutDown(){
        executorService.shutdown();
        log.info("Parallel Data Simulator shutting down ......");
    }
}
