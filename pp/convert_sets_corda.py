import re, sys
from pathlib import Path

RANGED = ('ARCO_CORTO', 'ARCO_LUNGO', 'BALESTRA')
DURABILITA_ARCHI = 100
# Gambe: lowerID e i 2 upperID accettati (su 3). Passo (ex set 4) accetta Guardia e Voto;
# Ombra (ex set 5) accetta Veggente e Voto.
LOWER = {4: (1, 1, 3), 5: (2, 2, 3)}

def ranged_block(p):
    # Arco/Balestra: legamenti[0] = ID ATTACK (ex corda), corda[0] = stesso ID per la PRECISIONE.
    # Parte da qualunque stato precedente; un blocco che ha già entrambi non si tocca.
    if 'w.legamenti[0] =' in p and 'w.corda[0] =' in p:
        return p, 0
    m = re.search(r'w\.legamenti\[0\] = (\d+);', p) or re.search(r'w\.corda(?:\[0\])? = (\d+);', p)
    if not m:
        return p, 0
    n = m.group(1)
    p = re.sub(r'\n\s*w\.(?:legamenti\[0\]|corda(?:\[0\])?) = \d+;', '', p)
    new = 'w.legamenti[0] = %s;\n                w.corda[0] = %s;' % (n, n)
    if 'w.struttura[0] =' not in p:
        new += '\n                w.struttura[0] = %d;' % DURABILITA_ARCHI
    p = re.sub(r'(\n\s*)(w\.rarity = )', r'\1' + new.replace('\\', '\\\\') + r'\1\2', p, count=1)
    return p, 1

def weapons(path):
    src = Path(path).read_text(encoding='utf-8')
    parts = re.split(r'(?=\n\s*case ")', src)
    out, n = [], 0
    for p in parts:
        m = re.search(r'WeaponSubtype\.([A-Z_]+)\)', p)
        if m and m.group(1) in RANGED:
            p, k = ranged_block(p)
            n += k
        out.append(p)
    Path(path).write_text(''.join(out), encoding='utf-8')
    print(path, 'archi con corda[0] (PRECISIONE):', n)

def armors(path):
    src = Path(path).read_text(encoding='utf-8')
    c = {'upper': 0, 'lower': 0}
    def repl(m):
        sid = int(m.group(2))
        if sid in LOWER:
            c['lower'] += 1
            lo, a, b = LOWER[sid]
            return '%sa.lowerID = %d; a.acceptedUpperIDs[0] = %d; a.acceptedUpperIDs[1] = %d;' % (m.group(1), lo, a, b)
        c['upper'] += 1
        return '%sa.upperID = %d;' % (m.group(1), sid)
    src = re.sub(r'(\s*)a\.armorSetID = (\d);', repl, src)
    src = re.sub(r'armorSetID: 1=Guardia/.*?Equip\.',
                 'upperID: 1=Guardia/2=Veggente/3=Voto (pezzi upper, per Build); lowerID: 1=Passo/2=Ombra\n'
                 ' * (gambe, per Build) + 2 acceptedUpperIDs — vedi Armor e Player.activeFullID(). Generato\n'
                 ' * da script a partire dalla scheda Equip.', src, count=1, flags=re.S)
    Path(path).write_text(src, encoding='utf-8')
    print(path, 'upperID:', c['upper'], '| lowerID:', c['lower'])

root = Path(sys.argv[1]) if len(sys.argv) > 1 else Path('src/items')
weapons(root / 'WeaponRegistry.java')
armors(root / 'ArmorRegistry.java')
