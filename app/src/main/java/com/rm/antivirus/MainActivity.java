package com.rm.antivirus;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AppAdapter adapter;
    List<AppInfo> appList = new ArrayList<>();

    static final Set<String> DANGEROUS_PERMS = new HashSet<>(Arrays.asList(
            "android.permission.SEND_SMS",
            "android.permission.READ_CONTACTS",
            "android.permission.RECORD_AUDIO",
            "android.permission.CAMERA",
            "android.permission.READ_SMS",
            "android.permission.WRITE_EXTERNAL_STORAGE"
    ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        scanApps();
    }

    private void scanApps() {
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo appInfo : apps) {
            try {
                PackageInfo pkgInfo = pm.getPackageInfo(appInfo.packageName, PackageManager.GET_PERMISSIONS);
                String[] requestedPermissions = pkgInfo.requestedPermissions;
                List<String> detected = new ArrayList<>();
                if (requestedPermissions != null) {
                    for (String perm : requestedPermissions) {
                        if (DANGEROUS_PERMS.contains(perm)) {
                            detected.add(perm.replace("android.permission.", ""));
                        }
                    }
                }
                if (!detected.isEmpty()) {
                    String label = (String) pm.getApplicationLabel(appInfo);
                    appList.add(new AppInfo(label, appInfo.packageName, detected));
                }
            } catch (Exception ignored) {}
        }

        adapter = new AppAdapter(appList);
        recyclerView.setAdapter(adapter);
    }
}