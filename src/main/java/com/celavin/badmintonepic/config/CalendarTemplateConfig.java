package com.celavin.badmintonepic.config;

import com.celavin.badmintonepic.enums.TournamentLevel;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "game.calendar")//todo这个注释啥意思
public class CalendarTemplateConfig {
    private Map<Integer, List<TournamentLevel>> template;

    public List<TournamentLevel> getLevelsForMonth(int month) {
        if (template == null) {
            return List.of(TournamentLevel.RANKED);
        }
        return template.getOrDefault(month, List.of(TournamentLevel.RANKED));
    }



    public Map<Integer, List<TournamentLevel>> getTemplate() {
        return template;
    }

    public void setTemplate(Map<Integer, List<TournamentLevel>> template) {
        this.template = template;
    }
}
