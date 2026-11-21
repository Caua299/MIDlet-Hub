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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

import ru.playsoftware.j2meloader.config.Config;

/**
 * Tempo jogado por jogo, guardado em um arquivo simples (o jogo roda em outro processo,
 * então não usamos o banco de dados). Modificação do fork MIDlet Hub.
 */
public final class PlayTime {

	private PlayTime() {
	}

	private static File file(String dirName) {
		return new File(Config.getEmulatorDir() + "/playtime", dirName);
	}

	public static long get(String dirName) {
		try {
			File f = file(dirName);
			if (!f.isFile()) {
				return 0;
			}
			byte[] buf = new byte[32];
			int n;
			try (FileInputStream in = new FileInputStream(f)) {
				n = in.read(buf);
			}
			return n <= 0 ? 0 : Long.parseLong(new String(buf, 0, n, StandardCharsets.UTF_8).trim());
		} catch (Exception e) {
			return 0;
		}
	}

	public static void add(String dirName, long millis) {
		if (dirName == null || millis <= 0) {
			return;
		}
		try {
			File f = file(dirName);
			File parent = f.getParentFile();
			if (parent != null && !parent.exists() && !parent.mkdirs()) {
				return;
			}
			long total = get(dirName) + millis;
			try (FileOutputStream out = new FileOutputStream(f)) {
				out.write(Long.toString(total).getBytes(StandardCharsets.UTF_8));
			}
		} catch (Exception ignored) {
		}
	}

	public static void delete(String dirName) {
		try {
			file(dirName).delete();
		} catch (Exception ignored) {
		}
	}

	public static String format(long millis) {
		long minutes = millis / 60000;
		long hours = minutes / 60;
		if (hours > 0) {
			return hours + "h " + (minutes % 60) + "min";
		}
		return minutes + " min";
	}
}
