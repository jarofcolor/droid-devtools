package real.droid.devtools.ui.dir;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import real.droid.devtools.R;
import real.droid.devtools.ui.text.EditorFragment;
import real.droid.libx.core.FragmentX;
import real.droid.libx.core.NavigatorX;

public class FileMenuDialog extends Dialog {
    private final String filePath;
    private final Activity activity;

    public FileMenuDialog(String filePath, Activity activity) {
        super(activity);
        this.activity = activity;
        this.filePath = filePath;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dt_dialog_file_menu);


        findViewById(R.id.tv_file_text_open).setOnClickListener(v -> {
            NavigatorX.on(activity).push(FragmentX.on(new EditorFragment())
                    .addArg(EditorFragment.KEY_FILE_PATH, filePath)
                    .fragment()
            );
            dismiss();
        });
    }
}
