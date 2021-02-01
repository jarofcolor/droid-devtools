package real.droid.devtools.ui.dir;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

import real.droid.devtools.R;
import real.droid.devtools.libx.FragmentX;
import real.droid.devtools.libx.NavigatorX;
import real.droid.libx.core.BundleX;

public class DirListFragment extends Fragment {

    public static final String KEY_TITLES = "key_titles";
    public static final String KEY_DIRS = "key_data";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dt_fragment_dir_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }

        String[] titles = bundle.getString(KEY_TITLES).split(";");
        String[] dirs = bundle.getString(KEY_DIRS).split(";");
        ArrayList<Object> data = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            data.add(new DirListViewAdapter.TitleItem(titles[i]));
            String dir = dirs[i];
            String[] files = new File(dir).list();
            for (String fileName : files) {
                data.add(new DirListViewAdapter.FileItem(dir, fileName));
            }
        }

        View view = getView();
        ListView listView = view.findViewById(R.id.view_list);
        listView.setAdapter(new DirListViewAdapter(getActivity(), data));

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Object item = data.get(position);
            if (item instanceof DirListViewAdapter.FileItem) {
                DirListViewAdapter.FileItem fileItem = (DirListViewAdapter.FileItem) item;
                File file = new File(fileItem.path, fileItem.name);
                if (file.isDirectory())
                    NavigatorX.on(getActivity()).push(FragmentX.on(new DirListFragment()).bundle(
                            new BundleX().
                                    putString(DirListFragment.KEY_TITLES, fileItem.name).
                                    putString(DirListFragment.KEY_DIRS, file.getAbsolutePath())
                    ).fragment());
            }
        });
    }
}
