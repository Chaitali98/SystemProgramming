%{
    #include "y.tab.h"

    int yywrap();
%}

%%

plays|sings|dances|is|are|have|has|had|was|are|will|be|eats   {return verb;}
in|under|top|bottom  {return Preposition;}
and|but|so|though|although|or|implies   {return Conjunction;}
[a-z]+          {return Noun;}
%%

int yywrap(){
    return 1;
}