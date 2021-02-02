package real.droid.devtools.ui.dir;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import real.droid.devtools.R;

public class DirListViewAdapter extends BaseAdapter {
    public static class TitleItem {
        public String title;

        public TitleItem(String title) {
            this.title = title;
        }
    }

    public static class FileItem {
        public String path;
        public String name;

        public FileItem(String path, String name) {
            this.path = path;
            this.name = name;
        }
    }

    private ArrayList<Object> data;
    private Activity context;

    public DirListViewAdapter(Activity context, ArrayList<Object> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object item = getItem(position);
        if (item instanceof TitleItem) {
            TitleItem titleItem = (TitleItem) item;
            View view = View.inflate(context, R.layout.dt_item_dir_list_header, null);
            TextView textView = view.findViewById(R.id.tv_title);
            textView.setText(titleItem.title);
            return view;
        } else if (item instanceof FileItem) {
            FileItem fileItem = (FileItem) item;
            View view = View.inflate(context, R.layout.dt_item_dir_list_file, null);
            TextView textView = view.findViewById(R.id.tv_file);
            textView.setText(fileItem.name);
            ImageView imageView = view.findViewById(R.id.iv_file_type);
            File file = new File(fileItem.path, fileItem.name);
            imageView.setImageResource(file.isDirectory() ? R.mipmap.dt_icon_dir : R.mipmap.dt_icon_file);
            return view;
        }
        return null;
    }
}
