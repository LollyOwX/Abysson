import re, sys
path = sys.argv[1]
src = open(path, encoding='utf-8').read()
RANGED = ('ARCO_CORTO', 'ARCO_LUNGO', 'BALESTRA')
parts = re.split(r'(?=\n\s*case ")', src)
out, n_melee, n_ranged = [], 0, 0
for p in parts:
    m = re.search(r'WeaponSubtype\.([A-Z_]+)\)', p)
    if m and m.group(1) in RANGED:
        p, k = re.subn(r'w\.punta = ', 'w.corda = ', p); n_ranged += k
    elif m:
        p, k = re.subn(r'w\.(?:taglio|contundente|peso) = ', 'w.punta = ', p); n_melee += k
    out.append(p)
open(path, 'w', encoding='utf-8').write(''.join(out))
print("melee/coltello -> punta:", n_melee, "| archi/balestra punta -> corda:", n_ranged)