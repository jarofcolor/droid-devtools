package real.droid.devtools.ui.dir;

import android.app.Activity;
import android.os.Bundle;

import real.droid.devtools.R;
import real.droid.devtools.libx.FragmentX;
import real.droid.devtools.libx.NavigatorX;
import real.droid.libx.core.BundleX;

public class DirListActivity extends Activity {
    private static final String TAG = "DirListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dt_activity_dir_list);
        NavigatorX navigatorX = NavigatorX.on(this).container(R.id.container);
        //internal data dir
        String internalDataPath = getFilesDir().getParentFile().getAbsolutePath();
        String externalDataPath = getExternalFilesDir(null).getParentFile().getAbsolutePath();

        navigatorX.push(FragmentX.on(new DirListFragment()).bundle(
                new BundleX().
                        putString(DirListFragment.KEY_TITLES, "内部目录;外部目录").
                        putString(DirListFragment.KEY_DIRS, internalDataPath + ";" + externalDataPath)
        ).fragment());


    }


    @Override
    public void onBackPressed() {
        NavigatorX navigatorX = NavigatorX.on(this);
        if (!navigatorX.canPop()) {
            super.onBackPressed();
        } else {
            navigatorX.pop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NavigatorX.on(this).destroy();
    }
}
