package com.bruce.android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bruce.android.R;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class ScriptsFragment extends Fragment {
    private TextView statusText;
    private EditText scriptInput, resultOutput;
    private Button runBtn, loadBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scripts, container, false);
        statusText = view.findViewById(R.id.status_text);
        scriptInput = view.findViewById(R.id.script_input);
        resultOutput = view.findViewById(R.id.result_output);
        runBtn = view.findViewById(R.id.btn_run);
        loadBtn = view.findViewById(R.id.btn_load);

        runBtn.setOnClickListener(v -> executeScript());
        loadBtn.setOnClickListener(v -> {
            scriptInput.setText("// Loaded script\nfunction main() {\n  return 'Hello from Bruce!';\n}\nmain();");
            updateStatus("Script loaded");
        });

        updateStatus("JS Interpreter ready");
        return view;
    }

    private void executeScript() {
        String code = scriptInput.getText().toString().trim();
        if (code.isEmpty()) {
            resultOutput.setText("// No code to execute");
            return;
        }
        try {
            Context rhino = Context.enter();
            rhino.setOptimizationLevel(-1);
            Scriptable scope = rhino.initSafeStandardObjects();
            Object result = rhino.evaluateString(scope, code, "BruceScript", 1, null);
            resultOutput.setText(Context.toString(result));
            updateStatus("Script executed successfully");
        } catch (Exception e) {
            resultOutput.setText("Error: " + e.getMessage());
            updateStatus("Script error");
        } finally {
            Context.exit();
        }
    }

    private void updateStatus(String s) { statusText.setText("Status: " + s); }
}
</write_to_file>