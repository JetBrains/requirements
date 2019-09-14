# Requirements plugin

https://plugins.jetbrains.com/plugin/10837

## Syntax

[PEP 440 -- Version Identification and Dependency Specification](https://www.python.org/dev/peps/pep-0440)

[PEP 508 -- Dependency specification for Python Software Packages](https://www.python.org/dev/peps/pep-0508)

[requirements-file-format](https://pip.pypa.io/en/stable/reference/pip_install/#requirements-file-format)

## Features

* Highlighting and Syntax check for requirements.txt files in Intellij IDE
* Highlighting not installed packages
* Package installing
* Reference to subrequirements files (Ctrl + click)

## TODO

* Check not exists packages
* Check duplicate packages
* Distinguish a plain text file from file requirements.txt
* Support relations:

    * "<"
    
    * "<="
    
    * ">"
    
    * ">="
    
    * "==" (support *)
    
    * "!="
    
    * "~="
    
    * "==="
* Reference to package files (Ctrl + click)
