package ru.netology.sender;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MessageSenderImplTest {
    GeoService geoService;
    LocalizationService localizationService;


    @BeforeEach
    public void startTest() {
        geoService = new GeoServiceImpl();
        localizationService = new LocalizationServiceImpl();
        System.out.println("Start test");
    }

    @AfterEach
    public void overTest() {
        geoService = null;
        localizationService = null;
        System.out.println("Over test");
    }

    @BeforeAll
    public static void startTesting() {
        System.out.println("Start testing");
    }

    @AfterAll
    public static void overTesting() {
        System.out.println("Over testing");
    }

    @Test
    public void messageSenderImplRussianLanguage() {
        Map<String, String> map = new HashMap<>();
        map.put("x-real-ip", "172.0.32.11");

        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp("172.0.32.11")).thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать ");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        String result = messageSender.send(map);

        assertEquals("Добро пожаловать ", result);

    }

    @Test
    public void messageSenderImplEnglishLanguage() {
        Map<String, String> map = new HashMap<>();
        map.put("x-real-ip", "96.44.183.149");

        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp("96.44.183.149")).thenReturn(new Location("New York", Country.USA, " 10th Avenue", 32));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome ");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        String result = messageSender.send(map);

        assertEquals("Welcome ", result);
    }
}
