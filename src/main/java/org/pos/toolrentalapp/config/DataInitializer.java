package org.pos.toolrentalapp.config;


import org.pos.toolrentalapp.entity.Tool;
import org.pos.toolrentalapp.entity.ToolType;
import org.pos.toolrentalapp.repository.ToolTypeRepository;
import org.pos.toolrentalapp.repository.ToolsRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!test")
public class DataInitializer {

    @Autowired
    private ToolTypeRepository toolTypeRepository;

    @Autowired
    private ToolsRespository toolsRespository;


    @Bean
    CommandLineRunner initDatabase() {
        return args -> {

            //Tool Types

            ToolType ladder = new ToolType();
            ladder.setToolTypeName("Ladder");
            ladder.setDailyCharge(1.99);
            ladder.setWeekDayCharge(true);
            ladder.setWeekEndCharge(true);
            ladder.setHolidayCharge(false);



            ToolType chainSaw = new ToolType();
            chainSaw.setToolTypeName("Chainsaw");
            chainSaw.setDailyCharge(1.49);
            chainSaw.setWeekDayCharge(true);
            chainSaw.setWeekEndCharge(false);
            chainSaw.setHolidayCharge(true);



            ToolType jackHammer = new ToolType();
            jackHammer.setToolTypeName("Jackhammer");
            jackHammer.setDailyCharge(2.99);
            jackHammer.setWeekDayCharge(true);
            jackHammer.setWeekEndCharge(false);
            jackHammer.setHolidayCharge(false);

            toolTypeRepository.saveAll(List.of(ladder,chainSaw,jackHammer));


            //Tools

            Tool chns=new Tool(null,"CHNS","Stihl",chainSaw);
            Tool ladw=new Tool(null,"LADW","Werner",ladder);
            Tool jakd=new Tool(null,"JAKD","DeWalt",jackHammer);
            Tool jakr=new Tool(null,"JAKR","Ridgid",jackHammer);

            toolsRespository.saveAll(List.of(chns,ladw,jakd,jakr));


        };
    }
}