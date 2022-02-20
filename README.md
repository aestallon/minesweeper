# minesweeper
Simple Minesweeper in Java
## HU
### Setup & Játékmenet
1) Töltsd le ugyanazon mappába a `Minesweeper.jar` és `MANIFEST.MF` file-okat, illetve a `graphics` mappát.
2) Nyiss meg egy parancssort, és lépj a letöltési mappába.
3) `java -jar Minesweeper.jar` paranccsal indul a program.
4) Futtatás során, hiba esetén a parancssorban kapsz értesítést a hibá(k)ról.

Új játék kezdésekor a parancssoron megjelenik a megoldókulcs.

Bal kattintásra megjelenik a cella tartalma, jobb kattintásra a cella 'gyanús'-nak jelölhető. Újabb jobb kattintásra
a cella ismét 'nem gyanús'-sá válik. Ha minden aknát tartalmazó cellát 'gyanús'-nak jelöltél,
a `Submit` gomb megnyomásával leellenőrízhető a megoldás.

### Forráskód
#### CellButton.java (GUI)

A **CellButton** az Aknakereső egyes celláinak nyomógombját írja le: hogy nézzen ki és milyen értékeket tároljon:
- isSus: true, ha a felhasználó gyanítja, hogy aknát rejt
- isUncovered: true, ha a felhasználó már felfedte az értékét (azaz rákattintott bal    egérgombbal);
- isMine: true, ha a aknát rejt;
- value: String-ként tárolja a a gomb 'alatt' lévő értéket (0, 1, ... 8, vagy "x" akna esetén);

A `set` kezdetű metódusok a fenti boolean tulajdonságokat tudják átállítani.

A `setCellButtonImage` metódus arra jó, hogy a cellagomb képét lehet vele változtatni.

*Ezeket leszámítva a cellagomb Swing-es JButton-ként viselkedik.*

#### Minefield.java ("backend")

Ez az osztály egy 'aknamezőt' generál. Az aknamezőnek cellái vannak (Minefield.cells). Ez egy string tömb. A cella értéke `x`, ha a cella egy akna, egyébként `0`, ha egyetlen szomszédja se akna, `1`, ha pontosan 1 szomszédja akna, és így tovább. Minden cellának maximum 8 szomszédja van ( az aknamező szélein értelemszerűen kevesebb ), így ez az érték maximum `8` lehet.

#### MinesweeperGrid.java (GUI)

Ez az osztály egy cellagombokból álló rács készítésére való. A megadott pályaméret és aknaszám alapján generál egy aknamezőt (lásd: Minefield.java) és ehhez generál cellagombokból rácsleosztásban egy panelt.
Itt található a metódus, ami leírja, hogy mi történjen ha egy cellagombra kattintunk.

*(Ezt leszámítva a minesweeperGrid egy Swing-es JPanel-ként viselkedik.)*

#### MsFrame.java (GUI)


Ez az osztály létrehozza a program GUI-ját. A felső gombokat (új játék méretválasztók, és a megoldásbeküldő `Submit` gomb) és az alul lévő játékteret (minesweeperGrid).

#### Main.java
*A psvm metódus értelemszerűen a Main.java osztályban található.*

Először azt ellenőrzi le, hogy megvan-e minden képfájl, ami a cellák megrajzolásához szükségesek. Ha ez
sikeresen lezajlott, egy játékablak konstrukciója történik.

### Screenshot folder

Itt képernyőképeket találhatóak arról, hogy nálam hogy működik a program.

## EN
### Setup
1) Download the files `Minesweeper.jar`, `MANIFEST.MF` and the directory `graphics` into your directory of your
choice.
2) Open a Command Prompt and navigate to your download directory.
3) The program may be started with the command `java -jar Minesweeper.jar`.
4) During program execution, the Command Prompt may inform you of any occuring errors.

Starting a new game will print the solution to the Command Prompt.

Left clicking on a cell will reveal its content, right clicking will flag it 'suspicious'. Further right clicks
will un-flag and flag the cell as such. If all cells containing a mine are flagged as 'suspicious', pressing the
`Submit` button will confirm your solution.

### Source code
#### CellButton.java (GUI)

**CellButton** describes the buttons representing each cell of the minefield (their looks and stored values):
- `isSus`: `true`, if the user suspects the cell contains a mine.
- `isUncovered`: `true`, if the user already uncovered the cell's contents by left-clicking on it;
- `isMine`: `true`, if the cell contains a mine;
- `value`: stores the cell's value as a string ("0", "1", ... "8", or "x" if the cell's a mine);

Methods starting with `set` are used to set the above attributes.

The `setCellButtonImage` method is used to change the cell's appearance.

*Apart from these, the CellButton behaves as a standard Swing JButton.*

#### Minefield.java ("backend")

This class is used to generate a minefield. Its cells are represented as entries in a String[][] array.

#### MinesweeperGrid.java (GUI)

This class creates the gameplay area by populating a grid with CellButtons and by constructing a minefield using the previous class.
This is where the MouseEventListener is implemented to respond to user inputs.

*(Apart from these, the MinesweeperGrid works as a Swing JPanel)*

#### MsFrame.java (GUI)

This class describes the window(frame) of the game, containing the buttons used for initiating a new game, the `Submit` button
used for submitting a solution, and the panel in which the game takes place.

#### Main.java

*Naturally, you can find the psvm method here.*

First the presence of the images necessary for decorating the minesweeper are checked, then
a new game window is constructed.

### Screenshot folder

Contains example images showcasing the program.
