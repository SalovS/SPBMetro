import core.Line;
import core.Station;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class RouteCalculatorTest {
    StationIndex stationIndex = new StationIndex();
    RouteCalculator rc;


    @Before
    public void setUp() throws Exception {
        stationIndex.addLine(new Line(1, "Кировско-Выборгская"));
        List<String> stations1 = Arrays.asList("Девяткино","Гражданский проспект","Академическая","Политехническая",
                "Площадь Мужества","Лесная","Выборгская","Площадь Ленина","Чернышевская","Площадь Восстания",
                "Владимирская","Пушкинская","Технологический институт","Балтийская","Нарвская","Кировский завод",
                "Автово","Ленинский проспект","Проспект Ветеранов");
        for(String station: stations1){
            stationIndex.addStation(new Station(station, stationIndex.getLine(1)));
            stationIndex.getLine(1).addStation(stationIndex.getStation(station));
        }

        stationIndex.addLine(new Line(2, "Невский проспект"));
        List<String> stations2 = Arrays.asList("Парнас", "Проспект Просвещения","Озерки","Удельная","Пионерская",
                "Чёрная речка","Петроградская","Горьковская","Невский проспект","Сенная площадь",
                "Технологический институт","Фрунзенская","Московские ворота","Электросила","Парк Победы",
                "Московская","Звёздная","Купчино");
        for(String station: stations2){
            stationIndex.addStation(new Station(station, stationIndex.getLine(2)));
            stationIndex.getLine(2).addStation(stationIndex.getStation(station));
        }

        List<Station> connect1 = new ArrayList<>();
        connect1.add(stationIndex.getStation("Технологический институт", 1));
        connect1.add(stationIndex.getStation("Технологический институт", 2));
        stationIndex.addConnection(connect1);

        rc = new RouteCalculator(stationIndex);
    }

    @Test
    public void getShortestRoute_WithoutTransplant() {
        List<Station> actual = new ArrayList<>();
        List<String> stations1 = Arrays.asList("Девяткино","Гражданский проспект","Академическая","Политехническая",
                "Площадь Мужества","Лесная","Выборгская","Площадь Ленина","Чернышевская","Площадь Восстания",
                "Владимирская","Пушкинская","Технологический институт","Балтийская","Нарвская","Кировский завод",
                "Автово","Ленинский проспект","Проспект Ветеранов");
        for(String station: stations1){
            actual.add(stationIndex.getStation(station));
        }

        List<Station> expected = rc.getShortestRoute(stationIndex.getStation("Девяткино"),
                stationIndex.getStation("Проспект Ветеранов"));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getShortestRoute_WithTransfer() {
        List<Station> actual = new ArrayList<>();
        List<String> stations1 = Arrays.asList("Девяткино","Гражданский проспект","Академическая","Политехническая",
                "Площадь Мужества","Лесная","Выборгская","Площадь Ленина","Чернышевская","Площадь Восстания",
                "Владимирская","Пушкинская","Технологический институт");
        for(String station: stations1){
            actual.add(stationIndex.getStation(station));
        }

        List<String> stations2 = Arrays.asList("Технологический институт","Фрунзенская","Московские ворота",
                "Электросила","Парк Победы", "Московская","Звёздная","Купчино");
        for(String station: stations2){
            actual.add(stationIndex.getStation(station));
        }

        List<Station> expected = rc.getShortestRoute(stationIndex.getStation("Девяткино"),
                stationIndex.getStation("Купчино"));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void calculateDuration() {
        double interStationDuration = 2.5;
        List<Station> stations = rc.getShortestRoute(stationIndex.getStation("Девяткино"),
                stationIndex.getStation("Проспект Ветеранов"));
        double actual = interStationDuration * (stations.size() - 1);
        double expected = rc.calculateDuration(stations);
        Assert.assertEquals(expected, actual, 0.0001);
    }
}