%{
    #include "y.tab.h"
%}
%%
[+-]?[0-9]+(.[0-9]+)*   {return Number;}
int|char|float|double|long|byte|boolean|signed|unsigned   {return datatype;}
public|private|protected|default {return Access;}
[-/+*]    {return Operator;}
;           {return SC;}
,           {return Comma;}
=[*/+-]?    {return assignment;}
[a-zA-Z_]+[a-zA-Z0-9_]* {return Identifier;}
[\t\n ' ']             {;}
%%

int yywrap(){
    return 1;
}