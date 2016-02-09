% Notes: not is written as neg, variables are written as lowercase atoms.
% example input: printcnf(or(and(p,q),neg(r))).


% print out the CNF version of X
printcnf(X) :- cnf(X,Y), !, write(Y).

% convert to CNF by repeatedly transforming until we no longer can.
cnf(X,Y) :- transform(X,Z), cnf(Z,Y).
cnf(X,X).


% transform towards CNF...
 
% eliminate implication
transform(implies(X,Y),or(neg(X),Y)).
 
% eliminate double negation
transform(neg(neg(X)),X).  

% push in negations (demorgan's law)
transform(neg(and(X,Y)),or(neg(X),neg(Y))).
transform(neg(or(X,Y)),and(neg(X),neg(Y))).

% push in disjunctions
transform(or(X,and(Y,Z)),and(or(X,Y),or(X,Z))).
transform(or(and(X,Y),Z),and(or(X,Z),or(Y,Z))).

% transform subexpressions
transform(or(X1,Y),or(X2,Y)) :- transform(X1,X2).
transform(or(X,Y1),or(X,Y2)) :- transform(Y1,Y2).
transform(and(X1,Y),and(X2,Y)) :- transform(X1,X2).
transform(and(X,Y1),and(X,Y2)) :- transform(Y1,Y2).
transform(neg(X1),neg(X2)) :- transform(X1,X2).