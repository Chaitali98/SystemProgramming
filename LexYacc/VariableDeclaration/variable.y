
%{
    #include<stdio.h>

    int yylex();
    void yyerror(const char* s);
%}

%token SC;
%token Number ;
%token Operator ;
%token Identifier; 
%token datatype ;
%token assignment; 
%token Comma;
%token Access;
%%
    program : start | start program  ;
    start : Access datatype varlist SC |datatype varlist SC    {printf("Valid Syntax");} ;
    varlist : term Comma varlist |
              term;
    term: Identifier assignment Number |
            Identifier assignment Identifier | 
            Identifier;
%%

int main(){
    printf("Enter variable declaration");
    yyparse();
    return 1;
}

void yyerror(const char* s){
    printf("error from yyerror%s" , s);
}