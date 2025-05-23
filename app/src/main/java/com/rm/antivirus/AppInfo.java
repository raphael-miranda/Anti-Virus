package com.rm.antivirus;

import java.util.List;

public class AppInfo {
    public String appName;
    public String packageName;
    public List<String> dangerousPermissions;

    public AppInfo(String appName, String packageName, List<String> dangerousPermissions) {
        this.appName = appName;
        this.packageName = packageName;
        this.dangerousPermissions = dangerousPermissions;
    }
}
