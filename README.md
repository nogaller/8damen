# 8damen
Kennen Sie das **8 Damen Problem**? 
Wir treiben es auf das nächste Level ;) 

Aufgabe: **N Damen** auf einem **NxN großen Brett** zu platzieren.
Constraints, 2 Damen sehen einander: 
*	Horizontal
*	Vertikal
*	Diagonal (±45°) 
*	Extra 1: keine 3 Damen auf einer beliebigen geraden Linie!
*	Extra 2: N - ungerade
 
Lösung in Form **0 1 2 3 4 5 6**, Aufzählung startet mit 0, X –Index der Zelle, Y –Zahl in der Zelle 

Z.B. 
*6 0 3 1 7 5 8 2 4*

Entspricht einem Board:  
o H o o o o o o o  
o o o H o o o o o  
o o o o o o o H o  
o o H o o o o o o  
o o o o o o o o H  
o o o o o H o o o  
H o o o o o o o o  
o o o o H o o o o  
o o o o o o H o o  
