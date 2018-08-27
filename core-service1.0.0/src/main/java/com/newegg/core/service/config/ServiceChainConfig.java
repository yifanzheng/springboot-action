package com.newegg.core.service.config;

import com.newegg.core.service.service.qin.Leave;
import com.newegg.core.service.service.qin.chain.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ServiceChainConfig {

    @Bean
    @Qualifier("entry")
    public Leave initLeaveServiceChain(){
        Leave l0=new Entry();
        Leave l1=new AnnualLeave();
        Leave l2=new MarriageLeave();
        Leave l3=new MatterLeave();
        Leave l4=new SickLeave();

        l0.setSuccessor(l1);
        l1.setSuccessor(l2);
        l2.setSuccessor(l3);
        l3.setSuccessor(l4);
        return l0;
    }
}
