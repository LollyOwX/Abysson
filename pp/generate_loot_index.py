import re
from pathlib import Path

root = Path('src/items')
out = ['package items;', '',
       '/** GENERATO da generate_loot_index.py — non modificare a mano. Rigenerare quando cambiano i registry. */',
       'final class LootIndex {', '    private LootIndex() {}', '']
for name, reg in (('WEAPONS', 'WeaponRegistry'), ('SHIELDS', 'ShieldRegistry'), ('ARMORS', 'ArmorRegistry')):
    ids = re.findall(r'case "([a-z_0-9]+)"', (root / (reg + '.java')).read_text(encoding='utf-8'))
    out.append('    static final String[] %s = {' % name)
    for k in range(0, len(ids), 4):
        out.append('        ' + ', '.join('"%s"' % i for i in ids[k:k + 4]) + ',')
    out.append('    };')
    out.append('')
    print(reg, len(ids))
out.append('}')
(root / 'LootIndex.java').write_text('\n'.join(out) + '\n', encoding='utf-8')
