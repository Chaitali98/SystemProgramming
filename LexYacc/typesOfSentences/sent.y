%{
    #include<stdio.h>
    int yylex();
    void yyerror(const char*);
%}
%token Preposition Noun verb Conjunction;
%%
program : sentence| sentence program;

sentence : simple|compound;

compound: simple Conjunction simple| Noun Conjunction Noun verb predicate   { char* s = $$; printf("%s Statement is comp sent",s );};
simple : subject verb predicate         { printf(" Statement is simp sent" );};
subject: Noun;
predicate : Noun| Preposition Noun;
%%

int main(){
    printf("Enter a Statement:\n");
    yyparse();

    return 1;
}

void yyerror(const char* s){
    printf("Error from yyerror");
}