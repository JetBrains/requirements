import json
import os
import platform
import sys

python_info = {
    "osName": os.name,
    "sysPlatform": sys.platform,
    "platformMachine": platform.machine(),
    "platformPythonImplementation": platform.python_implementation(),
    "platformRelease": platform.release(),
    "platformSystem": platform.system(),
    "platformVersion": platform.version(),
    "pythonVersion": ".".join(platform.python_version_tuple()[:2]),
    "pythonFullVersion": platform.python_version(),
}


def format_full_version(info):
    version = "{0.major}.{0.minor}.{0.micro}".format(info)
    kind = info.releaselevel
    if kind != "final":
        version += kind[0] + str(info.serial)
    return version


if hasattr(sys, "implementation"):
    python_info["implementationVersion"] = format_full_version(sys.implementation.version)
    python_info["implementationName"] = sys.implementation.name
else:
    python_info["implementationVersion"] = "0"
    python_info["implementationName"] = ''

print(json.dumps(python_info))
