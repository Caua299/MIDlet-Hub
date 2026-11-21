package ru.playsoftware.j2meloader.crashes;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.auto.service.AutoService;

import org.acra.config.CoreConfiguration;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderFactory;

@AutoService(ReportSenderFactory.class)
public class LocalReportSenderFactory implements ReportSenderFactory {
	@NonNull
	@Override
	public ReportSender create(@NonNull Context context, @NonNull CoreConfiguration config) {
		return new LocalReportSender();
	}

	@Override
	public boolean enabled(@NonNull CoreConfiguration config) {
		return true;
	}
}
