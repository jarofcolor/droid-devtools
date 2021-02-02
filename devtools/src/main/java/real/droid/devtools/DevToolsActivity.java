package real.droid.devtools;

import android.app.Activity;
import android.os.Bundle;

import real.droid.devtools.ui.main.MainFragment;
import real.droid.libx.core.NavigatorX;

public class DevToolsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dt_activity_container);
        NavigatorX.on(this).container(R.id.container).push(new MainFragment());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NavigatorX.on(this).destroy();
    }

    @Override
    public void onBackPressed() {
        NavigatorX navigatorX = NavigatorX.on(this);
        if (!navigatorX.pop()) {
            super.onBackPressed();
        }
    }
}
