package ru.kir.online.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kir.online.store.utils.AspectForMeasuringTime;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final AspectForMeasuringTime aspectForMeasuringTime;

    @GetMapping("/services")
    public String showStatisticsServices(){
        StringBuilder sb = new StringBuilder();
        Map<String, Long> mapTime =  aspectForMeasuringTime.getMapTime();
        for(String serviceTitle : mapTime.keySet()){
            sb.append(serviceTitle).append(": ").append(mapTime.get(serviceTitle)).append(" ms.").append(System.lineSeparator());
        }
        return sb.toString();
    }
}
