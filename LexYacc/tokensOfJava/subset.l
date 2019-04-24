%{
#include<stdio.h>
#define SC 1
#define Number 2
#define Operator 3
#define Identifier 4
#define Keyword 5
#define Brackets 6
%}

%%
[+-]?[0-9]+  {printf("%s : Number\n" , yytext);}
void|System|println|main|if|else|while|for|do|int|char|byte|long|double|static|public|private|protected|default|byte  {printf("%s : keyword\n" , yytext);}
[a-zA-Z_]+[0-9a-zA-Z_]*  {printf("%s : Identifier\n" , yytext);}
;  {printf("%s : semicolon\n" , yytext);}
[ \t\n]  {;}
[\(\)\[\]\{\}]  {printf("%s : brackets\n" , yytext);}
[+-=*/]  {printf("%s operator\n" , yytext);}
%%
