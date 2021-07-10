package com.google.ar.core.examples.java.helloar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.fragment.app.DialogFragment;

public class ResolveDialogFragment extends DialogFragment {
    private static final int MAX_FIELD_LENGTH = 6;

    /** Functional interface for getting the value entered in this DialogFragment. */
    public interface OkListener {
        /**
         * This method is called by the dialog box when its OK button is pressed.
         *
         * @param dialogValue the long value from the dialog box
         */
        void onOkPressed(int dialogValue);
    }

    public static ResolveDialogFragment createWithOkListener(OkListener listener) {
        ResolveDialogFragment frag = new ResolveDialogFragment();
        frag.okListener = listener;
        return frag;
    }

    private EditText shortCodeField;
    private OkListener okListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setView(createDialogLayout())
                .setTitle("Resolve Anchor")
                .setPositiveButton("Resolve", (dialog, which) -> onResolvePressed())
                .setNegativeButton("Cancel", (dialog, which) -> {});
        return builder.create();
    }

    private LinearLayout createDialogLayout() {
        Context context = getContext();
        LinearLayout layout = new LinearLayout(context);
        shortCodeField = new EditText(context);
        // Only allow numeric input.
        shortCodeField.setInputType(InputType.TYPE_CLASS_NUMBER);
        shortCodeField.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        // Set a max length for the input text to avoid overflows when parsing.
        shortCodeField.setFilters(new InputFilter[] {new InputFilter.LengthFilter(MAX_FIELD_LENGTH)});
        layout.addView(shortCodeField);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return layout;
    }

    private void onResolvePressed() {
        Editable roomCodeText = shortCodeField.getText();
        if (okListener != null && roomCodeText != null && roomCodeText.length() > 0) {
            int longVal = Integer.parseInt(roomCodeText.toString());
            okListener.onOkPressed(longVal);
        }
    }
}
