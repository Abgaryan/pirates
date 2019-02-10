package pirates.com.pirates.comman;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import pirates.com.pirates.R;

public class DialogHelper {

    public static void showErrorPopup(final Context context, String message) {
        if (context != null && !((Activity) context).isFinishing()) {
            new AlertDialog.Builder(context, R.style.DialogTheme).setCancelable(false)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, (dialog, id) -> {
                    })
                    .show();
        }
    }
}
