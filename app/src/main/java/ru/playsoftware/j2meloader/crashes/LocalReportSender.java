package ru.playsoftware.j2meloader.crashes;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.acra.ReportField;
import org.acra.data.CrashReportData;
import org.acra.sender.ReportSender;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import ru.playsoftware.j2meloader.R;
import ru.playsoftware.j2meloader.config.Config;
import ru.playsoftware.j2meloader.util.Constants;

/**
 * Salva o relatório de erro em um arquivo local (crash.txt) em vez de enviá-lo a um servidor.
 */
public class LocalReportSender implements ReportSender {

	@Override
	public void send(@NonNull Context context, @NonNull CrashReportData report) {
		String logFile = Config.getEmulatorDir() + "/crash.txt";
		try (FileOutputStream fos = new FileOutputStream(logFile)) {
			String logcat = report.getString(ReportField.LOGCAT);
			if (logcat != null) {
				fos.write(logcat.getBytes(StandardCharsets.UTF_8));
			}
			String stack = report.getString(ReportField.STACK_TRACE);
			if (stack != null) {
				fos.write("\n====================Error==================\n".getBytes(StandardCharsets.UTF_8));
				fos.write(stack.getBytes(StandardCharsets.UTF_8));
			}
			JSONObject o = (JSONObject) report.get(ReportField.CUSTOM_DATA.name());
			if (o != null) {
				Object od = o.opt(Constants.KEY_CRASH_ATTACHMENT);
				if (od != null) {
					fos.write("\n==========application=info=============\n".getBytes(StandardCharsets.UTF_8));
					fos.write(((String) od).getBytes(StandardCharsets.UTF_8));
				}
			}
			Toast.makeText(context, context.getString(R.string.crash_report_saved, logFile), Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(context, "Can't save report!", Toast.LENGTH_LONG).show();
		}
	}
}
