import core.Line;
import core.Station;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class RouteCalculatorTest {
    StationIndex stationIndex = new StationIndex();
    RouteCalculator rc;
    List<Station> arr1;
    @Before
    public void setUp() throws Exception {
        stationIndex.addLine(new Line(1, "Кировско-Выборгская"));
        stationIndex.addStation(new Station("Девяткино", stationIndex.getLine(1)));
        stationIndex.addStation(new Station("Гражданский проспект", stationIndex.getLine(1)));
        stationIndex.addStation(new Station("Академическая", stationIndex.getLine(1)));
        stationIndex.addStation(new Station("Политехническая", stationIndex.getLine(1)));
        stationIndex.addStation(new Station("Площадь Мужества", stationIndex.getLine(1)));
        stationIndex.addStation(new Station("Лесная", stationIndex.getLine(1)));
        stationIndex.addStation(new Station("Выборгская", stationIndex.getLine(1)));
        stationIndex.addStation(new Station("Площадь Ленина", stationIndex.getLine(1)));
        stationIndex.addStation(new Station("Чернышевская", stationIndex.getLine(1)));
        stationIndex.addStation(new Station("Площадь Восстания", stationIndex.getLine(1)));
        stationIndex.addStation(new Station("Владимирская", stationIndex.getLine(1)));
        stationIndex.addStation(new Station("Пушкинская", stationIndex.getLine(1)));
        stationIndex.addStation(new Station("Технологический институт", stationIndex.getLine(1)));
        stationIndex.addStation(new Station("Балтийская", stationIndex.getLine(1)));
        stationIndex.addStation(new Station("Нарвская", stationIndex.getLine(1)));
        stationIndex.addStation(new Station("Кировский завод", stationIndex.getLine(1)));
        stationIndex.addStation(new Station("Автово", stationIndex.getLine(1)));
        stationIndex.addStation(new Station("Ленинский проспект", stationIndex.getLine(1)));
        stationIndex.addStation(new Station("Проспект Ветеранов", stationIndex.getLine(1)));


        stationIndex.addLine(new Line(2, "Невский проспект"));
        stationIndex.addStation(new Station("Парнас", stationIndex.getLine(2)));
        stationIndex.addStation(new Station("Проспект Просвещения", stationIndex.getLine(2)));
        stationIndex.addStation(new Station("Озерки", stationIndex.getLine(2)));
        stationIndex.addStation(new Station("Удельная", stationIndex.getLine(2)));
        stationIndex.addStation(new Station("Пионерская", stationIndex.getLine(2)));
        stationIndex.addStation(new Station("Чёрная речка", stationIndex.getLine(2)));
        stationIndex.addStation(new Station("Петроградская", stationIndex.getLine(2)));
        stationIndex.addStation(new Station("Горьковская", stationIndex.getLine(2)));
        stationIndex.addStation(new Station("Невский проспект", stationIndex.getLine(2)));
        stationIndex.addStation(new Station("Сенная площадь", stationIndex.getLine(2)));
        stationIndex.addStation(new Station("Технологический институт", stationIndex.getLine(2)));
        stationIndex.addStation(new Station("Фрунзенская", stationIndex.getLine(2)));
        stationIndex.addStation(new Station("Московские ворота", stationIndex.getLine(2)));
        stationIndex.addStation(new Station("Электросила", stationIndex.getLine(2)));
        stationIndex.addStation(new Station("Парк Победы", stationIndex.getLine(2)));
        stationIndex.addStation(new Station("Московская", stationIndex.getLine(2)));
        stationIndex.addStation(new Station("Звёздная", stationIndex.getLine(2)));
        stationIndex.addStation(new Station("Купчино", stationIndex.getLine(2)));

        List<Station> connect1 = new ArrayList<>();
        connect1.add(stationIndex.getStation("Технологический институт", 1));
        connect1.add(stationIndex.getStation("Технологический институт", 2));
        stationIndex.addConnection(connect1);
        arr1 = new ArrayList<>();
        arr1.add(stationIndex.getStation("Девяткино"));
        arr1.add(stationIndex.getStation("Гражданский проспект"));
        arr1.add(stationIndex.getStation("Академическая"));

        rc = new RouteCalculator(stationIndex);
    }


    @Test
    public void getShortestRoute() {

    }

    @Test
    public void calculateDuration() {

    }
}