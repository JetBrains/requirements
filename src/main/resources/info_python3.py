import os
import platform
import sys

print("os_name: " + os.name)
print("sys_platform: " + sys.platform)
print("platform_machine: " + platform.machine())
print("platform_python_implementation: " + platform.python_implementation())
print("platform_release: " + platform.release())
print("platform_system: " + platform.system())
print("platform_version: " + platform.version())
print("python_version: " + ".".join(platform.python_version_tuple()[:2]))
print("python_full_version: " + platform.python_version())
print("implementation_name: " + sys.implementation.name)


def format_full_version(info):
    version = "{0.major}.{0.minor}.{0.micro}".format(info)
    kind = info.releaselevel
    if kind != "final":
        version += kind[0] + str(info.serial)
    return version


if hasattr(sys, "implementation"):
    implementation_version = format_full_version(sys.implementation.version)
else:
    implementation_version = "0"

print("implementation_version: " + implementation_version)
print("extra: ")
