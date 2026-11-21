/*
 * Copyright 2017-2026 Nikita Shakarun
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.playsoftware.j2meloader.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.playsoftware.j2meloader.applist.AppItem;
import ru.playsoftware.j2meloader.config.Config;

/**
 * Pontos de save: cópias do diretório de dados salvos (RMS) de um jogo.
 * Modificação do fork MIDlet Hub.
 */
public final class SaveSlots {
	private static final String NAME_FORMAT = "yyyyMMdd-HHmmss";

	private SaveSlots() {
	}

	public static File gameDataDir(AppItem item) {
		return new File(Config.getDataDir(), item.getPath());
	}

	private static File slotsRoot(AppItem item) {
		return new File(Config.getEmulatorDir() + "/saves/" + item.getPath());
	}

	public static boolean hasData(AppItem item) {
		String[] list = gameDataDir(item).list();
		return list != null && list.length > 0;
	}

	/** Pontos de save do jogo, do mais recente para o mais antigo. */
	public static List<File> list(AppItem item) {
		File[] dirs = slotsRoot(item).listFiles((FileFilter) f -> f.isDirectory());
		if (dirs == null) {
			return new ArrayList<>();
		}
		List<File> slots = new ArrayList<>(Arrays.asList(dirs));
		Collections.sort(slots, (a, b) -> b.getName().compareTo(a.getName()));
		return slots;
	}

	public static File create(AppItem item) throws IOException {
		String name = new SimpleDateFormat(NAME_FORMAT, Locale.US).format(new Date());
		File slot = new File(slotsRoot(item), name);
		copyDir(gameDataDir(item), slot);
		return slot;
	}

	/** Substitui os dados do jogo pelo ponto de save. Antes, guarda os dados atuais em um novo ponto. */
	public static void restore(AppItem item, File slot) throws IOException {
		File data = gameDataDir(item);
		if (hasData(item)) {
			create(item);
		}
		if (data.exists() && !FileUtils.deleteDirectory(data)) {
			throw new IOException("Can't clear " + data);
		}
		copyDir(slot, data);
	}

	public static void delete(File slot) {
		FileUtils.deleteDirectory(slot);
	}

	public static void deleteAll(AppItem item) {
		FileUtils.deleteDirectory(slotsRoot(item));
	}

	public static String label(File slot) {
		try {
			Date date = new SimpleDateFormat(NAME_FORMAT, Locale.US).parse(slot.getName());
			if (date != null) {
				return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(date);
			}
		} catch (ParseException ignored) {
		}
		return slot.getName();
	}

	private static void copyDir(File src, File dst) throws IOException {
		if (!dst.exists() && !dst.mkdirs()) {
			throw new IOException("Can't create " + dst);
		}
		File[] files = src.listFiles();
		if (files == null) {
			return;
		}
		for (File file : files) {
			File to = new File(dst, file.getName());
			if (file.isDirectory()) {
				copyDir(file, to);
			} else {
				FileUtils.copyFileUsingChannel(file, to);
			}
		}
	}
}
