package main;

/**
 * Data di gioco: per ora solo giorno e mese correnti, senza un orologio che li faccia avanzare.
 * Un giorno di gioco dura MINUTES_PER_DAY minuti reali: mese 10 giorni, stagione 30, anno 120 (20 ore).
 */
public class GameCalendar {
    public static final int MINUTES_PER_DAY = 10;
    public static final int DAYS_PER_MONTH = 10;
    public static final int MONTHS_PER_SEASON = 3;
    public static final int SEASONS_PER_YEAR = 4;
    public static final int DAYS_PER_SEASON = DAYS_PER_MONTH * MONTHS_PER_SEASON;
    public static final int DAYS_PER_YEAR = DAYS_PER_SEASON * SEASONS_PER_YEAR;

    public int day = 1;
    public int month = 1;
}
