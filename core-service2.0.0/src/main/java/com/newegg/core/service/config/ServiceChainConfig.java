package com.newegg.core.service.config;

import com.newegg.core.service.service.Leave;
import com.newegg.core.service.service.chain.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceChainConfig {

    @Bean
    public Leave initLeaveServiceChain(){
        Leave l1=new Entry();
        Leave l2=new SickLeave();
        Leave l3=new AnnualLeave();
        Leave l4=new MarriageLeave();
        Leave l5=new GestationLeave();
        Leave l6=new NursingLeave();
        Leave l7=new PrenatalExam();

        l1.setSuccessor(l2);
        l2.setSuccessor(l3);
        l3.setSuccessor(l4);
        l4.setSuccessor(l5);
        l5.setSuccessor(l6);
        l6.setSuccessor(l7);
        return l1;
    }
}
