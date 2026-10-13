package main;

/**
 * Data di gioco: per ora solo giorno e mese correnti, senza un orologio che li faccia avanzare.
 * DAYS_PER_MONTH è quello disegnato nella griglia del calendario del libro (6 x 5 giorni).
 */
public class GameCalendar {
    public static final int DAYS_PER_MONTH = 30;

    public int day = 1;
    public int month = 1;
}
