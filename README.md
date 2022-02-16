# minesweeper
Simple Minesweeper in Java
## HU
### CellButton.java (GUI)

A **CellButton** az Aknakereső egyes celláinak nyomógombját írja le: hogy nézzen ki és milyen értékeket tároljon:
isSus: true, ha a felhasználó gyanítja, hogy aknát rejt
- isUncovered: true, ha a felhasználó már felfedte az értékét (azaz rákattintott bal    egérgombbal);
- isMine: true, ha a aknát rejt;
- value: String-ként tárolja a a gomb 'alatt' lévő értéket (0, 1, ... 8, vagy "x" akna esetén);

A `set` kezdetű metódusok a fenti boolean tulajdonságokat tudják átállítani.

A `setCellButtonImage` metódus arra jó, hogy a cellagomb képét lehet vele változtatni.

**Fontos**: az itt található fájlcím mondja meg a programnak, hogy hol találja az képeket.
Ezt a címet valószínűleg át kell írni arra a helyre ahol te magad tárolod a képeket, pl.
ha a képeket a `C:\minesweeper\képek` mappába töltötted le, akkor a szignatúra `C:\\minesweeper\\képek\\" + filename + ".png"` legyen.

*Ezeket leszámítva a cellagomb Swing-es JButton-ként viselkedik.*

### Minefield.java ("backend")

Ez az osztály egy 'aknamezőt' generál. Az aknamezőnek cellái vannak (Minefield.cells). Ez egy string tömb. A cella értéke `x`, ha a cella egy akna, egyébként `0`, ha egyetlen szomszédja se akna, `1`, ha pontosan 1 szomszédja akna, és így tovább. Minden cellának maximum 8 szomszédja van ( az aknamező szélein értelemszerűen kevesebb ), így ez az érték maximum `8` lehet.

### MinesweeperGrid.java (GUI)

Ez az osztály egy cellagombokból álló rács készítésére való. A megadott pályaméret és aknaszám alapján generál egy aknamezőt (lásd: Minefield.java) és ehhez generál cellagombokból rácsleosztásban egy panelt.
Itt található a metódus, ami leírja, hogy mi történjen ha egy cellagombra kattintunk.

*(Ezt leszámítva a minesweeperGrid egy Swing-es JPanel-ként viselkedik.)*

### MsFrame.java (GUI)


Ez az osztály létrehozza a program GUI-ját. A felső gombokat (új játék méretválasztók, és a megoldásbeküldő `Submit` gomb) és az alul lévő játékteret (minesweeperGrid).

### Main.java
*A psvm metódus értelemszerűen a Main.java osztályban található.*

### Screenshot folder

Itt képernyőképeket találhatóak arról, hogy nálam hogy működik a program.
