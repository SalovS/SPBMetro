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
                "Владимирская","Пушкинская","Технологический институт 1","Балтийская","Нарвская","Кировский завод",
                "Автово","Ленинский проспект","Проспект Ветеранов");
        for(String station: stations1){
            stationIndex.addStation(new Station(station, stationIndex.getLine(1)));
            stationIndex.getLine(1).addStation(stationIndex.getStation(station));
        }

        stationIndex.addLine(new Line(2, "Невский проспект"));
        List<String> stations2 = Arrays.asList("Парнас", "Проспект Просвещения","Озерки","Удельная","Пионерская",
                "Чёрная речка","Петроградская","Горьковская","Невский проспект","Сенная площадь",
                "Технологический институт 2","Фрунзенская","Московские ворота","Электросила","Парк Победы",
                "Московская","Звёздная","Купчино");
        for(String station: stations2){
            stationIndex.addStation(new Station(station, stationIndex.getLine(2)));
            stationIndex.getLine(2).addStation(stationIndex.getStation(station));
        }

        stationIndex.addLine(new Line(3, "Невско-Василеостровская"));
        List<String> stations3 = Arrays.asList("Беговая","Новокрестовская", "Приморская", "Василеостровская",
                "Гостиный двор", "Маяковская", "Площадь Александра Невского", "Елизаровская", "Ломоносовская",
                "Пролетарская", "Обухово","Рыбацкое");
        for(String station: stations3){
            stationIndex.addStation(new Station(station, stationIndex.getLine(3)));
            stationIndex.getLine(3).addStation(stationIndex.getStation(station));
        }

        List<Station> connect1 = new ArrayList<>();
        connect1.add(stationIndex.getStation("Технологический институт 1", 1));
        connect1.add(stationIndex.getStation("Технологический институт 2", 2));
        List<Station> connect2 = new ArrayList<>();
        connect2.add(stationIndex.getStation("Невский проспект", 2));
        connect2.add(stationIndex.getStation("Гостиный двор", 3));
        List<Station> connect3 = new ArrayList<>();
        connect3.add(stationIndex.getStation("Площадь Восстания", 1));
        connect3.add(stationIndex.getStation("Маяковская", 3));
        stationIndex.addConnection(connect1);
        stationIndex.addConnection(connect2);
        stationIndex.addConnection(connect3);

        rc = new RouteCalculator(stationIndex);
    }

    @Test
    public void getShortestRoute_WithoutTransplant() {
        List<Station> actual = new ArrayList<>();
        List<String> stations1 = Arrays.asList("Девяткино","Гражданский проспект","Академическая","Политехническая",
                "Площадь Мужества","Лесная","Выборгская","Площадь Ленина","Чернышевская","Площадь Восстания",
                "Владимирская","Пушкинская","Технологический институт 1","Балтийская","Нарвская","Кировский завод",
                "Автово","Ленинский проспект","Проспект Ветеранов");
        for(String station: stations1){
            actual.add(stationIndex.getStation(station));
        }

        List<Station> expected = rc.getShortestRoute(stationIndex.getStation("Девяткино"),
                stationIndex.getStation("Проспект Ветеранов"));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getShortestRoute_With_One_Transfer() {
        List<Station> actual = new ArrayList<>();
        List<String> stations1 = Arrays.asList("Девяткино","Гражданский проспект","Академическая","Политехническая",
                "Площадь Мужества","Лесная","Выборгская","Площадь Ленина","Чернышевская","Площадь Восстания",
                "Владимирская","Пушкинская","Технологический институт 1");
        for(String station: stations1){
            actual.add(stationIndex.getStation(station));
        }

        List<String> stations2 = Arrays.asList("Технологический институт 2","Фрунзенская","Московские ворота",
                "Электросила","Парк Победы", "Московская","Звёздная","Купчино");
        for(String station: stations2){
            actual.add(stationIndex.getStation(station));
        }

        List<Station> expected = rc.getShortestRoute(stationIndex.getStation("Девяткино"),
                stationIndex.getStation("Купчино"));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getShortestRoute_With_Two_Transfer() {
        List<Station> actual = new ArrayList<>();
        List<String> stations1 = Arrays.asList("Девяткино","Гражданский проспект","Академическая","Политехническая",
                "Площадь Мужества","Лесная","Выборгская","Площадь Ленина","Чернышевская","Площадь Восстания");
        for(String station: stations1){
            actual.add(stationIndex.getStation(station));
        }

        List<String> stations2 = Arrays.asList("Маяковская","Гостиный двор");
        for(String station: stations2){
            actual.add(stationIndex.getStation(station));
        }

        List<String> stations3 = Arrays.asList("Невский проспект", "Горьковская", "Петроградская",
                "Чёрная речка", "Пионерская","Удельная","Озерки","Проспект Просвещения","Парнас");
        for(String station: stations3){
            actual.add(stationIndex.getStation(station));
        }

        List<Station> expected = rc.getShortestRoute(stationIndex.getStation("Девяткино"),
                stationIndex.getStation("Парнас"));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void calculateDuration_WithoutTransplant_1() {
        double interStationDuration = 2.5;
        List<Station> stations = rc.getShortestRoute(stationIndex.getStation("Девяткино"),
                stationIndex.getStation("Проспект Ветеранов"));
        double actual = interStationDuration * 18;
        double expected = rc.calculateDuration(stations);
        Assert.assertEquals(expected, actual, 0.0001);
    }

    @Test
    public void calculateDuration_WithoutTransplant_2() {
        double interStationDuration = 2.5;
        List<Station> stations = rc.getShortestRoute(stationIndex.getStation("Петроградская"),
                stationIndex.getStation("Электросила"));
        double actual = interStationDuration * 7;
        double expected = rc.calculateDuration(stations);
        Assert.assertEquals(expected, actual, 0.0001);
    }

    @Test
    public void calculateDuration_With_One_Transfer_1() {
        double interStationDuration = 2.5;
        double interConnectionDuration = 3.5;
        List<Station> stations = rc.getShortestRoute(stationIndex.getStation("Девяткино"),
                stationIndex.getStation("Купчино"));
        double actual = interStationDuration * 19 + interConnectionDuration * 1;
        double expected = rc.calculateDuration(stations);
        Assert.assertEquals(expected, actual, 0.0001);
    }

    @Test
    public void calculateDuration_With_One_Transfer_2() {
        double interStationDuration = 2.5;
        double interConnectionDuration = 3.5;
        List<Station> stations = rc.getShortestRoute(stationIndex.getStation("Парнас"),
                stationIndex.getStation("Проспект Ветеранов"));
        double actual = interStationDuration * 16 + interConnectionDuration * 1;
        double expected = rc.calculateDuration(stations);
        Assert.assertEquals(expected, actual, 0.0001);
    }

    @Test
    public void calculateDuration_With_One_Transfer_3() {
        double interStationDuration = 2.5;
        double interConnectionDuration = 3.5;
        List<Station> stations = rc.getShortestRoute(stationIndex.getStation("Фрунзенская"),
                stationIndex.getStation("Елизаровская"));
        double actual = interStationDuration * 6 + interConnectionDuration * 1;
        double expected = rc.calculateDuration(stations);
        Assert.assertEquals(expected, actual, 0.0001);
    }

    @Test
    public void calculateDuration_With_Two_Transfer_1() {
        double interStationDuration = 2.5;
        double interConnectionDuration = 3.5;
        List<Station> stations = rc.getShortestRoute(stationIndex.getStation("Девяткино"),
                stationIndex.getStation("Парнас"));
        double actual = interStationDuration * 18 + interConnectionDuration * 2;
        double expected = rc.calculateDuration(stations);
        Assert.assertEquals(expected, actual, 0.0001);
    }

    @Test
    public void calculateDuration_With_Two_Transfer_2() {
        double interStationDuration = 2.5;
        double interConnectionDuration = 3.5;
        List<Station> stations = rc.getShortestRoute(stationIndex.getStation("Горьковская"),
                stationIndex.getStation("Чернышевская"));
        double actual = interStationDuration * 3 + interConnectionDuration * 2;
        double expected = rc.calculateDuration(stations);
        Assert.assertEquals(expected, actual, 0.0001);
    }
}

