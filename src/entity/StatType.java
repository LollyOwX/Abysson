package entity;

/**
 * Le statistiche del personaggio. Ogni stat finale si calcola come:
 *   stat = baseStat (flat, definito in Player) * (1 + bonusPercentuale/100)
 * dove bonusPercentuale è la somma dei bonus % dati dall'equipaggiamento
 * (accumulata via Player.calcStat() ad ogni equip/unequip).
 *
 * Le 6 coppie FIRE_ATK/FIRE_DEF...LIGHT_ATK/LIGHT_DEF sono una per elemento
 * (FUOCO/ACQUA/FULMINE/TERRA/ARIA/LUCE) — niente ElementoATK generico: un colpo
 * a Fuoco usa FireATK/FireDEF, uno a Fulmine usa ElectricATK/ElectricDEF, ecc.
 * FISICO/NONE non hanno una coppia elementale: usano ATTACK/DIFESA.
 * Vedi ElementSystem.attackStat()/defenseStat() per la mappa Element -> StatType.
 */
public enum StatType {
    VITA, ATTACK, DIFESA, VELOCITA, ELUSIONE, PRECISIONE, EFFICIENZA,
    FIRE_ATK, FIRE_DEF,
    WATER_ATK, WATER_DEF,
    ELECTRIC_ATK, ELECTRIC_DEF,
    EARTH_ATK, EARTH_DEF,
    WIND_ATK, WIND_DEF,
    LIGHT_ATK, LIGHT_DEF
}
