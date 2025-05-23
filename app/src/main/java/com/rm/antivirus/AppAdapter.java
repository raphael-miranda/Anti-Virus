package com.rm.antivirus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

    private List<AppInfo> appList;

    public AppAdapter(List<AppInfo> appList) {
        this.appList = appList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, packageView, permView;

        public ViewHolder(View v) {
            super(v);
            nameView = v.findViewById(R.id.appName);
            packageView = v.findViewById(R.id.packageName);
            permView = v.findViewById(R.id.dangerPerms);
        }
    }

    @NonNull
    @Override
    public AppAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppInfo app = appList.get(position);
        holder.nameView.setText(app.appName);
        holder.packageView.setText(app.packageName);
        holder.permView.setText(String.join(", ", app.dangerousPermissions));
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }
}
