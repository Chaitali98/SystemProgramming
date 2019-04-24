%{
#include<stdio.h>
int line_count=0;
int char_count=0;
int word_count=0;
%}

%%
[a-zA-z0-9]  {char_count++;}
[' ' \t]+        {word_count++;}
[\n]+         {line_count++;word_count++;}
.       {char_count++ ;}
%%

int main(){
	yylex();
    printf("char_count : %d \n line_count : %d \n word_count : %d \n" , char_count ,line_count, word_count  );
}
