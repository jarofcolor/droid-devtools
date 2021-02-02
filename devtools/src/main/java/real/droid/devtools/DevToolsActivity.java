package real.droid.devtools;

import android.app.Activity;
import android.os.Bundle;

import real.droid.devtools.ui.dir.DirListActivity;
import real.droid.libx.core.IntentX;

public class DevToolsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dt_activity_dev_tools);
        findViewById(R.id.view_dir).setOnClickListener(v -> {
            IntentX.on(this).className(getPackageName(), DirListActivity.class).startActivity();
        });
    }
}
