import re, sys
from pathlib import Path

BASE_ATK = 40                                   # Player.baseAttack
TIER = [0, 2, 4, 6, 8, 10, 13, 16, 19, 22, 26, 30, 34, 38, 42]
DURABILITA_ARCHI = 100                          # struttura[0] di archi/balestre (valore diretto)
MAZZE_PESO = ('MAZZA_CHIODATA', 'ASCIA')        # danno = peso
SPADONE = 'SPADONE'                             # danno = ATK + peso
RANGED = ('ARCO_CORTO', 'ARCO_LUNGO', 'BALESTRA')

def parity(n):                                  # bonus ATTACK % di un vecchio ID, in punti di ATK
    return round(BASE_ATK * TIER[n] / 100)

def to_arrays(text, names):
    for n in names:
        text = re.sub(r'\b(w|a)\.%s = (-?\d+);' % n, r'\1.%s[0] = \2;' % n, text)
    return text

def ranged_block(p):
    # Il vecchio ATTACK di corda passa a legamenti[0]; corda (ora PRECISIONE) resta non assegnata.
    # Parte sia dal registry dopo fix_weapon_registry.py ('w.corda = N;') sia dalla versione
    # precedente di questo script ('w.corda[0] = N;'). Un blocco che ha già legamenti[0] non si tocca.
    if 'w.legamenti[0] =' in p:
        return p, 0
    p, k = re.subn(r'w\.corda(?:\[0\])? = (\d+);', r'w.legamenti[0] = \1;', p)
    if k and 'w.struttura[0] =' not in p:
        p = re.sub(r'(w\.legamenti\[0\] = \d+;)', r'\1\n                w.struttura[0] = %d;' % DURABILITA_ARCHI, p, count=1)
    return p, k

def weapons(path):
    src = Path(path).read_text(encoding='utf-8')
    parts = re.split(r'(?=\n\s*case ")', src)
    out, stats = [], {'punta': 0, 'peso': 0, 'archi (corda->legamenti)': 0}
    for p in parts:
        m = re.search(r'WeaponSubtype\.([A-Z_]+)\)', p)
        sub = m.group(1) if m else None
        if sub in RANGED:
            p, k = ranged_block(p)
            stats['archi (corda->legamenti)'] += k
        elif sub in MAZZE_PESO:
            p, k = re.subn(r'w\.punta = (\d+);', lambda mm: 'w.punta[0] = %d;' % (BASE_ATK + parity(int(mm.group(1)))), p)
            stats['peso'] += k
        elif sub == SPADONE:
            p, k = re.subn(r'w\.punta = (\d+);', lambda mm: 'w.punta[0] = %d;' % parity(int(mm.group(1))), p)
            stats['peso'] += k
        elif sub:
            p, k = re.subn(r'w\.punta = (\d+);', r'w.punta[0] = \1;', p)
            stats['punta'] += k
        out.append(to_arrays(p, ['pomo', 'guardia', 'manico', 'lama']))
    Path(path).write_text(''.join(out), encoding='utf-8')
    print(path, stats)

def shields(path):
    src = Path(path).read_text(encoding='utf-8')
    src, k = re.subn(r'w\.guardia = (-?\d+);', r'w.metalli[0] = \1;', src)
    src = to_arrays(src, ['pomo'])
    Path(path).write_text(src, encoding='utf-8')
    print(path, 'guardia -> metalli[0]:', k)

def armors(path):
    src = Path(path).read_text(encoding='utf-8')
    src, k = re.subn(r'a\.legamenti = (\d+);', r'a.legamenti[0] = \1;', src)
    Path(path).write_text(src, encoding='utf-8')
    print(path, 'legamenti -> legamenti[0]:', k)

root = Path(sys.argv[1]) if len(sys.argv) > 1 else Path('src/items')
weapons(root / 'WeaponRegistry.java')
shields(root / 'ShieldRegistry.java')
armors(root / 'ArmorRegistry.java')
