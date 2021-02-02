package real.droid.devtools.ui.dir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

import real.droid.devtools.R;
import real.droid.devtools.base.BaseFragment;
import real.droid.devtools.ui.text.EditorFragment;
import real.droid.devtools.wigets.Toolbar;
import real.droid.libx.core.FragmentX;
import real.droid.libx.core.NavigatorX;

public class DirListFragment extends BaseFragment {

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
            ArrayList<Object> dirList = new ArrayList<>();
            ArrayList<Object> fileList = new ArrayList<>();
            for (String fileName : files) {
                File file = new File(dir, fileName);
                DirListViewAdapter.FileItem item = new DirListViewAdapter.FileItem(dir, fileName);
                if (file.isDirectory())
                    dirList.add(item);
                else
                    fileList.add(item);
            }

            data.addAll(dirList);
            data.addAll(fileList);
        }

        View view = getView();

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setOnBackListener(() -> {
            getActivity().onBackPressed();
        });

        ListView listView = view.findViewById(R.id.view_list);
        listView.setAdapter(new DirListViewAdapter(getActivity(), data));

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Object item = data.get(position);
            if (item instanceof DirListViewAdapter.FileItem) {
                DirListViewAdapter.FileItem fileItem = (DirListViewAdapter.FileItem) item;
                File file = new File(fileItem.path, fileItem.name);
                if (file.isDirectory())
                    NavigatorX.on(getActivity()).push(FragmentX.on(new DirListFragment()).
                            addArg(DirListFragment.KEY_TITLES, fileItem.name).
                            addArg(DirListFragment.KEY_DIRS, file.getAbsolutePath()).
                            fragment());
                else {
                    new FileMenuDialog(file.getAbsolutePath(), getActivity()).show();
                }
            }
        });
    }
}
