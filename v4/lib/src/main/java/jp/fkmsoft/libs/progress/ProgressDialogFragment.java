package jp.fkmsoft.libs.progress;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

/**
 * Dialog fragment for displaying Progress
 */
public class ProgressDialogFragment extends DialogFragment {
    private static final String ARGS_TITLE = "title";
    private static final String ARGS_MESSAGE = "message";
    private static final String ARGS_EXTRA = "extra";

    public static final String EXTRA_DATA = "extra";

    public static ProgressDialogFragment newInstance(Fragment target, int requestCode,
                                                     String title, String message, Bundle extra) {
        ProgressDialogFragment fragment = new ProgressDialogFragment();
        fragment.setTargetFragment(target, requestCode);
        
        Bundle args = new Bundle();
        args.putString(ARGS_TITLE, title);
        args.putString(ARGS_MESSAGE, message);
        args.putBundle(ARGS_EXTRA, extra);
        fragment.setArguments(args);
        
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();
        if (activity == null) { return null; }

        Bundle args = getArguments();
        String title = args.getString(ARGS_TITLE);
        String message = args.getString(ARGS_MESSAGE);

        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setTitle(title);
        dialog.setMessage(message);

        return dialog;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);

        Fragment target = getTargetFragment();
        if (target == null) { return; }

        Bundle args = getArguments();
        Bundle extra = args.getBundle(ARGS_EXTRA);

        Intent data = new Intent();
        data.putExtra(EXTRA_DATA, extra);

        target.onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, data);
    }
}
