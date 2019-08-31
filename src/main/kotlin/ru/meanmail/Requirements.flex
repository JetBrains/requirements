package ru.meanmail;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import ru.meanmail.psi.RequirementsTypes;

%%

%class RequirementsLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

CRLF=\R
WHITE_SPACE=[\ \t\f]
END_OF_LINE_COMMENT="#"[^\r\n]*
RELATION="~=" | "==" "="? | "!=" | "<=" | ">=" | "<" | ">"
ANSI_LETTER=[a-zA-Z0-9]
PACKAGE_CHARACTER={ANSI_LETTER} | [_\-.]

EPOCH=\d+ "!"
RELEASE=\d+(\.\d+)*
a=[aA]
b=[bB]
c=[cC]
rc=[rR][cC]
alpha=[aA][lL][pP][hH][aA]
beta=[bB][eE][tT][aA]
pre=[pP][rR][eE]
preview=[pP][rR][eE][vV][iI][eE][wW]
post=[pP][oO][sS][tT]
rev=[rR][eE][vV]
r=[rR]
dev=[dD][eE][vV]
v=[vV]
V_SEP=[-_\.]
PRE={V_SEP}?({a}|{b}|{c}|{rc}|{alpha}|{beta}|{pre}|{preview}){V_SEP}?\d*
POST=-\d+|{V_SEP}?({post}|{rev}|{r}){V_SEP}?\d*
DEV={V_SEP}?{dev}{V_SEP}?\d*
LOCAL=\+[a-zA-Z0-9]+({V_SEP}[a-zA-Z0-9]+)*
VERSION={v}? {EPOCH}? {RELEASE} {PRE}? {POST}? {DEV}? {LOCAL}?

DIRECTORY=([a-zA-Z]:\\)?([a-zA-Z0-9._\-\\]+) | [a-zA-Z0-9._\-/]+
FILENAME_CHARACTER={DIRECTORY}?[a-zA-Z0-9._-]+
SVN="svn" | "svn+svn" | "svn+http" | "svn+https" | "svn+ssh"
HTTP="http" | "https"
GIT="git" | "git+http" | "git+https" | "git+ssh"
MERCURIAL="hg+http" | "hg+https" | "hg+static-http" | "hg+ssh"
BAZAAR="bzr+http" | "bzr+https" | "bzr+ssh" | "bzr+sftp" | "bzr+ftp" | "bzr+lp"
FILE="file"
SCHEMA=({SVN} | {HTTP} | {GIT} | {MERCURIAL} | {BAZAAR} | {FILE})":"
PATH=\/\/([\w\.\-]+)\.([a-z]{2,6}\.?)(\/[\w\.\-]*)*\/?
EGG="#egg="
BRANCH="@"[^\ \n\t\f\\@#]+

%state WAITING_VERSION
%state WAITING_FILENAME
%state WAITING_URL
%state WAITING_PATH
%state WAITING_EGG

%%

<YYINITIAL> {
    {END_OF_LINE_COMMENT}                             { yybegin(YYINITIAL); return RequirementsTypes.COMMENT; }
    "-r"                                              { yybegin(WAITING_FILENAME); return RequirementsTypes.REQUIREMENT; }
    "-e"                                              { yybegin(WAITING_URL); return RequirementsTypes.REQUIREMENT_EDITABLE; }
    {ANSI_LETTER}({PACKAGE_CHARACTER}*{ANSI_LETTER})? { yybegin(YYINITIAL); return RequirementsTypes.PACKAGE; }
    {RELATION}                                        { yybegin(WAITING_VERSION); return RequirementsTypes.RELATION; }
    "["                                               { yybegin(YYINITIAL); return RequirementsTypes.LSBRACE; }
    "]"                                               { yybegin(YYINITIAL); return RequirementsTypes.RSBRACE; }
    {SCHEMA}                                          { yybegin(WAITING_PATH); return RequirementsTypes.SCHEMA; }
}

<WAITING_FILENAME> {FILENAME_CHARACTER}+              { yybegin(YYINITIAL); return RequirementsTypes.FILENAME; }

<WAITING_VERSION> {VERSION}                           { yybegin(YYINITIAL); return RequirementsTypes.VERSION; }

<WAITING_URL> {
    {SCHEMA}                                          { yybegin(WAITING_PATH); return RequirementsTypes.SCHEMA; }
    {DIRECTORY}                                       { yybegin(WAITING_EGG); return RequirementsTypes.PATH; }
}

<WAITING_PATH> {PATH}                                 { yybegin(WAITING_EGG); return RequirementsTypes.PATH; }

<WAITING_EGG> {
    {EGG}                                             { yybegin(YYINITIAL); return RequirementsTypes.EGG; }
    {BRANCH}                                          { yybegin(WAITING_EGG); return RequirementsTypes.BRANCH; }
}

{CRLF}+                                               { yybegin(YYINITIAL); return RequirementsTypes.CRLF; }

{WHITE_SPACE}+                                        {return TokenType.WHITE_SPACE;}

.                                                     { return TokenType.BAD_CHARACTER; }
