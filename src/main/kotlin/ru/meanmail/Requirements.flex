package ru.meanmail;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import ru.meanmail.psi.RequirementsTypes;
import com.intellij.psi.TokenType;

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
SEPARATOR="<=" | "<" | "==" | ">=" | ">"
PACKAGE_CHARACTER=[^:=<>\[\]\ \n\t\f\\.]
VERSION=[^\ \n\t\f\\]
DIRECTORY=([a-zA-Z]:\\\\)?([a-zA-Z0-9._\-\\]+) | [a-zA-Z0-9._\-/]+
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
    {END_OF_LINE_COMMENT}                { yybegin(YYINITIAL); return RequirementsTypes.COMMENT; }
    "-r"                                 { yybegin(WAITING_FILENAME); return RequirementsTypes.REQUIREMENT; }
    "-e"                                 { yybegin(WAITING_URL); return RequirementsTypes.REQUIREMENT_EDITABLE; }
    {PACKAGE_CHARACTER}+                 { yybegin(YYINITIAL); return RequirementsTypes.PACKAGE; }
    {SEPARATOR}                          { yybegin(WAITING_VERSION); return RequirementsTypes.SEPARATOR; }
    "["                                  { yybegin(YYINITIAL); return RequirementsTypes.LSBRACE; }
    "]"                                  { yybegin(YYINITIAL); return RequirementsTypes.RSBRACE; }
    {SCHEMA}                             { yybegin(WAITING_PATH); return RequirementsTypes.SCHEMA; }
}

<WAITING_FILENAME> {FILENAME_CHARACTER}+ { yybegin(YYINITIAL); return RequirementsTypes.FILENAME; }

<WAITING_VERSION> {VERSION}+             { yybegin(YYINITIAL); return RequirementsTypes.VERSION; }

<WAITING_URL> {
    {SCHEMA}                             { yybegin(WAITING_PATH); return RequirementsTypes.SCHEMA; }
    {DIRECTORY}                          { yybegin(WAITING_EGG); return RequirementsTypes.PATH; }
}

<WAITING_PATH> {PATH}                    { yybegin(WAITING_EGG); return RequirementsTypes.PATH; }

<WAITING_EGG> {
    {EGG}                                { yybegin(YYINITIAL); return RequirementsTypes.EGG; }
    {BRANCH}                             { yybegin(WAITING_EGG); return RequirementsTypes.BRANCH; }
}

{CRLF}+                                  { yybegin(YYINITIAL); return RequirementsTypes.CRLF; }

{WHITE_SPACE}+                           {return TokenType.WHITE_SPACE;}

.                                        { return TokenType.BAD_CHARACTER; }
